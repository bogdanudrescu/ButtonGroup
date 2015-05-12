package org.vaadin.addons.toggle;

/**
 * Event fired when the selection state of the {@link ToggleExtension} changes.
 * 
 * @author bogdanudrescu
 */
public class ToggleStateEvent {

    /*
     * The source of the event.
     */
    private ToggleExtension source;

    /*
     * The selected state.
     */
    private boolean selected;

    /**
     * Create an event for the state of the {@link ToggleExtension}.
     * 
     * @param source
     *            the source of the event.
     * @param buttonSelected
     *            the selected state of the button.
     */
    public ToggleStateEvent(ToggleExtension source, boolean buttonSelected) {
        this.source = source;
        this.selected = buttonSelected;
    }

    /**
     * Gets the source of the event.
     * 
     * @return the source of the event.
     */
    public ToggleExtension getSource() {
        return source;
    }

    /**
     * The selected state of the button.
     * 
     * @return selected state of the button.
     */
    public boolean isButtonSelected() {
        return selected;
    }

}
