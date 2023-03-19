package com.example.homework28.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ViewModelFactory
import com.example.domain.models.NewsData
import com.example.feature.SecondActivity
import com.example.homework28.NewsApp
import com.example.homework28.databinding.ActivityMainBinding
import com.example.ui_kit.NewsAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: NewsViewModel by viewModels { factory }
    private lateinit var binding: ActivityMainBinding

    var itemClick: (NewsData) -> Unit = { news ->
        viewModel.onClick(news.url)
        viewModel.openNewsLiveData.observe(this) {
            if (it != "") {
                val address = Uri.parse(it)
                val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
                startActivity(openLinkIntent)
            } else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as NewsApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getNewsList()

        val recycler = binding.rvNews
        val progressBar = binding.customProgressBar
        val set = AnimationUtils.loadAnimation(this, com.example.homework28.R.anim.rotate)
        set.fillAfter = true

        binding.buttonSearch.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        viewModel.loadingLiveData.observe(this) { show ->
            if (show) {
                progressBar.startAnimation(set)
            } else {
                progressBar.clearAnimation()
            }
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
        ContextCompat.getDrawable(this, com.example.ui_kit.R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }
}