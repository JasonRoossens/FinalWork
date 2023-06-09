package ehb.finalwork.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue
    var id: Long = -1,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var accessToken: String = "",
    var expirationDate: Long = System.currentTimeMillis(),
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "user_favorite_sneaker",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "sneaker_id")]
)
    @JsonIgnore
    var favorites: MutableList<Sneaker> = mutableListOf()

)
