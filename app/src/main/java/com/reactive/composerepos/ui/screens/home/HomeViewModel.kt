package com.reactive.composerepos.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.reactive.domain.usecases.GetPagedKotlinReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getReposUseCase : GetPagedKotlinReposUseCase,
): ViewModel() {

    val items = getReposUseCase().cachedIn(viewModelScope)
}
