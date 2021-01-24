package Garage.garage.Controller;

import Garage.garage.DAO.entity.Car;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
class EmployeesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port = 8080;

    private static final String CLIENT_NAME = "User1";
    private static final String CLIENT_PASSWORD = "User1";

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    void getCars() {
        ResponseEntity<Car[]> response = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars/", Car[].class);
        Integer cost = 30000;

        List<Car> car1 = Arrays.asList(response.getBody().clone());

        assertEquals(car1.get(0).getBrand(), "BMW");
        assertEquals(car1.get(0).getCost(), cost);
        assertEquals(car1.get(1).getManufactureYear(), LocalDate.parse("1997-05-20"));
        assertEquals(car1.get(1).getModel(), "Berlingo");
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Assert.assertNotNull(response.getBody());
    }

    @Test
    void getByBrand() {
        ResponseEntity<Car[]> response = restTemplate.withBasicAuth(CLIENT_NAME, CLIENT_PASSWORD)
                .getForEntity(getRootUrl() + "/employees/cars" + "/BMW", Car[].class);
        Integer cost = 30000;

        List<Car> car1 = Arrays.asList(response.getBody().clone());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Assert.assertNotNull(response.getBody());
        assertEquals(car1.get(0).getBrand(), "BMW");
        assertEquals(car1.get(0).getCost(), cost);
        assertEquals(1, car1.size());
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
        Assert.assertNotNull(getResponse.getBody());
        assertEquals(car1.get(2).getBrand(), "Audi");
        assertEquals(car1.get(2).getCost(), cost);
    }

    @Test
    void updateCars() {
    }

    @Test
    void deleteCars() {
    }
}