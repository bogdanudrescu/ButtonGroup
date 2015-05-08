package org.vaadin.addons.toggle.test;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
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

	// We don't need a custom widget here.

	/* (non-Javadoc)
	 * @see com.vaadin.client.ui.orderedlayout.AbstractOrderedLayoutConnector#onConnectorHierarchyChange(com.vaadin.client.ConnectorHierarchyChangeEvent)
	 */
	@Override
	public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
		super.onConnectorHierarchyChange(event);

		List<ComponentConnector> childConnectors = getChildComponents();

		if (childConnectors.size() > 0) {

			ComponentConnector firstConnector = childConnectors.get(0);
			setLeftStyle(firstConnector.getWidget().getElement());
			setLeftStyle(firstConnector.getWidget().getElement().getFirstChildElement());

			for (int i = 1; i < childConnectors.size() - 1; i++) {
				ComponentConnector centerConnector = childConnectors.get(i);
				setCenterStyle(centerConnector.getWidget().getElement());
				setCenterStyle(centerConnector.getWidget().getElement().getFirstChildElement());
			}

			ComponentConnector lastConnector = childConnectors.get(childConnectors.size() - 1);
			setRightStyle(lastConnector.getWidget().getElement());
			setRightStyle(lastConnector.getWidget().getElement().getFirstChildElement());
		}
	}

	/*
	 * Sets the left style.
	 */
	private void setLeftStyle(Element element) {
		element.addClassName("v-horizontalbuttongroup-left");
	}

	/*
	 * Sets the center style.
	 */
	private void setCenterStyle(Element element) {
		element.addClassName("v-horizontalbuttongroup-center");
	}

	/*
	 * Sets the right style.
	 */
	private void setRightStyle(Element element) {
		element.addClassName("v-horizontalbuttongroup-right");
	}

}
