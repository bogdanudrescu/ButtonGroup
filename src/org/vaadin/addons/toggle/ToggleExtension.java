package org.vaadin.addons.toggle;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.toggle.shared.ToggleState;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Extension used to maintain the toggled button state on the browser side.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
public class ToggleExtension extends AbstractExtension {

    /*
     * The list of state change listeners.
     */
    private List<ToggleStateListener> listeners = new ArrayList<>();

    /**
     * Create an extension to handle the toggle for this button.
     * 
     * @param button
     *            the button with toggle functionality.
     */
    public ToggleExtension(Button button) {
        extend(button);
    }

    /**
     * Sets the selected state of the button.
     * 
     * @param selected
     *            the selected state of the button.
     */
    public void setSelected(boolean selected) {
        if (selected != getState(false).selected) {
            getState(true).selected = selected;

            fireStateChange(new ToggleStateEvent(this, selected));
        }
    }

    /**
     * Gets the selected state of the button.
     * 
     * @return true if the button is selected, otherwise false.
     */
    public boolean isSelected() {
        return getState(false).selected;
    }

    /**
     * Sets the style activated when the button is selected.
     * 
     * @param toggleStyle
     *            the toggle style.
     */
    public void setToggleStyle(String toggleStyle) {
        getState(true).toggledStyle = toggleStyle;
    }

    @Override
    protected void extend(AbstractClientConnector target) {
        if (!(target instanceof Button)) {
            throw new IllegalArgumentException(
                    "target argument must be a Button.");

        } else {
            Button oldButton = (Button) getParent();
            if (oldButton != null) {
                oldButton.removeClickListener(clickListener);
            }

            Button button = (Button) target;
            button.addClickListener(clickListener);
        }

        super.extend(target);
    }

    /**
     * Gets the extended button.
     * 
     * @return the button applying this extension.
     */
    public Button getButton() {
        return (Button) getParent();
    }

    /*
     * Click listener to switch the toggle state.
     */
    private ClickListener clickListener = new ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            if (isSelected() && !getState(false).allowDeselect) {
                return;
            }
            setSelected(!isSelected());
        }
    };

    @Override
    public ToggleState getState() {
        return (ToggleState) super.getState();
    }

    @Override
    protected ToggleState getState(boolean markAsDirty) {
        return (ToggleState) super.getState(markAsDirty);
    }

    /**
     * Adds the specified listener to be notified when the selected state
     * change.
     * 
     * @param listener
     *            the listener to add.
     */
    public void addToggleStateListener(ToggleStateListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the specified listener
     * 
     * @param listener
     *            the listener to add.
     */
    public void removeToggleStateListener(ToggleStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fire the specified event.
     * 
     * @param event
     *            the event to fire.
     */
    protected void fireStateChange(ToggleStateEvent event) {
        for (ToggleStateListener listener : listeners) {
            listener.toggleStateChanged(event);
        }
    }

}
