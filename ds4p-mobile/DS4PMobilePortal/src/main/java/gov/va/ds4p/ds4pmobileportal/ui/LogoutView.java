/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.ds4pmobileportal.ui;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import gov.va.ds4p.ds4pmobileportal.MyVaadinApplication;

/**
 *
 * @author Duane DeCouteau
 */
public class LogoutView extends NavigationView {
    private Label logoutAction;
    
    @Override
    public void attach() {
        super.attach();
        if (logoutAction == null) {
            buildView();
        }
    }
    
    private void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("Logout / End Session");
        
        VerticalComponentGroup vGroup = new VerticalComponentGroup();
        logoutAction = new Label(
                "<div style='color:#333;'><p>You have requested to end your session, if true continue by clicking the \"Ok\" button.</p></div>", Label.CONTENT_XHTML);
        Button okBTN = new Button("Ok");
        okBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                ((MyVaadinApplication)getApplication()).logoutRequest();
            }
        });
        vGroup.addComponent(logoutAction);
        vGroup.addComponent(okBTN);
        content.addComponent(vGroup);
        setContent(content);
        
    }
    
}
