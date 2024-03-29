package com.example.feature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ViewModelFactory
import com.example.core.findDependencies
import com.example.domain.models.NewsData
import com.example.feature.databinding.ActivitySecondBinding
import com.example.feature.di.DaggerFeatureComponent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SecondViewModel by viewModels { factory }
    private lateinit var binding: ActivitySecondBinding

    var itemClick: (NewsData) -> Unit = { news ->
        val address = Uri.parse(news.url)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        startActivity(openLinkIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFeatureComponent.factory().create(findDependencies()).inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = binding.editText
        val recycler = binding.rvSecond
        val nothingIsFoundText = binding.tvNothingIsFound

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                viewModel.setQuery(input)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s == ""){
                    val input = s.toString()
                    viewModel.setQuery(input)
                }
            }
        })

        lifecycleScope.launch {
            viewModel.getResult()
                .flowWithLifecycle(this@SecondActivity.lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect {
                    if (it != null) {
                        recycler.isVisible = true
                        nothingIsFoundText.isVisible = false
                        val adapter = com.example.ui_kit.NewsAdapter(it, itemClick)
                        recycler.adapter = adapter
                        recycler.layoutManager =
                            LinearLayoutManager(
                                this@SecondActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                    } else {
                        recycler.isVisible = false
                        nothingIsFoundText.isVisible = true
                    }
                }
        }

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ContextCompat.getDrawable(this, com.example.ui_kit.R.drawable.rv_divider)
            ?.let()
            { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }
}