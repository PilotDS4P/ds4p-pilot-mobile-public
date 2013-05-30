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

import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;
import gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32Service;
import gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RegisteryStoredQueryRequest;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetResponse;
import gov.samhsa.ds4ppilot.ws.client.XDSRepositorybWebServiceClient;
import gov.samhsa.ds4ppilot.ws.client.XdsbRegistryWebServiceClient;
import gov.va.ds4p.cas.providers.ClinicalDocumentProvider;
import gov.va.ds4p.ds4pmobileportal.displayobjects.ExchangeResults;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ds4p.ds4pmobileportal.pep.XACMLPolicyEnforcement;
import gov.va.ds4p.security.saml.SAMLTokenConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.AddressingFeature;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.LocalizedStringType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.hl7.v3.ED;
import org.hl7.v3.POCDMT000040Act;
import org.hl7.v3.POCDMT000040ClinicalDocument;
import org.hl7.v3.POCDMT000040Component2;
import org.hl7.v3.POCDMT000040Component3;
import org.hl7.v3.POCDMT000040Entry;
import org.hl7.v3.POCDMT000040EntryRelationship;
import org.hl7.v3.POCDMT000040ObservationMedia;
import org.hl7.v3.POCDMT000040Section;
import org.hl7.v3.POCDMT000040StructuredBody;
import org.oasis.names.tc.xspa.v2.PolicyEnforcementObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Duane DeCouteau
 */
public class eHealthExchange extends NavigationView {
    private Table table;
    private String patientId;
    private SAMLTokenConstants tokenSAML = new SAMLTokenConstants();
    private String messageId;
    
    private String patientName = "";
    private String docId = "";
    private String docType = "";
    private String respId = "";
    
    private String currentDocument = "";
    private String decryptDocument = "";
    private byte[] keyD;
    private byte[] keyM;
    private String metaData = "";
    
    private String qTransform = "../../../../../../resources/transforms/CDA.xsl";
    
    private String cdaEndpoint = "http://localhost:8080/DS4PACSServices/CDAR2ConsentDirective?wsdl";
    private ClinicalDocumentProvider cProvider = new ClinicalDocumentProvider();
    private static String CONSENT_MEDIA_TYPE_PDF = "application/pdf";
    private static String CONSENT_MEDIA_TYPE_XACML = "application/xacml+xml";
    
    //policy style sheet transform
    private String xStyleSheet = "../../../../../../resources/transforms/xml2html.xsl";
    
    private Window subwindow;
    
    private Button retrieveBTN;
    private Button decryptDocBTN;
    private Button decryptDocMaskBTN;
    private Button viewHTMLBTN;
    private Button viewPDFBTN;
    private Button viewXACMLBTN;
    private Button viewSAMLBTN;
    private Button viewMetaData;
    private String requestedService = "DocQuery";
    
    private String disclosure = "This information has been disclosed to you from records protected by Federal confidentiality rules (42 CFR part 2). The Federal rules prohibit you from making any further disclosure of this information unless further disclosure is expressly permitted by the written consent of the person to whom it pertains or as otherwise permitted by 42 CFR part 2. A general authorization for the release of medical or other information is NOT sufficient for this purpose. The Federal rules restrict any use of the information to criminally investigate or prosecute any alcohol or drug abuse patient.  [52 FR 21809, June 9, 1987; 52 FR 41997, Nov. 2, 1987]";
    
    private XACMLPolicyEnforcement policyEngine = new XACMLPolicyEnforcement();
    
    @Override
    public void attach() {
        super.attach();
        buildView();
    }
    
    private void buildView() {
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        setCaption("eHealth Exchange - "+AdminContext.getSessionAttributes().getSelectedPatientName());
        
        VerticalComponentGroup vGroup = new VerticalComponentGroup();

        
        Label displayAction = new Label(
                "<div style='color:#333;'><p>First perform a secured <b>Search</b> (DocumentQuery) for all available patient documents."
						+ "  Then <b>highlight</b> the document of interest in the table, then click"
						+ " on the <b>Retrieve Document</b> button to perform a secured DocumentRetrieve."
						+ "  Decrpyt the document by clicking any of the 2 <b>Decryption</b> buttons results may vary depending"
                                                + " on you privileges.  Once decrypted you may view the HTML form by clicking on the <b>View Clinical Document</b>.</p></div>",
				Label.CONTENT_XHTML);
        
        vGroup.addComponent(displayAction);
        
        Button searchBTN = new Button("Search");
        searchBTN.setStyleName(Runo.BUTTON_SMALL);
        searchBTN.setWidth("100");
        content.addComponent(vGroup);
        content.addComponent(searchBTN);
        
        table = new Table();
        table.setWidth("100%");
        table.setHeight("350px");
        table.setMultiSelect(false);
        table.setSelectable(true);
        table.setImmediate(true); // react at once when something is selected
        table.setEditable(false);
        table.setWriteThrough(true);
        table.setContainerDataSource(populateTable());

        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(false);
        table.setVisibleColumns(new Object[] {"patientId", "docId", "docType", "respId", "msgId"});
        table.setColumnHeaders(new String[] {"Patient ID", "Document ID", "Document Type", "Repository", "Message"});
        
        content.addComponent(table);
        
        retrieveBTN = new Button("Retrieve Document");
        decryptDocBTN = new Button("Decrypt Document");
        decryptDocMaskBTN = new Button("Decrypt Doc and Entries");
        viewHTMLBTN = new Button("View Clinical Document");
        viewPDFBTN = new Button("Consent PDF");
        viewXACMLBTN = new Button("Consent Policy");
        viewSAMLBTN = new Button("View Authorization (SAML)");
        viewMetaData = new Button("View Meta Data");

        HorizontalComponentGroup hGroup = new HorizontalComponentGroup();
        hGroup.setWidth("100%");

        retrieveBTN.setEnabled(false);
        decryptDocBTN.setEnabled(false);
        decryptDocMaskBTN.setEnabled(false);
        viewHTMLBTN.setEnabled(false);
        viewPDFBTN.setEnabled(false);
        viewXACMLBTN.setEnabled(false);
        viewSAMLBTN.setEnabled(false);
        viewMetaData.setEnabled(false);
        
        hGroup.addComponent(retrieveBTN);
        hGroup.addComponent(decryptDocBTN);
        hGroup.addComponent(decryptDocMaskBTN);
        hGroup.addComponent(viewHTMLBTN);
        hGroup.addComponent(viewPDFBTN);
        hGroup.addComponent(viewXACMLBTN);
        hGroup.addComponent(viewSAMLBTN);
        hGroup.addComponent(viewMetaData);
        
        content.addComponent(hGroup);
        
        searchBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                patientId = AdminContext.getSessionAttributes().getSelectedPatientId();
                patientName = AdminContext.getSessionAttributes().getSelectedPatientName();
                currentDocument = "";
                decryptDocument = "";
                requestedService = "DocQuery";
                //area.setValue(currentDocument);
                //area.setCaption("No Documents Selected");
                
                String decision = "Permit";
                if (patientId.equals("PUI100015060013")) {
                    PolicyEnforcementObject pObj = policyEngine.enforceResouce(AdminContext.getSessionAttributes().getPurposeOfUse(), "PSY", "DocumentQueryOut");
                    decision = pObj.getPdpDecision();
                }
                if (decision.equals("Permit")) {
                    String res = lookUpPatient();
                    metaData = res;
                    if (res.indexOf("XDSDocumentEntry.uniqueId") > -1) {
                        AdhocQueryResponse adhoc = getQueryResponse(res);


                        RegistryObjectListType rList = adhoc.getRegistryObjectList();
                        List<ExchangeResults> xList = processMetaData(rList);
                        refreshTable(xList);
                    }
                    else {
                        //throw warning 
                        docId = "NA";
                        docType = "NA";
                        messageId = "NA";
                        respId = "1.3.6.1.4.1.21367.2010.1.2.1040";
                        ExchangeResults eR = new ExchangeResults(patientName, patientId, docId, docType, "", "", "", messageId, respId);
                        List<ExchangeResults> xList = new ArrayList();
                        xList.add(eR);
                        refreshTable(xList);
                        displayErrorMessage(res);                    
                    }
                    retrieveBTN.setEnabled(false);
                    decryptDocBTN.setEnabled(false);
                    decryptDocMaskBTN.setEnabled(false);
                    viewHTMLBTN.setEnabled(false);
                    viewPDFBTN.setEnabled(false);
                    viewXACMLBTN.setEnabled(false);
                    viewSAMLBTN.setEnabled(true);
                    viewMetaData.setEnabled(true);  
                }
                else {
                    getWindow().showNotification("Warning", "You do not have the necessary authorization privileges to perform this operation.", Window.Notification.TYPE_WARNING_MESSAGE);                                        
                }
            }
        });
        
        table.addListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object rowId = table.getValue();
                if (rowId != null) {
                    patientName = (String)table.getContainerProperty(rowId, "patientName").getValue();
                    patientId = (String)table.getContainerProperty(rowId, "patientId").getValue();
                    docId = (String)table.getContainerProperty(rowId, "docId").getValue();
                    docType = (String)table.getContainerProperty(rowId, "docType").getValue();
                    respId = (String)table.getContainerProperty(rowId, "respId").getValue();
                    messageId = (String)table.getContainerProperty(rowId, "msgId").getValue();
                    if (docType.equals("Consult Notes")) {
                        retrieveBTN.setEnabled(true);
                        decryptDocBTN.setEnabled(true);
                        decryptDocBTN.setCaption("Decrypt Document");
                        decryptDocMaskBTN.setEnabled(true);
                        viewHTMLBTN.setEnabled(true);
                        viewPDFBTN.setEnabled(false);
                        viewXACMLBTN.setEnabled(false); 
                    }
                    else if (docType.equals("Consent Notes") || docType.equals("Consult")) {
                        retrieveBTN.setEnabled(true);
                        decryptDocBTN.setEnabled(true);
                        decryptDocBTN.setCaption("Extract Document");
                        decryptDocMaskBTN.setEnabled(false);
                        viewHTMLBTN.setEnabled(true);
                        viewPDFBTN.setEnabled(true);
                        viewXACMLBTN.setEnabled(true);
                    }
                    else {
                        getWindow().showNotification("Warning", "Could not determine document type for processing.", Window.Notification.TYPE_WARNING_MESSAGE);                        
                    }
                }                

            }
        });
        
        retrieveBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Object rowId = table.getValue();
                String decision = "Permit";
                if (patientId.equals("PUI100015060013")) {
                    PolicyEnforcementObject pObj = policyEngine.enforceResouce(AdminContext.getSessionAttributes().getPurposeOfUse(), "PSY", "DocumentRetrieveOut");
                    decision = pObj.getPdpDecision();
                }
                if (decision.equals("Permit")) {
                    if (rowId != null) {
                        getDocument();
                        requestedService = "DocRetrieve";
                        System.out.println("CURRENT DOCUMENT SIZE: "+currentDocument.length());
                        Popover popover = getPopoverTextArea(currentDocument, "Document Retrieve Response");
                        popover.showRelativeTo(getNavigationBar());
                    }
                    else {
                        getWindow().showNotification("Warning", "No row selected, Please select document for retrieval.", Window.Notification.TYPE_WARNING_MESSAGE);
                    }
                }
                else {
                    getWindow().showNotification("Warning", "You do not have the necessary authorization privileges to perform this operation.", Window.Notification.TYPE_WARNING_MESSAGE);                    
                }
            }
        });
        
        decryptDocBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (currentDocument.length() == 0) {
                    getWindow().showNotification("Warning", "You must select and retrieve document first.", Window.Notification.TYPE_WARNING_MESSAGE);                    
                }
                else {
                    
                    try {
                        String sOrg = AdminContext.getSessionAttributes().getSelectedOrg();
                        if (sOrg.equals("FEISystems")) {
                            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse xdsbRetrieveDocumentSetResponse = unmarshallFromXml(ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.class, currentDocument);
                                ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse documentResponse = xdsbRetrieveDocumentSetResponse.getDocumentResponse().get(0);
                            if (docType.equals("Consult Notes")) {
                                byte[] processDocBytes = documentResponse.getDocument();                    

                                decryptDocument = decryptDocumentExcludeElements(processDocBytes, keyD);
                            }
                            else {
                                byte[] processDocBytes = documentResponse.getDocument();
                                decryptDocument = new String(processDocBytes);
                            }
                        }
                        else {
                            decryptDocument = currentDocument;
                        }
                        Popover popover = getPopoverLabel(convertXMLtoXHTML(decryptDocument), "Decrypted - Document Only");
                        popover.showRelativeTo(getNavigationBar());
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        decryptDocMaskBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (currentDocument.length() == 0) {
                    getWindow().showNotification("Warning", "You must select and retrieve document first.", Window.Notification.TYPE_WARNING_MESSAGE);                    
                }
                else {
                    String decision = "Permit";
                    
                    if (patientId.equals("PUI100015060013")) {
                        PolicyEnforcementObject pObj = policyEngine.enforceResouce(AdminContext.getSessionAttributes().getPurposeOfUse(), "PSY", "DocumentEntryUnMask");
                        decision = pObj.getPdpDecision();
                    }
                    if (decision.equals("Permit")) {
                        try {
                            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse xdsbRetrieveDocumentSetResponse = unmarshallFromXml(ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.class, currentDocument);
                                ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse documentResponse = xdsbRetrieveDocumentSetResponse.getDocumentResponse().get(0);
                            if (docType.equals("Consult Notes")) {
                                byte[] processDocBytes = documentResponse.getDocument();                    

                                decryptDocument = decryptDocument(processDocBytes, keyD, keyM);
                            }
                            else {
                                byte[] processDocBytes = documentResponse.getDocument();
                                decryptDocument = new String(processDocBytes);
                            }
                            Popover popover = getPopoverLabel(convertXMLtoXHTML(decryptDocument), "Decrypted - Document and Masked Entries");
                            popover.showRelativeTo(getNavigationBar());
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
                        getWindow().showNotification("Warning", "You do not have the necessary authorization privileges to perform this operation.", Window.Notification.TYPE_WARNING_MESSAGE);                                            
                    }
                }
            }
        });
        
        viewHTMLBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (decryptDocument.length() == 0) {
                    getWindow().showNotification("Warning", "You must select and retrieve document first, then view decrypted version, before attempting to transform it.", Window.Notification.TYPE_WARNING_MESSAGE);                                        
                }
                else {
                    String decision = "Permit";
                    
                    if (patientId.equals("PUI100015060013")) {
                        PolicyEnforcementObject pObj = policyEngine.enforceResouce(AdminContext.getSessionAttributes().getPurposeOfUse(), "PSY", "DocumentView");
                        decision = pObj.getPdpDecision();
                    }
                    if (decision.equals("Permit")) {
                        String sOrg = AdminContext.getSessionAttributes().getSelectedOrg();
                        if (sOrg.equals("FEISystems")) {
                            getWindow().showNotification("Warning", disclosure, Window.Notification.TYPE_WARNING_MESSAGE); 
                        }
                        String title = "";
                        if (docType.equals("Consult Notes")) {
                            title = "Summary of Episode - C32";
                        }
                        else if (docType.equals("Consent Notes")) {
                            title = "CDA R2 Consent Directive";
                        }
                        else {
                            title = "Unknown Document Type";
                        }

                        Popover popover = getPopoverLabel(getHTMLVersionOfCDA(decryptDocument), title);
                        popover.showRelativeTo(getNavigationBar());
                    }
                    else {
                        getWindow().showNotification("Warning", "You do not have the necessary authorization privileges to perform this operation.", Window.Notification.TYPE_WARNING_MESSAGE);                                                                    
                    }
                }
            }
        });
        
        viewPDFBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                processPDFForViewing();
            }
        });
        
        viewXACMLBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                processXACMLForViewing();
            }
        });
        
        viewSAMLBTN.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                processSAMLForViewing();
            }
        });
        
        viewMetaData.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                processMetaDataForViewing();
            }
        });
        
        setContent(content);        
        
    }
    
    private IndexedContainer populateTable() {
        String patientName = AdminContext.getSessionAttributes().getSelectedPatientName();
        patientId = AdminContext.getSessionAttributes().getSelectedPatientId();
        
        IndexedContainer res = new IndexedContainer();
        if (patientId != null || patientId.length() == 0  || patientName.equals("No Patient Selected")) {
            List<ExchangeResults> lres = new ArrayList();
            res = createTableContainer(lres);
        }
        else {
            String ad = lookUpPatient();
            List<ExchangeResults> lres = new ArrayList();
            res = createTableContainer(lres);
            
        }
        return res;
    }
    
    private IndexedContainer createTableContainer(Collection<ExchangeResults> collection) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("patientName", String.class, null);
        container.addContainerProperty("patientId", String.class, null);
        container.addContainerProperty("docId", String.class, null);
        container.addContainerProperty("docType", String.class, null);        
        container.addContainerProperty("msgId", String.class, null);
        container.addContainerProperty("respId", String.class, null);
        container.addContainerProperty("oExchangeResults", ExchangeResults.class, null);
        
        Integer tt = new Integer(1);
        for (ExchangeResults p : collection) {
            //System.out.println("ITEM ID IS: "+p.getRuleId());
            Item item = container.addItem(tt);
            item.getItemProperty("patientName").setValue(p.getPatientName());
            item.getItemProperty("patientId").setValue(p.getPatientId());            
            item.getItemProperty("docId").setValue(p.getDocId()); 
            item.getItemProperty("docType").setValue(p.getDocType());
            item.getItemProperty("msgId").setValue(p.getMesgId()); 
            item.getItemProperty("respId").setValue(p.getRespId());
            item.getItemProperty("oExchangeResults").setValue(p);
            tt++;
        }        
        return container;
    }
    
    private void getDocument() {
        String sOrg = AdminContext.getSessionAttributes().getSelectedOrg();
        if (sOrg.equals("FEISystems")) {
            RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
            AdminContext.getSessionAttributes().setServiceName("DocumentRetrieve");
            setSAMLValues();
            request.setDocumentUniqueId(docId);
            request.setMessageId(messageId);
            request.setIntendedRecipient(AdminContext.getSessionAttributes().getProviderId());

            WebServiceFeature features = new AddressingFeature(true);

            SecuredFilterC32Service service = new SecuredFilterC32Service();
            SecuredFilterC32ServicePortType port = service.getSecuredFilterC32Port(features);
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, AdminContext.getSessionAttributes().getSelectedXDSRep());

            RetrieveDocumentSetResponse response = port.retrieveDocumentSet(request);

            currentDocument = response.getReturn();
            metaData = response.getMetadata();
            keyD = response.getKekEncryptionKey();
            keyM = response.getKekMaskingKey();
        }
        else {
            XDSRepositorybWebServiceClient gWeb = new XDSRepositorybWebServiceClient(AdminContext.getSessionAttributes().getSelectedXDSRep());
            ihe.iti.xds_b._2007.RetrieveDocumentSetRequest request = new ihe.iti.xds_b._2007.RetrieveDocumentSetRequest();
            ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest docRequest = new ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest();
            docRequest.setRepositoryUniqueId("1.3.6.1.4.1.21367.2010.1.2.1040");
            docRequest.setHomeCommunityId(AdminContext.getSessionAttributes().getSelectedOrgId());
            docRequest.setDocumentUniqueId(docId);
            request.getDocumentRequest().add(docRequest);
            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse resp =  gWeb.retrieveDocumentSetRequest(request);
            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse docResp = resp.getDocumentResponse().get(0);
            currentDocument = new String(docResp.getDocument()); 
        }
    }
    
    private String lookUpPatient() {
        String res = "";
        String sOrg = AdminContext.getSessionAttributes().getSelectedOrg();        
        if (sOrg.equals("FEISystems")) {
            RegisteryStoredQueryRequest request = new RegisteryStoredQueryRequest();
            AdminContext.getSessionAttributes().setServiceName("DocumentQuery");
            setSAMLValues();
            request.setPatientId("\'"+patientId+"^^^&"+AdminContext.getSessionAttributes().getSelectedOrgId()+"&ISO\'");
            request.setMessageId(messageId);
            
            WebServiceFeature features = new AddressingFeature(true);

            SecuredFilterC32Service service = new SecuredFilterC32Service();
            SecuredFilterC32ServicePortType port = service.getSecuredFilterC32Port(features);
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, AdminContext.getSessionAttributes().getSelectedXDSReg());

            RegisteryStoredQueryResponse response = port.registeryStoredQuery(request);
            res = response.getReturn();
            AdminContext.getSessionAttributes().setMostRecentSAMLAssertion(tokenSAML.getRECENT_REQUEST());       
        }
        else {
            
            String gEndpoint = AdminContext.getSessionAttributes().getSelectedXDSReg();
            String gUser = AdminContext.getSessionAttributes().getUserId();
            String gProviderId = AdminContext.getSessionAttributes().getProviderId();
            String gPou = AdminContext.getSessionAttributes().getPurposeOfUse();
            String gOrg = AdminContext.getSessionAttributes().getOrganization();
            String gOrgUnit = AdminContext.getSessionAttributes().getOrganizationUnit();
            String gCity = AdminContext.getSessionAttributes().getCity();
            String gState = AdminContext.getSessionAttributes().getState();
            String gCountry = AdminContext.getSessionAttributes().getCountry();
            String gOrgId = AdminContext.getSessionAttributes().getSelectedOrgId();
            System.out.println("****JERICHO ENDPOINT: "+gEndpoint);            
            XdsbRegistryWebServiceClient gWeb = new XdsbRegistryWebServiceClient(gEndpoint, gUser, gProviderId, gPou, gOrg, gOrgUnit, gCity, gState, gCountry, gOrgId);

            AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();

            ResponseOptionType responseOptionType = new ResponseOptionType();
            responseOptionType.setReturnComposedObjects(true);
            responseOptionType.setReturnType("LeafClass");
            registryStoredQuery.setResponseOption(responseOptionType);

            AdhocQueryType adhocQueryType = new AdhocQueryType();
            adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"); // FindDocuments
                                                                                                                                                            // by
                                                                                                                                                            // patientId
            registryStoredQuery.setAdhocQuery(adhocQueryType);

            SlotType1 patientIdSlotType = new SlotType1();
            patientIdSlotType.setName("$XDSDocumentEntryPatientId");
            ValueListType patientIdValueListType = new ValueListType();

            //jericho patient id
            //patientIdValueListType.getValue().add(
            //		"6^^^&1.2.3.4.5.6.7.8&ISO"); // PatientId
            if (sOrg.equals("Jericho")) {
                patientIdValueListType.getValue().add(patientId+"^^^&"+AdminContext.getSessionAttributes().getSelectedOrgId()+"&ISO"); // PatientId                
            }
            else {
                //HIPAAT
                patientIdValueListType.getValue().add("'"+patientId+"^^^&"+AdminContext.getSessionAttributes().getSelectedOrgId()+"&ISO'"); // PatientId
            }

            patientIdSlotType.setValueList(patientIdValueListType);
            adhocQueryType.getSlot().add(patientIdSlotType);

            SlotType1 statusSlotType = new SlotType1();
            statusSlotType.setName("$XDSDocumentEntryStatus");
            ValueListType statusValueListType = new ValueListType();
            statusValueListType.getValue().add(
                            "('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
            statusSlotType.setValueList(statusValueListType);
            adhocQueryType.getSlot().add(statusSlotType);

            Object result = gWeb.registryStoredQuery(registryStoredQuery);            
            res = marshall(result);
        }
        
        System.out.println("RESULTS: "+res);
        return res;
    }

    private AdhocQueryResponse getQueryResponse(String mxml) {
        AdhocQueryResponse obj = null;
        try {
            JAXBContext context = JAXBContext.newInstance(AdhocQueryResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(mxml);

            Object o = unmarshaller.unmarshal(sr);
            obj = (AdhocQueryResponse)o;

        }
        catch (Exception e) {
            //log.warn("",e);
            e.printStackTrace();
        }        
        return obj;
        
    }
    
    private List<ExchangeResults> processMetaData(RegistryObjectListType objList) {
        List<ExchangeResults> docInfoList = new ArrayList();
            //add all from nhinresults
        List<JAXBElement<? extends IdentifiableType>> extrinsicObjects = objList.getIdentifiable();

        if (extrinsicObjects != null && extrinsicObjects.size() > 0) {
            for (JAXBElement<? extends IdentifiableType> jaxb : extrinsicObjects) {
                ExchangeResults docInfo = new ExchangeResults();

                if (jaxb.getValue() instanceof ExtrinsicObjectType) {
                    ExtrinsicObjectType extrinsicObject = (ExtrinsicObjectType) jaxb.getValue();

                    if (extrinsicObject != null) {
                        docInfo.setPatientId(patientId);
                        docInfo.setDocName(extractDocumentTitle(extrinsicObject));
                        docInfo.setDocType(extractDocumentType(extrinsicObject));
                        //String creationTime = extractCreationTime(extrinsicObject);
                        docInfo.setDocDate(""); //(formatDate(creationTime, HL7_DATE_FORMAT, REGULAR_DATE_FORMAT));
                        docInfo.setOrgName(extractInstitution(extrinsicObject));
                        docInfo.setDocId(extractDocumentID(extrinsicObject));
                        docInfo.setRespId(extractRespositoryUniqueID(extrinsicObject));
                        docInfo.setMesgId(messageId);
                        docInfoList.add(docInfo);
                    }
                }
            }
        }
        
        
        
        return docInfoList;
    }
    
    private String extractSingleSlotValue(ExtrinsicObjectType extrinsicObject, String slotName) {
        String slotValue = null;
        for (SlotType1 slot : extrinsicObject.getSlot()) {
            if (slot != null && slot.getName().contentEquals(slotName)) {
                if (slot.getValueList().getValue().size() > 0) {
                    slotValue = slot.getValueList().getValue().get(0);
                    break;
                }
            }
        }
        return slotValue;
    }

    private String extractDocumentTitle(ExtrinsicObjectType extrinsicObject) {

        String documentTitle = null;

        if (extrinsicObject != null &&
                extrinsicObject.getName() != null) {
            List<LocalizedStringType> localizedString = extrinsicObject.getName().getLocalizedString();

            if (localizedString != null && localizedString.size() > 0) {
                documentTitle = localizedString.get(0).getValue();
            }
        }

        return documentTitle;
    }
    private String extractDocumentType(ExtrinsicObjectType extrinsicObject) {
        ClassificationType classification = extractClassification(extrinsicObject, "urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a");

        String documentTypeCode = classification.getName().getLocalizedString().get(0).getValue();
        return documentTypeCode;
    }

    private ClassificationType extractClassification(ExtrinsicObjectType extrinsicObject, String classificationScheme) {
        ClassificationType classification = null;

        for (ClassificationType classificationItem : extrinsicObject.getClassification()) {
            if (classificationItem != null && classificationItem.getClassificationScheme().contentEquals(classificationScheme)) {
                classification = classificationItem;
                break;
            }
        }

        return classification;
    }
    
    private String extractCreationTime(ExtrinsicObjectType extrinsicObject) {
        return extractSingleSlotValue(extrinsicObject, "creationTime");
    }
    private String formatDate(String dateString, String inputFormat, String outputFormat) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat);
        Date date = null;

        try {
            date = inputFormatter.parse(dateString);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return outputFormatter.format(date);
    }

    private String extractInstitution(ExtrinsicObjectType extrinsicObject) {
        ClassificationType classification = extractClassification(extrinsicObject, "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d");

        String institution = null;

        if (classification != null && classification.getSlot() != null && !classification.getSlot().isEmpty()) {
            for (SlotType1 slot : classification.getSlot()) {
                if (slot != null && slot.getName().contentEquals("authorInstitution")) {
                    if (slot.getValueList() != null && slot.getValueList().getValue() != null && !slot.getValueList().getValue().isEmpty()) {
                        institution = slot.getValueList().getValue().get(0);
                        break;
                    }
                }
            }
        }

        return institution;
    }
    
    private String extractDocumentID(ExtrinsicObjectType extrinsicObject) {
        String documentID = null;

        ExternalIdentifierType identifier = extractIndentifierType(extrinsicObject, "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab");

        if (identifier != null) {
            documentID = identifier.getValue();
        }

        return documentID;
    }

    private ExternalIdentifierType extractIndentifierType(ExtrinsicObjectType extrinsicObject, String identificationScheme) {
        ExternalIdentifierType identifier = null;

        for (ExternalIdentifierType identifierItem : extrinsicObject.getExternalIdentifier()) {
            if (identifierItem != null && identifierItem.getIdentificationScheme().contentEquals(identificationScheme)) {
                identifier = identifierItem;
                break;
            }
        }

        return identifier;
    }
    private String extractRespositoryUniqueID(ExtrinsicObjectType extrinsicObject)
    {
        return extractSingleSlotValue(extrinsicObject, "repositoryUniqueId");
    }
    
    // just decrypt document not observations that have been masked
    public String decryptDocumentExcludeElements(byte[] processDocBytes, byte[] kekEncryptionKeyBytes) {
        Document processedDoc = null;
        String processedDocString = "";
        DESedeKeySpec desedeEncryptKeySpec;
        try {

                org.apache.xml.security.Init.init();

                String processDocString = new String(processDocBytes);

                processedDoc = loadDocument(processDocString);

                desedeEncryptKeySpec =
                                new DESedeKeySpec(kekEncryptionKeyBytes);
                SecretKeyFactory skfEncrypt =
                                SecretKeyFactory.getInstance("DESede");
                SecretKey desedeEncryptKey = skfEncrypt.generateSecret(desedeEncryptKeySpec);			

                /*************************************************
                 * DECRYPT DOCUMENT
                 *************************************************/
                Element encryptedDataElement = (Element) processedDoc
                                .getElementsByTagNameNS(
                                                EncryptionConstants.EncryptionSpecNS,
                                                EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

                /*
                 * The key to be used for decrypting xml data would be obtained from
                 * the keyinfo of the EncrypteData using the kek.
                 */
                XMLCipher xmlCipher = XMLCipher.getInstance();
                xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
                xmlCipher.setKEK(desedeEncryptKey);

                /*
                 * The following doFinal call replaces the encrypted data with
                 * decrypted contents in the document.
                 */
                if (encryptedDataElement != null)
                        xmlCipher.doFinal(processedDoc, encryptedDataElement);


                processedDocString = converXmlDocToString(processedDoc);

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }


        return processedDocString;
        
    }
    
	public String decryptDocument(byte[] processDocBytes, byte[] kekEncryptionKeyBytes, byte[] kekMaskingKeyBytes ) {

		Document processedDoc = null;
		String processedDocString = "";
		DESedeKeySpec desedeEncryptKeySpec;
		DESedeKeySpec desedeMaskKeySpec;
		try {

			org.apache.xml.security.Init.init();

			String processDocString = new String(processDocBytes);

			processedDoc = loadDocument(processDocString);

			desedeEncryptKeySpec =
					new DESedeKeySpec(kekEncryptionKeyBytes);
			SecretKeyFactory skfEncrypt =
					SecretKeyFactory.getInstance("DESede");
			SecretKey desedeEncryptKey = skfEncrypt.generateSecret(desedeEncryptKeySpec);			

			desedeMaskKeySpec =
					new DESedeKeySpec(kekMaskingKeyBytes);
			SecretKeyFactory skfMask =
					SecretKeyFactory.getInstance("DESede");
			SecretKey desedeMaskKey = skfMask.generateSecret(desedeMaskKeySpec);


			/*************************************************
			 * DECRYPT DOCUMENT
			 *************************************************/
			Element encryptedDataElement = (Element) processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

			/*
			 * The key to be used for decrypting xml data would be obtained from
			 * the keyinfo of the EncrypteData using the kek.
			 */
			XMLCipher xmlCipher = XMLCipher.getInstance();
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
			xmlCipher.setKEK(desedeEncryptKey);

			/*
			 * The following doFinal call replaces the encrypted data with
			 * decrypted contents in the document.
			 */
			if (encryptedDataElement != null)
				xmlCipher.doFinal(processedDoc, encryptedDataElement);

			/*************************************************
			 * DECRYPT ELEMENTS
			 *************************************************/
			NodeList encryptedDataElements = processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA);

			while (encryptedDataElements.getLength() > 0) {
				/*
				 * The key to be used for decrypting xml data would be obtained
				 * from the keyinfo of the EncrypteData using the kek.
				 */
				XMLCipher xmlMaskCipher = XMLCipher.getInstance();
				xmlMaskCipher.init(XMLCipher.DECRYPT_MODE, null);
				xmlMaskCipher.setKEK(desedeMaskKey);

				xmlMaskCipher.doFinal(processedDoc,
						((Element) encryptedDataElements.item(0)));

				encryptedDataElements = processedDoc.getElementsByTagNameNS(
						EncryptionConstants.EncryptionSpecNS,
						EncryptionConstants._TAG_ENCRYPTEDDATA);
			}

			processedDocString = converXmlDocToString(processedDoc);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return processedDocString;
	}	

    private Document getW3CDocument(String docString) {
        Document doc = null;
        try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource source = new InputSource(new StringReader(docString));

		doc = db.parse(source);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		return doc;
    }
    
    private void setSAMLValues() {
        messageId = UUID.randomUUID().toString();
        tokenSAML.setRESOURCE_ACTION_ID(AdminContext.getSessionAttributes().getAction());
        tokenSAML.setRESOURCE_ID(AdminContext.getSessionAttributes().getSelectedPatientId());
        tokenSAML.setRESOURCE_NAME(AdminContext.getSessionAttributes().getServiceName());
        tokenSAML.setRESOURCE_TYPE(AdminContext.getSessionAttributes().getServiceType());
        tokenSAML.setSUBJECT_STRUCTURED_ROLE(AdminContext.getSessionAttributes().getRole());
        tokenSAML.setSUBJECT_ID(AdminContext.getSessionAttributes().getProviderId());
        tokenSAML.setSUBJECT_LOCALITY(AdminContext.getSessionAttributes().getProviderHomeCommunityId());
        tokenSAML.setSUBJECT_PERMISSIONS(AdminContext.getSessionAttributes().getAllowedSensitivityActions());
        tokenSAML.setSUBJECT_PURPOSE_OF_USE(AdminContext.getSessionAttributes().getPurposeOfUse());
        tokenSAML.setUSER_CITY(AdminContext.getSessionAttributes().getCity());
        tokenSAML.setUSER_COUNTRY(AdminContext.getSessionAttributes().getCountry());
        tokenSAML.setUSER_NAME(AdminContext.getSessionAttributes().getUserId());
        tokenSAML.setUSER_ORGANIZATION(AdminContext.getSessionAttributes().getOrganization());
        tokenSAML.setUSER_ORGANIZATION_UNIT(AdminContext.getSessionAttributes().getOrganizationUnit());
        tokenSAML.setUSER_STATE(AdminContext.getSessionAttributes().getState());
        tokenSAML.setMESSAGE_ID(messageId);
        
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
    private String converXmlDocToString(Document xmlDocument) {

        String xmlString = "";

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
            xmlString = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return xmlString;
    }
    private static Document loadDocument(String xmlString) throws Exception {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource source = new InputSource(new StringReader(xmlString));

            Document doc = db.parse(source);

            return doc;
    }
    
    private void refreshTable(List<ExchangeResults> lres) {
        IndexedContainer res = createTableContainer(lres);
        table.setContainerDataSource(res);
        table.setVisibleColumns(new Object[] {"patientId", "docId", "docType", "respId", "msgId"});
        table.setColumnHeaders(new String[] {"Patient ID", "Document ID", "Document Type", "Repository", "Message"});
        table.requestRepaint();
    }
    
    private void displayErrorMessage(String err) {
         getWindow().showNotification("Warning - Registry Lookup Return No Results.", "", Window.Notification.TYPE_WARNING_MESSAGE);                            
    }
    
    private <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
    }

    private void processPDFForViewing() {
            final String pdfS = getPDFFromCDA();
            
            System.out.println("PDF STRING: "+pdfS.length());
            
            try {
                Popover popover = getPopoverPDF(pdfS, "Patient Consent Directive");
                popover.showRelativeTo(getNavigationBar());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            
    }
    
    private void processXACMLForViewing() {
        String x = getXACMLFromCDA();
            try {
                Popover popover = getPopoverLabel(convertXMLtoXHTML(x), "Patient Consent Computable(XACML)");
                popover.showRelativeTo(getNavigationBar());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }     
    }
    
    private void processSAMLForViewing() {
        String x = AdminContext.getSessionAttributes().getMostRecentSAMLAssertion();
        try {
                Popover popover = getPopoverLabel(convertXMLtoXHTML(x), "Most Recent SAML Assertion");
                popover.showRelativeTo(getNavigationBar());            
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void processMetaDataForViewing() {
        try {
                if (requestedService.equals("DocQuery")) {
                    Popover popover = getPopoverLabel(convertXMLtoXHTML(metaData), "Meta Data");
                    popover.showRelativeTo(getNavigationBar());
                }
                else {
                    Popover popover2 = getPopoverTextArea(metaData, "Meta Data");
                    popover2.showRelativeTo(getNavigationBar());                    
                }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }        
    }
    
    private String getPDFFromCDA() {
        String res = "";
        try {
            POCDMT000040ClinicalDocument consentDocument = cProvider.createClinicalDocumentFromXMLString(getConsentDirective());
            String bPDF = processClinicalDocument(consentDocument, CONSENT_MEDIA_TYPE_PDF);
            res = new String(Base64.decodeBase64(bPDF.getBytes()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    private String getXACMLFromCDA() {
        String res = "";
        try {
            POCDMT000040ClinicalDocument consentDocument = cProvider.createClinicalDocumentFromXMLString(getConsentDirective());
            String sOrg = AdminContext.getSessionAttributes().getSelectedOrg();
            String bPDF = "";
            if (sOrg.equals("Jericho")) {
                bPDF = processClinicalDocument(consentDocument, "text/string");
                res = new String(Base64.decodeBase64(bPDF.getBytes()));
            }
            else {
                bPDF = processClinicalDocument(consentDocument, CONSENT_MEDIA_TYPE_XACML);
                res = new String(Base64.decodeBase64(bPDF.getBytes()));              
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;        
    }

    private String processClinicalDocument(POCDMT000040ClinicalDocument doc, String contentType) {
        String res = "";
        try {
            POCDMT000040Component2 comp2 = doc.getComponent();
            POCDMT000040StructuredBody body = comp2.getStructuredBody();
            POCDMT000040Component3 comp3 = body.getComponent().get(0);
            POCDMT000040Section section = comp3.getSection();
            POCDMT000040Entry entry = section.getEntry().get(0);
            POCDMT000040Act act = entry.getAct();
            List<POCDMT000040EntryRelationship> relationships = act.getEntryRelationship();
            Iterator iterRelationships = relationships.iterator();
            // determine if this is a negative disclosure
            while (iterRelationships.hasNext()) {
                POCDMT000040EntryRelationship rel = (POCDMT000040EntryRelationship)iterRelationships.next();
                try {
                    POCDMT000040ObservationMedia obs =  rel.getObservationMedia();
                    if (obs != null) {
                        ED eVal = obs.getValue();
                        if (contentType.equals(eVal.getMediaType())) {
                            res = (String)eVal.getContent().get(0);
                        }
                    }
                }
                catch (Exception px) {
                    //just ignore as many will be null
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("PDF FILE LENGTH: "+res.length());
        return res;
    }
    private Resource createPdf(String mpdf) {
        StreamResource resource = new StreamResource(new Pdf(mpdf), "test.pdf?" + System.currentTimeMillis(), this.getApplication());
        resource.setMIMEType("application/pdf");
        return resource;
    }
    
    public static class Pdf implements com.vaadin.terminal.StreamResource.StreamSource {
        private final ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        public Pdf(String mpdf) {
            try {
                os.write(mpdf.getBytes());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(os.toByteArray());
        }
        

    }

    private String getConsentDirective() {
        String res = decryptDocument;
        // for testing lets just go get test version
//        try {
//            MitreConsentData cd = new MitreConsentData();
//            
//            CDAR2ConsentDirective_Service service = new CDAR2ConsentDirective_Service();
//            CDAR2ConsentDirective port = service.getCDAR2ConsentDirectivePort();
//            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, cdaEndpoint);
//            
//            byte[] results = port.getCDAR2ConsentDirective(cd.getPatientName(), cd.getPatientId(), cd.getPatientIDType(), cd.getPatientGender(), cd.getPatientDateOfBirth(),
//                                                cd.getAuthorization(), cd.getIntendedPOU(), cd.getAllowedPOU(), 
//                                                cd.getPrimaryRecipient(), cd.getAllowedRecipients(), cd.getMaskingActions(), cd.getRedactActions(), cd.getHomeCommunityId());
//            
//            res = new String(results);
//            decryptDocument = res;
//            System.out.println("PDF STRING FROM WEB SERVICE: "+res.length());
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
        //remove or comment testing block above
        return res;
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
        
        Button close = new Button(null, new ClickListener() {

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
        
        Button close = new Button(null, new ClickListener() {

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
    
    private Popover getPopoverPDF(String val, String title) {
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
        Embedded pdf = new Embedded();
        pdf.setType(Embedded.TYPE_BROWSER);
        pdf.setMimeType("application/pdf");
        pdf.setSizeFull();
        pdf.setWidth("100%");
        pdf.setHeight("700px");

        pdf.setSource(createPdf(val));
        
        layout2.addComponent(pdf);
        
        popLayout.addComponent(layout2);
        
        Button close = new Button(null, new ClickListener() {

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
    
    private String marshall(Object obj) {
        String res = "";
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

        // Create the marshaller, this is the nifty little thing that will
        // actually transform the object into XML
            Marshaller marshaller = context.createMarshaller();

        // Create a stringWriter to hold the XML
            StringWriter stringWriter = new StringWriter();

        // Marshal the javaObject and write the XML to the stringWriter
            marshaller.marshal(obj, stringWriter);
            
            res = stringWriter.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
}
