package com.example.taller4_142514

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.FragmentDetalles
import com.example.taller1.FragmentLista

class FragmentsActivity : AppCompatActivity(), FragmentLista.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)

        // Cargar FragmentLista
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerLista, FragmentLista())
            .commit()

        // Cargar FragmentDetalles
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerDetalles, FragmentDetalles())
            .commit()
    }

    override fun onItemSelected(item: String) {
        // Buscar el fragmento y agregar el nombre seleccionado a la lista
        val fragmentDetalles =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerDetalles) as? FragmentDetalles
        fragmentDetalles?.addSelectedName(item) // Reemplazar setDetailText por addSelectedName
    }
}
