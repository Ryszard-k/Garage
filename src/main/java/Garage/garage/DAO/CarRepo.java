package Garage.garage.DAO;

import Garage.garage.DAO.entity.Car;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CarRepo extends CrudRepository<Car, Long> {

    List<Car> findByBrand(String brand);

    @Modifying
    @Transactional
    @Query ("UPDATE Car c SET c.model = :model WHERE c.id = :id")
    Car updateModel (@Param(value = "id") Long id, @Param(value = "model") String model);

    @Modifying
    @Transactional
    @Query ("UPDATE Car c SET c.brand = :brand WHERE c.id = :id")
    void updateBrand (@Param(value = "id") Long id, @Param(value = "brand") String brand);

    @Modifying
    @Transactional
    @Query ("UPDATE Car c SET c.cost = :cost WHERE c.id = :id")
    void updateCost (@Param(value = "id") Long id, @Param(value = "cost") int cost);

    @Modifying
    @Transactional
    @Query ("UPDATE Car c SET c.manufactureYear = :manufactureYear WHERE c.id = :id")
    void updateManufactureYear (@Param(value = "id") Long id, @Param(value = "manufactureYear") LocalDate manufactureYear);

    @Query("select c from Car c where lower(c.model) like lower(concat('%', :searchModel, '%')) " +
            "or lower(c.model) like lower(concat('%', :searchModel, '%'))")
    List<Car> searchModel(@Param("searchModel") String searchModel);
}
