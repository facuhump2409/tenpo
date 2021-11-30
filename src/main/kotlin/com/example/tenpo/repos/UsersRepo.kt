package com.example.tenpo.repos

import arrow.core.Option
import arrow.core.toOption
import com.example.tenpo.model.users.User
import com.example.tenpo.model.utils.SHA512Hash
import org.springframework.stereotype.Component

@Component
class UsersRepo {
    val users: MutableList<User> = mutableListOf()

    fun saveUser(user: User) {
        users.add(user)
    }

    fun findByMail(mail: String): Option<User> {
        return users.find { it.email == mail }.toOption()
    }

    fun findUserByMailAndPassword(mail: String, password: String): Option<User> {
        val userFound = findByMail(mail)
        return userFound.filter { it.password == SHA512Hash.hashPassword(password, it.salt) }
    }
}