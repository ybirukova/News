package com.example.homework28.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework28.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel>()

    val itemClick: (String) -> Unit = {
        val address = Uri.parse(it)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        startActivity(openLinkIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getNewsList()

        val recycler = findViewById<RecyclerView>(R.id.rv_news)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        viewModel.loadingLiveData.observe(this) { show ->
            progressBar.isVisible = show
            recycler.isVisible = show.not()
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.newsLiveData.observe(this) {
            val adapter = NewsAdapter(it, itemClick)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.rv_divider))
        recycler.addItemDecoration(dividerItemDecoration)
    }
}