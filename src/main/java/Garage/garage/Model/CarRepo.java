package Garage.garage.Model;

import Garage.garage.Model.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepo extends CrudRepository<Car, Long> {

    List<Car> findByBrand(String brand);
}
