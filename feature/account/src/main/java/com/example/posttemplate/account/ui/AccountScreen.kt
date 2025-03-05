package com.example.posttemplate.account.ui

import Account
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject


@Composable
fun AccountScreen(
    accountId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = koinInject(),
) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleIntent(AccountIntent.LoadAccount(accountId))
    }
    AccountScreenContent(
        state = state,
        onBack = onBack,
        onUpdate = { account ->
            viewModel.handleIntent(
                AccountIntent.UpdateAccount(account)
            )
        }
    )
}

@Composable
fun AccountScreenContent(
    state: AccountState,
    onBack: () -> Unit,
    onUpdate: (Account) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> ErrorMessage(message = state.errorMessage, onBack = onBack)
        else -> {
            state.account?.let { account ->

                var fullName by remember { mutableStateOf(account.fullName) }
                var phone by remember { mutableStateOf(account.phone) }
                var address by remember { mutableStateOf(account.address) }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = address,
                        onValueChange = { address = address },
                        label = { Text("Address") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = phone,
                        onValueChange = { phone = phone },
                        label = { Text("Phone") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = account.email,
                        onValueChange = {},
                        label = { Text("Email") },
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        onUpdate(
                            account.copy(
                                fullName = fullName,
                                phone = phone,
                                address = address
                            )
                        )
                    }) {
                        Text("Update")
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Go Back")
        }
    }
}

@Composable
fun LoadingIndicator() {
    // Implement a loading indicator
}
