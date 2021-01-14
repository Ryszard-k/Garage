package Garage.garage.GUI;

import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("AddCars")
public class AddCarsGUI extends VerticalLayout {

    private CarManager carManager;

    @Autowired
    public AddCarsGUI(CarManager carManager) {
        this.carManager = carManager;

        TextField addBrand = new TextField("Brand");
        TextField addModel = new TextField("Model");
        IntegerField addPrize = new IntegerField("Prize");
        DatePicker addManufactureYear = new DatePicker("Manufacture year (dd.mm.yyyy)");
        addManufactureYear.setAutoOpen(false);
        Button addCars = new Button("Add car");

        addCars.addClickListener(e ->{
            Car newCar = new Car();
            newCar.setBrand(addBrand.getValue());
            newCar.setModel(addModel.getValue());
            newCar.setPrize(addPrize.getValue());
            newCar.setManufactureYear(addManufactureYear.getValue());
            carManager.save(newCar);

            Notification notification = new Notification(
                    "Car added", 3000, Notification.Position.TOP_START);
            notification.open();

            addBrand.clear(); addModel.clear(); addPrize.clear(); addManufactureYear.clear();
        });

        add(addBrand, addModel, addPrize, addManufactureYear, addCars);
    }
}
