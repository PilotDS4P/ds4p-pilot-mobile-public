/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.ds4pmobileportal.ui;

import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.Runo;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ehtac.ds4p.ws.au.AuthLog;
import gov.va.ehtac.ds4p.ws.au.DS4PAudit;
import gov.va.ehtac.ds4p.ws.au.DS4PAuditService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Duane DeCouteau
 */
public class AuditLogs extends NavigationView {
    private Table table;
    private boolean noResults = false;
    
    @Override
    public void attach() {
        super.attach();
        buildView();
    }
    
    public void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("Access Control Decisioning Logs - "+AdminContext.getSessionAttributes().getSelectedPatientName());
        
        table = new Table();
        table.setWidth("100%");
        table.setHeight("350px");
        table.setMultiSelect(false);
        table.setSelectable(true);
        table.setImmediate(true); // react at once when something is selected
        table.setEditable(false);
        table.setWriteThrough(true);
        table.setContainerDataSource(populateAuthorizationRequests());

        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(false);
        table.setVisibleColumns(new Object[] {"msgDate","healthcareObject","purposeOfUse","requestor","uniqueIdentifier","decision","responsetime", "messageId"});
        table.setColumnHeaders(new String[] {"Date","Resource","POU","Recipient","Patient ID","PDP Decision","Resp. Time(ms)", "Message ID"});
        
        content.addComponent(table);
 
        Button obligationsBTN = new Button("Obligations");
        Button rulesGeneratedBTN = new Button("SLS - Rules Generated");
        Button rulesExecutedBTN = new Button("SLS - Rules Executed");
        
        HorizontalComponentGroup hGroup = new HorizontalComponentGroup();
        hGroup.setWidth("100%");

        hGroup.addComponent(obligationsBTN);
        hGroup.addComponent(rulesGeneratedBTN);
        hGroup.addComponent(rulesExecutedBTN);
        
        content.addComponent(hGroup);
        
        obligationsBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AuthLog log = getAuthLogObject();
                if (log != null) {
                    String drl = log.getObligations();
                    Popover popover = getPopoverTextArea(drl, "XACML Response - Obligations");
                    popover.showRelativeTo(getNavigationBar());
                }                
            }
        });
        
        rulesGeneratedBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AuthLog log = getAuthLogObject();
                if (log != null) {
                    String drl = log.getGenDrl();
                    Popover popover = getPopoverTextArea(drl, "Generated DRL (Annotation Rules)");
                    popover.showRelativeTo(getNavigationBar());
                }                
            }
        });
        
        rulesExecutedBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AuthLog log = getAuthLogObject();
                if (log != null) {
                    String drl = log.getExecRules();
                    Popover popover = getPopoverTextArea(drl, "Executed Annotation Rules");
                    popover.showRelativeTo(getNavigationBar());
                }                
            }
        });
        
        setContent(content);
    }
    
    private IndexedContainer populateAuthorizationRequests() {
        IndexedContainer container = new IndexedContainer();
        DS4PAuditService service = new DS4PAuditService();
        DS4PAudit port = service.getDS4PAuditPort();
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, AdminContext.getSessionAttributes().getAuditEndpoint());
        List<AuthLog> aList = port.getAuthorizationByUId(AdminContext.getSessionAttributes().getSelectedPatientId());
        
        if (aList == null || aList.isEmpty()) {
            noResults = true;
        }
        else {
            noResults = false;
        }
        
        container = createIndexedContainer(aList);
        return container;
    }
    
    private IndexedContainer createIndexedContainer(Collection<AuthLog> collection) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("msgDate", String.class, null);
        container.addContainerProperty("healthcareObject", String.class, null);
        container.addContainerProperty("purposeOfUse", String.class, null);
        container.addContainerProperty("requestor", String.class, null);
        container.addContainerProperty("uniqueIdentifier", String.class, null);
        container.addContainerProperty("decision", Button.class, null);
        container.addContainerProperty("responsetime", Long.class, null);
        container.addContainerProperty("messageId", String.class, null);
        //container.addContainerProperty("authLogObject", AuthLog.class, null);

        int i = 0;
        for (AuthLog p : collection) {
            i++;
            Integer id = new Integer(i);
            Item item = container.addItem(id);
            item.getItemProperty("msgDate").setValue(convertDateToString(p.getMsgDate()));
            item.getItemProperty("healthcareObject").setValue(p.getHealthcareObject());
            item.getItemProperty("purposeOfUse").setValue(p.getPurposeOfUse());
            item.getItemProperty("requestor").setValue(p.getRequestor());
            item.getItemProperty("uniqueIdentifier").setValue(p.getUniqueIdentifier());
            item.getItemProperty("decision").setValue(getButtonWithIcon(p.getDecision()));
            item.getItemProperty("responsetime").setValue(new Long(p.getResponseTime()));
            item.getItemProperty("messageId").setValue(p.getHieMsgId());
            //item.getItemProperty("authLogObject").setValue(p);
        }
        return container;
    }
    private String convertDateToString(XMLGregorianCalendar xres) {
        Date dt = null;
        String res = "";
        try {
            Calendar xcal = Calendar.getInstance();
            xcal.set(xres.getYear(), xres.getMonth() - 1, xres.getDay(), xres.getHour(), xres.getMinute(), xres.getSecond());
            dt = xcal.getTime();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                res = sdt.format(dt);
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        catch (Exception ex) {
            System.err.println("LocalPatientSearch "+ex.getMessage());
        }
        return res;
    }
    
    private AuthLog getAuthLogObject() {
        AuthLog res = null;
        try {
            Object rowId = table.getValue();
            if (rowId != null) {
                String obj = (String)table.getContainerProperty(rowId, "messageId").getValue();
            
                DS4PAuditService service = new DS4PAuditService();
                DS4PAudit port = service.getDS4PAuditPort();
                ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, AdminContext.getSessionAttributes().getAuditEndpoint());
                res = port.getAuthorizationEventByHIEMsgId(obj);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    private Button getButtonWithIcon(String val) {
        Button res = new Button(val);
        res.setStyleName(Runo.BUTTON_LINK);
        if ("Permit".equals(val)) {
            res.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
        }
        else {
            res.setIcon(new ThemeResource("../runo/icons/16/attention.png"));
        }
        return res;
    }

    private Popover getPopoverTextArea(String val, String title) {
        Popover popover = new Popover();
        popover.setModal(true);
        popover.setClosable(true);
        popover.setWidth("700px");
        popover.setHeight("100%");
        CssLayout popLayout = new CssLayout();
        popLayout.setSizeFull();
        popLayout.setMargin(true);
        
        NavigationView navView = new NavigationView(popLayout);
        navView.setCaption(title);

        CssLayout layout2 = new CssLayout();
        TextArea textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setHeight("700px");
        textArea.setValue(val);
        textArea.setReadOnly(true);
        layout2.addComponent(textArea);
        
        popLayout.addComponent(layout2);
        
        Button close = new Button(null, new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                event.getButton().getWindow().getParent()
                        .removeWindow(event.getButton().getWindow());
            }
        });
        close.setIcon(new ThemeResource("../runo/icons/64/cancel.png"));
        navView.setRightComponent(close);
        
        popover.setContent(navView);
        
        return popover;
    }
    
    
}
