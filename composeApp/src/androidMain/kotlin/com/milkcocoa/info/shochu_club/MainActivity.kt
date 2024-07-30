package com.milkcocoa.info.shochu_club

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.milkcocoa.info.shochu_club.models.HandleNameAccountIdentifier
import com.milkcocoa.info.shochu_club.services.AccountManagementService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.rpc.client.withService
import kotlinx.rpc.serialization.protobuf
import kotlinx.rpc.streamScoped
import kotlinx.rpc.transport.ktor.client.installRPC
import kotlinx.rpc.transport.ktor.client.rpc
import kotlinx.rpc.transport.ktor.client.rpcConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val rpcClient =
                HttpClient(CIO) {
                    installRPC()
                }.rpc {
                    url("ws://10.0.2.2:8080/account")

                    rpcConfig {
                        serialization {
                            protobuf()
                        }
                    }
                }

            streamScoped {
                rpcClient.withService<AccountManagementService>().checkAccountExistence(
                    HandleNameAccountIdentifier(handleName = "milkcocoa0902"),
                )
                rpcClient.withService<AccountManagementService>().signInWithEmail(
                    email = "",
                    password = "",
                )
                rpcClient.withService<AccountManagementService>().signin(HandleNameAccountIdentifier(handleName = "milkcocoa0902"))
                rpcClient.withService<AccountManagementService>().signup(HandleNameAccountIdentifier(handleName = "milkcocoa0902"))
                rpcClient.withService<AccountManagementService>().logout(HandleNameAccountIdentifier(handleName = "milkcocoa0902"))
                rpcClient.withService<AccountManagementService>().deleteAccount(
                    HandleNameAccountIdentifier(handleName = "milkcocoa0902"),
                    deleteAccountToken = "asgrfg",
                )
            }

//            streamScoped {
//                rpcClient.withService<AwesomeService>().getNews("KotlinBurg").collect { article ->
//                    println(article)
//                }
//            }
        }
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
