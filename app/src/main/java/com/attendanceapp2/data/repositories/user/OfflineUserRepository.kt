package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.model.user.User
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

    fun getStudents(): Flow<List<User>> {
        return userDao.getStudents()
    }

    fun filterUsersByAdmin (query: String, usertype: String): Flow<List<User>> {
        return userDao.filterUsersByAdmin(query, usertype)
    }

    fun filterUsersByStartingUserId(query: String): Flow<List<User>> {
        return userDao.filterUsersByStartingUserId(query)
    }

    fun filterUsersByUserType(userType: String): Flow<List<User>> {
        return userDao.filterUsersByUserType(userType)
    }

    suspend fun getUserByFullName(firstname: String, lastname: String): User? {
        return userDao.getUserByFullName(firstname, lastname)
    }

    fun searchStudents(query: String): Flow<List<User>> {
        return userDao.searchStudents(query)
    }
}