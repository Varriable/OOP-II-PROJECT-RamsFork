package com.codewithriddler.tenant_management_system.views;



import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Route("")
@PageTitle("Login | Tenant System")
@AnonymousAllowed
public class LoginView extends LoginOverlay {

    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, HttpServletRequest request) {
        this.authenticationManager = authenticationManager;
        this.request = request;

        setTitle("Tenant Management System");
        setDescription("Login to manage your tenants");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getHeader().setTitle("Tenant System");
        i18n.getHeader().setDescription("Please enter your credentials");
        i18n.setAdditionalInformation("Contact admin if you need access.");

        i18n.getErrorMessage().setTitle("Login failed");
        i18n.getErrorMessage().setMessage("Incorrect username or password.");

        setI18n(i18n);


        setOpened(true);

        addLoginListener(event -> {
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(event.getUsername(), event.getPassword())
                );

                // Store the authentication in the session
                HttpSession session = request.getSession(true);
                session.setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        new org.springframework.security.core.context.SecurityContextImpl(auth)
                );

                // Redirect to main view or dashboard
                getUI().ifPresent(ui -> ui.navigate("dashboard"));
                close();

            } catch (AuthenticationException e) {
                setError(true);
            }
        });
    }
}
