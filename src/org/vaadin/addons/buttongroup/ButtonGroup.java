package org.vaadin.addons.buttongroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

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
	private List<Button> buttons = new ArrayList<>();

	/*
	 * The current selected button.
	 */
	private Button selectedButton;

	/*
	 * The click listener that will handle the toggle functionality.
	 */
	private ToggleClickListener toggleClickListener = new ToggleClickListener();

	/*
	 * The list of listeners to send the events to.
	 * 
	 * FIXME: Don't like to use the EventRouter because it uses reflection. That's slow.
	 */
	private List<ToggleButtonGroupListener> listeners;

	/**
	 * Create an empty button group.
	 */
	public ButtonGroup() {
	}

	// TODO: continue this...

	/**
	 * Adds a button at the specified index.
	 * @param button	the button to add.
	 * @param index		the index of the button.
	 * @return	the added button.
	 */
	public Button addButton(Button button, int index) {
		buttons.add(index, button);

		setupButton(button);

		return button;
	}

	/*
	 * Make sure at least one button is selected.
	 */
	private void setupButton(Button button) {
		button.addClickListener(toggleClickListener);

		if (selectedButton == null) {
			toggleButton(button);
		}
	}

	/**
	 * Gets the index of the specified button.
	 * @param button	the button to get the index for.
	 * @return	the index of the specified button.
	 */
	public int indexOfButton(Button button) {
		return buttons.indexOf(button);
	}

	/**
	 * Gets the button at the specified index.
	 * @param index	the index of the button.
	 * @return	the button at the specified index.
	 */
	public Button getButton(int index) {
		return buttons.get(index);
	}

	/**
	 * Gets the index of the selected button.
	 * @return	the index of the selected button.
	 */
	public int indexOfSelectedButton() {
		return indexOfButton(selectedButton);
	}

	/**
	 * Gets the selected button instance.
	 * @return	the selected button.
	 */
	public Button getSelectedButton() {
		return selectedButton;
	}

	/*
	 * The toggled style name.
	 */
	private final static String CSS_STYLE_TOGGLED = "toggled"; // "v-pressed";// // "v-button-toggled";// 

	/**
	 * Toggle the specified button.
	 * 
	 * FIXME: or name it setSelectedButton, or offer both APIs.
	 * 
	 * @param button	the button to toggle.
	 * @return	true if the button was toggled, otherwise false.
	 */
	public boolean toggleButton(Button button) {
		if (button != null && button != selectedButton && buttons.contains(button)) {

			// Remove the selection from the previous selected button.
			int previousButtonIndex = indexOfButton(selectedButton);
			selectedButton.removeStyleName(CSS_STYLE_TOGGLED);

			// Add the selection on the new selected button.
			int selectedButtonIndex = indexOfButton(button);
			button.addStyleName(CSS_STYLE_TOGGLED);

			// Set the new selected button.
			selectedButton = button;

			notifyListener(selectedButtonIndex, previousButtonIndex);

			return true;
		}

		return false;
	}

	/**
	 * Toggle the button at the specified index.
	 * 
	 * FIXME: or name it setSelectedButtonIndex, or offer both APIs.
	 * 
	 * @param buttonIndex	the index of the button to toggle.
	 * @return	true if any button was toggled, otherwise false.
	 */
	public boolean toggleButtonIndex(int buttonIndex) {
		return toggleButton(getButton(buttonIndex));
	}

	/*
	 * Handle the toggle functionality when a button is clicked.
	 */
	private class ToggleClickListener implements ClickListener {

		/* (non-Javadoc)
		 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
		 */
		@Override
		public void buttonClick(ClickEvent event) {
			System.out.println("buttonClick currentSelectedButtonIndex: " + indexOfButton(selectedButton));

			toggleButton(event.getButton());
		}

	}

	/**
	 * Add a listener to be notified when a selection change occur.
	 * @param listener	the listener.
	 */
	public void addListener(ToggleButtonGroupListener listener) {
		if (listeners == null) {
			listeners = new LinkedList<ToggleButtonGroupListener>();
		}

		listeners.add(listener);
	}

	/**
	 * Remove the specified listener.
	 * @param listener	the listener to remove.
	 */
	public void removeListener(ToggleButtonGroupListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notify the listeners when the selection changes.
	 * @param selectedButtonIndex	the new selected button index.
	 * @param previousButtonIndex	the previous selected button index.
	 */
	protected void notifyListener(int selectedButtonIndex, int previousButtonIndex) {
		if (selectedButtonIndex == previousButtonIndex) {
			return;
		}

		if (listeners == null) {
			return;
		}

		ToggleButtonGroupEvent event = new ToggleButtonGroupEvent(this, selectedButtonIndex, previousButtonIndex);
		for (ToggleButtonGroupListener listener : listeners) {
			listener.selectedButtonChanged(event);
		}
	}

	/**
	 * Implement this to listen to the toggle button changes inside the ToggleButtonGroup.
	 */
	public static interface ToggleButtonGroupListener extends Serializable {

		/**
		 * Called when the ToggleButtonGroup selection changes.
		 * @param event	the event with the selection change details.
		 */
		void selectedButtonChanged(ToggleButtonGroupEvent event);

	}

	/**
	 * Event object for the toggled state change.
	 */
	public static class ToggleButtonGroupEvent extends EventObject {

		// FIXME: 1. Should we add the button references? I think it's better with the index, because you can still
		// access the button through the ButtonGroup API, while for a quick implementation the index is more handy.
		// 2. Then should we use "new" for the newButtonIndex or only buttonIndex, same for "old" or to use "prev".

		/*
		 * The index of the current toggled button.
		 */
		private int selectedButtonIndex;

		/*
		 * The index of the old toggled button.
		 */
		private int previousButtonIndex;

		/**
		 * Create a toggled button event.
		 * @param source			the source of the event, a ToggleButtonGroup instance.
		 * @param selectedButtonIndex	the index of the current toggled button.
		 * @param previousButtonIndex	the index of the previously toggled button.
		 */
		public ToggleButtonGroupEvent(ButtonGroup source, int selectedButtonIndex, int previousButtonIndex) {
			super(source);
			this.selectedButtonIndex = selectedButtonIndex;
			this.previousButtonIndex = previousButtonIndex;
		}

		/**
		 * Gets the source of the event, the ToggleButtonGroup with the selection changed.
		 * @return	the source ToggleButtonGroup.
		 */
		public ButtonGroup getToggleButtonGroup() {
			return (ButtonGroup) getSource(); // FIXME: This cast is not nice.
		}

		/**
		 * Gets the previous selected button index.
		 * @return	the previous selected button index.
		 */
		public int getPreviousButtonIndex() {
			return previousButtonIndex;
		}

		/**
		 * Gets the new selected button index.
		 * @return	the new selected button index.
		 */
		public int getSelectedButtonIndex() {
			return selectedButtonIndex;
		}

	}

}
