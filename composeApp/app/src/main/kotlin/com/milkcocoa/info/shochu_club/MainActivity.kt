package com.milkcocoa.info.shochu_club

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.newrelic.agent.android.NewRelic
import kotlin.uuid.ExperimentalUuidApi

class MainActivity : ComponentActivity() {
    private val signInLauncher =
        registerForActivityResult(
            FirebaseAuthUIActivityResultContract(),
        ) { res ->
            this.onSignInResult(res)
        }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NewRelic.withApplicationToken(BuildConfig.NewRelicApiKey).start(this.applicationContext)



//        CoroutineScope(Dispatchers.IO).launch {
//            val accountManagementClient = accountManagementRpcClient("ws://10.0.2.2:54676/account")
//
//            streamScoped {
//                kotlin.runCatching {
//                    accountManagementClient.promoteProvisionedAccount(
//                        email = "test@aaa.com",
//                        passwordRaw = "aA3av_3g86486_iyf5A",
//                        confirmationCode = "93440242"
//                    )
////                    val anonymousAccount = accountManagementClient.signInAnonymously()
////                    when(anonymousAccount){
////                        is Result.Success -> {
////                            accountManagementClient.provisioningAnonymousAccount(
////                                systemUid = SystemUid(anonymousAccount.value.shochuClubUserId.value),
////                                email = "test@aaa.com",
////                                passwordRaw = "aA3av_3g86486_iyf5A",
////                                authProvider = 0
////                            )
////                        }
////                        else -> {}
////                    }
////                    println(accountManagementClient.signInAnonymously())
//
//                }.getOrElse { println(it) }
//            }
//        }
        setContent {
            App()
        }
    }
}

// @Preview
// @Composable
// fun AppAndroidPreview() {
//    App()
// }
