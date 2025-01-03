package com.example.posttemplate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.posttemplate.ui.R
import com.example.posttemplate.ui.navigation.Route

@Composable
fun DrawerContent(
    currentDestination: String?,
    onNavigate: (String) -> Unit,
    onLogOut: () -> Unit

) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Text(
            text = "Navigation",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Image"
        )

        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem(
            label = "Home",
            isSelected = currentDestination == Route.Posts.route,
            onClick = { onNavigate(Route.Posts.route) }
        )
        DrawerItem(
            label = "Downloads",
            isSelected = currentDestination?.startsWith(Route.Downloads.route) == true,
            onClick = { onNavigate(Route.Downloads.route) }
        )

        DrawerItem(
            label = "Sign Out",
            iconResId = R.drawable.google_logo,
            isSelected = currentDestination?.startsWith(Route.Profile.route) == true,
            onClick = { onLogOut() }
        )
    }
}

@Composable
fun DrawerItem(
    label: String,
    isSelected: Boolean,
    iconResId: Int? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconResId?.let {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun DrawerContentPreview() {
    DrawerContent(
        currentDestination = Route.Posts.route,
        onNavigate = {},
        onLogOut = {}
    )
}
