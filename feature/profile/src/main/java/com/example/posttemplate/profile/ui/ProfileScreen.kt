package com.example.posttemplate.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posttemplate.profile.domain.model.Address
import com.example.posttemplate.profile.domain.model.Company
import com.example.posttemplate.profile.domain.model.User
import com.example.posttemplate.ui.components.LoadingIndicator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(
    userId: Int,
    onBack: () -> Unit,
    viewModel: ProfileViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ProfileIntent.LoadProfile(userId))
    }

    ProfileScreenContent(
        state = state,
        onBack = onBack
    )

}

@Composable
fun ProfileScreenContent(
    state: ProfileState,
    onBack: () -> Unit
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> ErrorMessage(state.errorMessage, onBack)
        else -> {
            state.user?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    SelectionContainer {
                        Text(
                            text = "Email: ${user.email}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = "Address",
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = TextAlign.Center
                    )
                    AddressSection(user)

                    ContactSection(user)

                    CompanySection(user)
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
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
fun AddressSection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Street: ${user.address?.street ?: "N/A"}")
            Text(text = "Suite: ${user.address?.suite ?: "N/A"}")
            Text(text = "City: ${user.address?.city ?: "N/A"}")
            Text(text = "Zipcode: ${user.address?.zipcode ?: "N/A"}")
        }
    }
}

@Composable
fun ContactSection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Phone: ${user.phone}")
            Text(text = "Website: ${user.website}")
        }
    }
}

@Composable
fun CompanySection(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Company: ${user.company?.name ?: "N/A"}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = "CatchPhrase: ${user.company?.catchPhrase ?: "N/A"}")
            Text(text = "BS: ${user.company?.bs ?: "N/A"}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    MaterialTheme {
        ProfileScreenContent(
            state = ProfileState(
                isLoading = false,
                user = User(
                    id = 1,
                    fullName = "Leanne Graham",
                    email = "Sincere@april.biz",
                    address = Address(
                        street = "Kulas Light",
                        suite = "Apt. 556",
                        city = "Gwenborough",
                        zipcode = "92998-3874"
                    ),
                    phone = "1-770-736-8031 x56442",
                    website = "hildegard.org",
                    company = Company(
                        name = "Romaguera-Crona",
                        catchPhrase = "Multi-layered client-server neural-net",
                        bs = "harness real-time e-markets"
                    )
                )
            ),
            onBack = { /* Handle back */ }
        )
    }
}
