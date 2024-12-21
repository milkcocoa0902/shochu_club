package com.milkcocoa.info.shochu_club

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milkcocoa.info.shochu_club.net.rpc.accountManagementRpcClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// @Composable
// @Preview
// fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
// }

@Composable
@Preview
fun App(){
    val coroutineScope = rememberCoroutineScope()
    val processing = remember { mutableStateOf(false) }

    LaunchedEffect(processing.value) {
        if(processing.value){
            val accountManagementClient = accountManagementRpcClient("ws://10.0.2.2:54676/account")

            val a = accountManagementClient.signInAnonymously()
            println(a)

            delay(5000)
            processing.value = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            modifier = Modifier.padding(32.dp),
            onClick = {
                coroutineScope.launch {
                    processing.value = true
                }
            }
        ) {
            if(processing.value){
                CircularProgressIndicator()
            }else{
                Text(
                    text = "匿名で開始"
                )
            }
        }
    }
}