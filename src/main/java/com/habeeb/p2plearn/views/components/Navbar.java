package com.habeeb.p2plearn.views.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class Navbar extends HorizontalLayout {
    public Navbar(){
        setPadding(false);
        setMargin(false);
        setSpacing(false);
        addClassName("navbar");
        getStyle().set("background","linear-gradient(135deg, rgb(20, 15, 122) 0%, rgb(65, 15, 122) 50%, rgb(101, 15, 122) 100%)")
                .set("height","8vh")
                .set("display","flex")
                .set("align-items","center")
                .set("justify-content","space-between")
                .set("padding-left","100px")
                .set("padding-right","100px")
                ;
        Span title = new Span("P2PLearn");
        title.addClassName("title");
        title.getStyle()
                .set("color","white")
                .set("font-size","18px")
                .set("font-weight","bold");
        add(title);
        HorizontalLayout navItems = new HorizontalLayout();
        navItems.addClassName("nav-item");
        navItems.getStyle().set("display","flex")
                .set("flex-direction","row")
                .set("flex-wrap","no-wrap")
                .set("align-items","center")
                .set("justify-content","space-between");
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search articles...");
        searchField.addClassName("search");

        searchField.getStyle()
                .set("padding","0")
                ;
        navItems.add(searchField);
        add(navItems);

    }
}