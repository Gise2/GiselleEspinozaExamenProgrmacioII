package com.giseespinozza.gastos.ui.theme.Listado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giseespinozza.gastos.R
import com.giseespinozza.gastos.viewmodel.MedicionViewModel

@Composable
fun ListadoScreen(viewModel: MedicionViewModel, onNavigateToFormulario: () -> Unit) {
    // Observa las mediciones desde el ViewModel
    val mediciones = viewModel.mediciones.observeAsState(initial = emptyList())

    // Estructura principal de la pantalla
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToFormulario) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Respeta el espacio alrededor de Scaffold
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre elementos
        ) {
            // Itera sobre la lista de mediciones
            items(mediciones.value ?: emptyList()) { medicion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Espacio lateral para las tarjetas
                    elevation = 4.dp // Sombra en las tarjetas
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Margen interno de la tarjeta
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Ícono según el tipo de medición
                        Icon(
                            painter = painterResource(
                                id = when (medicion.tipo.lowercase()) {
                                    "agua" -> R.drawable.ic_water
                                    "luz" -> R.drawable.ic_electricity
                                    "gas" -> R.drawable.ic_gas
                                    else -> R.drawable.ic_placeholder
                                }
                            ),
                            contentDescription = "Ícono ${medicion.tipo}",
                            modifier = Modifier.size(40.dp) // Tamaño del ícono
                        )

                        // Información de la medición
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa el espacio restante después del ícono
                                .padding(start = 8.dp) // Espacio entre ícono y texto
                        ) {
                            Text(
                                text = "${medicion.tipo}: ${medicion.valor}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = medicion.fecha,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
