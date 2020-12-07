package Garage.garage.GUI;

import Garage.garage.DAO.entity.Car;
import Garage.garage.manager.CarManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Route("Grid")
public class LoadGUI extends VerticalLayout {

    private final Grid<Car> carGrid;
    private Set<Car> selectedCar;
    private CarManager carManager;

    @Autowired
    public LoadGUI(CarManager carManager) {
        this.carGrid = new Grid<>(Car.class);
        this.carManager = carManager;

        Button deleteCarsButton = new Button("Delete cars", new Icon(VaadinIcon.TRASH));
        deleteCarsButton.setIconAfterText(true);
        deleteCarsButton.addClickListener(e -> {
            for(Iterator<Car> iteratorCar = selectedCar.iterator(); iteratorCar.hasNext();){
                Car car = iteratorCar.next();
                carManager.deleteById(car.getId());
            }
            loadListOfCars();
        });

        Button addCarsButton = new Button("Add cars", new Icon(VaadinIcon.CAR));
        addCarsButton.setIconAfterText(true);
        addCarsButton.addClickListener(e -> {

        });

        add(carGrid, deleteCarsButton, addCarsButton);
        gridCustom();
        loadListOfCars();
        rowsSelect();
    }

    private void gridCustom(){
        carGrid.setColumnReorderingAllowed(true);
        carGrid.setColumns("id", "brand", "model", "parking", "plate");
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

}
