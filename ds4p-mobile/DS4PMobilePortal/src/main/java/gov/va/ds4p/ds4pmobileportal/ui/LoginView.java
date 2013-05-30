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

/**
 *
 * @author Duane DeCouteau
 */
import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.client.ui.VMarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import gov.va.ds4p.ds4pmobileportal.MyVaadinApplication;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ehtac.ds4p.ws.up.AuthUserProfile;
import gov.va.ehtac.ds4p.ws.up.UserAuthProfile;
import gov.va.ehtac.ds4p.ws.up.UserProfile;
import gov.va.ehtac.ds4p.ws.up.UserProfile_Service;
import javax.xml.ws.BindingProvider;



public class LoginView extends NavigationView  {
    private String userN = "";
    private String userP = "";
    private UserAuthProfile userProfile = null;
    
    @Override
    public void attach() {
        super.attach();
        buildView();
    }
    
    private void buildView() {
        try {
            CssLayout content = new CssLayout();
            content.setWidth("100%");
            content.setMargin(true, true, true, true);

            content.addComponent(getLoginPanel());
            setContent(content);
            setCaption("VA/SAMHSA DS4P Pilot Demonstrations - Please Login");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private Panel getLoginPanel() {
        Panel p = new Panel();
        VerticalLayout v = (VerticalLayout)p.getContent();
        v.setSpacing(true);
        v.setHeight("300px");
            Label sandi = new Label("");
            sandi.setIcon(new ThemeResource("onc_s_and_i_logo.png"));
            v.addComponent(sandi);           
        
            final TextField username = new TextField("User Name:");
            final PasswordField userpass = new PasswordField("Password:");
            username.setWidth("500px");
            userpass.setWidth("500px");
            v.addComponent(username);
            v.addComponent(userpass);
       
            
            HorizontalLayout h = new HorizontalLayout();
            h.setSpacing(true);
            h.setWidth("500px");
            Button okBTN = new Button("Ok");
            Label l = new Label("");
            l.setIcon(new ThemeResource("header-logo.png"));
            h.addComponent(okBTN);
            h.addComponent(l);
            v.addComponent(h);
            okBTN.addListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    userN = (String)username.getValue();
                    userP = (String)userpass.getValue();
                    authenticate();
                    if (userProfile == null) {
                        getWindow().showNotification("Login Error", "Incorrect username or password combination.", Window.Notification.TYPE_WARNING_MESSAGE);                    
                    }
                    else {
                        AdminContext.getSessionAttributes().setUserProfile(userProfile);
                        ((MyVaadinApplication)getApplication()).loginComplete();
                    }
                }
            });
            
            okBTN.setImmediate(true);
        
        
        return p;
    }
    
    private void authenticate() {
        try {
            UserProfile_Service service = new UserProfile_Service();
            UserProfile port = service.getUserProfilePort();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, AdminContext.getSessionAttributes().getUserEndpoint());
            userProfile = port.authUserProfile(userN, userP);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }  
}
