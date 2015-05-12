package org.vaadin.addons.toggle.test;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.toggle.ButtonGroup;
import org.vaadin.addons.toggle.ButtonGroupSelectionEvent;
import org.vaadin.addons.toggle.ButtonGroupSelectionListener;
import org.vaadin.addons.toggle.ToggleExtension;
import org.vaadin.addons.toggle.ToggleStateEvent;
import org.vaadin.addons.toggle.ToggleStateListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Test the ButtonGroup.
 * 
 * @author bogdanudrescu
 */
@SuppressWarnings("serial")
@Theme("buttongrouplayout")
public class ButtonGroupTestUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ButtonGroupTestUI.class, widgetset = "org.vaadin.addons.toggle.ToggleButtonWidgetset")
    public static class Servlet extends VaadinServlet {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    protected void init(VaadinRequest request) {
        Button button = new Button("Toggle");
        ToggleExtension toggle = new ToggleExtension(button);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(button);

        final Label label2 = new Label();
        layout.addComponent(label2);

        ButtonGroup buttonGroup = new ButtonGroup("Ana", "are", "mere");

        HorizontalLayout horizontalLayout = new HorizontalLayout(
                buttonGroup.getButtons());

        layout.addComponent(horizontalLayout);

        final Label label = new Label();
        layout.addComponent(label);

        setContent(layout);

        buttonGroup.addSelectionListener(new ButtonGroupSelectionListener() {

            @Override
            public void buttonGroupSelectionChanged(
                    ButtonGroupSelectionEvent event) {
                label.setValue(event.getSelectedButton().getCaption());
            }
        });

        toggle.addToggleStateListener(new ToggleStateListener() {

            @Override
            public void toggleStateChanged(ToggleStateEvent event) {
                label2.setValue("" + event.isButtonSelected());

            }
        });

    }

}
