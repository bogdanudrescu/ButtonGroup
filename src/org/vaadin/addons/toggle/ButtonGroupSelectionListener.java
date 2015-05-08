package org.vaadin.addons.toggle;

import java.io.Serializable;

/**
 * Implement this to listen to the toggle changes inside the {@link ButtonGroup}
 * .
 * 
 * @author bogdanudrescu
 */
public interface ButtonGroupSelectionListener extends Serializable {

    /**
     * Called when the {@link ButtonGroup} selected button changes.
     * 
     * @param event
     *            the event containing the selection change details.
     */
    void buttonGroupSelectionChanged(ButtonGroupSelectionEvent event);

}