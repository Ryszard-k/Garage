package Garage.garage.Manager;

import Garage.garage.DAO.CarRepo;
import Garage.garage.DAO.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

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
        cars.add(new Car((long)1,"Audi", "A1", "KRK3465", "1A"));
        cars.add(new Car((long)2,"Ford", "Mustang", "GAZ1", "11B"));
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
        when(carRepo.findByBrand("Audi")).thenReturn(carList().subList(0,1));

        List<Car> returned = carManager.findByBrand("Audi");

        assertEquals("Audi", returned.get(0).getBrand());
    }

    @Test
    void updateModel() {
        Car updated = new Car((long)2,"Ford", "Focus", "GAZ1", "11B");
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateModel(updated.getId(), updated.getModel());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateModel(updated.getId(), updated.getModel());
        assertEquals(updated.getModel(), returned.get().getModel());
    }

    @Test
    void updateBrand() {
        Car updated = new Car((long)2,"BMW", "Focus", "GAZ1", "11B");
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateBrand(updated.getId(), updated.getBrand());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateBrand(updated.getId(), updated.getBrand());
        assertEquals(updated.getBrand(), returned.get().getBrand());
    }

    @Test
    void updatePlate() {
        Car updated = new Car((long)2,"Ford", "Focus", "GAZ1", "11B");
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updatePlate(updated.getId(), updated.getPlate());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updatePlate(updated.getId(), updated.getPlate());
        assertEquals(updated.getPlate(), returned.get().getPlate());
    }

    @Test
    void updateParking() {
        Car updated = new Car((long)2,"Ford", "Focus", "GAZ1", "11B");
        when(carRepo.findById(updated.getId())).thenReturn(java.util.Optional.of(updated));

        Optional<Car> returned = carManager.updateParking(updated.getId(), updated.getParking());

        verify(carRepo, times(1)).findById(updated.getId());
        verify(carRepo, times(1)).updateParking(updated.getId(), updated.getParking());
        assertEquals(updated.getParking(), returned.get().getParking());
    }

    @Test
    void save() {
        Car newCar = new Car((long)3,"BMW", "E36", "WW33465", "11B");
        when(carRepo.save(newCar)).thenReturn(newCar);

        Car returned = carManager.save(newCar);

        verify(carRepo, times(1)).save(newCar);
        assertEquals(newCar, returned);
    }

    @Test
    void deleteById() {
        Car deleteCar = new Car((long)1,"Audi", "A1", "KRK3465", "1A");
        when(carRepo.findById(deleteCar.getId())).thenReturn(java.util.Optional.of(deleteCar));

        Optional<Car> returned = carManager.deleteById(deleteCar.getId());

        verify(carRepo, times(1)).findById(deleteCar.getId());
        verify(carRepo, times(1)).deleteById(deleteCar.getId());
        assertEquals(deleteCar.getId(), returned.get().getId());
        assertEquals(deleteCar.getModel(), returned.get().getModel());
        assertEquals(deleteCar.getParking(), returned.get().getParking());
        assertEquals(deleteCar.getBrand(), returned.get().getBrand());
    }
}