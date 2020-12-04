package Garage.garage.GUI;

import Garage.garage.Dao.CarRepo;
import Garage.garage.Dao.Entity.Car;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.Set;

@Route()
public class LoadGUI extends VerticalLayout {

    private CarRepo carRepo;
    private final Grid<Car> carGrid;

    @Autowired
    public LoadGUI(CarRepo carRepo) {
        this.carRepo = carRepo;
        this.carGrid = new Grid<>(Car.class);

        Button deleteCarsButton = new Button("Delete cars", new Icon(VaadinIcon.TRASH));
        deleteCarsButton.setIconAfterText(true);

        add(carGrid);
        add(deleteCarsButton);
        gridCustom();
        loadListOfCars();
        rowsSelect();
    }

    private void gridCustom(){
        carGrid.setColumnReorderingAllowed(true);
        carGrid.setColumns("id", "brand", "model", "parking", "plate");
    }

    private void loadListOfCars() {
        carGrid.setItems((Collection<Car>) carRepo.findAll());
    }

    private void rowsSelect(){
        carGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        carGrid.addSelectionListener(selectionData -> {
            Set<Car> selectedCar = selectionData.getAllSelectedItems();
        });
    }
}
