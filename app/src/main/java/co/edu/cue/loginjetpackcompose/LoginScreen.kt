package co.edu.cue.loginjetpackcompose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(onLoginSuccess:  (String) -> Unit) {

    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val onLogin = remember { mutableStateOf(true) }
    val dbFirebase = Firebase.firestore
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bienvenido", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                 value = username.value,
                 onValueChange = { if(it.length <= 25){
                     username.value = it
                 }
                 },
                 placeholder = { Text("Nombre de usuario") },
                 modifier = Modifier.fillMaxWidth(0.8f),
                 keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
             )

             Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { if(it.length <= 15){
                    password.value = it
                }
                },
                placeholder = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility.value){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisibility.value){
                                Icons.Default.Check
                            } else{
                                Icons.Default.Close
                            },
                            contentDescription = ""
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 3.dp).height(70.dp),
                onClick = {
                    if (username.value.isBlank() || password.value.isBlank()){
                        Toast.makeText(context,"Por favor ingrese sus credenciales", Toast.LENGTH_SHORT).show()
                    } else{
                        if (onLogin.value){
                            dbFirebase.collection("usuarios").
                                document(username.value).
                                get().
                                    addOnSuccessListener { usuario ->
                                        val pwd = usuario.get("password")
                                        if (pwd!!.equals(password.value)){
                                            Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show()
                                            onLoginSuccess(username.value)
                                        } else {
                                            Toast.makeText(context,"usuario no existe", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                        }
                        onLogin.value = true
                    }
                }
            ) {
                Text(text = "Iniciar Sesión")
            }
        }
    }
}
