/**
 * This software is being provided per FARS 52.227-14 Rights in Data - General.
 * Any redistribution or request for copyright requires written consent by the
 * Department of Veterans Affairs.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.ds4pmobileportal.filter;

import java.io.Serializable;
import gov.va.ds4p.ds4pmobileportal.session.SessionAttributes;


/**
 *
 * @author mdufel
 */
public class AdminContext implements Serializable{
 
    private static ThreadLocal<AdminContext> ctx = new ThreadLocal<AdminContext>(); 
    
    static AdminContext getContext(){
        return ctx.get();
    }
    static void setContext(AdminContext ctx){
        AdminContext.ctx.set(ctx);
    }
    
    AdminContext(){
        sessionAttributes = new SessionAttributes();
        //providerAttributes = new ProviderAttributes();
    }
    
    private SessionAttributes sessionAttributes;
    //private ProviderAttributes providerAttributes;


    /**
     * @return the patientSessionAttributes
     */
    public static SessionAttributes getSessionAttributes() {
        return ctx.get().sessionAttributes;
    }

    /**
     * @param patientSessionAttributes the patientSessionAttributes to set
     */
    public static void setSessionAttributes(SessionAttributes sessionAttributes) {
        ctx.get().sessionAttributes = sessionAttributes;
    }
    
//    public static ProviderAttributes getProviderAttributes() {
//        return ctx.get().providerAttributes;
//    }
//    
//    public static void setProviderAttributes(ProviderAttributes providerAttributes) {
//        ctx.get().providerAttributes = providerAttributes;
//    }
    
}
