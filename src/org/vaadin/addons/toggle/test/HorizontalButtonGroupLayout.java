package org.vaadin.addons.toggle.test;

import java.util.Iterator;

import org.vaadin.addons.toggle.ButtonGroup;
import org.vaadin.addons.toggle.ButtonGroupDelegate;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * Horizontal layout for the button group.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
public class HorizontalButtonGroupLayout extends HorizontalLayout implements
        ButtonGroupDelegate {

    /*
     * The button group.
     */
    private ButtonGroup buttonGroup;

    /**
     * Create a horizontal button group layout with an empty button group.
     */
    public HorizontalButtonGroupLayout() {
        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.setDelegate(this);
    }

    /**
     * Create a new horizontal button group layout to actually display the
     * buttons from the group.
     * 
     * @param buttonGroup
     *            the button group.
     */
    public HorizontalButtonGroupLayout(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;

        Iterator<Button> iterator = buttonGroup.iterator();
        while (iterator.hasNext()) {
            Button button = (Button) iterator.next();
            super.addComponent(button);
        }

        this.buttonGroup.setDelegate(this);
    }

    /**
     * Create a new horizontal button group layout with some buttons.
     * 
     * @param buttonCaptions
     *            the captions of the default buttons to add to the group.
     */
    public HorizontalButtonGroupLayout(String... buttonCaptions) {
        this(new ButtonGroup(buttonCaptions));
    }

    /**
     * Create a new horizontal button group layout with some buttons.
     * 
     * @param button
     *            the default buttons to add to the group.
     */
    public HorizontalButtonGroupLayout(Button... buttons) {
        this(new ButtonGroup(buttons));
    }

    /**
     * Gets the button group.
     * 
     * @return the button group.
     */
    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    @Override
    public void buttonAdded(Button button) {
        super.addComponent(button);
    }

    @Override
    public void buttonRemoved(Button button) {
        super.removeComponent(button);
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void addComponent(Component c) {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void addComponent(Component c, int index) {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void addComponentAsFirst(Component c) {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void addComponents(Component... components) {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void removeComponent(Component c) {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

    /**
     * @deprecated Use {@link #getButtonGroup()} instead
     */
    @Override
    public void removeAllComponents() {
        throw new UnsupportedOperationException("Use ButtonGroup instead");
    }

}