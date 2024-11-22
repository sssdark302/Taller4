package com.example.taller4_142514

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taller4_142514.DatabaseHelper
import com.example.taller4_142514.R

class FragmentLista : Fragment() {

    interface OnItemSelectedListener {
        fun onItemSelected(item: String)
    }

    private var listener: OnItemSelectedListener? = null
    private lateinit var dbHelper: DatabaseHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnItemSelectedListener")
        }
        dbHelper = DatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Configuración del RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SimpleAdapter(getUserNames()) { selectedName ->
            listener?.onItemSelected(selectedName)
        }

        return view
    }

    // Método para obtener los nombres desde la base de datos
    private fun getUserNames(): List<String> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("users", arrayOf("name"), null, null, null, null, null)
        val userNames = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                userNames.add(name)
            }
        }
        cursor.close()
        return userNames
    }
}
