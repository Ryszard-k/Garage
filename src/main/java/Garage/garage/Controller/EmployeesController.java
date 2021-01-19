package Garage.garage.Controller;

import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees/cars")
public class EmployeesController {

    private final CarManager carManager;

   @Autowired
    public EmployeesController(CarManager carManager) {
        this.carManager = carManager;
    }

    @GetMapping("/all")
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
    @ResponseBody
    public ResponseEntity<List<Car>> getByBrand(@PathVariable String brand){
       List<Car>foundCars = carManager.findByBrand(brand);
       if(foundCars.isEmpty()){
           return new ResponseEntity("Bad brand", HttpStatus.BAD_REQUEST);
       } else
       return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @PatchMapping("/employees/{id}")
    public void modifyModel(@RequestParam Long id, @RequestParam String model){
        carManager.updateModel(id, model);
    }

    @PostMapping
    public void addCars(@RequestBody Car car){
        carManager.save(car);
    }

    @PutMapping
    public void updateCars(@RequestBody Car car) {
        carManager.save(car);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteCars(@RequestParam Long id) {
        carManager.deleteById(id);
    }
}
