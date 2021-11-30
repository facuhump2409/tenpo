package com.example.tenpo.controllers.helpers

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.example.tenpo.exceptions.CustomException
import com.example.tenpo.exceptions.TokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
object JwtSigner {
    private val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)

    fun createJwt(userId: String): String =
        Jwts.builder()
            .signWith(keyPair.private, SignatureAlgorithm.RS256)
            .setSubject(userId)
            .setIssuer("identity")
            .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(60))))
            .setIssuedAt(Date.from(Instant.now()))
            .compact()

    fun validateJwt(jwt: String?): Either<CustomException, Jws<Claims>> =
        try {
            Right(
                Jwts.parserBuilder()
                    .setSigningKey(keyPair.public)
                    .build()
                    .parseClaimsJws(jwt)
            )
        } catch (ex: Exception) {
            Left(TokenException())
        }
}