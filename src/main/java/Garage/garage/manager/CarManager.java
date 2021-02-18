package Garage.garage.Manager;

import Garage.garage.Model.CarRepo;
import Garage.garage.Model.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Car> findById(Long id){
        return carRepo.findById(id);
    }

    public Car save(Car car){
        return carRepo.save(car);
    }

    public Optional<Car> deleteById(Long id){
        Optional<Car> deleted = carRepo.findById((Long) id);
        carRepo.deleteById(id);

        return deleted;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new Car((long) 1,"BMW", "E36", 30000, LocalDate.parse("2000-02-23")));
        save(new Car((long) 2,"Citroen", "Berlingo", 22000, LocalDate.parse("1997-05-20")));
    }
}
