package com.attendanceapp2.authentication

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.data.model.User
import com.attendanceapp2.data.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepo : UserRepository) : ViewModel(){

    private val users = listOf(
        User(101,
            "Je",
            "Ysaac",
            "je@yahoo.com",
            "123",
            "Student",
            "ComSci"),
        User(201,
            "Admin",
            "Ysaac",
            "admin@yahoo.com",
            "123",
            "Admin",
            "ComSci"),
        User(301,
            "Faculty",
            "Ysaac",
            "faculty@yahoo.com",
            "123",
            "Faculty",
            "ComSci")
    )

    private val _status: Boolean
        get() = status
    var status = false

    private val _email: String
        get() = email
    var email = ""

    private val _password: String
        get() = password
    var password = ""
    fun checkUser() : String{
//        viewModelScope.launch {
//            userRepo.getUser(user).collect {
//                print(it.toString())
//            }
//
//        }
        var userType = ""
        users.forEach { user ->
            if(user.email == _email && user.password == _password){

                when(user.usertype){
                    "Student" -> userType = "Student"
                    "Admin" -> userType = "Admin"
                    "Faculty" -> userType = "Faculty"
                }
            }else{
                viewModelScope.launch {
                    Toast.makeText(null, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }


        return userType
    }

}