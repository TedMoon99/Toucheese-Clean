package com.tedmoon99.domain.home.model

sealed class HomeEvent {
    data object GetHome: HomeEvent()
    data class CardClick(val conceptId: Int): HomeEvent()
}