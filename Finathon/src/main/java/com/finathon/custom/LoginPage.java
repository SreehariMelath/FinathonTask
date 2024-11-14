package com.finathon.custom;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends WebPage {

    @SpringBean
    private AuthenticationService authenticationService;

    // Declare the username and password model fields
    private String username;
    private String password;

    public LoginPage() {
        // Feedback panel for error/success messages
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        // Create the form
        Form<Void> loginForm = new Form<Void>("loginForm") {
            @Override
            protected void onSubmit() {
                // Get the model values from the form components
                if (authenticationService.authenticate(username, password)) {
                    setResponsePage(HelloPage.class); // Redirect to HelloPage
                } else {
                    error("Invalid credentials");
                }
            }
        };
        add(loginForm);

        // Add text fields for username and password, binding them to models
        loginForm.add(new TextField<>("username", new Model<String>(username)));
        loginForm.add(new PasswordTextField("password", new Model<String>(password)));
    }
}
