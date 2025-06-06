package co.edu.cue.loginjetpackcompose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import co.edu.cue.loginjetpackcompose.HomeScreen
import co.edu.cue.loginjetpackcompose.LoginScreen
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                onLoginSuccess = { email ->
                    navController.navigate(Home(email))
                }
            )
        }

        composable<Home> {
            val args = it.toRoute<Home>()
            HomeScreen(
                email = args.email,
                navigateBack = {
                    navController.navigate(Login) {
                        popUpTo<Login> { inclusive = true }
                    }
                }
            )
        }
    }
}