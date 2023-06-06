package ehb.finalwork.controllers

import ehb.finalwork.dto.CreateUserRequest
import ehb.finalwork.dto.LoginUserRequest
import ehb.finalwork.dto.LoginUserResponse
import ehb.finalwork.models.User
import ehb.finalwork.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
@CrossOrigin
class UserController {

    @Autowired
    lateinit var userService: UserService;

    // Create user
    @PostMapping
    fun createUser(@RequestBody userRequest: CreateUserRequest): User {
        return userService.createUser(userRequest)
    }

    @RequestMapping("/login")
    @PostMapping
    fun loginUser(@RequestBody userRequest: LoginUserRequest): LoginUserResponse? {
        return userService.loginUser(userRequest)
    }

    // Get user by id
    @GetMapping("user/{id}")
    fun getUserById(@PathVariable id: Long): User {
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

        return userService.getAllUsers();
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