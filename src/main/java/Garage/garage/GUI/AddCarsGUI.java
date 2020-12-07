package Garage.garage.GUI;

import Garage.garage.DAO.entity.Car;
import Garage.garage.manager.CarManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("AddGUI")
public class AddCarsGUI extends HorizontalLayout {

    private CarManager carManager;

    @Autowired
    public AddCarsGUI(CarManager carManager) {
        this.carManager = carManager;

        TextField addBrand = new TextField("Add brand");
        TextField addModel = new TextField("Add model");
        TextField addPlate = new TextField("Add plate");
        TextField addParking = new TextField("Add parking");
        Button addCars = new Button("Add cars");

        addCars.addClickListener(e ->{
            Car newCar = new Car();
            newCar.setBrand(addBrand.getValue());
            newCar.setModel(addModel.getValue());
            newCar.setPlate(addPlate.getValue());
            newCar.setParking(addParking.getValue());
            carManager.save(newCar);

            Notification notification = new Notification(
                    "Car added", 3000, Notification.Position.TOP_START);
            notification.open();

            addBrand.clear(); addModel.clear(); addPlate.clear(); addParking.clear();
        });

        add(addBrand, addModel, addPlate, addParking, addCars);
    }
}
