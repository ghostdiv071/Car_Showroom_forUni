package com.minakov.views.model;

import com.minakov.entity.model.Model;
import com.minakov.service.modelService.ModelService;
import com.minakov.views.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Set;
import java.util.TreeSet;

@Route(value = "model", layout = MainView.class)
@PageTitle("Model")
@CssImport("./styles/views/color/color-view.css")
public class ModelView extends Div {

    private Binder<Model> binder;

    private ModelService service = new ModelService();

    private Grid<Model> grid = new Grid<>(Model.class);

    private TextField name;
    private ComboBox<String> brand;

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");
    private Model model;

    public ModelView() {
        setId("color-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        grid.setColumns("name", "brand");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.setItems(service.getAll());

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Model modelFromBack = service.get(event.getValue().getId());
                populateForm(modelFromBack);
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(Model.class);

        binder.bindInstanceFields(this);

        binder.forField(name).asRequired().bind(Model::getName, Model::setName);
        binder.forField(brand).asRequired().bind(Model::getBrand, Model::setBrand);

        save.addClickListener(e -> {
            try {
                if (this.model == null) {
                    this.model = new Model();
                }
                binder.writeBean(this.model);
                service.save(this.model);
                clearForm();
                refreshGrid();
                Notification.show("Data saved.");
            } catch (ValidationException exception) {
                Notification.show("An exception happened while trying to store the information.");
            }
        });

        delete.addClickListener(e -> {
            if (this.model == null) {
                this.model = new Model();
            }
            service.delete(this.model.getId());
            clearForm();
            refreshGrid();
            Notification.show("Data deleted.");
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        brand = new ComboBox<>("Brand");
        Set<String> brands = new TreeSet<>();
        for (Model temp : service.getAll()) {
            brands.add(temp.getBrand());
        }
        brand.setItems(brands);
        AbstractField<?, ?>[] fields = new AbstractField<?, ?>[]{name, brand};

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
        buttonLayout.add(save, delete, cancel);
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

    private void populateForm(Model value) {
        this.model = value;
        binder.readBean(this.model);
    }
}
