package org.vaadin.addons.toggle;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

/**
 * Button with toggle functionality. A toggle button is a button that is either
 * in selected or normal state.
 * 
 * @author bogdanudrescu
 */
public class ToggleButton extends Button {

    /**
     * Create a toggle button.
     */
    public ToggleButton() {
        super();
    }

    /**
     * Create a toggle button with an icon.
     * 
     * @param icon
     *            the icon to set on the button.
     */
    public ToggleButton(Resource icon) {
        super(icon);
    }

    /**
     * Create a toggle button with a caption.
     * 
     * @param caption
     *            the caption to set on the button.
     */
    public ToggleButton(String caption) {
        super(caption);
    }

    /**
     * Create a toggle button with a caption and an icon.
     * 
     * @param caption
     *            the caption to set on the button.
     * @param icon
     *            the icon to set on the button.
     */
    public ToggleButton(String caption, Resource icon) {
        super(caption, icon);
    }

    /**
     * Create a toggle button with a caption and the state listener.
     * 
     * @param caption
     *            the caption to set on the button.
     * @param listener
     *            the listener to be called when the selected state changes.
     */
    public ToggleButton(String caption, ToggleStateListener listener) {
        super(caption);
    }

}
