package ehb.finalwork.controllers

import ehb.finalwork.dto.CreateUserRequest
import ehb.finalwork.dto.LoginUserRequest
import ehb.finalwork.dto.LoginUserResponse
import ehb.finalwork.models.User
import ehb.finalwork.services.SendGridEmailService
import ehb.finalwork.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
@CrossOrigin
class UserController(private val userService: UserService, private val emailService: SendGridEmailService) {
    // Create user
    @PostMapping
    fun createUser(@RequestBody userRequest: CreateUserRequest): User {
        val newUser = userService.createUser(userRequest)

        // Send a welcome email to the new user
        emailService.sendWelcomeEmail(newUser.email, "${newUser.firstname} ${newUser.lastname}")

        return newUser
    }

    @RequestMapping("/login")
    @PostMapping
    fun loginUser(@RequestBody userRequest: LoginUserRequest): ResponseEntity<LoginUserResponse> {
        val loginUserResponse = userService.loginUser(userRequest)
        return if (loginUserResponse != null) {
            // Set JWT token as a cookie in the response
            val responseHeaders = HttpHeaders()
            responseHeaders.set(HttpHeaders.SET_COOKIE, "access_token=${loginUserResponse.accessToken}; HttpOnly; Max-Age=86400; SameSite=None; Secure")
            ResponseEntity(loginUserResponse, responseHeaders, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    // Get user by id
    @GetMapping("user/{id}")
    fun getUserById(@PathVariable id: Long?): User {
        if (id == null) {
            throw IllegalArgumentException("User ID must not be null")
        }
        return userService.getUserById(id)
    }

    // Get user by name (firstname, lastname)
    @GetMapping("/{name}")
    fun getUsersByName(@PathVariable name: String): List<User> {
        return userService.getUsersByName(name)
    }

    // Get all users
    @GetMapping
    fun getAllUsers(): MutableList<User> {
        return userService.getAllUsers()
    }

    // Update user by id
    @PutMapping("user/{id}")
    fun updateUserById(@PathVariable id: Long, @RequestBody userRequest: CreateUserRequest): User {
        return userService.updateUserById(id, userRequest)
    }

    // Delete user by id
    @DeleteMapping("user/{id}")
    fun deleteUserById(@PathVariable id: Long) {
        userService.deleteUserById(id)
    }
}