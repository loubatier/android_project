package com.example.news.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.materialspinner.MaterialSpinner
import com.example.network.repositories.Sourcepository
import com.example.news.R
import com.example.news.adapters.ArticleAdapter
import com.example.news.adapters.SubmodeAdapter
import com.example.news.change
import com.example.news.models.ModeType
import com.example.news.models.Submode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModesFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var submodeAdapter: SubmodeAdapter

    private lateinit var spinner: MaterialSpinner
    private lateinit var recycler: RecyclerView

    private lateinit var modes: Array<String>
    private lateinit var baseContext: Context

    private val sourceRepository = Sourcepository()

    private val submodes = listOf(
        listOf(
            Submode("Affaires", ModeType.CATEGORY, "https://www.martinjutras.com/images/pourquoi-faire-affaire-avec-moi.jpg", "business"),
            Submode("Divertissement", ModeType.CATEGORY, "https://www.hollydays.fr/wp-content/uploads/2019/09/distance-tv-canape-e1567513422653.jpg", "entertainment"),
            Submode("Général", ModeType.CATEGORY, "https://www.francetvinfo.fr/image/75rsu773m-4eb4/1200/450/20723447.jpg","general"),
            Submode("Santé", ModeType.CATEGORY, "https://www.dw.com/image/51087401_303.jpg", "health"),
            Submode("Sciences", ModeType.CATEGORY, "https://lh3.googleusercontent.com/proxy/zTe_qSimYK7Qx-u9To2erPS0WH50zrXBvQtT-2Bgyn8hdX4kQXtyepMYLVttyEr2nfSs72lqPJATNvxhcHWYDw", "science"),
            Submode("Sport", ModeType.CATEGORY, "https://www.arcueil.fr/wp-content/uploads/2018/04/sports-arcueil.jpg", "sports"),
            Submode("Technologie", ModeType.CATEGORY, "https://airbus-h.assetsadobe2.com/is/image/content/dam/corporate-topics/innovation/Innovation-future-technology.jpg?wid=1920&fit=fit,1&qlt=85,0", "technology")
        ),
        listOf(
            //TODO : construire le tableau a partir des sources récupérées en ligne
            Submode("source1", ModeType.SOURCE,"yikes", ""),
            Submode("source2", ModeType.SOURCE,"yikes", ""),
            Submode("source3", ModeType.SOURCE,"yikes", ""),
            Submode("source4", ModeType.SOURCE,"yikes", "")
        ),
        listOf(
            Submode("France", ModeType.COUNTRY,"fr", ""),
            Submode("États-Unis", ModeType.COUNTRY,"us", ""),
            Submode("Corée du Sud", ModeType.COUNTRY,"kr", ""),
            Submode("Portugal", ModeType.COUNTRY,"pt", "")
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseContext = view.context

        // ---------- SETUP SPINNER DES MODES
        modes = resources.getStringArray(R.array.modes)
        spinner = view.findViewById(R.id.material_spinner)
        setupModesSpinner()

        // ---------- SETUP RECYCLER DES SUBMODES
        recycler = view.findViewById(R.id.submodes_recycler_view)

    }

    // ------------------------- RETRIEVE SOURCE LIST

    private suspend fun getData() {
        withContext(Dispatchers.IO) {
            var result = sourceRepository.list()

            bindData(result)
        }
    }

    private suspend fun bindData(result: List<com.example.network.models.Source>) {
        withContext(Dispatchers.Main) {
            Log.d("Articles", result.toString())
        }
    }

    // ------------------------- RECYCLER

    private fun bindSubmodeRecyclerView(submodes: List<Submode>) {
        submodeAdapter = SubmodeAdapter(submodes) {
            activity?.change(ArticlesFragment.newInstance(it.type, it.queryUrl))
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = submodeAdapter
    }

    // ------------------------- SPINNER

    private fun setupModesSpinner() {
        adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, modes)
        spinner.setAdapter(adapter)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.getSpinner().onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(baseContext, "Vous n'avez rien selectionné", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0){
                    //Toast.makeText(baseContext, "Vous avez selectionné ${modes[position]}", Toast.LENGTH_LONG).show()
                    bindSubmodeRecyclerView(submodes[position - 1])
                }

            }
        }

        spinner.setError("Please select mode")
        spinner.setLabel("Mode")
        spinner.setErrorEnabled(false)
    }

}