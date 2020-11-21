package Garage.garage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Car> getCars(){
        return carManager.getCarList();
    }

    @GetMapping
    public Car getByBrand(@RequestParam String brand){
        Optional<Car> first = carManager.carList.stream().filter(element -> element.getBrand().equals(brand))
                .findAny();
        return first.get();
    }

    @PostMapping
    public boolean addCars(@RequestBody Car car){
        return carManager.AddCar(car);
    }

    @PutMapping
    public void updateCars(@RequestParam int index, @RequestBody Car car) {
        carManager.carList.add(index, car);
    }

    @DeleteMapping
    public boolean deleteCars(@RequestParam int index) {
        return carManager.carList.removeIf(element -> element.getId() == index);
    }
}
