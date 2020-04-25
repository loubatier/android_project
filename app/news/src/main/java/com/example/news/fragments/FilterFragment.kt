package com.example.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.news.R

class FilterFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var spinner: Spinner
    private lateinit var modes: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modes = resources.getStringArray(R.array.modes)
        spinner = view.findViewById(R.id.mode_spinner)

        adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, modes)

        spinner.adapter = adapter

        val baseContext = view.context

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(baseContext, "Vous n'avez rien selectionné", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(baseContext, "Vous avez selectionné ${modes[position]}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bindModeRecyclerView() {

    }

}