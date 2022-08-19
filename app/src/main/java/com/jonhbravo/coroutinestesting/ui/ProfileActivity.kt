package com.jonhbravo.coroutinestesting.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonhbravo.coroutinestesting.models.AccountInformation
import com.jonhbravo.coroutinestesting.models.User
import com.jonhbravo.coroutinestesting.models.UserInformation
import com.jonhbravo.coroutinestesting.ui.ui.theme.CoroutinesTestingTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesTestingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfilePage()
                }
            }
        }
    }
}

@Composable
fun ProfilePage(viewModel: ProfileViewModel = viewModel()) {
    val state by viewModel.state.observeAsState(ProfileState.Loading)
    when (val uiState: ProfileState = state) {
        ProfileState.Loading -> Loading()
        is ProfileState.Success -> ProfileContainer(information = uiState.userInformation)
    }
}

@Composable
fun ProfileContainer(information: UserInformation) {
    Box(modifier = Modifier.fillMaxSize()) {
        Profile(information, modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Composable
fun Profile(information: UserInformation, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = with(information.user) { "$name $lastName" },
                style = MaterialTheme.typography.h5
            )
            Text(
                text = information.accountInformation.bankAccount,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    CoroutinesTestingTheme {
        Profile(
            information = UserInformation(
                user = User("User", "Testing", 12),
                accountInformation = AccountInformation("1234", "Mexico", "123")
            )
        )
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun LoadingPreview() {
    CoroutinesTestingTheme {
        Loading()
    }
}