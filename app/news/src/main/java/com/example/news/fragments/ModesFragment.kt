package com.example.news.fragments

import android.content.Context
import android.os.Bundle
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
import com.example.news.R
import com.example.news.adapters.SubmodeAdapter
import com.example.news.change
import com.example.news.models.ModeType
import com.example.news.models.Submode

class ModesFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var submodeAdapter: SubmodeAdapter

    private lateinit var spinner: MaterialSpinner
    private lateinit var recycler: RecyclerView

    private lateinit var modes: Array<String>
    private lateinit var baseContext: Context

    private val submodes = listOf(
        listOf(
            Submode("business",ModeType.CATEGORY,"business"),
            Submode("entertainment",ModeType.CATEGORY,"entertainment"),
            Submode("general",ModeType.CATEGORY,"general"),
            Submode("health",ModeType.CATEGORY,"health"),
            Submode("science",ModeType.CATEGORY,"science"),
            Submode("sports",ModeType.CATEGORY,"sports"),
            Submode("technology",ModeType.CATEGORY,"technology")
        ),
        listOf(
            //TODO : construire le tableau a partir des sources récupérées en ligne
            Submode("source1",ModeType.SOURCE,"yikes"),
            Submode("source2",ModeType.SOURCE,"yikes"),
            Submode("source3",ModeType.SOURCE,"yikes"),
            Submode("source4",ModeType.SOURCE,"yikes")),
        listOf(
            Submode("france",ModeType.COUNTRY,"fr"),
            Submode("United States",ModeType.COUNTRY,"us"),
            Submode("South korea",ModeType.COUNTRY,"kr"),
            Submode("Portugal",ModeType.COUNTRY,"pt"))

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
        recycler = view.findViewById(R.id.submode_recycler_view)



    }

    private fun bindSubmodeRecyclerView(submodes: List<Submode>) {
        submodeAdapter = SubmodeAdapter(submodes) {
            activity?.change(ArticlesFragment.newInstance(it.type, it.queryUrl))
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = submodeAdapter
    }

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