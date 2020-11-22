package Garage.garage.API;

import Garage.garage.Manager.CarManager;
import Garage.garage.Dao.Entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiCar {

    public List<Car> carList;

    public ApiCar() {
        this.carList = new ArrayList<>();
        carList.add(new Car((long) 1,"BMW", "E36", "KR8TM32", "12E"));
        carList.add(new Car((long) 2, "Citroen", "Berlingo", "KR5ZJ22", "4J"));
    }

    @GetMapping("/Cars")
    public List<Car> getCars(){
        return carList;
    }

    @GetMapping
    public Car getByBrand(@RequestParam String brand){
        Optional<Car> first = carList.stream().filter(element -> element.getBrand().equals(brand))
                .findAny();
        return first.get();
    }

    @PostMapping
    public boolean addCars(@RequestBody Car car){
        return carList.add(car);
    }

    @PutMapping
    public void updateCars(@RequestParam int index, @RequestBody Car car) {
        carList.add(index, car);
    }

    @DeleteMapping
    public boolean deleteCars(@RequestParam int index) {
        return carList.removeIf(element -> element.getId() == index);
    }
}
