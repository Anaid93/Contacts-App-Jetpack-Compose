package com.example.mycontacts.ui.theme

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mycontacts.ContactsApplication
import com.example.mycontacts.ui.theme.contact.DetailsScreenViewModel
import com.example.mycontacts.ui.theme.contact.EditScreenViewModel
import com.example.mycontacts.ui.theme.contact.EntryScreenViewModel
import com.example.mycontacts.ui.theme.home.HomeViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EditScreenViewModel(
                this.createSavedStateHandle(),
                contactsApplication().container.contactsRepository
            )
        }

        initializer {
            EntryScreenViewModel(
                contactsApplication().container.contactsRepository
            )
        }

        initializer {
            DetailsScreenViewModel(
                this.createSavedStateHandle(),
                contactsApplication().container.contactsRepository
            )
        }

        initializer {
            HomeViewModel(contactsApplication().container.contactsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [ContactsApplication].
 */
fun CreationExtras.contactsApplication(): ContactsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ContactsApplication)

