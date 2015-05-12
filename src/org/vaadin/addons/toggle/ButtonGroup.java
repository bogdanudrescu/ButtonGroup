package org.vaadin.addons.toggle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.Button;

/**
 * Layout buttons in a group with the possibility to toggle them.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
public class ButtonGroup implements Serializable {

    /*
     * The list of buttons in this group.
     */
    private List<Button> buttons = new ArrayList<Button>();

    /*
     * The toggle button extensions.
     */
    private Map<Button, ToggleExtension> toggleExtensions = new HashMap<>();

    /*
     * The current selected button.
     */
    private Button selectedButton;

    /*
     * The click listener that will handle the toggle functionality.
     */
    private ToggleStateHandler toggleStateListener = new ToggleStateHandler();

    /**
     * Create an empty button group.
     */
    public ButtonGroup() {
    }

    /**
     * Creates a button group with some default buttons.
     * 
     * @param buttonCaptions
     *            the captions of the default buttons to add to the group.
     */
    public ButtonGroup(String... buttonCaptions) {
        addButtons(buttonCaptions);
    }

    /**
     * Creates a button group with some default buttons.
     * 
     * @param button
     *            the default buttons to add to the group.
     */
    public ButtonGroup(Button... buttons) {
        addButtons(buttons);
    }

    /**
     * Adds some buttons to the button group.
     * 
     * @param buttonCaptions
     *            the captions of the default buttons to add to the group.
     */
    public void addButtons(String... buttonCaptions) {
        for (String buttonCaption : buttonCaptions) {
            addButton(buttonCaption);
        }
    }

    /**
     * Adds some buttons to the button group.
     * 
     * @param button
     *            the default buttons to add to the group.
     */
    public void addButtons(Button... buttons) {
        for (Button button : buttons) {
            addButton(button);
        }
    }

    /**
     * Adds a button with the given caption to this button group. Group will be
     * filled from left to right.
     * 
     * @param buttonCaption
     *            the button caption.
     * @return the button instance.
     */
    public Button addButton(String buttonCaption) {
        return addButton(new Button(buttonCaption));
    }

    /**
     * Adds a button to the group.
     * 
     * @param button
     *            the button to add.
     * @return the added button.
     */
    public Button addButton(Button button) {
        buttons.add(button);

        setupButton(button);

        if (delegate != null) {
            delegate.buttonAdded(button);
        }

        return button;
    }

    /**
     * Sets the toggle style for the specified button.
     * 
     * @param button
     *            the button to set the style for.
     * @param selectedStyle
     *            the style to apply when the button is toggled.
     */
    public void setSelectedStyle(Button button, String selectedStyle) {
        getToggleExtension(button).getState().selectedStyle = selectedStyle;
    }

    /*
     * Gets the toggle extension of the specified button.
     */
    private ToggleExtension getToggleExtension(Button button) {
        return toggleExtensions.get(button);
    }

    /**
     * Remove the specified button from the group.
     * 
     * @param button
     *            the button to remove.
     * @return true if the button was removed, otherwise false if it doesn't
     *         belong to the group.
     */
    public boolean removeButton(Button button) {
        if (buttons.remove(button)) {
            unsetupButton(button);

            return true;
        }

        return false;
    }

    /*
     * Make sure at least one button is selected.
     */
    private void setupButton(Button button) {
        // Create the extension to toggle the button on the client side as well.
        ToggleExtension toggleExtension = new ToggleExtension(button);

        // Do not allow deselecting the buttons.
        toggleExtension.getState().allowDeselect = false;
        toggleExtension.addToggleStateListener(toggleStateListener);
        toggleExtensions.put(button, toggleExtension);

        if (selectedButton == null) {
            setSelectedButton(button);
        }
    }

    /*
     * Unselect the button when removed and clear any reference with the group.
     */
    private void unsetupButton(Button button) {
        // Removes the extension for the toggle button.
        ToggleExtension toggleButtonExtension = toggleExtensions.remove(button);
        toggleButtonExtension.removeToggleStateListener(toggleStateListener);
        toggleButtonExtension.remove();

        if (selectedButton == button) {
            setSelectedButtonIndex(0);
        }

        if (delegate != null) {
            delegate.buttonRemoved(button);
        }
    }

    /**
     * Gets the index of the specified button.
     * 
     * @param button
     *            the button to get the index for.
     * @return the index of the specified button.
     */
    public int indexOfButton(Button button) {
        return buttons.indexOf(button);
    }

    /**
     * Gets the button at the specified index.
     * 
     * @param index
     *            the index of the button.
     * @return the button at the specified index.
     */
    public Button getButton(int index) {
        return buttons.get(index);
    }

    /**
     * Gets the array of buttons.
     * 
     * @return the array of buttons.
     */
    public Button[] getButtons() {
        return buttons.toArray(new Button[buttons.size()]);
    }

    /**
     * Gets the index of the selected button.
     * 
     * @return the index of the selected button.
     */
    public int indexOfSelectedButton() {
        return indexOfButton(selectedButton);
    }

    /**
     * Gets the selected button instance.
     * 
     * @return the selected button.
     */
    public Button getSelectedButton() {
        return selectedButton;
    }

    /**
     * Gets the button count.
     * 
     * @return the number of buttons in the group.
     */
    public int countButtons() {
        return buttons.size();
    }

    /**
     * Gets the buttons iterator.
     * 
     * @return the buttons iterator.
     */
    public Iterator<Button> iterator() {
        return buttons.iterator();
    }

    /**
     * Toggle the specified button.
     * 
     * @param button
     *            the button to toggle.
     * @return true if the button was toggled, otherwise false.
     */
    public boolean setSelectedButton(Button button) {
        if (button != null && button != selectedButton
                && buttons.contains(button)) {

            // Stop listening to toggle extensions to avoid endless events
            // cycle.
            toggleStateListener.ignoreEvents = true;

            try {
                Button previousButton = selectedButton;

                // Remove the selection from the previous selected button.
                for (ToggleExtension toggleExtension : toggleExtensions
                        .values()) {
                    toggleExtension.setSelected(false);
                }

                // Add the selection on the new selected button.
                getToggleExtension(button).setSelected(true);

                // Set the new selected button.
                selectedButton = button;

                notifySelectionListeners(selectedButton, previousButton);

            } finally {
                // Listen again to the state events from the toggle extensions.
                toggleStateListener.ignoreEvents = false;
            }

            return true;
        }

        return false;
    }

    /**
     * Toggle the button at the specified index.
     * 
     * @param buttonIndex
     *            the index of the button to toggle.
     * @return true if any button was toggled, otherwise false.
     */
    public boolean setSelectedButtonIndex(int buttonIndex) {
        return setSelectedButton(getButton(buttonIndex));
    }

    /*
     * Handle the toggle functionality when a button is clicked.
     */
    private class ToggleStateHandler implements ToggleStateListener {

        /*
         * Whether to ignore the events.
         */
        boolean ignoreEvents;

        @Override
        public void toggleStateChanged(ToggleStateEvent event) {
            if (ignoreEvents) {
                return;
            }

            setSelectedButton(event.getSource().getButton());
        }

    }

    /*
     * The list of listeners to send the events to.
     * 
     * FIXME: Don't like to use the EventRouter because it uses reflection.
     * That's slow.
     */
    private List<ButtonGroupSelectionListener> listeners;

    /**
     * Add a listener to be notified when a selection change occur.
     * 
     * @param listener
     *            the listener.
     */
    public void addSelectionListener(ButtonGroupSelectionListener listener) {
        if (listeners == null) {
            listeners = new LinkedList<ButtonGroupSelectionListener>();
        }

        listeners.add(listener);
    }

    /**
     * Remove the specified listener.
     * 
     * @param listener
     *            the listener to remove.
     */
    public void removeSelectionListener(ButtonGroupSelectionListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Notify the listeners when selection changes.
     * 
     * @param selectedButton
     *            new selected button.
     * @param previousButton
     *            previous selected button.
     */
    protected void notifySelectionListeners(Button selectedButton,
            Button previousButton) {
        if (selectedButton == previousButton) {
            return;
        }

        if (listeners == null) {
            return;
        }

        ButtonGroupSelectionEvent event = new ButtonGroupSelectionEvent(this,
                selectedButton, previousButton);
        for (ButtonGroupSelectionListener listener : listeners) {
            listener.buttonGroupSelectionChanged(event);
        }
    }

    /*
     * The delegate to handle the changes to the buttons list.
     */
    private ButtonGroupDelegate delegate;

    /**
     * Sets the delegate.
     * 
     * @param delegate
     *            the delegate to set.
     */
    public void setDelegate(ButtonGroupDelegate delegate) {
        this.delegate = delegate;
    }

}
