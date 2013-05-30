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
package gov.va.ds4p.ds4pmobileportal;

import com.vaadin.addon.touchkit.ui.TouchKitWindow;

/**
 *
 * @author Duane DeCouteau
 */
public class DS4PWindow extends TouchKitWindow {
    
    public DS4PWindow() {
        setCaption("DS4P");
        addApplicationIcon(MyVaadinApplication.get().getURL()
                + "VAADIN/themes/ds4p/logo.png");
        setStartupImage(MyVaadinApplication.get().getURL()
                + "VAADIN/themes/ds4p/logo_edmond_scientific.png");
        setWebAppCapable(true);
        setPersistentSessionCookie(true); 
        setOfflineTimeout(-1);
    }
}
