package `in`.lightspeed.todo

import android.provider.ContactsContract

class User {
    lateinit var email:String
    lateinit var name:String
    lateinit var age:String
    lateinit var dateofbirth:String
    lateinit var userID:String

    constructor(){

    }
    constructor(email:String,name:String,age:String,dateofbirth:String, userID:String){
        this.email = email
        this.name = name
        this.age = age
        this.dateofbirth = dateofbirth
        this.userID = userID
    }
}