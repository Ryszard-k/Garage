package Garage.garage.API;

import Garage.garage.Dao.Entity.Car;
import Garage.garage.Manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiCar {


    private CarManager carManager;

   @Autowired
    public ApiCar(CarManager carManager) {
        this.carManager = carManager;
    }

    @GetMapping("/Cars")
    public Iterable<Car> getCars(){
        return carManager.findAll();
    }

   /* @GetMapping
    public Car getByBrand(@RequestParam String brand){
        Optional<Car> first = carList.stream().filter(element -> element.getBrand().equals(brand))
                .findAny();
        return first.get();
    }*/

    @PostMapping
    public Car addCars(@RequestBody Car car){
        return carManager.save(car);
    }

    @PutMapping
    public void updateCars(@RequestBody Car car) {
        carManager.save(car);
    }

    @DeleteMapping
    public void deleteCars(@RequestParam Long index) {
        carManager.deleteById(index);
    }
}
