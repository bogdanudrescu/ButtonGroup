package org.vaadin.addons.toggle;

import com.vaadin.ui.Button;

/**
 * Implement this to listen when the contained buttons of the group are added or
 * removed.
 * <p>
 * It's usefull when implementing specific layout using only buttons managed by
 * a {@link ButtonGroup}.
 * 
 * @author bogdanudrescu
 */
public interface ButtonGroupDelegate {

    /*
     * Called when a button is added.
     */
    void buttonAdded(Button button);

    /*
     * Called when a button is removed.
     */
    void buttonRemoved(Button button);

}
