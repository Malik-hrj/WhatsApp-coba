package com.example.whatsappcoba.util

data class User(
    val email: String? = "",
    val phone: String? = "",
    val name: String? = "",
    val imageUrl: String? = "",
    val status: String? = "",
    val statusUrl: String? = "",
    val statusTime: String? = ""
)

data class Contacts(
    val name: String? = "",
    val phone: String? = ""
)