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
package gov.va.ds4p.ds4pmobileportal.pep;

import com.jerichosystems.esds.evaluation.xacml.context.ActionType;
import com.jerichosystems.esds.evaluation.xacml.context.AttributeType;
import com.jerichosystems.esds.evaluation.xacml.context.AttributeValueType;
import com.jerichosystems.esds.evaluation.xacml.context.DecisionType;
import com.jerichosystems.esds.evaluation.xacml.context.EnvironmentType;
import com.jerichosystems.esds.evaluation.xacml.context.RequestType;
import com.jerichosystems.esds.evaluation.xacml.context.ResourceType;
import com.jerichosystems.esds.evaluation.xacml.context.ResponseType;
import com.jerichosystems.esds.evaluation.xacml.context.ResultType;
import com.jerichosystems.esds.evaluation.xacml.context.StatusType;
import com.jerichosystems.esds.evaluation.xacml.context.SubjectType;
import com.jerichosystems.esds.evaluation.xacml.policy.ObligationsType;
import com.jerichosystems.esds.evaluation.xacml.policy.PolicySetType;
import com.jerichosystems.services.xacmlpolicyevaluationservice._1.XACMLPolicyEvaluationService;
import com.jerichosystems.services.xacmlpolicyevaluationservice._1.XACMLPolicyEvaluationServiceService;
import gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32Service;
import gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetResponse;
import gov.samhsa.ds4ppilot.ws.client.XDSRepositorybWebServiceClient;
import gov.samhsa.ds4ppilot.ws.client.XdsbRegistryWebServiceClient;
import gov.va.ds4p.cas.constants.DS4PConstants;
import gov.va.ds4p.cas.providers.ClinicalDocumentProvider;
import gov.va.ds4p.ds4pmobileportal.displayobjects.ExchangeResults;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ehtac.ds4p.ws.au.AuthLog;
import gov.va.ehtac.ds4p.ws.au.DS4PAudit;
import gov.va.ehtac.ds4p.ws.au.DS4PAuditService;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
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
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.apache.commons.codec.binary.Base64;
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
import org.oasis.names.tc.xspa.v2.XacmlResultType;
import org.oasis.names.tc.xspa.v2.XacmlStatusDetailType;
import org.oasis.names.tc.xspa.v2.XacmlStatusType;
/**
 *
 * @author Duane DeCouteau
 */
public class XACMLPolicyEnforcement {
    private String cdaDoc;
    private String xacmlPolicyS;
    private ClinicalDocumentProvider cProvider = new ClinicalDocumentProvider();
    private String docId;
    private String docRep;
    private String docType;
    private PolicyEnforcementObject decisionObject;
    private RequestType query = new RequestType();
    private PolicySetType policy = new PolicySetType();
    private ResponseType response = new ResponseType();
    private String requestResource;
    private String actInformationSensitivity;
    private Date requestStart;
    private Date requestEnd;
    
    private String pdpEndpoint = "http://75.145.119.97/XACMLPolicyEvaluationService/soap?wsdl";
    private String auditEndpoint = "http://localhost:8080/DS4PACSServices/DS4PAuditService?wsdl";
    
    private String messageId;
    private String purposeOfUse;
    
    public XACMLPolicyEnforcement() {
        
    }
    
    public PolicyEnforcementObject enforceResouce(String purposeOfUse, String actInformationSensitivity, String requestResource) {
        //if all else fails deny
        System.out.println("***** VA POLICY ENFORCEMENT REQUEST ********");
        this.requestResource = requestResource;
        this.actInformationSensitivity = actInformationSensitivity;
        decisionObject = new PolicyEnforcementObject();
        requestStart = new Date();
        messageId = UUID.randomUUID().toString();
        this.purposeOfUse = purposeOfUse;
        
        query = new RequestType();
        policy = new PolicySetType();
        response = new ResponseType();
        
        
        try {
            getCDAConsentDirectiveFromXDSb();
            setXACMLRequestAuthorization();

            XACMLPolicyEvaluationServiceService service = new XACMLPolicyEvaluationServiceService();
            XACMLPolicyEvaluationService port = service.getXACMLPolicyEvaluationServicePort();
            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pdpEndpoint);
            
            response = port.evaluatePolicySet(query, policy);
            
            processDecision();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        requestEnd = new Date();
        logEvent();
        return decisionObject;
    }
    
    public void getCDAConsentDirectiveFromXDSb() {
        try {
            String res = lookupPatient();
            AdhocQueryResponse adhoc = getQueryResponse(res);
            RegistryObjectListType rList = adhoc.getRegistryObjectList();
            processMetaData(rList);
            
            getDocument();
            getXACMLFromCDA();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String lookupPatient() {
        String res = "";
            String gEndpoint = "http://75.145.119.97/JerichoExchangeServices/XDSRegistry";
            String gUser = AdminContext.getSessionAttributes().getUserId();
            String gProviderId = AdminContext.getSessionAttributes().getProviderId();
            String gPou = AdminContext.getSessionAttributes().getPurposeOfUse();
            String gOrg = AdminContext.getSessionAttributes().getOrganization();
            String gOrgUnit = AdminContext.getSessionAttributes().getOrganizationUnit();
            String gCity = AdminContext.getSessionAttributes().getCity();
            String gState = AdminContext.getSessionAttributes().getState();
            String gCountry = AdminContext.getSessionAttributes().getCountry();
            String gOrgId = AdminContext.getSessionAttributes().getSelectedOrgId();
            String patientId = AdminContext.getSessionAttributes().getSelectedPatientId();           
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

            patientIdValueListType.getValue().add("6^^^&1.2.3.4.5.6.7.8&ISO"); // PatientId                

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
            
            return res;
    }
    
    private void getDocument() {
            XDSRepositorybWebServiceClient gWeb = new XDSRepositorybWebServiceClient("http://75.145.119.97/JerichoExchangeServices/XDSRepository");
            ihe.iti.xds_b._2007.RetrieveDocumentSetRequest request = new ihe.iti.xds_b._2007.RetrieveDocumentSetRequest();
            ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest docRequest = new ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest();
            docRequest.setRepositoryUniqueId(docRep);
            docRequest.setHomeCommunityId(AdminContext.getSessionAttributes().getSelectedOrgId());
            docRequest.setDocumentUniqueId(docId);
            request.getDocumentRequest().add(docRequest);
            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse resp =  gWeb.retrieveDocumentSetRequest(request);
            ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse docResp = resp.getDocumentResponse().get(0);
            cdaDoc = new String(docResp.getDocument());   
            //System.out.println(cdaDoc);
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
    
    private String getXACMLFromCDA() {
        String res = "";
        try {
            POCDMT000040ClinicalDocument consentDocument = cProvider.createClinicalDocumentFromXMLString(cdaDoc);
            String bPDF = "";
            bPDF = processClinicalDocument(consentDocument, "text/string");
            xacmlPolicyS = new String(Base64.decodeBase64(bPDF.getBytes()));
            System.out.println(xacmlPolicyS);
            policy = getXACMLPolicySetFromString();            
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
        //System.out.println("PDF FILE LENGTH: "+res.length());
        return res;
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
    
    private void processMetaData(RegistryObjectListType objList) {
        List<ExchangeResults> docInfoList = new ArrayList();
            //add all from nhinresults
        List<JAXBElement<? extends IdentifiableType>> extrinsicObjects = objList.getIdentifiable();

        if (extrinsicObjects != null && extrinsicObjects.size() > 0) {
            for (JAXBElement<? extends IdentifiableType> jaxb : extrinsicObjects) {
                ExchangeResults docInfo = new ExchangeResults();

                if (jaxb.getValue() instanceof ExtrinsicObjectType) {
                    ExtrinsicObjectType extrinsicObject = (ExtrinsicObjectType) jaxb.getValue();

                    if (extrinsicObject != null) {
                        docType = extractDocumentType(extrinsicObject);
                        //String creationTime = extractCreationTime(extrinsicObject);
                        docId = extractDocumentID(extrinsicObject);
                        docRep = extractRespositoryUniqueID(extrinsicObject); 
                    }
                }
            }
        }
        System.out.println("DOCUMENT TYPE: "+docType);
        System.out.println("DOCUMENT ID: "+docId);
        System.out.println("DOCUMENT RESP: "+docRep);
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
    
    private PolicySetType getXACMLPolicySetFromString() {
        PolicySetType res = null;

        try {
            //this is a base65 value so decode
            //String policyString = new String(com.sun.org.apache.xml.internal.security.utils.Base64.decode(xacmlPolicyS));
            //System.out.println("POLICYSET_STRING: "+policyString);
            JAXBContext context = JAXBContext.newInstance(PolicySetType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xacmlPolicyS);

            Object o = unmarshaller.unmarshal(sr);
            JAXBElement element = (JAXBElement)o;
            res = (PolicySetType)element.getValue();
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return res;
    }
    private void setXACMLRequestAuthorization() {
    
        RequestType rt = new RequestType();
        SubjectType st = new SubjectType();
        ActionType act = new ActionType();
        ResourceType resource = new ResourceType();
        EnvironmentType environment = new EnvironmentType();
        //Subject Information Identifier
        AttributeType at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_ID_NS);
        at.setDataType(DS4PConstants.STRING);
        AttributeValueType avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getProviderId());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);
        //Subject Purpose of Use
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_PURPOSE_OF_USE_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getPurposeOfUse());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);
        //Subject Home Community Identifier
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_LOCALITY_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getSelectedOrgId());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);
        //Subject ROLE
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_STRUCTURED_ROLE_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getRole());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);

        //the next subject attributes may be informational only as our focus on nwhin is homeCommunity...
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_ORGANIZATION_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getSelectedOrg());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);
        //this is location-clinic placeholder
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.SUBJECT_ORGANIZATION_ID_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getSelectedOrg());
        at.getAttributeValue().add(avt);
        st.getAttribute().add(at);
        
        List<String> sensList = AdminContext.getSessionAttributes().getAllowedSensitivityActions();
        if (!sensList.isEmpty()) {
            Iterator iter = sensList.iterator();
            at = new AttributeType();
            at.setAttributeId(DS4PConstants.SUBJECT_SENSITIVITY_PRIVILEGES);
            at.setDataType(DS4PConstants.STRING);
            while (iter.hasNext()) {
                String s = (String)iter.next();
                avt = new AttributeValueType();
                avt.getContent().add(s);
                at.getAttributeValue().add(avt);
            }
            if (!at.getAttributeValue().isEmpty()) st.getAttribute().add(at);
        }
        

        //Set Resource Attributes - Organization and Region
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.RESOURCE_NWHIN_SERVICE_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(requestResource);
        at.getAttributeValue().add(avt);
        resource.getAttribute().add(at);

        at = new AttributeType();
        at.setAttributeId(DS4PConstants.RESOURCE_LOCALITY_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getSelectedOrgId());
        at.getAttributeValue().add(avt);
        resource.getAttribute().add(at);

        //Kairon Patient Consent
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.MITRE_PATIENT_AUTHORIZATION);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add("Permit");
        at.getAttributeValue().add(avt);
        resource.getAttribute().add(at);
        
        at = new AttributeType();
        at.setAttributeId("urn:oasis:names:tc:xspa:1.0:resource:hl7:sensitivity");
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(actInformationSensitivity);
        at.getAttributeValue().add(avt);
        resource.getAttribute().add(at);
        
        
        // set request action
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.RESOURCE_ACTION_ID_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add("Execute");
        at.getAttributeValue().add(avt);
        act.getAttribute().add(at);
        
        //set environment
        at = new AttributeType();
        at.setAttributeId(DS4PConstants.RESOURCE_LOCALITY_NS);
        at.setDataType(DS4PConstants.STRING);
        avt = new AttributeValueType();
        avt.getContent().add(AdminContext.getSessionAttributes().getSelectedOrgId());
        at.getAttributeValue().add(avt);
        environment.getAttribute().add(at);
        
        
        
                
        query.getSubject().add(st);
        query.getResource().add(resource);
        query.setAction(act);
        query.setEnvironment(environment);
        
   }    
    
    private void processDecision() {
            ResultType xResult = response.getResult().get(0);
            DecisionType d = xResult.getDecision();
            StatusType s = xResult.getStatus();
            ObligationsType o = xResult.getObligations();

            XacmlResultType x = new XacmlResultType();
            x.setXacmlResultTypeDecision(d.value());
            x.setXacmlResultTypeResourceId(xResult.getResourceId());

            XacmlStatusType xStat = new XacmlStatusType();
            xStat.setXacmlStatusCodeType(s.getStatusCode().getValue());
            xStat.setXacmlStatusMessage(s.getStatusMessage());
            XacmlStatusDetailType detail = new XacmlStatusDetailType();

            if (s.getStatusDetail() != null) {
                List<Object> sObjs = s.getStatusDetail().getAny();
                Iterator sObjsIter = sObjs.iterator();
                while (sObjsIter.hasNext()) {
                    String obj = (String) sObjsIter.next();
                    detail.getXacmlStatusDetail().add(obj);
                }
            }
            System.out.println("XACMLCONTEXTHANDLER_DECISION: "+d.value());
            decisionObject.setPdpDecision(d.value());
            decisionObject.setPdpStatus("ok");
            //the following is a work around for XACML 2.0
            decisionObject.getPdpObligation().clear();
            decisionObject.setPdpRequest(dumpRequestToString());
            decisionObject.setPdpResponse(dumpResponseToString(xResult));
            decisionObject.setRequestTime(convertDateToXMLGregorianCalendar(requestStart));
            decisionObject.setResponseTime(convertDateToXMLGregorianCalendar(new Date()));
            decisionObject.setResourceName(requestResource);
            decisionObject.setResourceId(AdminContext.getSessionAttributes().getSelectedPatientId());
            decisionObject.setHomeCommunityId(AdminContext.getSessionAttributes().getSelectedOrgId()); 
            decisionObject.setMessageId(messageId);
    }    
    
    private String dumpRequestToString() {   
        String res = "";
        JAXBElement<RequestType> element = new JAXBElement<RequestType>(new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Request"), RequestType.class, query);
        try {
            JAXBContext context = JAXBContext.newInstance(RequestType.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(element, sw);
            res = sw.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    private String dumpResponseToString(ResultType resp) {
        String res = "";
        JAXBElement<ResultType> element = new JAXBElement<ResultType>(new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Result"), ResultType.class, resp);
        try {
            JAXBContext context = JAXBContext.newInstance(ResultType.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(element, sw);
            res = sw.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }  
        return res;
    }        
    
   private void logEvent() {
            AuthLog log = new AuthLog();
                //log.setDecision(d.value());
                log.setHealthcareObject(requestResource);
                log.setMsgDate(getCurrentDateTime());
                StringBuffer sb = new StringBuffer();
                if (!decisionObject.getPdpObligation().isEmpty()) {
                    Iterator iter = decisionObject.getPdpObligation().iterator();
                    while (iter.hasNext()) {
                        String obS = (String)iter.next() + "\n";
                        sb.append(obS);
                    }
                    log.setObligations(sb.toString());
                }
                else {
                    log.setObligations("");
                }
                log.setDecision(decisionObject.getPdpDecision());
                log.setPurposeOfUse(purposeOfUse);
                log.setRequestor(AdminContext.getSessionAttributes().getProviderId());
                log.setUniqueIdentifier(AdminContext.getSessionAttributes().getSelectedPatientId());
                log.setXacmlRequest(dumpRequestToString());
                //log.setXacmlRequest("");
                log.setXacmlResponse(dumpResponseToString(response.getResult().get(0)));
                //log.setXacmlResponse("");
                long startTime = requestStart.getTime();
                long endTime = requestEnd.getTime();
                long respTime = endTime - startTime;
                log.setHieMsgId(decisionObject.getMessageId());

                log.setServicingOrg("");

                log.setResponseTime(respTime);
                
            //log.setHieMsgId(currSubject.getMessageId());
            logEvent(log);
       
   }
   
   private void logEvent(AuthLog authlog) {
      try {
          DS4PAuditService service = new DS4PAuditService();
          DS4PAudit port = service.getDS4PAuditPort();
          ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, auditEndpoint);  
          port.saveAuthorizationEvent(authlog);
      }
      catch (Exception ex) {
          ex.printStackTrace();
      }
   }

      private XMLGregorianCalendar getCurrentDateTime() {
       XMLGregorianCalendar xgc = null;
       try {
        GregorianCalendar gc = new GregorianCalendar();
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        xgc = dtf.newXMLGregorianCalendar(gc);
       }
       catch (Exception ex) {
          ex.printStackTrace();
       }
        return xgc;
   }    
      
    private XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date dt) {
        XMLGregorianCalendar xcal = null;
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dt);
            xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return xcal;
    }
      
}
