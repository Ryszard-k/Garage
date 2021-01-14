package Garage.garage.GUI;

import Garage.garage.DAO.entity.Car;
import Garage.garage.Manager.CarManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Route("Grid")
public class LoadGUI extends VerticalLayout {

    private final Grid<Car> carGrid = new Grid<>(Car.class);
    private Set<Car> selectedCar;
    private final CarManager carManager;
    private TextField modelFindField = new TextField();
    private Button deleteCarsButton = new Button("Delete cars", new Icon(VaadinIcon.TRASH));

    @Autowired
    public LoadGUI(CarManager carManager) {
        this.carManager = carManager;

        configureGrid();
        configureDeleteButton();
        configureFilter();

        add(modelFindField, carGrid, deleteCarsButton);
        rowsSelect();
        editGridRows();
    }

    private void configureFilter() {
        modelFindField.setPlaceholder("Search by model");
        modelFindField.setClearButtonVisible(true);
        modelFindField.setValueChangeMode(ValueChangeMode.LAZY);
        modelFindField.addValueChangeListener(e -> carGrid.setItems(carManager.findAll(modelFindField.getValue())));

    }

    private void configureDeleteButton(){
        deleteCarsButton.setIconAfterText(true);
        deleteCarsButton.addClickListener(e -> {
            for(Iterator<Car> iteratorCar = selectedCar.iterator(); iteratorCar.hasNext();){
                Car car = iteratorCar.next();
                carManager.deleteById(car.getId());
            }
            carGrid.setItems((Collection<Car>) carManager.findAll());
            Notification notification = new Notification(
                    "Cars deleted", 3000, Notification.Position.TOP_START);
            notification.open();
        });
    }

    private void configureGrid() {
        carGrid.setColumns("id", "brand", "model", "prize", "manufactureYear");
        carGrid.getColumns().forEach(col -> col.setAutoWidth(true));
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

        IntegerField prizeEdit = new IntegerField();
        binder.bind(prizeEdit, "prize");
        carGrid.getColumnByKey("prize").setEditorComponent(prizeEdit);

        DatePicker manufactureYearEdit = new DatePicker();
        binder.bind(manufactureYearEdit, "manufactureYear");
        carGrid.getColumnByKey("manufactureYear").setEditorComponent(manufactureYearEdit);

        carGrid.addItemDoubleClickListener(event -> {
            carGrid.getEditor().editItem(event.getItem());
            idEdit[0] = event.getItem().getId();
        });

        binder.addValueChangeListener(event -> {
            if(event.getValue().equals(brandEdit.getValue())) {
                carManager.updateBrand(idEdit[0], brandEdit.getValue());
            } else if(event.getValue().equals(modelEdit.getValue())) {
                carManager.updateModel(idEdit[0], modelEdit.getValue());
            } else if(event.getValue().equals(prizeEdit.getValue())) {
                carManager.updatePrize(idEdit[0], prizeEdit.getValue());
            } else if(event.getValue().equals(manufactureYearEdit.getValue())) {
                carManager.updateManufactureYear(idEdit[0], manufactureYearEdit.getValue());
            }
            carGrid.getEditor().refresh();
        });

        carGrid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");
    }

}
