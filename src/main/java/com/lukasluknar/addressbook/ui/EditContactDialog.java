package com.lukasluknar.addressbook.ui;

import com.lukasluknar.addressbook.model.Contact;
import com.lukasluknar.addressbook.service.ContactService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;


public class EditContactDialog extends Dialog {

    private final ContactListView contactListView;

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final TextField emailField = new TextField("Email");
    private final TextField phoneField = new TextField("Phone");


    private final ContactService contactService;

    public EditContactDialog(Contact contact, ContactService contactService, ContactListView contactListView) {
        this.contactService = contactService;
        this.contactListView = contactListView;

        firstNameField.setValue(contact.getFirstName());
        lastNameField.setValue(contact.getLastName());
        emailField.setValue(contact.getEmail());
        phoneField.setValue(contact.getPhone());

        FormLayout formLayout = new FormLayout();
        formLayout.getStyle().set("font-weight", "bold");
        Button saveButton = new Button("Save");
        saveButton.setMaxWidth("100px");
        saveButton.getStyle().set("margin-top", "20px");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel");
        cancelButton.setMaxWidth("100px");
        cancelButton.getStyle().set("margin-top", "20px");

        formLayout.add(firstNameField, lastNameField, emailField, phoneField, saveButton, cancelButton);
        add(formLayout);

        saveButton.addClickListener(event -> save(contact));
        cancelButton.addClickListener(event -> close());
    }

    private void save(Contact contact) {
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        String email = emailField.getValue();
        String phone = phoneField.getValue();

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setPhone(phone);

        contactService.updateContact(contact.getId(), contact);
        contactListView.refreshContacts(contactService.getAllContacts());
        Notification notification = new Notification("Contact edited",5000, Notification.Position.BOTTOM_STRETCH);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.open();


        close();
    }
}
