package com.example.news.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.network.repositories.Articlepository
import com.example.news.R
import com.example.news.adapters.ArticleAdapter
import com.example.news.models.ModeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesFragment : Fragment() {
    private val repository = Articlepository()

    private lateinit var adapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView

    private val query: String by lazy {
        arguments?.getString(ARGS_QUERY) ?: ""
    }
    private val type: ModeType by lazy {
        arguments?.getParcelable(ARGS_TYPE) ?: ModeType.CATEGORY
    }

    companion object {
        const val ARGS_QUERY = "ARGS_QUERY"
        const val ARGS_TYPE = "ARGS_TYPE"

        fun newInstance(type:ModeType,query: String):ArticlesFragment {
            return ArticlesFragment().apply {

                arguments = bundleOf(
                    ARGS_QUERY to query,
                    ARGS_TYPE to type)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articles_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleScope.launch {
            getData()
        }
    }

    // ------------------------- RETRIEVE ARTICLE LIST

    private suspend fun getData() {
        withContext(Dispatchers.IO) {
            var result = repository.list(query)

            when(type){
                ModeType.CATEGORY -> {
                    result = repository.list(query)
                }
                ModeType.COUNTRY -> {
                    result = repository.countryList(query)
                }
                ModeType.SOURCE -> {
                    result = repository.sourceList(query)
                }
            }
            bindData(result)
        }
    }

    private suspend fun bindData(result: List<com.example.network.models.Article>) {
        withContext(Dispatchers.Main) {
            //afficher les données dans le recycler

            Log.d("Articles $query", result.toString())

            recyclerView = activity!!.findViewById<RecyclerView>(R.id.articles_recycler_view)

            adapter = ArticleAdapter(result) {
                val intent = Intent(android.content.Intent.ACTION_VIEW)
                intent.data = Uri.parse(it.url)
                startActivity(intent)
            }

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }
}