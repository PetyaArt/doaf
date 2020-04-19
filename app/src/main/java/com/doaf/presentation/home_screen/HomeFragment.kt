package com.doaf.presentation.home_screen

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
import com.doaf.presentation.main_screen.MainActivity
import com.doaf.presentation.streams_screen.StreamsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_streams.*
import ru.terrakok.cicerone.Screen

class HomeFragment : Fragment(), ViewState<HomeViewState>, OnItemClickListener {

    private var presenter: HomePresenter? = HomePresenter(this)
    private val adapter: StreamsAdapter = StreamsAdapter(onItemClickListener = this)

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
        presenter?.listStreams(509538)

        recycler_streams.adapter = adapter
        recycler_streams.layoutManager = LinearLayoutManager(context)
        recycler_streams.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recycler_streams.layoutManager!!.itemCount;
                val lastVisibleItem = (recycler_streams.layoutManager!! as LinearLayoutManager).findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + 5) {
                    presenter?.nextListStreams(adapter.streams.pagination.cursor)
                }
            }
        })
    }

    override fun itemClick(position: Int) {
        (activity as MainActivity).bottom_sheet.visibility = View.GONE
        App.application.getRouter().navigateTo(Screens.StreamScreen)
    }

    override fun render(state: HomeViewState) {
        when(state) {
            is HomeViewState.DataState -> adapter.streams = state.data
            is HomeViewState.NextDateState -> {
                adapter.streams.data.addAll(state.data.data)
                adapter.streams.pagination.cursor = state.data.pagination.cursor
            }
            is HomeViewState.Error -> Unit
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter?.destroy()
        presenter = null
        super.onDestroy()
    }
}