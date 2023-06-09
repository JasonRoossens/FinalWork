package ehb.finalwork.services

import ehb.finalwork.models.Sneaker
import ehb.finalwork.models.User
import ehb.finalwork.repositories.SneakerRepository
import ehb.finalwork.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class FavoritesService(
    private val userRepository: UserRepository,
    private val sneakerRepository: SneakerRepository
) {
    fun addToFavorites(userId: Long, sneakerId: Long) {
        val userOptional: Optional<User> = userRepository.findById(userId)
        val user: User = userOptional.orElseThrow { NoSuchElementException("User not found") }

        val sneaker: Sneaker = sneakerRepository.findById(sneakerId)
            .orElseThrow { NoSuchElementException("Sneaker not found") }

        user.favorites.add(sneaker)
        userRepository.save(user)
    }

    fun getFavoritesByUserId(userId: Long): List<Sneaker> {
        val userOptional: Optional<User> = userRepository.findById(userId)
        val user: User = userOptional.orElseThrow { NoSuchElementException("User not found") }

        return user.favorites
    }

    fun removeFromFavorites(userId: Long, sneakerId: Long) {
        val userOptional: Optional<User> = userRepository.findById(userId)
        val user: User = userOptional.orElseThrow { NoSuchElementException("User not found") }

        val sneaker: Sneaker = sneakerRepository.findById(sneakerId)
            .orElseThrow { NoSuchElementException("Sneaker not found") }

        user.favorites.remove(sneaker)
        userRepository.save(user)
    }
}