package org.vaadin.addons.buttongroup.client;

import org.vaadin.addons.buttongroup.HorizontalButtonGroupLayout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.shared.ui.Connect;

/**
 * Horizontal alignment for a group of buttons.
 * 
 * @author bogdanudrescu
 */
@Connect(HorizontalButtonGroupLayout.class)
@SuppressWarnings("serial")
public class HorizontalButtonGroupLayoutConnector extends HorizontalLayoutConnector {

	/**
	 * Create the server connector for the horizontal button group layout.
	 */
	public HorizontalButtonGroupLayoutConnector() {
	}

	/* (non-Javadoc)
	 * @see com.vaadin.client.ui.AbstractComponentConnector#createWidget()
	 */
	@Override
	protected Widget createWidget() {
		return GWT.create(VHorizontalButtonGroupLayout.class);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector#getWidget()
	 */
	@Override
	public VHorizontalButtonGroupLayout getWidget() {
		return (VHorizontalButtonGroupLayout) super.getWidget();
	}

}
