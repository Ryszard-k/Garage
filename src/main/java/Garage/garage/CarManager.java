package Garage.garage;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarManager {

    protected List<Car> carList;

    public CarManager() {
        this.carList = new ArrayList<>();
        carList.add(new Car((long) 1,"BMW", "E36", "KR8TM32", "12E"));
        carList.add(new Car((long) 2, "Citroen", "Berlingo", "KR5ZJ22", "4J"));
    }

    public boolean AddCar(Car car){
        return carList.add(car);
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
