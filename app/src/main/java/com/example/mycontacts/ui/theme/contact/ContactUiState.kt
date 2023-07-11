package com.example.mycontacts.ui.theme.contact

import com.example.mycontacts.data.Contact


data class ContactUiState(
    val id: Int = 0,
    val name: String = "",
    val mobile: String? = "",
    val email: String? = "",
    val actionEnable: Boolean = false
)

fun ContactUiState.toContact(): Contact = Contact(
    id = id,
    name = name,
    mobile = mobile?.toLongOrNull() ?: 0,
    email = email
)

fun Contact.toContactUiState(actionEnable: Boolean = false): ContactUiState = ContactUiState(
    id = id,
    name = name,
    mobile = mobile.toString(),
    email = email,
    actionEnable = actionEnable
)


fun ContactUiState.isValid(): Boolean {
    return name.isNotBlank()
}