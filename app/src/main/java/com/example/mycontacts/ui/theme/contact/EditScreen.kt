package com.example.mycontacts.ui.theme.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycontacts.ContactsTopAppBar
import com.example.mycontacts.R
import com.example.mycontacts.ui.theme.AppViewModelProvider
import com.example.mycontacts.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch


object EditScreenDestination: NavigationDestination {
    override val route = "contact_edit"
    override val titleRes = R.string.edit_contact
    const val contactIdArg = "contactId"
    val routeWithArgs =  "$route/{$contactIdArg}"
}

@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ContactsTopAppBar(
                title = stringResource(EditScreenDestination.titleRes),
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
                    viewModel.updateContact()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colors.background)
        )
    }
}