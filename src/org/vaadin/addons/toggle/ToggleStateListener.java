package org.vaadin.addons.toggle;

/**
 * Notify when the toggle state of a {@link ToggleExtension} changes.
 * 
 * @author bogdanudrescu
 */
public interface ToggleStateListener {

    /**
     * Method called when the selection state of the {@link ToggleExtension}
     * changes.
     * 
     * @param event
     *            the event containing the state of the extension.
     */
    void toggleStateChanged(ToggleStateEvent event);

}
