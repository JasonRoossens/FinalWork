package ehb.finalwork.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate


@Entity
@Table(name = "sneakers")
data class Sneaker(
    @Id
    @GeneratedValue
    var id: Long = -1,
    var stylecode: String,
    var brand: String,
    var model: String,
    var colorway: String,
    var releasedate: LocalDate,
    var price: Double,
    var images: List<String>

)
