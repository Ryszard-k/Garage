package Garage.garage.Manager;

import Garage.garage.Model.CarRepo;
import Garage.garage.Model.entity.Car;
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
    void findById() {
        when(carRepo.findById(1L))
                .thenReturn(Optional.of(new Car((long) 1, "BMW", "E36", 30000, LocalDate.parse("2000-02-23"))));

        Optional<Car> returned = carManager.findById(1L);

        assertEquals("BMW", returned.get().getBrand());
        assertEquals("E36", returned.get().getModel());
        assertEquals(30000, returned.get().getCost());
        assertEquals(LocalDate.parse("2000-02-23"), returned.get().getManufactureYear());
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