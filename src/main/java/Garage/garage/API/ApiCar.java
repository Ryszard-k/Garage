package Garage.garage.API;

import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Car> getByBrand(@RequestParam String brand){
        return carManager.findByBrand(brand);
    }

    @PatchMapping
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

    @DeleteMapping
    public void deleteCars(@RequestParam Long index) {
        carManager.deleteById(index);
    }
}