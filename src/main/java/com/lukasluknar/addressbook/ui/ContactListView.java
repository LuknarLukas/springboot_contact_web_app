package com.lukasluknar.addressbook.ui;


import com.lukasluknar.addressbook.model.Contact;
import com.lukasluknar.addressbook.service.ContactService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.component.button.Button;



import java.util.List;

@SpringComponent
@UIScope
public class ContactListView extends VerticalLayout {
    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);

    public ContactListView(ContactService contactService) {
        this.contactService = contactService;


        List<Contact> contacts = contactService.getAllContacts();

        grid.setColumns("firstName", "lastName", "email", "phone");

        grid.addComponentColumn(this::createEditButton)
                .setHeader("Edit");
        grid.addComponentColumn(this::createDeleteButton)
                .setHeader("Delete");

        grid.setItems(contacts);
        grid.getStyle().set("font-weight", "bold");

        add(grid);
    }


    private Button createEditButton(Contact contact) {
        Button editButton = new Button("Edit");
        editButton.addClickListener(event -> {
            EditContactDialog dialog;
            dialog = new EditContactDialog(contact, contactService, this);
            dialog.open();
        });
        return editButton;
    }


    private Button createDeleteButton(Contact contact) {
        Button deleteButton = new Button("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(event -> {
            ConfirmDialog dialog = new ConfirmDialog("Confirm Delete",
                    "Are you sure you want to delete this contact?", "Yes", e -> {
                contactService.deleteContact(contact.getId());
                refreshContacts(contactService.getAllContacts());
                Notification notification = new Notification("Contact deleted",5000, Notification.Position.BOTTOM_STRETCH);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }, "No", e -> {
            });
            dialog.setConfirmButtonTheme("error primary");
            dialog.open();

        });
        return deleteButton;
    }


    public void refreshContacts(List<Contact> contacts) {
        grid.setItems(contacts);
    }
}
