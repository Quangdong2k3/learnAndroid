package com.tlu.firebaseexample

data class Classroom(
    val id: String = "",
    val className: String = "",
    val students: List<Student> = listOf()
)