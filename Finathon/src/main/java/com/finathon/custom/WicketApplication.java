package com.finathon.custom;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}

	@Override
	protected void init() {
		super.init();
		// Add Spring integration for Wicket components
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}
}
