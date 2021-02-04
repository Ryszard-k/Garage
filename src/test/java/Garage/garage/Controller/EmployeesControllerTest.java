package Garage.garage.Controller;

import Garage.garage.DAO.UserRepo;
import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import Garage.garage.Manager.UserDetailsServiceImplement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EmployeesController.class)
class EmployeesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManager carManager;

    @MockBean
    private UserDetailsServiceImplement userDetailsServiceImplement;

    @MockBean
    private UserRepo userRepo;

    private List<Car> carList(){
        List<Car> cars = new ArrayList<>();
        cars.add(new Car((long) 1,"BMW", "E36", 30000, LocalDate.parse("2000-02-23")));
        cars.add(new Car((long) 2,"Citroen", "Berlingo", 22000, LocalDate.parse("1997-05-20")));
        return  cars;
    }

    @Test
    void getCars() throws Exception {
        when(carManager.findAll()).thenReturn(carList());

        this.mvc.perform(get("/employees/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(carManager, times(1)).findAll();
    }

/*
    @Test
    void getByBrand() {
        ResponseEntity<Car[]> response = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars" + "/BMW", Car[].class);
        Integer cost = 30000;

        List<Car> car1 = Arrays.asList(response.getBody().clone());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(car1.get(0).getBrand(), "BMW");
        Assertions.assertEquals(car1.get(0).getCost(), cost);
        Assertions.assertEquals(1, car1.size());
    }

    @Test
    void modifyCar() {
    }

    @Test
    void addCars() {
        ResponseEntity<Car[]> postResponse = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .postForEntity(getRootUrl() + "/employees/cars",
                        new Car(3L, "Audi", "A3", 50000, LocalDate.parse("2015-03-12")),
                        Car[].class);

        ResponseEntity<Car[]> getResponse = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars", Car[].class);
        Integer cost = 50000;
        List<Car> car1 = Arrays.asList(getResponse.getBody().clone());

        assertThat(postResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertEquals(car1.get(2).getBrand(), "Audi");
        Assertions.assertEquals(car1.get(2).getCost(), cost);
    }

    @Test
    void updateCars() {
        restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .put(getRootUrl() + "/employees/cars" + "/2",
                        new Car(2L, "Audi", "A3", 50000, LocalDate.parse("2015-03-12")),
                        Car[].class);

        ResponseEntity<Car[]> getResponse = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars", Car[].class);
        List<Car> car1 = Arrays.asList(getResponse.getBody().clone());
        Integer cost = 50000;

        Assertions.assertEquals(car1.get(1).getBrand(), "Audi");
        Assertions.assertEquals(car1.get(1).getModel(), "A3");
        Assertions.assertEquals(car1.get(1).getCost(), cost);
        Assertions.assertEquals(car1.get(1).getManufactureYear(), LocalDate.parse("2015-03-12"));
    }

 /*   @Test
    void deleteCars() {
        restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .delete(getRootUrl() + "/employees/cars" + "/1");

        ResponseEntity<Car[]> getResponse = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars", Car[].class);
        List<Car> car1 = Arrays.asList(getResponse.getBody().clone());

        Assertions.assertEquals(1, car1.size());
    }*/
}