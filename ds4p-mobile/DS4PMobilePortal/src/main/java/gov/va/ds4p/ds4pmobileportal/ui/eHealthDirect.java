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
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.StreamVariable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ehtac.ds4p.ws.dp.Directprocessing;
import gov.va.ehtac.ds4p.ws.dp.NwHINDirectDocumentProcessing;
import gov.va.ehtac.ds4p.ws.dp.NwHINDirectDocumentProcessing_Service;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Duane DeCouteau
 */
public class eHealthDirect extends NavigationView {

    private Label deleteBTN = new Label();
    private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB    
    private ProgressIndicator progress = new ProgressIndicator();
    private Label label1 = new Label("Click on Folder icon");
    private Label label2 = new Label("to select file to upload");
    private Label label3 = new Label("and begin processing.");
    
    private Table table;
    
    //buttons
    private Button refresh = new Button("Refresh Inbox");
    private Button viewMetaData = new Button("View XDM MetaData");
    private Button viewEncryptedDoc = new Button("View Encrypted Document");
    private Button viewDocument = new Button("View Document");
    private Button viewTransformed = new Button("View Transformed Document");
    private Button testDisclosure = new Button("Test Disclosure");
    
    private HorizontalSplitPanel hp = new HorizontalSplitPanel();
    
    private String XDM_ENDPOINT = "http://localhost:8080/DS4PACSServices/NwHINDirectDocumentProcessing?wsdl";
    
    private String fileName;
    private String mimeType;
    
    private String xStyleSheet = "../../../../../../resources/transforms/xml2html.xsl";
    private String qTransform = "../../../../../../resources/transforms/CDA.xsl";
    private String disclosure = "This information has been disclosed to you from records protected by Federal confidentiality rules (42 CFR part 2). The Federal rules prohibit you from making any further disclosure of this information unless further disclosure is expressly permitted by the written consent of the person to whom it pertains or as otherwise permitted by 42 CFR part 2. A general authorization for the release of medical or other information is NOT sufficient for this purpose. The Federal rules restrict any use of the information to criminally investigate or prosecute any alcohol or drug abuse patient.  [52 FR 21809, June 9, 1987; 52 FR 41997, Nov. 2, 1987]";
    
    
    @Override 
    public void attach() {
        super.attach();
        buildView();
    }
    
    private void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("eHealth Direct - "+AdminContext.getSessionAttributes().getUserId());
        
        hp.setStyleName(Runo.SPLITPANEL_SMALL);
        hp.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);
        hp.setHeight("450px");
        hp.setLocked(false);
        hp.setFirstComponent(getDropArea());
        hp.setSecondComponent(getResultsTable());

        
        HorizontalComponentGroup hg = new HorizontalComponentGroup();
        hg.setWidth("100%");
        hg.addComponent(refresh);
        hg.addComponent(viewMetaData);
        hg.addComponent(viewEncryptedDoc);
        hg.addComponent(viewTransformed);
        hg.addComponent(testDisclosure);
        
        viewMetaData.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                processMetaDataForViewing();
            }
        });
        
        viewTransformed.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                //test for selection
                Directprocessing obj = getTableObject();
                if (obj != null) {
                    String refrainPolicy = obj.getRefrainPolicy();
                    String doc = obj.getDocument();
                    if (refrainPolicy.equals("NORDSLCD")) {
                        getWindow().showNotification("Warning", disclosure, Window.Notification.TYPE_WARNING_MESSAGE);
                    }
                    Popover popover = getPopoverLabel(getHTMLVersionOfCDA(doc), "Transformed Clinical Summary");
                    popover.showRelativeTo(getNavigationBar());
                    
                }
            }
        });
        
        
        
        content.addComponent(hp);
        content.addComponent(hg);
        
        setContent(content);
        
    }
    
    private Panel getDropArea() {
        Panel p = new Panel();
        p.setStyleName(Runo.PANEL_LIGHT);
        VerticalLayout v = (VerticalLayout)p.getContent();
        v.setHeight("100%");
        v.addComponent(getDropPanel());
        v.addComponent(label1);
        v.addComponent(label2);
        v.addComponent(label3);
        
        return p;
    }
    
    private Panel getDropPanel() {
        Panel p = new Panel();
        VerticalLayout v = (VerticalLayout) p.getContent();
        v.setSpacing(true);
        //v.setHeight("100%");
        p.setStyleName(Runo.PANEL_LIGHT);
        
        Panel wrapperPanel = new Panel();
        VerticalLayout vDPanel = (VerticalLayout)wrapperPanel.getContent();
        deleteBTN.setIcon(new ThemeResource("../runo/icons/64/folder-add.png"));
        deleteBTN.setWidth("64px");
        deleteBTN.setHeight("64px");

        vDPanel.addComponent(deleteBTN);
        final DragAndDropWrapper wrapper = new DragAndDropWrapper(wrapperPanel);
        wrapper.setWidth("100px");
        wrapper.setHeight("100px");
        
        wrapperPanel.setStyleName(Runo.PANEL_LIGHT);        
        
        wrapper.setDropHandler(new DropHandler() {

                @Override
                public void drop(DragAndDropEvent event) {
                    // expecting this to be an html5 drag
                    DragAndDropWrapper.WrapperTransferable tr = (DragAndDropWrapper.WrapperTransferable) event.getTransferable();
                    Html5File[] files = tr.getFiles();
                    if (files != null) {
                        for (final Html5File html5File : files) {
                            final String fileName = html5File.getFileName();

                            if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                                getWindow()
                                        .showNotification(
                                                "File rejected. Max 2MB files are accepted by Sampler",
                                                Window.Notification.TYPE_WARNING_MESSAGE);
                            } else {

                                final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                                StreamVariable streamVariable = new StreamVariable() {

                                    public OutputStream getOutputStream() {
                                        return bas;
                                    }

                                    public boolean listenProgress() {
                                        return false;
                                    }

                                    public void onProgress(StreamVariable.StreamingProgressEvent event) {
                                    }

                                    public void streamingStarted(
                                            StreamVariable.StreamingStartEvent event) {
                                    }

                                    public void streamingFinished(
                                            StreamVariable.StreamingEndEvent event) {
                                            progress.setVisible(false);
                                            Boolean res = updateProviderProcessingInbox(bas.toByteArray());
                                            if (res.booleanValue()) {
                                                getWindow().showNotification("Processing Complete: "+fileName +" "+html5File.getType()+" "+html5File.getFileSize(), Window.Notification.TYPE_HUMANIZED_MESSAGE);                                        
                                            }
                                            else {
                                                getWindow().showNotification("Processing Error: You Do Not Have Necessary Permissions to Receive and Process Files.", Window.Notification.TYPE_TRAY_NOTIFICATION);                                                                                        
                                            }
                                    }

                                    public void streamingFailed(
                                            StreamVariable.StreamingErrorEvent event) {
                                            progress.setVisible(false);
                                    }

                                    public boolean isInterrupted() {
                                        return false;
                                    }
                                };
                                html5File.setStreamVariable(streamVariable);
                                progress.setVisible(true);
                            }
                        }

                    } else {
                        String text = tr.getText();
                        if (text != null) {                            
                            getWindow().showNotification(text, Window.Notification.TYPE_HUMANIZED_MESSAGE);                        
                        }
                    }                    
                }

                @Override
                public AcceptCriterion getAcceptCriterion() {
                    return AcceptAll.get();
                }
         });
        
        v.addComponent(wrapper);
        
      return p;
    }
    
    private Panel getResultsTable() {
        Panel p = new Panel();
        p.setStyleName(Runo.PANEL_LIGHT);
        VerticalLayout v = (VerticalLayout)p.getContent();
        v.setSpacing(true);
        table = new Table();
        table.setStyleName(Runo.TABLE_SMALL);
        table.setCaption("Processed XDM Packages");
        table.setWidth("100%");
        table.setHeight("400px");
        table.setMultiSelect(false);
        table.setSelectable(true);
        table.setImmediate(true); // react at once when something is selected
        table.setEditable(false);
        table.setWriteThrough(false);
        table.setContainerDataSource(populateTable());

        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(false);
        table.setVisibleColumns(new Object[] {"oDate","oPatientName", "oSender", "oConfidentiality", "oRefrainPolicy", "oPurposeOfUse"});
        table.setColumnHeaders(new String[] {"Date Processed", "Patient Name", "From", "Confidentiality", "Primary Refrain Policy", "Intended Purpose Of Use"});

        v.addComponent(table);
        return p;
    }
    
    private IndexedContainer populateTable() {
        IndexedContainer res = null;
        try {
            NwHINDirectDocumentProcessing_Service service = new NwHINDirectDocumentProcessing_Service();
            NwHINDirectDocumentProcessing port = service.getNwHINDirectDocumentProcessingPort();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XDM_ENDPOINT);
            List<Directprocessing> list = port.getXDMObjectListByProviderId(AdminContext.getSessionAttributes().getProviderId());
            res = createTableContainer(list);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
        
    }
    
    private IndexedContainer createTableContainer(Collection<Directprocessing> collection) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("oDate", String.class, null);
        container.addContainerProperty("oPatientName", String.class, null);
        container.addContainerProperty("oSender", String.class, null);
        container.addContainerProperty("oConfidentiality", String.class, null);
        container.addContainerProperty("oRefrainPolicy", String.class, null);
        container.addContainerProperty("oPurposeOfUse", String.class, null);
        container.addContainerProperty("oObject", Directprocessing.class, null);
        
        for (Directprocessing p : collection) {
            //System.out.println("ITEM ID IS: "+p.getRuleId());
            Item item = container.addItem(p.getIddirectprocessing());
            item.getItemProperty("oDate").setValue(convertDateToString(p.getDateProcessed()));            
            item.getItemProperty("oPatientName").setValue(p.getPatientName());
            item.getItemProperty("oSender").setValue(p.getSendingProviderId());
            item.getItemProperty("oConfidentiality").setValue(p.getConfidentiality());
            item.getItemProperty("oRefrainPolicy").setValue(p.getRefrainPolicy());
            item.getItemProperty("oPurposeOfUse").setValue(p.getPou());
            item.getItemProperty("oObject").setValue(p);
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
    
    private Boolean updateProviderProcessingInbox(byte[] payload) {
        Boolean res = Boolean.TRUE;
        try {
//            DS4PContextHandler_Service service = new DS4PContextHandler_Service();
//            DS4PContextHandler port = service.getDS4PContextHandlerPort();
            NwHINDirectDocumentProcessing_Service service = new NwHINDirectDocumentProcessing_Service();
            NwHINDirectDocumentProcessing port = service.getNwHINDirectDocumentProcessingPort();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XDM_ENDPOINT);
            String providerId = AdminContext.getSessionAttributes().getUserId();
            String recipientId = AdminContext.getSessionAttributes().getProviderId();
            String purposeOfUse = AdminContext.getSessionAttributes().getPurposeOfUse();
            String role = AdminContext.getSessionAttributes().getRole();
            String organizationName = AdminContext.getSessionAttributes().getOrganization();
            String organizationId = AdminContext.getSessionAttributes().getProviderHomeCommunityId();
            List<String> servicePermissions = AdminContext.getSessionAttributes().getAllowedResourceActions();
            List<String> sensitivityPrivileges = AdminContext.getSessionAttributes().getAllowedSensitivityActions();
            List<String> requiredPermission = new ArrayList();
            List<String> requiredSensitivityPrivileges = new ArrayList();
            String intendedRecipient = "";
            String uniquePatientId = "";
            String intendedPurposeOfUse = "";
            
            res = port.unpackEnforceXDMPackage(payload, providerId, recipientId, organizationName, organizationId, role, purposeOfUse, servicePermissions, sensitivityPrivileges, intendedPurposeOfUse, intendedRecipient, requiredPermission, requiredSensitivityPrivileges, "DirectDocumentReceive", uniquePatientId);
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    
    } 
    
    private Directprocessing getTableObject() {
        Directprocessing res = null;
        try {
            Object rowId = table.getValue();
            if (rowId != null) {
                res = (Directprocessing)table.getContainerProperty(rowId, "oObject").getValue();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }       
        return res;
    }
  
    private void processMetaDataForViewing() {
        Directprocessing d = getTableObject();
        String metaData = d.getMetadata();
        try {
            Popover popover = getPopoverLabel(convertXMLtoXHTML(metaData), "Meta Data");
            popover.showRelativeTo(getNavigationBar());
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }        
    }
    private String convertXMLtoXHTML(String xml) {
        String res = "";
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(xStyleSheet)));
            Transformer transformer = tFactory.newTransformer(new StreamSource(reader));
            StringWriter out = new StringWriter();
            Result result = new StreamResult(out);
            transformer.transform(new StreamSource(new StringReader(xml)), result);
            res = out.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    private Popover getPopoverLabel(String val, String title) {
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
        Label lbl = new Label();
        lbl.setContentMode(Label.CONTENT_XHTML);
        lbl.setStyleName(Runo.LABEL_SMALL);
        lbl.setValue(val);
        layout2.addComponent(lbl);
        
        popLayout.addComponent(layout2);
        
        Button close = new Button(null, new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                event.getButton().getWindow().getParent()
                        .removeWindow(event.getButton().getWindow());
            }
        });
        close.setIcon(new ThemeResource("../runo/icons/64/cancel.png"));
        navView.setRightComponent(close);
        
        popover.setContent(navView);
        
        return popover;        
    }

    private Panel createHTMLVersionOfC32(String c32) {
        Panel p = new Panel();
        p.setStyleName(Runo.PANEL_LIGHT);
        VerticalLayout v = (VerticalLayout)p.getContent();
        v.setSpacing(true);
        v.setHeight("100%");
        v.setWidth("100%");
        Label l = new Label();
        l.setContentMode(Label.CONTENT_XHTML);
        l.setValue(getHTMLVersionOfCDA(c32));
        v.addComponent(l);
        return p;
    }

    private String getHTMLVersionOfCDA(String q) {
        String res = "";
        //q is the string of c32
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(qTransform)));
            Transformer transformer = tFactory.newTransformer(new StreamSource(reader));
            StringWriter out = new StringWriter();
            Result result = new StreamResult(out);
            transformer.transform(new StreamSource(new StringReader(q)), result);
            res = out.toString();
            System.out.println(res);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
}
