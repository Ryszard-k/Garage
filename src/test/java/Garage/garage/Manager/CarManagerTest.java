package Garage.garage.Manager;

import Garage.garage.DAO.CarRepo;
import Garage.garage.DAO.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CarManagerTest {

    @Mock
    private CarRepo carRepo;

    @InjectMocks
    private CarManager carManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Car> carList(){
        List<Car> cars = new ArrayList<>();
        cars.add(new Car((long) 1,"BMW", "E36", 30000, LocalDate.parse("2000-02-23")));
        cars.add(new Car((long) 2,"Citroen", "Berlingo", 22000, LocalDate.parse("1997-05-20")));
        return  cars;
    }

    @Test
    void findAll() {
        when(carRepo.findAll()).thenReturn(carList());

        Iterable<Car> carList2 = carManager.findAll();
        int iterations = 0;
        for (Car car: carList2) {
            iterations++;
        }
        assertEquals(2, iterations);
    }

    @Test
    void findByBrand() {
        when(carRepo.findByBrand("BMW")).thenReturn(carList().subList(0,1));

        List<Car> returned = carManager.findByBrand("BMW");

        assertEquals("BMW", returned.get(0).getBrand());
    }

    @Test
    void updateModel() {
        Car updated = new Car((long)2,"Ford", "Focus", 15000, LocalDate.parse("2010-01-21"));
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateModel(updated.getId(), updated.getModel());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateModel(updated.getId(), updated.getModel());
        assertEquals(updated.getModel(), returned.get().getModel());
    }

    @Test
    void updateBrand() {
        Car updated = new Car((long)2,"Audi", "Focus", 15000, LocalDate.parse("2010-01-21"));
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateBrand(updated.getId(), updated.getBrand());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateBrand(updated.getId(), updated.getBrand());
        assertEquals(updated.getBrand(), returned.get().getBrand());
    }

    @Test
    void updateCost() {
        Car updated = new Car((long)2,"Ford", "Focus", 50000, LocalDate.parse("2010-01-21"));
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateCost(updated.getId(), updated.getCost());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateCost(updated.getId(), updated.getCost());
        assertEquals(updated.getCost(), returned.get().getCost());
    }

    @Test
    void updateManufactureYear() {
        Car updated = new Car((long)2,"Ford", "Focus", 50000, LocalDate.parse("2011-11-21"));
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateManufactureYear(updated.getId(), updated.getManufactureYear());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateManufactureYear(updated.getId(), updated.getManufactureYear());
        assertEquals(updated.getManufactureYear(), returned.get().getManufactureYear());
    }

    @Test
    void save() {
        Car newCar = new Car((long)3,"Chevrolet", "Cruze", 56000, LocalDate.parse("2010-03-25"));
        when(carRepo.save(newCar)).thenReturn(newCar);

        Car returned = carManager.save(newCar);

        verify(carRepo, times(1)).save(newCar);
        assertEquals(newCar, returned);
    }

    @Test
    void deleteById() {
        Car deleteCar = new Car((long)1,"BMW", "E36", 30000, LocalDate.parse("2000-02-23"));
        when(carRepo.findById(deleteCar.getId())).thenReturn(java.util.Optional.of(deleteCar));

        Optional<Car> returned = carManager.deleteById(deleteCar.getId());

        verify(carRepo, times(1)).findById(deleteCar.getId());
        verify(carRepo, times(1)).deleteById(deleteCar.getId());
        assertEquals(deleteCar.getId(), returned.get().getId());
        assertEquals(deleteCar.getModel(), returned.get().getModel());
        assertEquals(deleteCar.getCost(), returned.get().getCost());
        assertEquals(deleteCar.getBrand(), returned.get().getBrand());
        assertEquals(deleteCar.getManufactureYear(), returned.get().getManufactureYear());
    }
}