package org.vaadin.addons.buttongroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vaadin.addons.buttongroup.shared.ButtonGroupStyle;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Layout buttons in a group with the possibility to toggle them.
 * 
 * @author bogdanudrescu
 * 
 * DON'T USE THIS: @StyleSheet(value = { "vaadin://addons/buttongroup/styles.scss" })
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
	private Map<Button, ToggleButtonExtension> toggleExtensions = new HashMap<>();

	/*
	 * The current selected button.
	 */
	private Button selectedButton;

	/*
	 * The click listener that will handle the toggle functionality.
	 */
	private ToggleClickListener toggleClickListener = new ToggleClickListener();

	/**
	 * Create an empty button group.
	 */
	public ButtonGroup() {
	}

	/**
	 * Creates a button group with some default buttons.
	 * @param buttonCaptions	the captions of the default buttons to add to the group.
	 */
	public ButtonGroup(String... buttonCaptions) {
		for (String buttonCaption : buttonCaptions) {
			addButton(buttonCaption);
		}
	}

	/**
	 * Creates a button group with some default buttons.
	 * @param button	the default buttons to add to the group.
	 */
	public ButtonGroup(Button... buttons) {
		for (Button button : buttons) {
			addButton(button);
		}
	}

	/**
	 * Adds some buttons to the button group.
	 * @param buttonCaptions	the captions of the default buttons to add to the group.
	 */
	public void addButtons(String... buttonCaptions) {
		for (String buttonCaption : buttonCaptions) {
			addButton(buttonCaption);
		}
	}

	/**
	 * Adds some buttons to the button group.
	 * @param button	the default buttons to add to the group.
	 */
	public void addButtons(Button... buttons) {
		for (Button button : buttons) {
			addButton(button);
		}
	}

	/**
	 * Adds a button with the given caption to this button group. Group will be filled from left to right.
	 * @param buttonCaption	the button caption.
	 * @return	the button instance.
	 */
	public Button addButton(String buttonCaption) {
		return addButton(new Button(buttonCaption));
	}

	/**
	 * Adds a button to the group.
	 * @param button	the button to add.
	 * @return	the added button.
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
	 * Adds a button with the given caption to this button group. Group will be filled from left to right.
	 * @param buttonCaption	the button caption.
	 * @param index			the index of the button in the group.
	 * @return	the button instance.
	 */
	public Button addButton(String buttonCaption, int index) {
		return addButton(new Button(buttonCaption), index);
	}

	/**
	 * Adds a button at the specified index.
	 * @param button	the button to add.
	 * @param index		the index of the button.
	 * @return	the added button.
	 */
	public Button addButton(Button button, int index) {
		buttons.add(index, button);

		setupButton(button);

		if (delegate != null) {
			delegate.buttonAdded(button, index);
		}

		return button;
	}

	/**
	 * Remove the specified button from the group.
	 * @param button	the button to remove.
	 * @return	true if the button was removed, otherwise false if it doesn't belong to the group.
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
		button.addClickListener(toggleClickListener);

		// Create the extension to toggle the button on the client side as well.
		toggleExtensions.put(button, ToggleButtonExtension.toggleButton(button));

		if (selectedButton == null) {
			setSelectedButton(button);
		}
	}

	/*
	 * Unselect the button when removed and clear any reference with the group.
	 */
	private void unsetupButton(Button button) {
		button.removeClickListener(toggleClickListener);

		// Removes the extension for the toggle button.
		ToggleButtonExtension toggleButtonExtension = toggleExtensions.remove(button);
		if (toggleButtonExtension != null) { // This should always be valid.
			toggleButtonExtension.remove();
		}

		if (selectedButton == button) {
			setSelectedButtonIndex(0);
		}

		if (delegate != null) {
			delegate.buttonRemoved(button);
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

	/**
	 * Gets the button count.
	 * @return	the number of buttons in the group.
	 */
	public int countButtons() {
		return buttons.size();
	}

	/**
	 * Gets the buttons iterator.
	 * @return	the buttons iterator.
	 */
	public Iterator<Button> iterator() {
		return buttons.iterator();
	}

	/**
	 * Toggle the specified button.
	 * 
	 * @param button	the button to toggle.
	 * @return	true if the button was toggled, otherwise false.
	 */
	public boolean setSelectedButton(Button button) {
		if (button != null && button != selectedButton && buttons.contains(button)) {

			// Remove the selection from the previous selected button.
			int previousButtonIndex = indexOfButton(selectedButton);
			if (selectedButton != null) {
				selectedButton.removeStyleName(ButtonGroupStyle.CSS_STYLE_TOGGLED);
			}

			// FIXME: See if this is OK, or overall, how can it be optimized.
			for (Button b : buttons) {
				b.removeStyleName(ButtonGroupStyle.CSS_STYLE_TOGGLED);
			}

			// Add the selection on the new selected button.
			int selectedButtonIndex = indexOfButton(button);
			button.addStyleName(ButtonGroupStyle.CSS_STYLE_TOGGLED);
			//button.setStyleName(CSS_STYLE_TOGGLED);

			// System.out.println("button.getStyleName(): " + button.getStyleName());

			// Set the new selected button.
			selectedButton = button;

			notifySelectionListeners(selectedButtonIndex, previousButtonIndex);

			return true;
		}

		return false;
	}

	/**
	 * Toggle the button at the specified index.
	 * 
	 * @param buttonIndex	the index of the button to toggle.
	 * @return	true if any button was toggled, otherwise false.
	 */
	public boolean setSelectedButtonIndex(int buttonIndex) {
		return setSelectedButton(getButton(buttonIndex));
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

			setSelectedButton(event.getButton());
		}

	}

	/*
	 * The list of listeners to send the events to.
	 * 
	 * FIXME: Don't like to use the EventRouter because it uses reflection. That's slow.
	 */
	private List<ButtonGroupSelectionListener> listeners;

	/**
	 * Add a listener to be notified when a selection change occur.
	 * @param listener	the listener.
	 */
	public void addSelectionListener(ButtonGroupSelectionListener listener) {
		if (listeners == null) {
			listeners = new LinkedList<ButtonGroupSelectionListener>();
		}

		listeners.add(listener);
	}

	/**
	 * Remove the specified listener.
	 * @param listener	the listener to remove.
	 */
	public void removeSelectionListener(ButtonGroupSelectionListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notify the listeners when the selection changes.
	 * @param selectedButtonIndex	the new selected button index.
	 * @param previousButtonIndex	the previous selected button index.
	 */
	protected void notifySelectionListeners(int selectedButtonIndex, int previousButtonIndex) {
		if (selectedButtonIndex == previousButtonIndex) {
			return;
		}

		if (listeners == null) {
			return;
		}

		ButtonGroupSelectionEvent event = new ButtonGroupSelectionEvent(this, selectedButtonIndex, previousButtonIndex);
		for (ButtonGroupSelectionListener listener : listeners) {
			listener.selectedButtonChanged(event);
		}
	}

	/**
	 * Implement this to listen to the toggle button changes inside the ToggleButtonGroup.
	 */
	public static interface ButtonGroupSelectionListener extends Serializable {

		/**
		 * Called when the ToggleButtonGroup selection changes.
		 * @param event	the event with the selection change details.
		 */
		void selectedButtonChanged(ButtonGroupSelectionEvent event);

	}

	/**
	 * Event object for the toggled state change.
	 */
	public static class ButtonGroupSelectionEvent extends EventObject {

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
		public ButtonGroupSelectionEvent(ButtonGroup source, int selectedButtonIndex, int previousButtonIndex) {
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

	/*
	 * The delegate to handle the changes to the buttons list.
	 */
	private ButtonGroupDelegate delegate;

	/*
	 * Sets the delegate.
	 */
	void setDelegate(ButtonGroupDelegate delegate) {
		this.delegate = delegate;
	}

	/*
	 * Implement this to listen when the contained buttons of the group are added or removed.
	 */
	interface ButtonGroupDelegate {

		/*
		 * Called when a button is added.
		 */
		void buttonAdded(Button button);

		/*
		 * Called when a button is added.
		 */
		void buttonAdded(Button button, int index);

		/*
		 * Called when a button is removed.
		 */
		void buttonRemoved(Button button);
	}

}
