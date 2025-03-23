package com.toucheese.presentation.common.component.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.toucheese.presentation.R

@Composable
fun BottomNavBarComponent(
    selectedTab: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {
    val tabList = listOf(
        Pair(R.string.tabItem_label_home, R.drawable.icon_home),
        Pair(R.string.tabItem_label_bookSchedule, R.drawable.icon_bookschedule),
        Pair(R.string.tabItem_label_qna, R.drawable.icon_qna),
        Pair(R.string.tabItem_label_myInfo, R.drawable.icon_info),
    )
    NavigationBar(
        modifier = modifier,
    ){


        tabList.forEachIndexed { index, tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(tab.second), // painterResource를 사용하여 아이콘 렌더링
                        contentDescription = stringResource(tab.first)
                    )
                },
                label = { Text(
                    text = stringResource(tab.first)
                ) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                alwaysShowLabel = true,
            )
        }
    }
}