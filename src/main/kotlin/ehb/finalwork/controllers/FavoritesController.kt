package ehb.finalwork.controllers

import ehb.finalwork.models.Sneaker
import ehb.finalwork.services.FavoritesService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favorites")
class FavoritesController(
    private val favoritesService: FavoritesService
) {
    @PostMapping("/{userId}/add/{sneakerId}")
    fun addToFavorites(
        @PathVariable userId: Long,
        @PathVariable sneakerId: Long
    ) {
        favoritesService.addToFavorites(userId, sneakerId)
        // Handle response or return appropriate data
    }

    @GetMapping("/{userId}")
    fun getFavorites(
        @PathVariable userId: Long
    ): List<Sneaker> {
        return favoritesService.getFavoritesByUserId(userId)
    }

    @DeleteMapping("/{userId}/remove/{sneakerId}")
    fun removeFromFavorites(
        @PathVariable userId: Long,
        @PathVariable sneakerId: Long
    ) {
        favoritesService.removeFromFavorites(userId, sneakerId)
        // Handle response or return appropriate data
    }
}