package com.lukasluknar.addressbook.ui;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringComponent
@UIScope
public class ThemeView extends VerticalLayout {

    public ThemeView() {
        var themeToggle = new Checkbox("Dark theme");
        themeToggle.addValueChangeListener(e -> {
            setTheme(e.getValue());
        });

        add(themeToggle);
    }

    private void setTheme(boolean dark) {
        var js = "document.documentElement.setAttribute('theme', $0)";

        getElement().executeJs(js, dark ? Lumo.DARK : Lumo.LIGHT);
    }
}

