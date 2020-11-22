package Garage.garage.Manager;

import Garage.garage.Dao.CarRepo;
import Garage.garage.Dao.Entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarManager {

    private CarRepo carRepo;

    @Autowired
    public CarManager(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

  /*  public Optional<Car> findById(Long id){
        return carRepo.findById(id);
    }*/

    public Iterable<Car> findAll(){
        return carRepo.findAll();
    }

    public Car save(Car car){
        return carRepo.save(car);
    }

    public void deleteById(Long id){
        carRepo.deleteById(id);
    }
}
