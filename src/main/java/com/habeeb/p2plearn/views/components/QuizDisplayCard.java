package com.habeeb.p2plearn.views.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class QuizDisplayCard extends Div {
    public QuizDisplayCard(String title){
        getStyle()
                .set("background","linear-gradient(#f3e8ff,#dbeafe)")
                .set("padding","16px")
                .set("border-radius","8px")
                .set("cursor","pointer");
        add(getQuizHeaderRow(title));

    }
    public Div getQuizHeaderRow(String title){
        return new Div(){{
            getStyle().set("display","flex")
                    .set("align-items","center")
                    .set("justify-content","space-between")
                    .set("margin-bottom","12px");
            add(new H4(title){{
                getStyle().set("font-weight","600")
                        .set("color","#1f2937");
            }});
            add(new Div("+200 XP"){{
                getStyle().set("display","inline-flex")
                        .set("font-weight","600")
                        .set("font-size","12px")
                        .set("padding-top","2px")
                        .set("padding-bottom","2px")
                        .set("padding-left","10px")
                        .set("padding-right","10px")
                        .set("border-radius","6px")
                        .set("color","white")
                        .set("background","#9333ea");
            }});

        }};
    }
    public Div getQuizDescription(String description){
        return new Div(description){{
            getStyle().set("color","#4b5563")
                    .set("font-size","14px")
                    .set("margin-bottom","12px");
        }};
    }
    public Div getQuizStartRow(int noOfQuestions){
        return new Div(){{
            getStyle().set("display","flex")
                    .set("align-items","center")
                    .set("justify-content","space-between");
           add(new Span(){{
               getStyle().set("font-size","14px")
                       .set("color","#6b7280");
               Icon quesionIcon = VaadinIcon.QUESTION_CIRCLE_O.create();
               add(quesionIcon);
               add(" ");
               add(noOfQuestions+" Questions");
           }});
           add(new Div(){{
               getStyle().set("box-shadow","0 1px 3px 0 rgb(0 0 0 / .1), 0 1px 2px -1px rgb(0 0 0 / .1)")
                       .set("font-weight","600")
                       .set("font-size","14px")
                       .set("padding","8px 16px 8px 16px")
                       .set("background","#171717")
                       .set("white-space","no-wrap")
                       .set("gap","8px");

           }});
        }};
    }

}
