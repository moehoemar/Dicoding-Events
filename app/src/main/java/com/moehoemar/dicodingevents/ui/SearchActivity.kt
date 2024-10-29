package com.moehoemar.dicodingevents.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moehoemar.dicodingevents.data.remote.response.ListEventsItem
import com.moehoemar.dicodingevents.databinding.ActivitySearchBinding
import com.moehoemar.dicodingevents.ui.adapter.EventSearchAdapter
import com.moehoemar.dicodingevents.ui.factory.SearchModelFactory
import com.moehoemar.dicodingevents.ui.model.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchViewModel by viewModels<SearchViewModel>{
        SearchModelFactory.getInstance(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvEventSearch.layoutManager = layoutManager

        binding.tfSearch.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val query = binding.tfSearch.editText?.text.toString()
                searchViewModel.fetchSearchEvent(query)
                true
            } else {
                false
            }
        }

        searchViewModel.listEvent.observe(this) { event ->
            setEventDataSearch(event)
        }

        searchViewModel.errorMessage.observe(this) {
            it.getContentIfNotHandled()?.let {errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        searchViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }


    }

    private fun setEventDataSearch(listEvent: List<ListEventsItem>) {
        val adapter = EventSearchAdapter(onItemClick = { eventId -> navigateToDetail(eventId) })
        adapter.submitList(listEvent)
        binding.rvEventSearch.adapter = adapter
    }

    private fun navigateToDetail(eventId: Int) {
        val intent = Intent(this@SearchActivity, DetailActivity::class.java).apply {
            putExtra("EVENT_ID", eventId)
        }
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}