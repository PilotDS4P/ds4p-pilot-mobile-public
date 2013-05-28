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
