package Garage.garage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarManager {

    private List<Car> carList;

    @Autowired
    public CarManager() {
        this.carList = new ArrayList<>();
        carList.add(new Car("BMW", "E36", "KR8TM32", "12E"));
        carList.add(new Car("Citroen", "Berlingo", "KR5ZJ22", "4J"));
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
