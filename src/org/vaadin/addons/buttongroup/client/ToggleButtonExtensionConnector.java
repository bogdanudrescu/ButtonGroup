package org.vaadin.addons.buttongroup.client;

import org.vaadin.addons.buttongroup.ToggleButtonExtension;
import org.vaadin.addons.buttongroup.shared.ButtonGroupStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VButton;
import com.vaadin.shared.ui.Connect;

/**
 * Client-side connector for the toggle button extension.
 * 
 * @author bogdanudrescu
 */
@Connect(ToggleButtonExtension.class)
@SuppressWarnings("serial")
public class ToggleButtonExtensionConnector extends AbstractExtensionConnector {

	/* (non-Javadoc)
	 * @see com.vaadin.client.extensions.AbstractExtensionConnector#extend(com.vaadin.client.ServerConnector)
	 */
	@Override
	protected void extend(ServerConnector target) {
		final VButton button = (VButton) ((ComponentConnector) target).getWidget();

		button.addClickHandler(new ClickHandler() {

			// FIXME: This has a drawback if this client event don't synch with the server one the toggle button style might get inconsistent.
			// The fix would be in ButtonGroup to remove the style from all buttons before setting the current one, this ways we let the 
			// server side have the final decision on witch button is selected.

			@Override
			public void onClick(ClickEvent event) {
				button.addStyleName(ButtonGroupStyle.CSS_STYLE_TOGGLED);
			}

		});
	}

}
