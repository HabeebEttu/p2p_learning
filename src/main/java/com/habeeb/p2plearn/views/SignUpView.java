package com.habeeb.p2plearn.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/auth/signup")
@PageTitle("create an account ")
public class SignUpView extends Div {
    public SignUpView() {
        addClassName("signup-view");
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setAlignItems(VerticalLayout.Alignment.CENTER);
        mainLayout.setJustifyContentMode(VerticalLayout.JustifyContentMode.CENTER);
        mainLayout.addClassName("main-layout");
        mainLayout.setSizeFull();
        getElement().getStyle()
                .set("width", "100%")
                .set("height", "100vh")
                .set("background", "linear-gradient(135deg, #1e3c72 0%, #2a5298 50%, #764ba2 100%)")
                .set("margin", "0")
                .set("padding", "0");
        VerticalLayout card = new VerticalLayout();
        card.addClassName("login-form");
        card.setWidth("380px");
        card.setPadding(false);
        card.setSpacing(false);

        card.getElement().getStyle()
                .set("background", "rgba(255, 255, 255, 0.1)")
                .set("backdrop-filter", "blur(10px)")
                .set("border-radius", "16px")
                .set("padding", "40px 35px")
                .set("box-shadow", "0 8px 32px 0 rgba(0, 0, 0, 0.37)");
        H1 title = new H1("P2PLearn ");
        title.addClassName("title");
        title.getStyle().set("color","white")
                .set("font-size","28px")
                .set("font-weight","bold")
                .set("margin","0")
                .set("text-align","center");
        Span subtitle = new Span("Peer-to-Peer Learning Platform");
        subtitle.addClassName("subtitle");
        subtitle.getStyle()
                .set("color", "rgba(255, 255, 255, 0.8)")
                .set("font-size", "13px")
                .set("margin-bottom", "15px")
                .set("text-align", "center");
        H2 joinText = new H2("Join P2PLearn");
        joinText.addClassName("join-text");
        joinText.getStyle()
                .set("color", "white")
                .set("font-size", "18px")
                .set("font-weight", "600")
                .set("margin", "10px 0")
                .set("text-align", "center");
        HorizontalLayout nameFields = new HorizontalLayout();
        nameFields.setPadding(false);
        nameFields.setSpacing(false);
        nameFields.addClassName("names-field");
        nameFields.getStyle()
                .set("display","flex")
                .set("flex-direction","flex-col")
                .set("gap","10px")
                .set("margin-bottom","10px");

        TextField firstname = new TextField();
        firstname.setPlaceholder("First Name");
        firstname.setWidth("150px");
        TextField lastname = new TextField();
        lastname.setPlaceholder("Last Name");
        lastname.setWidth("150px");
        nameFields.add(firstname);
        nameFields.add(lastname);

        TextField usernameField = new TextField();
        usernameField.setPlaceholder("Email");
        usernameField.setWidthFull();
        usernameField.getStyle()
                .set("margin-bottom", "10px");

        EmailField emailField = new EmailField();
        emailField.setPlaceholder("Email");
        emailField.setWidthFull();
        emailField.getStyle()
                .set("margin-bottom", "10px");

        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Password");
        passwordField.setWidthFull();
        passwordField.getStyle()
                .set("margin-bottom", "10px");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPlaceholder("Confirm Password");
        confirmPasswordField.setWidthFull();
        confirmPasswordField.getStyle()
                .set("margin-bottom", "15px");
        Button createAccountBtn = new Button("Create Account");
        createAccountBtn.addClassName("create-account-btn");
        createAccountBtn.setWidthFull();
        createAccountBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAccountBtn.getStyle()
                .set("background", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)")
                .set("color", "white")
                .set("font-weight", "600")
                .set("border", "none")
                .set("border-radius", "6px")
                .set("height", "45px")
                .set("cursor", "pointer")
                .set("margin-bottom", "15px");
        Span divider = new Span("Or continue with");
        divider.addClassName("divider");
        divider.getStyle()
                .set("color", "rgba(255, 255, 255, 0.6)")
                .set("font-size", "13px")
                .set("text-align", "center")
                .set("margin", "8px 0 15px 0")
                .set("display", "block");
        HorizontalLayout socialButtons = new HorizontalLayout();
        socialButtons.setWidthFull();
        socialButtons.setSpacing(true);

        Button googleBtn = new Button("Google");
        googleBtn.addClassName("social-btn");
        googleBtn.setIcon(new Icon(VaadinIcon.GOOGLE_PLUS));
        googleBtn.getStyle()
                .set("background", "rgba(255, 255, 255, 0.1)")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "6px")
                .set("height", "40px")
                .set("cursor", "pointer")
                .set("flex", "1");

        Button facebookBtn = new Button("Facebook");
        facebookBtn.addClassName("social-btn");
        facebookBtn.setIcon(new Icon(VaadinIcon.FACEBOOK));
        facebookBtn.getStyle()
                .set("background", "rgba(255, 255, 255, 0.1)")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "6px")
                .set("height", "40px")
                .set("cursor", "pointer")
                .set("flex", "1");

        socialButtons.add(googleBtn, facebookBtn);

        Span signInText = new Span("Already have an account? ");
        signInText.getStyle()
                .set("color", "rgba(255, 255, 255, 0.7)")
                .set("font-size", "13px");

        Anchor signInLink = new Anchor("/auth/login");
        signInLink.setText("Sign In");
        signInLink.getStyle()
                .set("color", "white")
                .set("font-size", "13px")
                .set("cursor", "pointer")
                .set("text-decoration", "underline");

        Div signInContainer = new Div(signInText, signInLink);
        signInContainer.getStyle()
                .set("text-align", "center")
                .set("margin-top", "20px");
        card.add(
                title,
                subtitle,
                joinText,
                nameFields,
                emailField,
                passwordField,
                confirmPasswordField,
                createAccountBtn,
                divider,
                socialButtons,
                signInContainer
        );

        mainLayout.add(card);
        add(mainLayout);
    }
}
