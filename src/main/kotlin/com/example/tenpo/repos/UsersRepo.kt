package com.example.tenpo.repos

import arrow.core.Option
import arrow.core.toOption
import com.example.tenpo.model.users.User
import com.example.tenpo.model.utils.SHA512Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@PersistenceContext
@Transactional
class UsersRepo { //Tambien se podria haber hecho con una interface usando JpaRepository pero me hubiese complicado para el salt
    @Autowired
    private lateinit var entityManager: EntityManager

    fun saveUser(user: User) {
        entityManager.persist(user)
    }

    fun findByMail(mail: String): Option<User> {
        return entityManager.createQuery("select u from User u where u.email = :mail", User::class.java)
                .setParameter("mail", mail)
                .resultList
                .firstOrNull().toOption()
    }

    fun findUserByMailAndPassword(mail: String, password: String): Option<User> {
        val userFound = findByMail(mail)
        return userFound.filter { it.password == SHA512Hash.hashPassword(password, it.salt) }
    }
}