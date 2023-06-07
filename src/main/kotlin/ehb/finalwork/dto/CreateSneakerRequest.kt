package ehb.finalwork.dto

import java.time.LocalDate

data class CreateSneakerRequest(
    var id: Long = -1,
    var stylecode: String,
    var brand: String,
    var model: String,
    var colorway: String,
    var releasedate: LocalDate,
    var price: Double,
    var images: List<String>
)