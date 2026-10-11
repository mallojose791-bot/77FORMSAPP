package com.example.a77formsapp.data.model

data class FormItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val url: String
)