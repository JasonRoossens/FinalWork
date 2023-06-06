package ehb.finalwork.services

import ehb.finalwork.dto.LoginUserRequest
import ehb.finalwork.dto.CreateUserRequest
import ehb.finalwork.dto.LoginUserResponse
import ehb.finalwork.models.User
import ehb.finalwork.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
            if(user.password ==  u.password){
                println("Succesfull login")
                u.accessToken = java.util.UUID.randomUUID().toString()
                u.expirationDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1)
                userRepository.save(u)
                return LoginUserResponse(u.accessToken)
            } else {
                println("Wrong password or email")
                return null
            }
        } else {
            println("Wrong Wrong password or email")
            return null
        }
    }

    fun isAuthenticated(token: String): Boolean {
        val u = userRepository.findByAccessToken(token)
        if(u != null) {
            println("User found")
            return u.expirationDate > System.currentTimeMillis()
        } else {
            println("User not found")
            return false
        }
    }

    // Get user by id
    fun getUserById(id: Long): User {
        println("user found!")
        return userRepository.findById(id).orElseThrow()
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
            firstname = user.firstname
            lastname = user.lastname
            email = user.email
            password = user.password
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