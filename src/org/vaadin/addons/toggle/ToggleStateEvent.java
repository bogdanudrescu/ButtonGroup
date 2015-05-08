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
     * @param selected
     *            the selected state of the {@link ToggleExtension}.
     */
    public ToggleStateEvent(ToggleExtension source, boolean selected) {
        this.source = source;
        this.selected = selected;
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
    public boolean isSelected() {
        return selected;
    }

}
