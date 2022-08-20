package com.jonhbravo.coroutinestesting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonhbravo.coroutinestesting.domain.GetUserInformationUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    //Injected dependencies
    private val getUserInformationUseCase: GetUserInformationUseCase = GetUserInformationUseCase()
) : ViewModel() {

    private val _state = MutableLiveData<ProfileState>()
    val state: LiveData<ProfileState> = _state

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            val information = getUserInformationUseCase()
            _state.value = ProfileState.Success(information)
        }
    }

}