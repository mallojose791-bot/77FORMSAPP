package com.example.a77formsapp.data.model

data class FormItem(
    val id: Int,
    val hrFormCode: String,
    val description: String? = null,
    val createdAt: String? = null
) {
    // Display name for the list: the description, or the code if there's no description.
    val title: String
        get() = description?.takeIf { it.isNotBlank() } ?: hrFormCode
}