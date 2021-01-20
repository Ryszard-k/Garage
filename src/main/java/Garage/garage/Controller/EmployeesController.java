package Garage.garage.Controller;

import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees/cars")
public class EmployeesController {

    private final CarManager carManager;

   @Autowired
    public EmployeesController(CarManager carManager) {
        this.carManager = carManager;
    }

    @GetMapping
    public ResponseEntity<Iterable<Car>> getCars(){
       Iterable<Car> foundCars = carManager.findAll();
        int iterations = 0;
        for (Car car: foundCars) {
            iterations++;
        }
        if(iterations == 0){
            return new ResponseEntity("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @GetMapping("/{brand}")
    public ResponseEntity<List<Car>> getByBrand(@PathVariable String brand){
       List<Car>foundCars = carManager.findByBrand(brand);
       if(foundCars.isEmpty()){
           return new ResponseEntity("Bad brand", HttpStatus.BAD_REQUEST);
       } else
       return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity modifyModel(@PathVariable Long id, @RequestBody String model){
        Optional<Car> foundCar = carManager.findById(id);
        if (foundCar.isPresent()) {
            carManager.updateModel(id, model);
        } else
            return new ResponseEntity("Not found car to update!", HttpStatus.NOT_FOUND);
        return null;
    }

    @PostMapping
    public void addCars(@RequestBody Car car){
        carManager.save(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCars(@RequestBody Car car, @PathVariable Long id) {
        Optional<Car> foundCar = carManager.findById(id);
        if (foundCar.isPresent()) {
            carManager.save(car);
        } else
            return new ResponseEntity("Not found car to update!", HttpStatus.NOT_FOUND);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCars(@PathVariable Long id) {
        Optional<Car> foundCar = carManager.findById(id);
        if (foundCar.isPresent()) {
            carManager.deleteById(id);
        } else
        return new ResponseEntity("Not found car to delete!", HttpStatus.NOT_FOUND);
        return null;
    }
}
