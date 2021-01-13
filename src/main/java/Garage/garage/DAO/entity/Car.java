package Garage.garage.DAO.entity;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Car {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String brand;
    private String model;
    private double prize;
    private double quantity;
    private LocalDate manufactureYear;

    public Car() {
    }

    public Car(Long id, String brand, String model, double prize, double quantity, LocalDate manufactureYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.prize = prize;
        this.quantity = quantity;
        this.manufactureYear = manufactureYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(LocalDate manufactureYear) {
        this.manufactureYear = manufactureYear;
    }
}
