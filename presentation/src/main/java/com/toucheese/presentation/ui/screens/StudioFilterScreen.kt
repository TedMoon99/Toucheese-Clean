package com.toucheese.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tedmoon99.domain.intent.filter.FilterEvent
import com.toucheese.presentation.R
import com.toucheese.presentation.common.component.appbar.BasicTopAppbarComponent
import com.toucheese.presentation.common.component.card.SliderCardItemComponent
import com.toucheese.presentation.ui.viewmodel.StudioFilterViewModel

@Composable
fun StudioFilterScreen(
    conceptId: Int,
    viewModel: StudioFilterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
) {
    val conceptName by viewModel.conceptName.collectAsStateWithLifecycle()
    val studioList = viewModel.studioListState.collectAsLazyPagingItems()


    LaunchedEffect(conceptId) {
        // 스튜디오 데이터 조회
        viewModel.onEvent(FilterEvent.EnterFilter(conceptId))
        Log.d("StudioFilterScreen", "컨셉: ${conceptId} 스튜디오 데이터 조회: ${studioList}")
    }

    Scaffold(
        topBar = {
            BasicTopAppbarComponent(
                title = conceptName,
                leadingIcon = R.drawable.icon_arrow_back, // 뒤로가기
                trailingIcon = R.drawable.icon_cart, // 장바구니
                showLeadingIcon = true,
                showTrailingIcon = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                onClickLeadingIcon = onClickLeadingIcon,
                onClickTrailingIcon = {
                    // 장바구니로 이동
                    onClickTrailingIcon()

                }
            )
        }

    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            items(
                studioList.itemCount,
                key = studioList.itemKey { it.id }
            ) { index: Int ->
                val item = studioList[index]
                if (item != null) {
                    SliderCardItemComponent(
                        imageUrl = item.profileImage,
                        studioName = item.name,
                        icon = R.drawable.icon_bookmark,
                        imageUrls = item.imageUrls,
                        studioRating = item.rating,
                        studioPrice = item.price,
                        onClick = {

                        }
                    )
                } else {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {

                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}