package com.habeeb.p2plearn.views.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ArticleListTile extends Div {
    public ArticleListTile(){
        getStyle().set("display","flex")
                .set("align-items","flex-start")
                .set("padding","1rem")
                .set("background","#f9fafb")
                .set("border-radius","8px")
                .set("cursor","pointer");
        Image image = new Image("/images/img.jpg","article title");
        image.getStyle().set("object-fit","cover")
                .set("border-radius","4px")
                .set("width","80px")
                .set("height","64px")
                .set("max-width","100%")
                .set("display","block")
                .set("vertical-align","middle");
        add(image);
        Div articleDetails = new Div();
        articleDetails.getStyle()
                .set("flex","1 1 0%");
        articleDetails.add(new H4("Advanced React Patterns for Modern Applications"){{
            getStyle().set("font-weight","600")
                    .set("color","#1f2937");
        }});
        articleDetails.add(new Paragraph("Learn about compound components, render props, and custom hooks..."){{
            getStyle().set("color","4b5563").set("font-size","14px").set("line-height","20px").set("margin-top","4px");
        }});
        articleDetails.add(new Div(){{
            getStyle().set("display","flex")
                    .set("align-items","center")
                    .set("font-size","14px")
                    .set("line-height","20px")
                    .set("margin-top","8px")
                    .set("color","#6b7280")
                    .set("gap","18px");
            add(new Span(){{
                getStyle().set("display","flex")
                        .set("align-items","center").set("gap","4px");
                Icon userIcon  = VaadinIcon.USER.create();
                userIcon.setSize("14px");
                add(userIcon);
                add("Sarah Chen");

            }});
            add(new Span(){{
                getStyle().set("display","flex")
                        .set("align-items","center").set("gap","4px");
                Icon clockIcon = VaadinIcon.CLOCK.create();
                clockIcon.setSize("14px");
                add(clockIcon);
                add("5 min read");

            }});
            add(new Span(){{
                getStyle().set("display","flex")
                        .set("align-items","center").set("gap","4px");
                Icon starIcon  = VaadinIcon.STAR.create();
                starIcon.setSize("14px");
                add(starIcon);
                add("+50 XP");
            }});

        }});

        add(articleDetails);
    }
}
