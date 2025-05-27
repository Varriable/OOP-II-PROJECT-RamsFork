package com.codewithriddler.tenant_management_system.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("dashboard")
@PageTitle("Dashboard")
@RolesAllowed("USER")  // or ADMIN if that's your role name in DB
public class DashboardView extends H1 {

    public DashboardView() {
        setText("Welcome to the Dashboard!");
    }
}
