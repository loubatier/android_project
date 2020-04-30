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
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModesFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var submodeAdapter: SubmodeAdapter

    private lateinit var spinner: MaterialSpinner
    private lateinit var recycler: RecyclerView

    private lateinit var modes: Array<String>
    private lateinit var baseContext: Context

    private val sourceRepository = Sourcepository()

    private val submodes = mutableListOf(
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

        ),
        listOf(
            Submode("France", ModeType.COUNTRY,"https://www.sortiraparis.com/images/80/1467/548346-coronavirus-le-point-sur-la-situation-en-france-ce-jeudi-19-mars.jpg", "fr"),
            Submode("États-Unis", ModeType.COUNTRY,"https://www.cartefinancement.com/wp-content/uploads/2019/05/non-residents-aux-etats-unis-ce-quil-faut-savoir-sur-la-fiscalite-immobiliere-1-1024x688.jpg", "us"),
            Submode("Corée du Sud", ModeType.COUNTRY,"https://geo.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Fgeo.2F2019.2F04.2F30.2Fb974d9c9-f2a5-453e-8cc7-635ce0bdef27.2Ejpeg/1120x630/background-color/ffffff/quality/90/coree-du-sud-nos-conseils-pratiques.jpg", "kr"),
            Submode("Portugal", ModeType.COUNTRY,"https://img-4.linternaute.com/o7wmqKffXpCl3CSTB2w5bvphWTQ=/1080x/smart/bc8297cd38f644ad9be3acfb4686a11a/ccmcms-linternaute/11011328.jpg", "pt")
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleScope.launch {
            getData()
        }
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

            var sourceList:MutableList<com.example.news.models.Submode> = mutableListOf()
            result.forEach {

                // CHEMIN DE L'IMAGE EN DUR CAR PAS PRÉSENT DANS L'OBJET SOURCE DE L'API
                sourceList.add(Submode(it.name,ModeType.SOURCE,"https://academiesciencesmoralesetpolitiques.files.wordpress.com/2018/08/s11.jpg",it.id))
            }
            submodes.set(1, sourceList)
            Log.d("Sources", submodes.toString())
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