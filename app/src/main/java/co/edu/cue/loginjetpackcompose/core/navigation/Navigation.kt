package co.edu.cue.loginjetpackcompose.core.navigation


import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class Home(val email: String)
