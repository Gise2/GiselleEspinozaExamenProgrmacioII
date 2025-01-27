package com.giseespinozza.gastos.ui.theme.formulario

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.giseespinozza.gastos.viewmodel.MedicionViewModel
import java.util.*

@Composable
fun FormularioScreen(viewModel: MedicionViewModel, onNavigateBack: () -> Unit) {
    var tipoSeleccionado by remember { mutableStateOf("Agua") }
    var valor by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Grupo de RadioButtons para seleccionar el tipo
        Text("Medidor de:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RadioButtonWithLabel(
                label = "Agua",
                selected = tipoSeleccionado == "Agua",
                onClick = { tipoSeleccionado = "Agua" }
            )
            RadioButtonWithLabel(
                label = "Luz",
                selected = tipoSeleccionado == "Luz",
                onClick = { tipoSeleccionado = "Luz" }
            )
            RadioButtonWithLabel(
                label = "Gas",
                selected = tipoSeleccionado == "Gas",
                onClick = { tipoSeleccionado = "Gas" }
            )
        }

        // Campo para el valor
        TextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor (Ej: 1500)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        // Campo para la fecha con DatePicker
        FechaPicker { fecha -> fechaSeleccionada = fecha }

        // Mostrar mensaje de error
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Botón para registrar la medición
        Button(
            onClick = {
                val valorDouble = valor.toDoubleOrNull()
                if (valorDouble != null && fechaSeleccionada.isNotBlank()) {
                    viewModel.agregarMedicion(tipoSeleccionado, valorDouble, fechaSeleccionada)
                    onNavigateBack()
                } else {
                    errorMessage = when {
                        valorDouble == null -> "El valor debe ser un número válido"
                        fechaSeleccionada.isBlank() -> "Por favor, selecciona una fecha válida"
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

// Composable para RadioButton con etiqueta
@Composable
fun RadioButtonWithLabel(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(label)
    }
}

// Composable para seleccionar fecha usando DatePickerDialog
@Composable
fun FechaPicker(onFechaSeleccionada: (String) -> Unit) {
    val context = LocalContext.current
    val calendario = Calendar.getInstance()
    val año = calendario.get(Calendar.YEAR)
    val mes = calendario.get(Calendar.MONTH)
    val día = calendario.get(Calendar.DAY_OF_MONTH)

    var fechaSeleccionada by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            fechaSeleccionada = "$year-${String.format("%02d", month + 1)}-${String.format("%02d", dayOfMonth)}"
            onFechaSeleccionada(fechaSeleccionada)
        },
        año, mes, día
    )

    Column {
        TextField(
            value = fechaSeleccionada,
            onValueChange = {},
            label = { Text("Fecha (YYYY-MM-DD)") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { datePickerDialog.show() }) {
            Text("Seleccionar Fecha")
        }
    }
}
