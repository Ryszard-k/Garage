package Garage.garage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiCar {

    @Autowired
    private CarManager carManager;

    @GetMapping("/getCars")
    public List<Car> getCars(){
        return carManager.getCarList();
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";

    }
}
