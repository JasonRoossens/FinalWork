package ehb.finalwork.repositories

import ehb.finalwork.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findAllByFirstnameOrLastname(firstname: String, lastname: String): List<User> // Firstname Or Lastname
    fun findByEmail(email: String): User?
    fun findByAccessToken(accessToken: String): User?
}