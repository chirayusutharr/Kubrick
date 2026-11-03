package com.example.kubrick.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip


@Composable
fun GlassBottomBar(
    selectedTab: Int,
    backdrop: LayerBackdrop,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(58.dp)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(78.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { shape },
                effects = {
                    vibrancy()
                    blur(2.dp.toPx())
                    lens(16.dp.toPx(), 32.dp.toPx())
                },
                onDrawSurface = {
                    drawRect(Color.DarkGray.copy(alpha = 0.12f))
                }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        val tabWidth = maxWidth / 3
        val indicatorWidth = tabWidth * 0.72f

        val indicatorOffset by animateDpAsState(
            targetValue = (tabWidth - indicatorWidth) / 2 +
                    (tabWidth * selectedTab),
            animationSpec = spring(
                dampingRatio = 0.65f,
                stiffness = 350f
            ),
            label = "Tab indicator"
        )

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(indicatorOffset.roundToPx(), 0)
                }
                .width(indicatorWidth)
                .height(56.dp)
                .clip(RoundedCornerShape(40.dp))
                .then(Modifier)
                .background(Accent.copy(alpha = 0.90f))
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                icon = Icons.Filled.Home,
                label = "Home",
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )

            NavigationItem(
                icon = Icons.Filled.Bookmark,
                label = "Watchlist",
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(1f)
            )

            NavigationItem(
                icon = Icons.Filled.Groups,
                label = "Watch Party",
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemColor =
        if (selected) Dark else Color.White.copy(alpha = 0.85f)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(40.dp))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = itemColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = label,
            color = itemColor,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold
            else FontWeight.Normal,
            maxLines = 1
        )
    }
}