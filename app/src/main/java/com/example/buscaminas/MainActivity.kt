package com.example.buscaminas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buscaminas.ui.theme.BuscaMinasTheme
import java.time.format.TextStyle
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuscaMinasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        titulo()
                        buscaMinas("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun buscaMinas(name: String, modifier: Modifier = Modifier) {
    val columnas = 6
    val filas = 10

    val estadoBotones = remember {
        List(filas*columnas) {
            mutableStateOf(true)
        }
    }

    val minas = remember {
        List(filas*columnas) {
            mutableStateOf(asignaMina())
        }
    }

    val verAlert = remember {
        mutableStateOf(false)
    }

    if (verAlert.value)
    {
        AlertDialog(
            onDismissRequest = {verAlert.value = false},
            confirmButton = {
                Button(onClick = {
                    verAlert.value = false
                    estadoBotones.forEach {item -> item.value = true}
                    minas.forEach {item -> item.value = asignaMina()}
                }) {
                    Text(text = "Otra partida")
                }
                Button(onClick = { verAlert.value = false }) {
                    Text(text = "Seguir")
                }
            },
            title = {
                Text(text = "Perdiste")
            },
            text = {
                Text(text = "Presiona otra partida para juego nuevo o seguir para continuar el juego")
            })
    }
    
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween){
        for (i in 0 until  filas)
        {
            Row(modifier = Modifier
                .fillMaxSize()
                .weight(.1f)
                .padding(3.dp)){
                for(j in 0 until  columnas)
                {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .weight(.1f)
                        .padding(2.5.dp)) {

                        val index = i * columnas + j
                        Button(onClick = { estadoBotones[index].value = false
                                            if (minas[index].value)
                                            {
                                                Log.d("perdiste", "Encontraste Mina")
                                                verAlert.value = true
                                            }
                                         },
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(.1f),
                            enabled = estadoBotones[index].value) {
                            Text(text = "0")
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
fun titulo(){
    Text(text = "Buena suerte, trata de no explotar", modifier = Modifier.fillMaxWidth(),
        fontSize = 24.sp, textAlign = TextAlign.Center)
}

fun asignaMina(): Boolean
{
    val random = java.util.Random()
    val numRandm = random.nextInt(11)
    if(numRandm > 7)
        return true
    return false
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuscaMinasTheme {
        Column {
            titulo()
            buscaMinas("Android")
        }
    }
}