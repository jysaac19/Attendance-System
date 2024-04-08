package com.attendanceapp2.data.repositories.user

import com.attendanceapp2.data.interfaces.UserDao
import com.attendanceapp2.data.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {

    override suspend fun insertStudent(user : User) = userDao.insert(user)

    override suspend fun updateStudent(user : User) = userDao.update(user)

    override suspend fun deleteStudent(user : User) = userDao.delete(user)
    override fun getUser(user: User) : Flow<User> = userDao.getUser(user)

}