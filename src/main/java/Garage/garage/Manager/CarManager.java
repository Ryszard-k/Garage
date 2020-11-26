package Garage.garage.Manager;

import Garage.garage.Dao.CarRepo;
import Garage.garage.Dao.Entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarManager {

    private CarRepo carRepo;

    @Autowired
    public CarManager(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public Iterable<Car> findAll(){
        return carRepo.findAll();
    }

    public List<Car> findByBrand(String brand){
        return carRepo.findByBrand(brand);
    }
    
    public void updateModel(Long id, String model){
        carRepo.updateModel(id, model);
    }

    public Car save(Car car){
        return carRepo.save(car);
    }

    public void deleteById(Long id){
        carRepo.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new Car((long) 1,"BMW", "E36", "KR8TM32", "12E"));
        save(new Car((long) 2, "Citroen", "Berlingo", "KR5ZJ22", "4J"));
    }
}
