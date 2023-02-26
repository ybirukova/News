package com.example.homework28.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework28.R
import com.example.homework28.domain.models.NewsData
import com.example.homework28.ui.utils.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel>()
    @Inject
    lateinit var networkConnection: NetworkConnection

    var itemClick: (NewsData) -> Unit = { news ->
        if (networkConnection.isNetworkAvailable()) {
            val address = Uri.parse(news.url)
            val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
            startActivity(openLinkIntent)
        } else openFragment(InfoFragment.newInstance(news.content))
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
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        viewModel.newsLiveData.observe(this) {
            val adapter = NewsAdapter(it, itemClick)
            recycler.adapter = adapter
            recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}