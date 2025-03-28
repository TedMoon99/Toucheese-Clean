package com.toucheese.presentation.home.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tedmoon99.domain.home.model.HomeEvent
import com.toucheese.presentation.R
import com.toucheese.presentation.common.component.appbar.ImageTopAppbarComponent
import com.toucheese.presentation.common.component.bottombar.BottomNavBarComponent
import com.toucheese.presentation.common.component.card.ImageCardComponent
import com.toucheese.presentation.common.component.searchbar.SearchBarComponent
import com.toucheese.presentation.home.viewmodel.HomeViewModel
import com.toucheese.presentation.common.utils.Concept.Companion.conceptArray

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onCardClick: (Int) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetHome)
    }

    Scaffold(
        topBar = {
            ImageTopAppbarComponent(
                titleImage = R.drawable.toucheese_text,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 86.dp),
            )
        },
        bottomBar = {
            BottomNavBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
        ) {

            // 검색
            SearchBarComponent(
                leadingIcon = R.drawable.icon_search,
                placeholder = R.string.searchbar_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .clickable {

                    },

            )
            // 컨셉 이미지
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(conceptArray) { index: Int, conceptImage: Pair<Int, Int> ->
                    ImageCardComponent(
                        image = conceptImage.first,
                        imageDescription = conceptImage.second,
                        imageNumber = index + 1,
                        showLabel = true,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(0.8f)
                            .padding(4.dp),
                        onCardClick = {
                            val conceptId = index + 1
                            // conceptId 저장
                            viewModel.onEvent(HomeEvent.CardClick(conceptId))
                            // 화면전환 - 스튜디오 리스트 조회
                            onCardClick(conceptId)
                            Log.d("HomeScreen", "HomeState: ${conceptId}")
                        }
                    )
                }
            }
        }
    }
}