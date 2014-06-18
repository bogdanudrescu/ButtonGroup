package org.vaadin.addons.buttongroup.test;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.buttongroup.HorizontalButtonGroupLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * Test the ButtonGroup.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
@Theme("buttongrouplayout")
public class ButtonGroupTestUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ButtonGroupTestUI.class, widgetset = "org.vaadin.addons.buttongroup.ButtonGroupLayoutWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected void init(VaadinRequest request) {
		HorizontalButtonGroupLayout layout = new HorizontalButtonGroupLayout("Left", "Center", "Right");

		setContent(layout);
	}

}
