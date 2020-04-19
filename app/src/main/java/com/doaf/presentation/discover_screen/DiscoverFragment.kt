package com.doaf.presentation.discover_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doaf.R
import com.doaf.presentation.OnItemClickListener
import com.doaf.presentation.ViewState
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment(), ViewState<DiscoverViewState>, OnItemClickListener {

    private var presenter: DiscoverPresenter? = DiscoverPresenter(this)
    private val adapter: DiscoverAdapter = DiscoverAdapter(onItemClickListener = this)
    private var layoutManager: GridLayoutManager? = GridLayoutManager(context, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.create()
        presenter?.listGames()

        recycler_discover.adapter = adapter
        recycler_discover.layoutManager = GridLayoutManager(context, 2)
        recycler_discover.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager!!.itemCount;
                val lastVisibleItem = layoutManager!!.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + 5) {
                    presenter?.nextListGames(adapter.games.pagination.cursor)
                }
            }
        })

    }

    override fun itemClick(position: Int) {
        presenter?.clickGame(adapter.games.data[position].id.toInt())
    }

    override fun render(state: DiscoverViewState) {
        when(state) {
            is DiscoverViewState.DataState -> adapter.games = state.data
            is DiscoverViewState.NextDateState -> {
                adapter.games.data.addAll(state.data.data)
                adapter.games.pagination.cursor = state.data.pagination.cursor
            }
            is DiscoverViewState.Error -> Unit
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        layoutManager = null
        presenter?.destroy()
        presenter = null
        super.onDestroy()
    }
}