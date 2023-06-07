package ehb.finalwork.services

import ehb.finalwork.dto.CreateSneakerRequest
import ehb.finalwork.models.Sneaker
import ehb.finalwork.repositories.SneakerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SneakerService {

    @Autowired
    private lateinit var sneakerRepository: SneakerRepository

    // Create sneaker
    fun createSneaker(sneaker: CreateSneakerRequest): Sneaker {
        val s = Sneaker(
            stylecode = sneaker.stylecode,
            brand = sneaker.brand,
            model = sneaker.model,
            colorway = sneaker.colorway,
            price = sneaker.price,
            releasedate = sneaker.releasedate,
            images = sneaker.images

        )
        println("sneaker created!")
        return sneakerRepository.save(s)
    }

    // Get sneaker by id
    fun getSneakerById(id: Long): Sneaker {
        println("sneaker found!")
        return sneakerRepository.findById(id).orElseThrow()
    }

    // Get sneaker by brand
    fun getSneakersByBrand(name: String): List<Sneaker> {
        println("sneakers found!")
        return sneakerRepository.findAllByBrand(name)
    }


    // Get all sneakers
    fun getAllSneakers(): MutableList<Sneaker> {
        println("all sneakers found!")
        return sneakerRepository.findAll()
    }

    // Update sneaker by id
    fun updateSneakerById(id: Long, sneaker: CreateSneakerRequest): Sneaker {
        val existingSneaker = getSneakerById(id)
        existingSneaker.apply {
            stylecode = sneaker.stylecode
            brand = sneaker.brand
            model = sneaker.model
            colorway = sneaker.colorway
            price = sneaker.price
            releasedate = sneaker.releasedate
            images = sneaker.images
        }
        println("sneaker updated!")
        return sneakerRepository.save(existingSneaker)
    }

    // Delete sneaker by id
    fun deleteSneakerById(id: Long) {
        println("sneaker deleted!")
        sneakerRepository.deleteById(id)
    }
}