package com.damjms.iniciosesion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.damjms.iniciosesion.ui.theme.InicioSesionTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InicioSesionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    var usuario by rememberSaveable { mutableStateOf("") }
    var contraseña by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    //variable para controlar el cierre de la app
    val activity = MainActivity()

    var numIntentos = 3

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(150.dp)
            )
            Text(text = "INICIO DE SESION")
        }
        OutlinedTextField(
            value = usuario,
            onValueChange = {usuario = it},
            label = {Text(text = "Introduce nombre usuario")}
        )
        OutlinedTextField(
            value = contraseña,
            onValueChange = {contraseña = it},
            label = {Text(text = "Introduce contraseña")}
        )
        //al pulsar el boton sale el mensaje de correcto/incorrecto con un toast
        Button(onClick = {
            //si ambas son correctas saca mensaje de acierto
            if(usuario == "admin" && contraseña == "admin"){
                Toast.makeText(context, "CORRECTO", Toast.LENGTH_SHORT).show()
                //si acierta reinicia el numero de intentos
                numIntentos = 3
            }

            if(usuario.equals("") && contraseña.equals("")){
                Toast.makeText(context, "Ambos campos estan vacios", Toast.LENGTH_SHORT).show()
            }

            //si el usuario es incorrecto o esta vacio saca mensaje de error en el usuario y lo mismo para la contraseña
            if(usuario != "admin" || usuario.equals("")){
                Toast.makeText(context, "Error en el usuario. Incorrecto o vacio", Toast.LENGTH_SHORT).show()
            }else if(contraseña != "admin" || contraseña.equals("")){
                Toast.makeText(context, "Error en la contraseña. Incorrecto o vacio", Toast.LENGTH_SHORT).show()
                //si al introducir de forma incorrecta 3 veces la contraseña entonces saca un mensaje y cierra la app
                numIntentos--
                if(numIntentos.equals(0)){
                    Toast.makeText(context, "El numero de intentos ha sido superado", Toast.LENGTH_SHORT).show()
                    activity.finish()
                    exitProcess(0)
                }
            }

        }) {
            Text(text = "VALIDAR")
        }
    }
}