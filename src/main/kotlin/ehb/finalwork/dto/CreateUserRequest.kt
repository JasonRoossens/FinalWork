package ehb.finalwork.dto

data class CreateUserRequest(
    var id: Long = -1,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
)
