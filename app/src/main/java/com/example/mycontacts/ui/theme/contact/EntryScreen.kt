package com.example.mycontacts.ui.theme.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycontacts.ContactsTopAppBar
import com.example.mycontacts.R
import com.example.mycontacts.ui.theme.AppViewModelProvider
import com.example.mycontacts.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.util.*

object EntryScreenDestination: NavigationDestination {
    override val route = "contact_entry"
    override val titleRes = R.string.add_contact
}

@Composable
fun EntryScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: EntryScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ContactsTopAppBar(
                title = stringResource(EntryScreenDestination.titleRes),
                navigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBody(
            contactUiState = viewModel.contactUiState,
            onContactValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveContact()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colors.background)
        )
    }
}

@Composable
fun EntryBody(
    contactUiState: ContactUiState,
    onContactValueChange: (ContactUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Box(modifier = modifier.align(alignment = Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(R.drawable.contacts_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.onPrimary,
                        shape = CircleShape
                    ),
            )
        }

        ContactInputForm(
            contactUiState = contactUiState,
            onValueChange = onContactValueChange
        )
        Button(
            onClick = onSaveClick,
            enabled = contactUiState.actionEnable,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_contact))
        }
    }
}

@Composable
fun ContactInputForm(
    contactUiState: ContactUiState,
    modifier: Modifier = Modifier,
    onValueChange: (ContactUiState) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = contactUiState.name,
            onValueChange = {onValueChange(contactUiState.copy(name = it))},
            label = { Text(stringResource(R.string.contact_name_input))},
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            textStyle = TextStyle(color = Color.Black),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
            )
        )
        contactUiState.mobile?.let {
            TextField(
                value = it,
                onValueChange = {onValueChange(contactUiState.copy(mobile = it))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(R.string.contact_phone_input))},
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                textStyle = TextStyle(color = Color.Black),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                )
            )
        }
        contactUiState.email?.let {
            TextField(
                value = it,
                onValueChange = {onValueChange(contactUiState.copy(email = it))},
                label = { Text(stringResource(R.string.contact_email_input))},
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                textStyle = TextStyle(color = Color.Black),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}
