package com.example.kubrick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GlassBlue = Color(0xB3303F42)
private val GlassBorder = Color(0x55FFFFFF)
private val SelectedBlue = Color(0x664D6385)

@Composable
fun GlassBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(GlassBlue)
            .border(1.dp, GlassBorder, CircleShape)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassTab(
            title = "Home",
            selected = selectedTab == 0,
            icon = Icons.Default.Home,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )

        GlassTab(
            title = "Watchlist",
            selected = selectedTab == 1,
            icon = Icons.Default.Bookmark,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GlassTab(
    title: String,
    selected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(
                if (selected) SelectedBlue else Color.Transparent
            )
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White
        )

        Spacer(Modifier.width(9.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}