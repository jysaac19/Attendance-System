package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineUserRepository(private val userDao: UserDao, private val users: List<User>) : UserRepository {

    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            users.forEach { user -> userDao.insert(user) }
        }
    }

    override suspend fun insertStudent(user : User) = userDao.insert(user)

    override suspend fun updateStudent(user : User) = userDao.update(user)

    override suspend fun deleteStudent(user : User) = userDao.delete(user)

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}