package ehb.finalwork.services

import ehb.finalwork.dto.LoginUserRequest
import ehb.finalwork.dto.CreateUserRequest
import ehb.finalwork.dto.LoginUserResponse
import ehb.finalwork.models.User
import ehb.finalwork.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    // Create user
    fun createUser(user: CreateUserRequest): User {
        val u = User(
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            password = user.password,
            // TODO: Add role

        )
        println("user created!")
        return userRepository.save(u)
    }

    fun loginUser(user: LoginUserRequest): LoginUserResponse? {
        val u = userRepository.findByEmail(user.email)
        if (u != null) {
            if (user.password == u.password) {
                println("Successful login")
                val accessToken = generateJwtToken(u.id)
                u.accessToken = accessToken
                u.expirationDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1)
                userRepository.save(u)
                return LoginUserResponse(accessToken, u.firstname, u.lastname, u.email, u.password, u.id) //
            } else {
                println("Wrong password or email")
                return null
            }
        } else {
            println("Wrong password or email")
            return null
        }
    }

    private fun generateJwtToken(userId: Long): String {
        val expirationTime = Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1)) // 1 day
        return Jwts.builder()
            .setSubject(userId.toString())
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, "OWFkZWUzNTdhOGQyZWQxM2UwMjNkZGIzOTU1NmQ2OTM0Njk4ZGE1YTU3N2E4NGJmNDNjNWIxYTQ1NDc5NzVkZg") // Replace with your secret key
            .compact()
    }

    // Get user by id
    fun getUserById(id: Long): User {
        println("user found!")
        return userRepository.findById(id).orElseThrow { NoSuchElementException("user with $id not found") }
    }

    // Get user by name (firstname, lastname)
    fun getUsersByName(name: String): List<User> {
        println("user found!")
        return userRepository.findAllByFirstnameOrLastname(name, name)
    }

    // Get all users
    fun getAllUsers(): MutableList<User> {
        println("all users found!")
        return userRepository.findAll()
    }

    // Update user by id
    fun updateUserById(id: Long, user: CreateUserRequest): User {
        val existingUser = getUserById(id)
        existingUser.apply {
            if (!user.firstname.isNullOrBlank()) {
                firstname = user.firstname
            }
            if (!user.lastname.isNullOrBlank()) {
                lastname = user.lastname
            }
            if (!user.email.isNullOrBlank()) {
                email = user.email
            }
            if (!user.password.isNullOrBlank()) {
                password = user.password
            }
        }
        println("user updated!")
        return userRepository.save(existingUser)
    }

    // Delete user by id
    fun deleteUserById(id: Long) {
        println("user deleted!")
        userRepository.deleteById(id)
    }
}