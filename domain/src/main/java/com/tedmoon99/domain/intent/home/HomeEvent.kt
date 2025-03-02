package com.tedmoon99.domain.intent.home

sealed class HomeEvent {
    data object GetHome: HomeEvent()
    data class CardClick(val conceptId: Int): HomeEvent()
}