package com.doaf.presentation.streams_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doaf.R
import com.doaf.app.App
import com.doaf.presentation.OnItemClickListener
import com.doaf.presentation.Screens
import com.doaf.presentation.ViewState
import kotlinx.android.synthetic.main.fragment_streams.*

class StreamsFragment : Fragment(), ViewState<StreamsViewState>, OnItemClickListener {

    companion object{

        private const val GAME_ID = "GAME_ID"

        fun newInstance(gameId: Int) =  StreamsFragment().apply {
            arguments = Bundle().apply {
                putInt(GAME_ID, gameId)
            }
        }
    }

    private var presenter: StreamsPresenter? = StreamsPresenter(this)
    private val adapter: StreamsAdapter = StreamsAdapter(onItemClickListener = this)
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_streams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.create()
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
        })
    }

    override fun itemClick(position: Int) {
        App.application.getRouter().navigateTo(Screens.StreamScreen(adapter.streams.data[position].userName))
    }

    override fun render(state: StreamsViewState) {
        when(state) {
            is StreamsViewState.DataState -> adapter.streams = state.data
            is StreamsViewState.NextDateState -> {
                adapter.streams.data.addAll(state.data.data)
                adapter.streams.pagination.cursor = state.data.pagination.cursor
            }
            is StreamsViewState.Error -> Unit
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter?.destroy()
        presenter = null
        super.onDestroy()
    }

}