package com.isaac.gymtracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.isaac.gymtracker.viewmodel.ChatViewModelFactory
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isaac.gymtracker.data.model.Mensaje // Importar tu data class Mensaje
import com.isaac.gymtracker.viewmodel.ChatViewModel // Importar tu ViewModel
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    factory: ChatViewModelFactory
) {
    // ⭐️ USAMOS EL FACTORY PARA CREAR EL VIEWMODEL
    val viewModel: ChatViewModel = viewModel(factory = factory)

    // ⭐️ RECOGE EL ESTADO 'mensajes'
    val mensajes by viewModel.mensajes.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    // Detecta nuevos mensajes para hacer scroll
    LaunchedEffect(mensajes.size) {
        if (mensajes.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(0) // Desplazar al último mensaje (ya que reverseLayout=true)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Asistente Gemini (IA)") }) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                state = listState,
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
            ) {
                // Iteramos sobre la lista de Mensajes (del ViewModel)
                items(mensajes.reversed()) { mensaje ->
                    MensajeBurbuja(mensaje = mensaje)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Escribe tu pregunta sobre rutinas...") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (inputText.isNotBlank()) {
                                viewModel.enviarMensaje(inputText) // ⭐️ LLAMADA CORREGIDA
                                inputText = ""
                                keyboardController?.hide()
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.enviarMensaje(inputText) // ⭐️ LLAMADA CORREGIDA
                            inputText = ""
                            keyboardController?.hide()
                        }
                    },
                    enabled = inputText.isNotBlank()
                ) {
                    Text("Enviar")
                }
            }
        }
    }
}

@Composable
fun MensajeBurbuja(mensaje: Mensaje) { // ⭐️ TIPO DE DATO CORREGIDO
    val esUsuario = mensaje.esUsuario // ⭐️ CAMPO CORREGIDO

    val colorBurbuja = if (esUsuario) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (esUsuario) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (esUsuario) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = colorBurbuja),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            // ⭐️ Lógica de "Escribiendo..." usando tu campo 'texto'
            val textoMostrar = if (mensaje.texto == "...") "Escribiendo..." else mensaje.texto

            Text(
                text = textoMostrar,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}