package com.example.taller4_142514

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentDetalles : Fragment() {

    private val selectedNames = mutableListOf<String>() // Lista de nombres seleccionados
    private lateinit var adapter: SimpleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalles, container, false)

        // Configurar RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDetalles)
        adapter = SimpleAdapter(selectedNames) {}
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    // Método para actualizar la lista de nombres seleccionados
    fun addSelectedName(name: String) {
        selectedNames.add(name)
        adapter.notifyDataSetChanged() // Actualizar el RecyclerView
    }
}
