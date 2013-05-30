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

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TabSheet.Tab;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;

/**
 *
 * @author Duane DeCouteau
 */
public class MainTabsheet extends TabBarView {

    
    public MainTabsheet() {
        
        SettingsView settings = new SettingsView();
        PatientSelectView patientView = new PatientSelectView();
        eHealthExchange exchangeView = new eHealthExchange();
        eHealthDirect directView = new eHealthDirect();
        ProfileView profileView = new ProfileView();
        LogoutView logout = new LogoutView();
        AuditLogs audit = new AuditLogs();
        Tab tab = addTab(patientView);
        tab.setIcon(new ThemeResource("../runo/icons/16/users.png"));
        tab.setCaption("Patient List");
        tab = addTab(exchangeView);
        tab.setIcon(new ThemeResource("../runo/icons/16/globe.png"));
        tab.setCaption("eHealth Exchange");
        tab = addTab(directView);
        tab.setIcon(new ThemeResource("../runo/icons/16/email-send.png"));
        tab.setCaption("eHealth Direct");
        tab = addTab(audit);
        tab.setIcon(new ThemeResource("../runo/icons/16/lock.png"));
        tab.setCaption("Access Control Decisioning");        
        tab = addTab(profileView);
        tab.setIcon(new ThemeResource("../runo/icons/16/user.png"));
        tab.setCaption(AdminContext.getSessionAttributes().getUserId());
        tab = addTab(settings);
        tab.setIcon(new ThemeResource("../runo/icons/16/settings.png"));
        tab.setCaption("Settings");
        tab = addTab(logout);
        tab.setIcon(new ThemeResource("../runo/icons/16/cancel.png"));
        tab.setCaption("Logout");
        
        setSelectedTab(patientView);        
                
        this.setImmediate(true);

        
    }
}
