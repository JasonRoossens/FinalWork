package ehb.finalwork.controllers

import ehb.finalwork.dto.CreateSneakerRequest
import ehb.finalwork.models.Sneaker
import ehb.finalwork.services.SneakerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("sneakers")
@CrossOrigin
class SneakerController {

    @Autowired
    lateinit var sneakerService: SneakerService;

    // Create sneaker
    @PostMapping
    fun createSneaker(@RequestBody sneakerRequest: CreateSneakerRequest): Sneaker {
        return sneakerService.createSneaker(sneakerRequest)
    }


    // Get sneaker by id
    @GetMapping("sneaker/{id}")
    fun getSneakerById(@PathVariable id: Long): Sneaker {
        return sneakerService.getSneakerById(id)
    }

    // Get sneaker by brand
    @GetMapping("/{name}")
    fun getSneakersByBrand(@PathVariable name: String): List<Sneaker> {
        return sneakerService.getSneakersByBrand(name)
    }

    // Get all users
    @GetMapping
    fun getAllSneakers(): MutableList<Sneaker> {

        return sneakerService.getAllSneakers();
    }

    // Update sneakers by id
    @PutMapping("sneaker/{id}")
    fun updateSneakerById(@PathVariable id: Long, @RequestBody sneakerRequest: CreateSneakerRequest): Sneaker {
        return sneakerService.updateSneakerById(id, sneakerRequest)
    }

    // Delete user by id
    @DeleteMapping("sneaker/{id}")
    fun deleteSneakerById(@PathVariable id: Long) {
        sneakerService.deleteSneakerById(id)
    }
}