package com.milkcocoa.info.shochu_club

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.milkcocoa.info.shochu_club.models.HandleNameAccountIdentifier
import com.milkcocoa.info.shochu_club.models.IdTokenAccountIdentifier
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
                    idToken =
                        IdTokenAccountIdentifier(
                            idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjBjYjQyNzQyYWU1OGY0ZGE0NjdiY2RhZWE0Yjk1YTI5ZmJhMGM1ZjkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc2hvY2h1IiwiYXVkIjoic2hvY2h1IiwiYXV0aF90aW1lIjoxNzIyMzI3Mjc0LCJ1c2VyX2lkIjoiaU9TWXBhRmVhRGZEYmNzNUlhRWNHNFZnSTlwMSIsInN1YiI6ImlPU1lwYUZlYURmRGJjczVJYUVjRzRWZ0k5cDEiLCJpYXQiOjE3MjIzMjcyNzQsImV4cCI6MTcyMjMzMDg3NCwiZW1haWwiOiJkZXZlbG9wZXJAbWlsa2NvY29hLmluZm8iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiZGV2ZWxvcGVyQG1pbGtjb2NvYS5pbmZvIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.XqHcLf9mmTSFTUGFD-rXgGqq6NzaxdCPtAnUqFEFwF10iNtgCABi0GtBGvHG5kKux7h8_6M0VoGggcvNtLGMT8Q2x29nDCRzrysy2F_BlvIGXIYyslXbdYJxnNDGT1ehmQaGRSTEi4nkti4cz8Uk3PjT96Oo6p5Eqfdm61UpH8HasjHb2400jBaFUW6k2qhmlioLN1jN6Kc_nGtC_fXHcKio2j1wThG-Wa_nVC2Vfjna7RHgbcebxs_jA-_sP9q_J8m5mD49sprfOge-z8De8XIEdFp8ypk1bNsxrskYzhbcQU5r8pZK67v_b9-Zz4mF0uB7FXCnoNPRfiGpqdH6pA",
                        ),
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
