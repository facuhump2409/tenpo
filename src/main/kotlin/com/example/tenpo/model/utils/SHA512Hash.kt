package com.example.tenpo.model.utils

import java.security.MessageDigest
import java.security.SecureRandom


object SHA512Hash {
    fun hashPassword(toHash: String): Pair<String, String> {
        val random = SecureRandom()
        val salt = ByteArray(512)
        random.nextBytes(salt)
        return Pair(salt.toString(), hashPassword(toHash, salt.toString()))
    }

    fun hashPassword(toHash: String, salt: String?): String {
        var hashedPassword = toHash
        for (i in 0..99999) {
            hashedPassword = SHA512once(toHash + salt)
        }
        return SHA512once(hashedPassword)
    }

    private fun SHA512once(toHash: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        md.update(toHash.toByteArray())
        val mb = md.digest()
        var out = ""
        for (i in mb.indices) {
            val temp = mb[i]
            var s = Integer.toHexString(temp.toInt())
            while (s.length < 2) {
                s = "0$s"
            }
            s = s.substring(s.length - 2)
            out += s
        }
        return out
    }

}
