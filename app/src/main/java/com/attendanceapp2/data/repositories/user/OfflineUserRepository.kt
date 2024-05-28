package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.model.subject.Subject
import com.attendanceapp2.data.model.user.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(
    private val userDao: UserDao
) {
    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    suspend fun insertUser(user : User) = userDao.insert(user)

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

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    fun getStudents(): Flow<List<User>> {
        return userDao.getStudents()
    }

    suspend fun getUsersByIds(userIds: List<Int>): List<User> {
        return userDao.getUsersByIds(userIds)
    }

    fun filterUsersByQueryAndUserType (query: String, usertype: String): Flow<List<User>> {
        return userDao.filterUsersByQueryAndUserType(query, usertype)
    }

    fun filterUsersByQuery(query: String): Flow<List<User>> {
        return userDao.filterUsersByQuery(query)
    }

    fun filterUsersByUserType(userType: String): Flow<List<User>> {
        return userDao.filterUsersByUserType(userType)
    }

    suspend fun getUserByFullName(firstname: String, lastname: String): User? {
        return userDao.getUserByFullName(firstname, lastname)
    }

    fun searchUser(query: String): Flow<List<User>> {
        return userDao.searchUser(query)
    }
}