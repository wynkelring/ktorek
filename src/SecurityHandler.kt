package com.example

import com.example.repository.UsersRepository
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory
import java.util.regex.Pattern

class SecurityHandler {

    val log = LoggerFactory.getLogger(SecurityHandler::class.java)
    var repository: UsersRepository = UsersRepository()

    fun isValid(username: String, password: String): Boolean {

        val user = repository.getUserByUsername(username)

        if (user != null) {
            return (user.username == username && user.password == hashPassword(password))
        }
        return false
    }

    companion object {
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, "$2a$10" + "$" + "KSSXMefRaDiXFog10AbwEO")
        }

        fun isValidPassword(password: String): Boolean {
            if (password.length < 5) {
                return false
            }

            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(password)
            if (!matcher.matches()) {
                return false
            }

            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (!matcher.matches()) {
                return false
            }

            exp = ".*[a-z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (!matcher.matches()) {
                return false
            }

            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (!matcher.matches()) {
                return false
            }

            return true
        }
    }
}
