package com.jonhbravo.coroutinestesting.domain

import com.jonhbravo.coroutinestesting.data.UserRepository
import com.jonhbravo.coroutinestesting.models.UserInformation
import kotlinx.coroutines.*

class GetUserInformationUseCase(
    //Injected dependencies
    private val userRepository: UserRepository = UserRepository(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): UserInformation = withContext(ioDispatcher) {
        val user = async { userRepository.getUser() }
        val information = async { userRepository.getAccountInformation() }

        UserInformation(user.await(), information.await())
    }
}