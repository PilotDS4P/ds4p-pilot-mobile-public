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
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Duane DeCouteau
 */
public class SensitivityPrivilegeView extends NavigationView {
    NavigationButton navBack = new NavigationButton("Back");
    Switch ethCHK = new Switch();
    Switch hivCHK = new Switch();
    Switch psyCHK = new Switch();
    Switch stdCHK = new Switch();
    Switch sickleCHK = new Switch();
    Switch tbooCHK = new Switch();

    @Override
    public void attach() {
        super.attach();
        buildView();
    }
    
    private void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("User Sensitivity Privileges");
        setLeftComponent(navBack);
        final NavigationManager navMgr = getNavigationManager();
        
        navBack.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                saveSensitivityCodes();
                navMgr.navigateBack();
            }
        });
        
        
        VerticalComponentGroup vGroup = new VerticalComponentGroup();
        Label profileAction = new Label(
                "<div style='color:#333;'><p>This screen allows the user to modify some of their <b>Data Segmentation for Privacy</b> "+
                "sensitivity privileges for test and demonstration purposes.</d></div>", Label.CONTENT_XHTML);
        
        vGroup.addComponent(profileAction);
        
        ethCHK.setCaption("Substance Abuse (ETH)");
        hivCHK.setCaption("Human Immunodeficiency Virus (HIV)");
        psyCHK.setCaption("Mental Health Related (PSY)");
        stdCHK.setCaption("Sexually Transmitted Disease (STD)");
        sickleCHK.setCaption("Sickle Cell Anemia (SICKLE)");
        tbooCHK.setCaption("Taboo (TBOO)");
        
        ethCHK.setWidth("400px");
        hivCHK.setWidth("400px");
        psyCHK.setWidth("400px");
        stdCHK.setWidth("400px");
        sickleCHK.setWidth("400px");
        tbooCHK.setWidth("400px");
                
        populateSensitivitySwitches();
        
        ethCHK.setImmediate(true);
        hivCHK.setImmediate(true);
        psyCHK.setImmediate(true);
        stdCHK.setImmediate(true);
        sickleCHK.setImmediate(true);
        tbooCHK.setImmediate(true);

        
        VerticalComponentGroup vGroup2 = new VerticalComponentGroup();
        vGroup2.addComponent(ethCHK);
        vGroup2.addComponent(hivCHK);
        vGroup2.addComponent(psyCHK);
        vGroup2.addComponent(stdCHK);
        vGroup2.addComponent(sickleCHK);
        vGroup2.addComponent(tbooCHK);
        
        content.addComponent(vGroup);
        content.addComponent(vGroup2);
        setContent(content);
    }
    
    private void populateSensitivitySwitches() {
        List<String> sensList = AdminContext.getSessionAttributes().getAllowedSensitivityActions();
        Iterator iter = sensList.iterator();
        while (iter.hasNext()) {
            String priv = (String)iter.next();
            if (priv.equals("ETH")) { 
                ethCHK.setValue(true);
            }
            if (priv.equals("HIV")) { 
                hivCHK.setValue(true);
            }
            if (priv.equals("PSY")) { 
                psyCHK.setValue(true);
            }
            if (priv.equals("STD")) { 
                stdCHK.setValue(true);
            }
            if (priv.equals("SICKLE")) { 
                sickleCHK.setValue(true);
            }
            if (priv.equals("TBOO")) { 
                tbooCHK.setValue(true);
            }
        }
    }
    
    private void saveSensitivityCodes() {
        List<String> s = new ArrayList();
        if (ethCHK.booleanValue()) { 
            s.add("ETH");
        }
        if (hivCHK.booleanValue()) { 
            s.add("HIV");
        }
        if (psyCHK.booleanValue()) { 
            s.add("PSY");
        }
        if (stdCHK.booleanValue()) { 
            s.add("STD");
        }
        if (sickleCHK.booleanValue()) { 
            s.add("SICKLE");
        }
        if (tbooCHK.booleanValue()) { 
            s.add("TBOO");
        } 
        
        AdminContext.getSessionAttributes().setAllowedSensitivityActions(s);
    }
}
