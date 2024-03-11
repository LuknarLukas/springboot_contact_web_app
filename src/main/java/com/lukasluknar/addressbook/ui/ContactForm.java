package com.lukasluknar.addressbook.ui;

import com.lukasluknar.addressbook.model.Contact;
import com.lukasluknar.addressbook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class ContactForm extends FormLayout {

    private final ContactListView contactListView;
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");
    private final TextField phone = new TextField("Phone");



    private final ContactService contactService;


    @Autowired
    public ContactForm(ContactService contactService, ContactListView contactListView) {
        this.contactService = contactService;
        this.contactListView = contactListView;

        Button cancelButton = new Button("Cancel");
        cancelButton.setMaxWidth("100px");
        cancelButton.getStyle().set("margin-top", "20px");
        Button saveButton = new Button("Save");
        saveButton.setMaxWidth("100px");
        saveButton.getStyle().set("margin-top", "20px");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, email, phone,saveButton, cancelButton);
        saveButton.addClickListener(event -> save());
        cancelButton.addClickListener(event -> clearFields());
    }

    private void save() {
        Contact contact = new Contact();
        contact.setFirstName(firstName.getValue());
        contact.setLastName(lastName.getValue());
        contact.setEmail(email.getValue());
        contact.setPhone(phone.getValue());

        Notification notification;
        if (contact.getFirstName().isEmpty() || contact.getLastName().isEmpty()) {
            notification = new Notification("Failed to save contact, First Name and Last Name cannot be empty", 5000, Notification.Position.BOTTOM_STRETCH);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            contactService.addContact(contact);
            notification = new Notification("Contact saved", 5000, Notification.Position.BOTTOM_STRETCH);
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        }
        notification.open();
        contactListView.refreshContacts(contactService.getAllContacts());
        clearFields();

    }

    private void clearFields() {
        firstName.clear();
        lastName.clear();
        email.clear();
        phone.clear();
    }
}
