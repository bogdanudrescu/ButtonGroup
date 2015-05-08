package org.vaadin.addons.toggle;

import java.util.EventObject;

import com.vaadin.ui.Button;

/**
 * Event object for the toggled state change.
 * 
 * @author bogdanudrescu
 */
public class ButtonGroupSelectionEvent extends EventObject {

    /*
     * The current toggled button.
     */
    private Button selectedButton;

    /*
     * The previous toggled button.
     */
    private Button previousButton;

    /**
     * Create a toggled button event.
     * 
     * @param source
     *            source of the event, a {@link ButtonGroup} instance.
     * @param selectedButton
     *            current selected button.
     * @param previousButton
     *            previously selected button.
     */
    public ButtonGroupSelectionEvent(ButtonGroup source, Button selectedButton,
            Button previousButton) {
        super(source);
        this.selectedButton = selectedButton;
        this.previousButton = previousButton;
    }

    /**
     * Gets the source of the event, the ToggleButtonGroup with the selection
     * changed.
     * 
     * @return the source ToggleButtonGroup.
     */
    public ButtonGroup getButtonGroup() {
        return (ButtonGroup) getSource();
    }

    /**
     * Gets the previous selected button.
     * 
     * @return the previous selected button.
     */
    public Button getPreviousButton() {
        return previousButton;
    }

    /**
     * Gets the new selected button.
     * 
     * @return the new selected button.
     */
    public Button getSelectedButton() {
        return selectedButton;
    }

}
