package com.habeeb.p2plearn.views;

import com.habeeb.p2plearn.views.components.Dashboard;
import com.habeeb.p2plearn.views.components.Navbar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("dashboard/home")
@PageTitle("p2p home")
public class HomeView extends Div {
 public HomeView(){
     setWidthFull();
     setHeightFull();
    add(new Navbar());
    add(new Dashboard());
 }
}
