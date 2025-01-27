package com.giseespinozza.gastos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.giseespinozza.gastos.data.entity.Medicion

@Dao
interface MedicionDao {
    @Insert
    suspend fun insertarMedicion(medicion: Medicion)

    @Query("SELECT * FROM mediciones")
    fun obtenerMediciones(): LiveData<List<Medicion>>
}
