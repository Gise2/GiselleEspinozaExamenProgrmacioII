package com.giseespinozza.gastos.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giseespinozza.gastos.ui.listado.ListadoScreen
import com.giseespinozza.gastos.ui.formulario.FormularioScreen
import com.giseespinozza.gastos.viewmodel.MedicionViewModel

@Composable
fun GastosApp(viewModel: MedicionViewModel) {
    // Inicializa el controlador de navegación
    val navController = rememberNavController()

    // Define el NavHost con las rutas
    NavHost(navController = navController, startDestination = "listado") {
        // Ruta para la pantalla de listado
        composable("listado") {
            ListadoScreen(viewModel = viewModel) {
                navController.navigate("formulario") // Navegar al formulario
            }
        }
        // Ruta para la pantalla del formulario
        composable("formulario") {
            FormularioScreen(viewModel = viewModel) {
                navController.popBackStack() // Volver al listado
            }
        }
    }
}
