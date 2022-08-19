package com.jonhbravo.coroutinestesting.ui

import com.jonhbravo.coroutinestesting.models.UserInformation

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val userInformation: UserInformation) : ProfileState()
}
