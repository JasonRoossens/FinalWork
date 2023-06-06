package ehb.finalwork.repositories

import ehb.finalwork.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findAllByFirstnameOrLastname(firstname: String, lastname: String): List<User> // Firstname Or Lastname
}