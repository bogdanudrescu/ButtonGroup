package org.vaadin.addons.buttongroup;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.Button;

/**
 * Extension used to maintain the toggled button state on the browser side.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
public class ToggleButtonExtension extends AbstractExtension {

	/**
	 * Create an extension to handle the toggle for this button.
	 * @param button	the button with toggle functionality.
	 */
	protected ToggleButtonExtension(Button button) {
		extend(button);
	}

	/**
	 * Create an extension to handle the toggle for this button.
	 * @param button	the button to be made toggle.
	 * @return	the toggle extension.
	 */
	public static ToggleButtonExtension toggleButton(Button button) {
		return new ToggleButtonExtension(button);
	}

}
