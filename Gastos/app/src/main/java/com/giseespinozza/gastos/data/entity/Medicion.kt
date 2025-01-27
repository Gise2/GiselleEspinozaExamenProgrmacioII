package com.giseespinozza.gastos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,   // Agua, Luz, o Gas
    val valor: Double,  // Valor registrado
    val fecha: String   // Fecha del registro
)
