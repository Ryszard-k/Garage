package Garage.garage;

public class Car {

    private Long id;
    private String brand;
    private String model;
    private String plate;
    private String parking;

    public Car() {
    }

    public Car(Long id, String brand, String model, String plate, String parking) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.parking = parking;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }
}
