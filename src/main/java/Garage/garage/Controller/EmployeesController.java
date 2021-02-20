package Garage.garage.Controller;

import Garage.garage.Model.entity.Car;
import Garage.garage.Manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees/cars")
@CrossOrigin(origins = "*")
public class EmployeesController {

    private final CarManager carManager;

   @Autowired
    public EmployeesController(CarManager carManager) {
        this.carManager = carManager;
    }

    @GetMapping
    public ResponseEntity getCars(){
       Iterable<Car> foundCars = carManager.findAll();
        int iterations = 0;
        for (Car car: foundCars) {
            iterations++;
        }
        if(iterations == 0){
            return new ResponseEntity<>("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @GetMapping("/{brand}")
    public ResponseEntity getByBrand(@PathVariable String brand){
       List<Car>foundCars = carManager.findByBrand(brand);
       if(foundCars.isEmpty()){
           return new ResponseEntity<>("Bad brand", HttpStatus.BAD_REQUEST);
       } else
       return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('Employee')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> modifyCar(@PathVariable Long id, @RequestBody Car params){
        Optional<Car> foundCar = carManager.findById(id);
        final Car[] car1 = new Car[1];
        if(foundCar.isPresent()){
                foundCar.map(car -> {
                    if(params.getBrand() != null){
                        car.setBrand(params.getBrand());
                    }
                    if(params.getModel() != null){
                        car.setModel(params.getModel());
                    }
                    if(params.getCost() != null){
                        car.setCost(params.getCost());
                    }
                    if(params.getManufactureYear() != null){
                        car.setManufactureYear(params.getManufactureYear());
                    }
                    car1[0] = car;
                    return carManager.save(car);
                });
            return new ResponseEntity<>(car1[0], HttpStatus.OK);
        } else
            return new ResponseEntity<>("Bad brand", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('Employee')")
    @PostMapping
    public ResponseEntity<Object> addCars(@RequestBody Car car){
       if (car != null) {
           carManager.save(car);
           return new ResponseEntity<>(car, HttpStatus.CREATED);
       } else
           return new ResponseEntity<>("Empty input data", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PreAuthorize("hasRole('Employee')")
    @PutMapping("/{id}")
    public ResponseEntity updateCar(@RequestBody Car car, @PathVariable Long id) {
        Optional<Car> foundCar = carManager.findById(id);
        if (foundCar.isPresent()) {
            carManager.save(car);
            return new ResponseEntity(car, HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found car to update!", HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PreAuthorize("hasRole('Employee')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long id) {
        Optional<Car> foundCar = carManager.findById(id);
        if (foundCar.isPresent()) {
            carManager.deleteById(id);
            return new ResponseEntity<>(foundCar,HttpStatus.OK);
        } else
        return new ResponseEntity<>("Not found car to delete!", HttpStatus.NOT_FOUND);
    }
}
