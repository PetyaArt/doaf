package com.doaf.presentation.stream_player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doaf.R
import com.doaf.presentation.main_screen.MainActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.util.Util.getUserAgent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player.*


class StreamFragment : Fragment() {

    companion object {

        private const val GAME_ID = "GAME_ID"

        fun newInstance(gameId: Int) = StreamFragment().apply {
            arguments = Bundle().apply {
                putInt(GAME_ID, gameId)
            }
        }
    }

    /*private var presenter: StreamsPresenter? = StreamsPresenter(this)
    private val adapter: StreamsAdapter = StreamsAdapter()
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context)
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val player = SimpleExoPlayer.Builder(context!!).build()
        stream_player.player = player
        val mediaSource = buildMediaSource(getString(R.string.url))
        player.prepare(mediaSource)
        /*presenter?.create()
        arguments?.let { bundle ->
            presenter?.listStreams(bundle.getInt(GAME_ID))
        }

        recycler_streams.adapter = adapter
        recycler_streams.layoutManager = layoutManager
        recycler_streams.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount;
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + 5) {
                    presenter?.nextListStreams(adapter.streams.pagination.cursor)
                }
            }
        })*/
    }

    fun buildMediaSource(url: String): MediaSource {
        val uri: Uri = Uri.parse(url)
        val dataSourceFactory: DataSource.Factory =
            DefaultHttpDataSourceFactory(getUserAgent(context!!, "text"))
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    override fun onDestroy() {
        (activity as MainActivity).bottom_sheet.visibility = View.VISIBLE
        /*presenter?.destroy()
        presenter = null*/
        super.onDestroy()
    }
}