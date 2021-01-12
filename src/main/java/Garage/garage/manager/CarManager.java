package Garage.garage.Manager;

import Garage.garage.DAO.CarRepo;
import Garage.garage.DAO.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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
    
    public Optional<Car> updateModel(Long id, String model){
        Optional<Car> updated = carRepo.findById((Long) id);
        carRepo.updateModel(id, model);
        return updated;
    }

    public Optional<Car> updateBrand(Long id, String brand){
        Optional<Car> updated = carRepo.findById((Long) id);
        carRepo.updateBrand(id, brand);
        return updated;
    }

    public Optional<Car> updatePlate(Long id, String plate){
        Optional<Car> updated = carRepo.findById((Long) id);
        carRepo.updatePlate(id, plate);
        return updated;
    }

    public Optional<Car> updateParking(Long id, String parking){
        Optional<Car> updated = carRepo.findById((Long) id);
        carRepo.updateParking(id, parking);
        return updated;
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
        save(new Car((long) 1,"BMW", "E36", "KR8TM32", "12E"));
        save(new Car((long) 2, "Citroen", "Berlingo", "KR5ZJ22", "4J"));
    }
}
