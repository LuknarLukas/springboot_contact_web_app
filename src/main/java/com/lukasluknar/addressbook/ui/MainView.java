package com.lukasluknar.addressbook.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
@Route("")
public class MainView extends VerticalLayout {


    public MainView(ContactForm contactForm, ContactListView contactListView, ThemeView themeView) {
        H1 heading = new H1("Contact List");

        add(heading, contactForm, contactListView, themeView);
    }


}