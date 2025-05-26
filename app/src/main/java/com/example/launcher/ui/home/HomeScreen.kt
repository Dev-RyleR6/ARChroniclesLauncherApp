package com.example.launcher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.launcher.R
import com.example.launcher.CardItem
import com.example.launcher.CardItemComposable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    val cards = listOf(
        CardItem(
            name = "Welcome to ARChronicles",
            description = "Explore historical landmarks through augmented reality",
            iconResId = R.drawable.ic_ar,
            onClick = { /* Handle AR click */ }
        ),
        CardItem(
            name = "Featured Landmarks",
            description = "Discover our most popular historical sites",
            iconResId = R.drawable.ic_landmark,
            onClick = { /* Handle landmarks click */ }
        ),
        CardItem(
            name = "Interactive Map",
            description = "Find landmarks close to your location",
            iconResId = R.drawable.ic_map,
            onClick = { /* Handle map click */ }
        )
    )

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            count = cards.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) { page ->
            CardItemComposable(item = cards[page])
        }

        // Tab indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(cards.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(1.dp)
                        .background(
                            color = if (pagerState.currentPage == index) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }
    }
} 