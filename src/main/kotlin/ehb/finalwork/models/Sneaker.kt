package ehb.finalwork.models

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "sneakers")
data class Sneaker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,
    var stylecode: String,
    var brand: String,
    var model: String,
    var name: String,
    var colorway: String,
    var releasedate: LocalDate,
    var price: Double,
    var description: String,
    var buyat: String,
    @ElementCollection
    var images: List<String>,
    @ManyToMany(mappedBy = "favorites")
    val users: MutableList<User> = mutableListOf()
)
