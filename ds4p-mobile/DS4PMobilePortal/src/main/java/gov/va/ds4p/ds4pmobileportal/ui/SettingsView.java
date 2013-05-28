/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.ds4pmobileportal.ui;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.PasswordField;

/**
 *
 * @author Duane DeCouteau
 */
public class SettingsView extends NavigationView {
    
    @Override
    public void attach() {
        super.attach();
        buildView();
    }
    
    private void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("Settings");
        
        VerticalComponentGroup vGroup = new VerticalComponentGroup();
        
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        
        vGroup.addComponent(username);
        vGroup.addComponent(password);
        
        content.addComponent(vGroup);
        
        setContent(content);
    }
    
}
