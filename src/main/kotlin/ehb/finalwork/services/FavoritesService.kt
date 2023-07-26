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
    private val sneakerRepository: SneakerRepository,
    private val emailService: EmailService
) {
    fun addToFavorites(userId: Long, sneakerId: Long) {
        val userOptional: Optional<User> = userRepository.findById(userId)
        val user: User = userOptional.orElseThrow { NoSuchElementException("User not found") }

        val sneaker: Sneaker = sneakerRepository.findById(sneakerId)
            .orElseThrow { NoSuchElementException("Sneaker not found") }

        user.favorites.add(sneaker)
        userRepository.save(user)

        // Send email notification to the user
        val userEmail = user.email // Assuming you have a field for user's email in the User entity
        val userFirstname= user.firstname // Assuming you have a field for user's email in the User entity
        val sneakerBrand = sneaker.brand // Assuming you have a field for sneaker's name in the Sneaker entity
        val sneakerModel = sneaker.model // Assuming you have a field for sneaker's name in the Sneaker entity
        val subject = "Sneaker added to favorites!"
        val message = "Thanks for using sneakpeek $userFirstname, you have added the sneaker: $sneakerBrand $sneakerModel to your favorites. You will be notified when the sneaker is dropping!"

        emailService.sendEmail(userEmail, subject, message)
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