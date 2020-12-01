package com.minakov.views.carcase;

import com.minakov.entity.carcase.Carcase;
import com.minakov.service.carcaseService.CarcaseService;
import com.minakov.views.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import java.util.Optional;

@Route(value = "carcase", layout = MainView.class)
@PageTitle("Carcase")
@CssImport("./styles/views/color/color-view.css")
public class CarcaseView extends Div {

    private Binder<Carcase> binder;

    private CarcaseService service = new CarcaseService();

    private Grid<Carcase> grid = new Grid<>(Carcase.class);

    private TextField name;

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");

    private Carcase carcase;

    public CarcaseView() {
        setId("color-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        grid.setColumns("name");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.setItems(service.getAll());

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Carcase> carcaseFromBack = Optional.of(service.get(event.getValue().getId()));
                populateForm(carcaseFromBack.get());
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(Carcase.class);

        binder.bindInstanceFields(this);

        binder.forField(name).asRequired().bind(Carcase::getName, Carcase::setName);

        save.addClickListener(e -> {
            try {
                if (this.carcase == null) {
                    this.carcase = new Carcase();
                }
                binder.writeBean(this.carcase);
                service.save(this.carcase);
                clearForm();
                refreshGrid();
                Notification.show("Data saved.");
            } catch (ValidationException exception) {
                Notification.show("An exception happened while trying to store the information.");
            }
        });

        delete.addClickListener(e -> {
            if (this.carcase == null) {
                this.carcase = new Carcase();
            }
            service.delete(this.carcase.getId());
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
        AbstractField<?, ?>[] fields = new AbstractField<?, ?>[]{name};

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

    private void populateForm(Carcase value) {
        this.carcase = value;
        binder.readBean(this.carcase);
    }
}
