package com.example.domain.user

data class Trainer(
    var nickname: String,
    var email: String,
    var password: String,
    var gymName: String,
    var street: String,
    var city: String,
    var country: String,
    var exerciseYears: Int,
    var gender: Gender,
    var introduce: String,
    var role: Role,
)
