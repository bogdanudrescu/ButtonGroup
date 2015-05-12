package org.vaadin.addons.toggle.shared;

import com.vaadin.shared.communication.SharedState;

/**
 * Status of the toogle button.
 * 
 * @author bogdanudrescu
 */
public class ToggleState extends SharedState {

    /**
     * The state of the toggle button, true if it's toggled, false if it's
     * normal.
     */
    public boolean selected = false;

    /**
     * Whether the button can be deselected.
     */
    public boolean allowDeselect = true;

    /**
     * The name of the style to be used when button is toggled.
     */
    public String selectedStyle = ToggleStyle.CSS_STYLE_TOGGLED;

}
