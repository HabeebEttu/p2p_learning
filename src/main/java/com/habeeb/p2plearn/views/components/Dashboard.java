package com.habeeb.p2plearn.views.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Dashboard extends Div {
    public Dashboard(){
        addClassName("dashboard-view");
        getStyle().set("width","100%").set("height","90vh").set("padding-top","40px")
                .set("background","linear-gradient(135deg ,#eff6ff,#faf5ff)");
        Div container = new Div();
        container.addClassName("container");
        container.getStyle().set("display","flex")
                .set("flex-direction","row")
                .set("flex-wrap","wrap")
                .set("gap","15px")
                .set("justify-content","center")
                .set("padding","100px,50px,0,50px");

        Div welcomeCard = new Div();
        welcomeCard.addClassName("welcome-card");
        welcomeCard.getStyle().set("padding","1.5rem")
                .set("background","linear-gradient(135deg,#2563eb,#9333ea)")
                .set("width","100%")
                .set("height","fit-content")
                .set("display","flex")
                .set("flex-direction","column")
                .set("gap","8px")
                .set("border-radius","15px")
                .set("max-width","930px")
                .set("box-shadow"," 0 1px 3px 0 rgb(0 0 0 / .1), 0 1px 2px -1px rgb(0 0 0 / .1)");
        welcomeCard.add(new H1("Welcome back, Alex John!"){
            {
        getStyle().set("color","white").set("font-weight","bold").set("font-size","25px");
            }
        }, new Paragraph("Continue your learning journey and earn more XP"){{
            getStyle().set("color","rgba(204,204,254,0.7)").set("font-size","16px");
        }}, new Div(){{
            getStyle().set("display","flex").set("flex-direction","row").set("align-items","center");
            Icon star = VaadinIcon.STAR.create();
            star.setColor("gold");
            star.setSize("19px");
            add(star);
            add(new Span("2450 XP"){{
                getStyle().set("color","white").set("font-size","18px").set("padding-left","6px");
            }});
            add(new Div(){{
                add(new Span("Advanced Learner"){{getStyle().set("font-size","12px");}});
                getStyle().set("background","rgb(255 255 255 / 0.2)")
                        .set("border-radius","10px")
                        .set("color","white")
                        .set("margin-left","19px")
                        .set("padding","5px")
                        .set("display","flex").set("align-items","center").set("font-weight","semibold");
            }});

        }});
        Div leftColumn=new Div();
        leftColumn.getStyle().set("display","flex")
                .set("align-items","start")
                .set("flex-direction","column")
                .set("width","50%")
                .set("gap","20px")
                .set("height","100%");

        Div rightColumn=new Div();
        rightColumn.getStyle().set("display","flex")
                .set("align-items","center")
                .set("flex-direction","column")
                .set("width","30%")
                .set("height","100%");
    leftColumn.add(welcomeCard);
    leftColumn.add(new Div(){{
        getStyle().set("padding","1.5rem")
                .set("background","white")
                .set("width","100%")
                .set("height","fit-content")
                .set("display","flex")
                .set("flex-direction","column")
                .set("gap","15px")
                .set("border-radius","15px")
                .set("max-width","930px")
                .set("box-shadow"," 0 1px 3px 0 rgb(0 0 0 / .1), 0 1px 2px -1px rgb(0 0 0 / .1)");
        add(new H1("Latest Articles"){{getStyle().set("color","black").set("font-weight","semibold").set("font-size","20px");}});
        add(new ArticleListTile());
        add(new ArticleListTile());
    }});

        container.add(leftColumn);
        container.add(rightColumn);

        add(container);


    }
}
