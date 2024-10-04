package com.reactive.domain.model

data class ResultList<T>(
    val totalCount : Int = 0,
    val items : List<T> = emptyList(),
)
