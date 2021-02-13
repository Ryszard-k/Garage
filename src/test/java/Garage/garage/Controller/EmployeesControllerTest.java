package Garage.garage.Controller;

import Garage.garage.DAO.UserRepo;
import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import Garage.garage.Manager.UserDetailsServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EmployeesController.class)
class EmployeesControllerTest {

    @InjectMocks
    EmployeesController employeesController;

    @MockBean
    CarManager carManager;

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserDetailsServiceImplement userDetailsServiceImplement;

    @MockBean
    private UserRepo userRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mvc = standaloneSetup(employeesController)
                .build();
    }

    private List<Car> carList() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car((long) 1, "BMW", "E36", 30000, LocalDate.parse("2000-02-23")));
        cars.add(new Car((long) 2, "Citroen", "Berlingo", 22000, LocalDate.parse("1997-05-20")));
        return cars;
    }

    @Test
    void getCars() throws Exception {
        when(carManager.findAll()).thenReturn(carList());

        this.mvc.perform(get("/employees/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(carManager, times(1)).findAll();
    }

        @Test
        void getByBrand() throws Exception {
            when(carManager.findByBrand(carList().get(1).getBrand())).thenReturn(carList().subList(1,2));

            this.mvc.perform(get("/employees/cars" + "/" + carList().get(1).getBrand())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].brand").value("Citroen"));

            verify(carManager, times(1)).findByBrand(carList().get(1).getBrand());
        }

  /*      @Test
        void modifyCar() {
        }
    */
    @Test
    void addCars() throws Exception {
        Car newCar = new Car((long) 3, "Chevrolet", "Cruze", 56000, LocalDate.parse("2010-03-25"));

        when(carManager.save(any(Car.class))).thenReturn(newCar);

        this.mvc.perform(post("/employees/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(newCar))
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Chevrolet"))
                .andExpect(jsonPath("$.model").value("Cruze"))
                .andExpect(jsonPath("$.cost").value(56000))
                .andExpect(jsonPath("$.manufactureYear").value("2010-03-25"));

        verify(carManager, times(1)).save(any(Car.class));
    }
/*
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

    public static String jsonAsString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writeValueAsString(obj);
    }
}