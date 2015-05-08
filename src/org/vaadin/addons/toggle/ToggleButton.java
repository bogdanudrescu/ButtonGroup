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

    /*
     * Toggle extenstion.
     */
    private ToggleExtension toggleExtension;

    /**
     * Create a toggle button.
     */
    public ToggleButton() {
        super();

        init();
    }

    /**
     * Create a toggle button with an icon.
     * 
     * @param icon
     *            the icon to set on the button.
     */
    public ToggleButton(Resource icon) {
        super(icon);

        init();
    }

    /**
     * Create a toggle button with a caption.
     * 
     * @param caption
     *            the caption to set on the button.
     */
    public ToggleButton(String caption) {
        super(caption);

        init();
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

        init();
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

        init();

        toggleExtension.addToggleStateListener(listener);
    }

    /*
     * Initialize the toggle extension.
     */
    private void init() {
        toggleExtension = new ToggleExtension(this);
    }

    /**
     * Sets the selected state of the button.
     * 
     * @param selected
     *            the selected state of the button.
     */
    public void setSelected(boolean selected) {
        toggleExtension.setSelected(selected);
    }

    /**
     * Gets the selected state of the button.
     * 
     * @return true if the button is selected, otherwise false.
     */
    public boolean isSelected() {
        return toggleExtension.isSelected();
    }

    /**
     * Sets the style activated when the button is selected.
     * 
     * @param toggleStyle
     *            the toggle style.
     */
    public void setToggledStyle(String toggleStyle) {
        toggleExtension.setToggledStyle(toggleStyle);
    }

}
