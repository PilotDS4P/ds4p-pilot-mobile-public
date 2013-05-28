package gov.va.ds4p.ds4pmobileportal;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import gov.va.ds4p.ds4pmobileportal.ui.LoginView;
import gov.va.ds4p.ds4pmobileportal.ui.MainTabsheet;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends TouchKitApplication
{
    private DS4PWindow mainWindow;
    private LoginView login = new LoginView();
    private MainTabsheet mainTabSheet = new MainTabsheet();
        
    
    @Override
    public void onBrowserDetailsReady() {
        mainWindow.setContent(login);
    }

    private void configureMainWindow() {
        mainWindow = new DS4PWindow();
        setMainWindow(mainWindow);
    }

    @Override
    public void init() {
        /*
         * Custom configurations (app icons etc) for main window need to be set
         * eagerly as they are written on the "host page".
         */
        try {
            configureMainWindow();
            setTheme("ds4p");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static MyVaadinApplication getApp() {
        return (MyVaadinApplication) get();
    }
    
    public void loginComplete() {
        mainTabSheet = new MainTabsheet();
        mainWindow.setContent(mainTabSheet);
    }
    
    public void logoutRequest() {
        login = new LoginView();
        mainWindow.setContent(login);
    }
}
