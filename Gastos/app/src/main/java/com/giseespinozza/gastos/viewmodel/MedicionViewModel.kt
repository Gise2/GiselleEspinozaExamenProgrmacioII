package com.giseespinozza.gastos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.giseespinozza.gastos.data.database.AppDatabase
import com.giseespinozza.gastos.data.entity.Medicion
import kotlinx.coroutines.launch
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giseespinozza.gastos.viewmodel.MedicionViewModel




class MedicionViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).medicionDao()
    val mediciones: LiveData<List<Medicion>> = dao.obtenerMediciones()

    fun agregarMedicion(tipo: String, valor: Double, fecha: String) {
        viewModelScope.launch {
            dao.insertarMedicion(Medicion(tipo = tipo, valor = valor, fecha = fecha))

        }
    }
}
