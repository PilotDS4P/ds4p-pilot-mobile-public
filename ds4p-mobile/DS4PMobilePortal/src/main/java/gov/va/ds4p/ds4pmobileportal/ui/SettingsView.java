/**
 * This software is being provided for technology demonstration purposes only.
 * Use of Vaadin Touchkit Add-on API are provided via Affero General Public License
 * (APGL 3.0).  Please refer the APGL 3.0 at www.gnu.org for further details.
 *
 * Items outside of the use of Vaadin Touchkit Add-on API are being provided per
 * FARS 52.227-14 Rights in Data - General.  Any redistribution or request for
 * copyright requires written consent by the Department of Veterans Affairs.
 */
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
