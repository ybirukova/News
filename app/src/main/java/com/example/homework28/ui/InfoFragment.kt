package com.example.homework28.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homework28.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val content = view.findViewById<TextView>(R.id.tv_news_content)
        content.text = arguments?.getString(CONTENT_TEXT)
    }


    companion object {
        private val CONTENT_TEXT = "content"

        fun newInstance(content: String): InfoFragment {
            val fragment = InfoFragment()
            val args: Bundle = Bundle()
            args.putString(CONTENT_TEXT, content)
            fragment.arguments = args
            return fragment
        }
    }
}