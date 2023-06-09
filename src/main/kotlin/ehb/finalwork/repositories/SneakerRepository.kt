package ehb.finalwork.repositories

import ehb.finalwork.models.Sneaker
import ehb.finalwork.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SneakerRepository : JpaRepository<Sneaker, Long> {
    fun findAllByBrand(brand: String): List<Sneaker> // brand

}