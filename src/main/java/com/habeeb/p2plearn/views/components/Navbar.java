package com.habeeb.p2plearn.views.components;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
//@CssImport(value = "././themes/p2plearn/components/style.css", themeFor = "vaadin-text-field")
public class Navbar extends HorizontalLayout {
    public Navbar(){
        addClassName("navbar");

        // Main navbar container
        HorizontalLayout navbar = new HorizontalLayout();
        navbar.setWidthFull();
        navbar.setAlignItems(HorizontalLayout.Alignment.CENTER);
        navbar.setJustifyContentMode(HorizontalLayout.JustifyContentMode.BETWEEN);
        navbar.getStyle()
                .set("background", "linear-gradient(90deg, #1e3a8a 0%, #581c87 100%)")
                .set("padding", "12px 30px")
                .set("height", "60px")
                .set("box-shadow", "0 2px 8px rgba(0, 0, 0, 0.1)");

        // Left section - Logo
        H1 logo = new H1("P2PLearn");
        logo.getStyle()
                .set("color", "white")
                .set("font-size", "22px")
                .set("font-weight", "bold")
                .set("margin", "0")
                .set("cursor", "pointer");

        // Center section - Search bar
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search articles, users...");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setWidth("400px");
        searchField.getStyle()
                .set("--vaadin-input-field-background", "rgba(255, 255, 255, 0.15)")
                .set("--vaadin-input-field-border-color", "rgba(255, 255, 255, 0.3)")
                .set("color", "white");

        // Right section - Actions
        HorizontalLayout rightSection = new HorizontalLayout();
        rightSection.setAlignItems(HorizontalLayout.Alignment.CENTER);
        rightSection.setSpacing(true);
        rightSection.getStyle().set("gap", "15px");

        // Dark mode toggle button
        Button darkModeBtn = new Button(new Icon(VaadinIcon.MOON));
        darkModeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        darkModeBtn.getStyle()
                .set("color", "white")
                .set("background", "transparent")
                .set("border", "none")
                .set("cursor", "pointer")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("padding", "0");

        // Notification button
        Button notificationBtn = new Button(new Icon(VaadinIcon.BELL));
        notificationBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        notificationBtn.getStyle()
                .set("color", "white")
                .set("background", "transparent")
                .set("border", "none")
                .set("cursor", "pointer")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("padding", "0");

        // User profile section
        HorizontalLayout userSection = new HorizontalLayout();
        userSection.setAlignItems(HorizontalLayout.Alignment.CENTER);
        userSection.setSpacing(false);
        userSection.getStyle()
                .set("gap", "10px")
                .set("cursor", "pointer");

        Avatar avatar = new Avatar("Alex Johnson");
        avatar.setImage("https://i.pravatar.cc/150?img=12");
        avatar.getStyle()
                .set("width", "35px")
                .set("height", "35px");

        Span userName = new Span("Alex Johnson");
        userName.getStyle()
                .set("color", "white")
                .set("font-size", "14px")
                .set("font-weight", "500");

        userSection.add(avatar, userName);

        // Admin button
        Button adminBtn = new Button("Admin");
        adminBtn.setIcon(new Icon(VaadinIcon.COG));
        adminBtn.getStyle()
                .set("background", "linear-gradient(135deg, #9333ea  0%, #db2777 100%)")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "6px")
                .set("padding", "8px 16px")
                .set("font-weight", "600")
                .set("cursor", "pointer")
                .set("font-size", "13px");

        rightSection.add(searchField,darkModeBtn, notificationBtn, userSection, adminBtn);

        navbar.add(logo,rightSection);
        add(navbar);

        // Container styling
        getElement().getStyle()
                .set("width", "100%")
                .set("margin", "0")
                .set("padding", "0");

    }
}