package org.vaadin.addons.toggle.client;

import org.vaadin.addons.toggle.ToggleExtension;
import org.vaadin.addons.toggle.shared.ToggleState;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VButton;
import com.vaadin.shared.ui.Connect;

/**
 * Client-side connector for the toggle button extension.
 * 
 * @author bogdanudrescu
 */
@Connect(ToggleExtension.class)
@SuppressWarnings("serial")
public class ToggleExtensionConnector extends AbstractExtensionConnector {

    /*
     * The button widget.
     */
    private VButton button;

    /*
     * Button click handler.
     */
    private HandlerRegistration clickHandler;

    @Override
    protected void extend(ServerConnector target) {
        if (clickHandler != null) {
            clickHandler.removeHandler();
        }

        button = (VButton) ((ComponentConnector) target).getWidget();

        clickHandler = button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (getState().selected && !getState().allowDeselect) {
                    return;
                }
                setToggledStyle(!getState().selected);
            }

        });
    }

    @Override
    public ToggleState getState() {
        return (ToggleState) super.getState();
    }

    @OnStateChange("selected")
    private void toggledStateChanged() {
        if (button != null) {
            setToggledStyle(getState().selected);
        }
    }

    /*
     * Sets the toggled style on the button.
     */
    private void setToggledStyle(boolean toggle) {
        if (toggle) {
            button.addStyleName(getState().toggledStyle);
        } else {
            button.removeStyleName(getState().toggledStyle);
        }
    }

}
