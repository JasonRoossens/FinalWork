package ehb.finalwork.services

import ehb.finalwork.dto.UserDto
import ehb.finalwork.models.User
import ehb.finalwork.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    // Create user
    fun createUser(user: UserDto): User {
        val u = User(
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            password = user.password,
            // TODO: Add role

        )
        return userRepository.save(u)
    }

    // Get user by id
    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    // Get user by name (firstname, lastname)
    fun getUsersByName(name: String): List<User> {
        return userRepository.findAllByFirstnameOrLastname(name, name)
    }

    // Get all users
    fun getAllUsers(): MutableList<User> {
        return userRepository.findAll()
    }

    // Update user by id
    fun updateUserById(id: Long, user: UserDto): User {
        val existingUser = getUserById(id)
        existingUser.apply {
            firstname = user.firstname
            lastname = user.lastname
            email = user.email
            password = user.password
        }
        return userRepository.save(existingUser)
    }

    // Delete user by id
    fun deleteUserById(id: Long) {
        userRepository.deleteById(id)
    }
}