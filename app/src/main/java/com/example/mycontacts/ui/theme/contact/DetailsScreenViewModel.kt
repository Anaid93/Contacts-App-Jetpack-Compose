package com.example.mycontacts.ui.theme.contact

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontacts.data.ContactsRepository
import kotlinx.coroutines.flow.*


class DetailsScreenViewModel(
    state: SavedStateHandle,
    private val contactsRepository: ContactsRepository
): ViewModel() {
    private val contactId: Int = checkNotNull(state[DetailsScreenDestination.contactIdArg])

    val uiState: StateFlow<ContactUiState> = contactsRepository.getContactStream(contactId)
        .filterNotNull()
        .map { it.toContactUiState(actionEnable = true) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ContactUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteContact(){
        contactsRepository.deleteContact(uiState.value.toContact())
    }
}