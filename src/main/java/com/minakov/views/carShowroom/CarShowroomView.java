package com.minakov.views.carShowroom;

import com.minakov.entity.carShowroom.CarShowroom;
import com.minakov.service.carShowroomService.CarShowroomService;
import com.minakov.utils.Statistics;
import com.minakov.views.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@Route(value = "showrooms", layout = MainView.class)
@PageTitle("Show Rooms")
@CssImport("./styles/views/color/color-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class CarShowroomView extends Div {

    private final Binder<CarShowroom> binder;

    private final CarShowroomService service = new CarShowroomService();

    private final Grid<CarShowroom> grid = new Grid<>(CarShowroom.class);

    private TextField street;
    private IntegerField house;
    private ComboBox<String> city;

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");
    private final Button stat = new Button("Show statistics");
    private final Button cars = new Button("Show cars");

    private final Dialog dialog = new Dialog();

    private CarShowroom carShowroom;

    public CarShowroomView() {
        setId("color-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        grid.setColumns("street", "houseNumber", "city");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.setItems(service.getAll());

        addFiltersToGrid();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                CarShowroom showroomFromBack = service.get(event.getValue().getId());
                populateForm(showroomFromBack);
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(CarShowroom.class);

        binder.bindInstanceFields(this);

        binder.forField(street).asRequired().bind(CarShowroom::getStreet, CarShowroom::setCity);
        binder.forField(house).asRequired().bind(CarShowroom::getHouseNumber, CarShowroom::setHouseNumber);
        binder.forField(city).asRequired().bind(CarShowroom::getCity, CarShowroom::setCity);

        save.addClickListener(e -> {
            try {
                if (this.carShowroom == null) {
                    this.carShowroom = new CarShowroom();
                }
                binder.writeBean(this.carShowroom);
                service.save(this.carShowroom);
                clearForm();
                refreshGrid();
                Notification.show("Data saved.");
            } catch (ValidationException exception) {
                Notification.show("An exception happened while trying to store the information.");
            }
        });

        delete.addClickListener(e -> {
            if (this.carShowroom == null) {
                this.carShowroom = new CarShowroom();
            }
            service.delete(this.carShowroom.getId());
            clearForm();
            refreshGrid();
            Notification.show("Data deleted.");
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        
        stat.addClickListener(e -> showCars());

        cars.addClickListener(e -> {
            dialog.removeAll();
            dialog.open();
        });
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField streetFilter = new TextField();
        streetFilter.setPlaceholder("Filter");
        streetFilter.setClearButtonVisible(true);
        streetFilter.setWidth("100%");
        streetFilter.setValueChangeMode(ValueChangeMode.EAGER);
        streetFilter.addValueChangeListener(
                e -> grid.setItems(service.getAll(streetFilter.getValue(), 1)));
        filterRow.getCell(grid.getColumnByKey("street")).setComponent(streetFilter);

        TextField houseFilter = new TextField();
        houseFilter.setPlaceholder("Filter");
        houseFilter.setClearButtonVisible(true);
        houseFilter.setWidth("100%");
        houseFilter.setValueChangeMode(ValueChangeMode.EAGER);
        houseFilter.addValueChangeListener(
                e -> grid.setItems(service.getAll(houseFilter.getValue(), 2)));
        filterRow.getCell(grid.getColumnByKey("houseNumber")).setComponent(houseFilter);

        TextField cityFilter = new TextField();
        cityFilter.setPlaceholder("Filter");
        cityFilter.setClearButtonVisible(true);
        cityFilter.setWidth("100%");
        cityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cityFilter.addValueChangeListener(
                e -> grid.setItems(service.getAll(cityFilter.getValue(), 3)));
        filterRow.getCell(grid.getColumnByKey("city")).setComponent(cityFilter);
    }

    private void showCars() {
        dialog.removeAll();
        Chart chart = new Chart();

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("");
        chart.getConfiguration().getChart().setType(ChartType.COLUMN);

        List<Statistics> statistics = service.statistics();
        for (Statistics s : statistics) {
            configuration.addSeries(new ListSeries(s.getCity(), s.getCount()));
        }

        XAxis x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("Car Showrooms");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Amount of t axed cars");
        configuration.addyAxis(y);

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        configuration.setTooltip(tooltip);

        dialog.add(chart);
        dialog.setHeight("500px");
        dialog.setWidth("400px");
        dialog.open();
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        street = new TextField("Street");
        house = new IntegerField("House Number");
        house.setMin(1);
        city = new ComboBox<>("City");
        Set<String> cities = new TreeSet<>();
        for (CarShowroom temp : service.getAll()) {
            cities.add(temp.getCity());
        }
        city.setItems(cities);
        AbstractField<?, ?>[] fields = new AbstractField<?, ?>[]{street, house, city};

        for (AbstractField<?, ?> field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        stat.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cars.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonLayout.add(save, delete, cancel, stat);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.setItems(service.getAll());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(CarShowroom value) {
        this.carShowroom = value;
        binder.readBean(this.carShowroom);
    }
}
