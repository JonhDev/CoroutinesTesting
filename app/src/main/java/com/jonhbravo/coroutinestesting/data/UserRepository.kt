package com.jonhbravo.coroutinestesting.data

import com.jonhbravo.coroutinestesting.models.AccountInformation
import com.jonhbravo.coroutinestesting.models.User
import kotlinx.coroutines.delay

class UserRepository() {

    suspend fun getUser(): User {
        delay(DEFAULT_DELAY)
        return User(
            name = "Jonathan",
            lastName = "Bravo",
            age = 25
        )
    }

    suspend fun getAccountInformation(): AccountInformation {
        delay(DEFAULT_DELAY)
        return AccountInformation(
            bankAccount = "12345678",
            address = "Mexico...",
            zipCode = "1234"
        )
    }

    private companion object {
        const val DEFAULT_DELAY = 1_000L
    }

}