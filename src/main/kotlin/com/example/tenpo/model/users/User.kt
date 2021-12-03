package com.example.tenpo.model.users

import com.example.tenpo.model.Requestable
import com.example.tenpo.model.dtos.DTO
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Column
    val email: String,
    @Column
    val password: String,
    @Column
    val salt: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) : Requestable {
    override fun dto(): DTO.UserDto {
        return DTO.UserDto(
            id,
            email
        )
    }

}