package com.giseespinozza.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.giseespinozza.gastos.viewmodel.MedicionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FormularioScreen(viewModel: MedicionViewModel, onNavigateBack: () -> Unit) {
    var tipo by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Campo para el tipo de medición
        TextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo (Ej: Agua, Luz, Gas)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para el valor
        TextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor (Ej: 1500)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Campo para la fecha
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Mostrar mensaje de error
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error, // Usando Material 3
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Botón para registrar la medición
        Button(
            onClick = {
                // Validar entrada
                val valorDouble = valor.toDoubleOrNull()
                val fechaValida = validarFecha(fecha)

                if (valorDouble != null && tipo.isNotBlank() && fechaValida) {
                    viewModel.agregarMedicion(tipo, valorDouble, fecha)
                    onNavigateBack()
                } else {
                    errorMessage = when {
                        tipo.isBlank() -> " No puede estar vacío"
                        valorDouble == null -> "El valor debe ser un número válido"
                        !fechaValida -> "La fecha debe estar en formato YYYY-MM-DD"
                        else -> "Por favor, completa todos los campos correctamente"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Registrar medición")
        }
    }
}

// Función para validar el formato de la fecha
fun validarFecha(fecha: String): Boolean {
    return try {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formato.isLenient = false // Asegura que la fecha sea estricta (no permite valores como 2024-02-30)
        formato.parse(fecha)
        true
    } catch (e: Exception) {
        false
    }
}