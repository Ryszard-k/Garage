package Garage.garage.GUI;

import Garage.garage.DAO.entity.Car;
import Garage.garage.manager.CarManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Route("Grid")
public class LoadGUI extends VerticalLayout {

    private final Grid<Car> carGrid;
    private Set<Car> selectedCar;
    private final CarManager carManager;

    @Autowired
    public LoadGUI(CarManager carManager) {
        this.carGrid = new Grid<>(Car.class);
        this.carManager = carManager;
        carGrid.setColumns("id", "brand", "model", "parking", "plate");

        Button deleteCarsButton = new Button("Delete cars", new Icon(VaadinIcon.TRASH));
        deleteCarsButton.setIconAfterText(true);
        deleteCarsButton.addClickListener(e -> {
            for(Iterator<Car> iteratorCar = selectedCar.iterator(); iteratorCar.hasNext();){
                Car car = iteratorCar.next();
                carManager.deleteById(car.getId());
            }
            loadListOfCars();
            Notification notification = new Notification(
                    "Cars deleted", 3000, Notification.Position.TOP_START);
            notification.open();
        });

        add(carGrid, deleteCarsButton);
        loadListOfCars();
        rowsSelect();
        editGridRows();
    }

    private void loadListOfCars() {
        carGrid.setItems((Collection<Car>) carManager.findAll());
    }

    private void rowsSelect(){
        carGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        carGrid.addSelectionListener(selectionData -> {
            selectedCar = selectionData.getAllSelectedItems();
        });
    }

    private void editGridRows(){
        final Long[] idEdit = new Long[1];

        Binder<Car> binder = new Binder<>(Car.class);
        Editor<Car> editor = carGrid.getEditor();
        editor.setBinder(binder);

        TextField brandEdit = new TextField();
        binder.bind(brandEdit, "brand");
        carGrid.getColumnByKey("brand").setEditorComponent(brandEdit);

        TextField modelEdit = new TextField();
        binder.bind(modelEdit, "model");
        carGrid.getColumnByKey("model").setEditorComponent(modelEdit);

        TextField plateEdit = new TextField();
        binder.bind(plateEdit, "plate");
        carGrid.getColumnByKey("plate").setEditorComponent(plateEdit);

        TextField parkingEdit = new TextField();
        binder.bind(parkingEdit, "parking");
        carGrid.getColumnByKey("parking").setEditorComponent(parkingEdit);

        carGrid.addItemDoubleClickListener(event -> {
            carGrid.getEditor().editItem(event.getItem());
            idEdit[0] = event.getItem().getId();
        });

        binder.addValueChangeListener(event -> {
            if(event.getValue().equals(brandEdit.getValue())) {
                carManager.updateBrand(idEdit[0], brandEdit.getValue());
            } else if(event.getValue().equals(modelEdit.getValue())) {
                carManager.updateModel(idEdit[0], modelEdit.getValue());
            } else if(event.getValue().equals(plateEdit.getValue())) {
                carManager.updatePlate(idEdit[0], plateEdit.getValue());}
            else if(event.getValue().equals(parkingEdit.getValue())) {
                carManager.updateParking(idEdit[0], parkingEdit.getValue());
            }
            carGrid.getEditor().refresh();
        });

        carGrid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");
    }

}
