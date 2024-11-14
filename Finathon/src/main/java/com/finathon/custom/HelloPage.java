package com.finathon.custom;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public class HelloPage extends WebPage {
	public HelloPage() {
		// Display welcome message to the logged-in user
		System.out.println(getSession());
		add(new Label("message", "Hello, " + getSession()));
	}
}
