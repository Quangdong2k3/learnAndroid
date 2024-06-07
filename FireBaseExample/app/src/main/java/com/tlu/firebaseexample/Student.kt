package com.tlu.firebaseexample

class Student {
    var id: String = ""
    var name: String = ""
    var email: String = ""

    constructor()

    constructor(id: String, name: String, email: String) {
        this.id = id
        this.name = name
        this.email = email
    }
}