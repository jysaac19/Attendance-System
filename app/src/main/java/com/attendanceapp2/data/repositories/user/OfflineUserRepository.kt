package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineUserRepository(
    private val userDao: UserDao,
    private val users: List<User>
) {
    init {
        // Initialize the database with the list of users
        CoroutineScope(Dispatchers.IO).launch {
            users.forEach { user -> userDao.insert(user) }
        }
    }

    suspend fun insertStudent(user : User) = userDao.insert(user)

    suspend fun updateStudent(user : User) = userDao.update(user)

    suspend fun deleteStudent(user : User) = userDao.delete(user)

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    fun getUsersByUserType(userType: String): Flow<List<User>> {
        return userDao.getUsersByUserType(userType)
    }

    fun getUsers(): Flow<List<User>> {
        return userDao.getUsers()
    }

    fun filterUsersByAdmin (userId: String, usertype: String): Flow<List<User>> {
        return userDao.filterUsersByAdmin(userId, usertype)
    }

    fun filterUsersByStartingUserId(userIdPrefix: String): Flow<List<User>> {
        return userDao.filterUsersByStartingUserId(userIdPrefix)
    }
}