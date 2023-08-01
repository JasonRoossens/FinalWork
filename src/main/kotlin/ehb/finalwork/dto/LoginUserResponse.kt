package ehb.finalwork.dto

data class LoginUserResponse (
    var accessToken : String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val id: Long
)
