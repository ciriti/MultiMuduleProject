package com.example.posttemplate.auth.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.posttemplate.auth.R
import com.example.posttemplate.ui.components.GoogleButton
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AuthenticationScreen(
    navigateToHome: () -> Unit,
    viewModel: AuthenticationViewModel = koinInject(),
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState()

    AuthenticationContent(
        loadingState = state.value.isLoading,
        onButtonClicked = {
            scope.launch {
                viewModel.handleIntent(AuthenticationIntent.Authenticate)
            }
        },
        navigateToHome = navigateToHome
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationContent(
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    navigateToHome: () -> Unit
) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(all = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .testTag("GoogleLogo"),
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google Logo"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.auth_title),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    modifier = Modifier.testTag("AuthTitle")
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    text = stringResource(id = R.string.auth_subtitle),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    modifier = Modifier.testTag("AuthSubtitle")
                )
            }
            Column(
                modifier = Modifier
                    .weight(weight = 2f)
                    .testTag("GoogleButtonContainer"),
                verticalArrangement = Arrangement.Bottom
            ) {
                GoogleButton(
                    modifier = Modifier.testTag("GoogleButton"),
                    loadingState = loadingState,
                    onClick = {
                        onButtonClicked()
                        navigateToHome()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthenticationScreen() {
    AuthenticationContent(
        loadingState = false,
        onButtonClicked = {},
        navigateToHome = {}
    )
}
