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
package gov.va.ds4p.ds4pmobileportal.session;

import gov.va.ehtac.ds4p.ws.up.UserAuthProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Duane DeCouteau
 */
public class SessionAttributes {
    private String selectedPatientName = "No Patient Selected";
    private String selectedPatientId = "0000000";
    private String userId = "DrDuane";
    private String serviceName = "DocumentRetrieve";
    private String providerId = "Duane_Decouteau@direct.healthvault-stage.com";
    private String organization = "Dept. of Veterans Affairs";
    private String organizationUnit = "HIG";
    private String state = "MT";
    private String city = "Missoula";
    private String country = "US";
    private String providerHomeCommunityId = "2.16.840.1.113883.4.349";
    private String purposeOfUse = "TREAT";
    private String patientId = "PUI100010060001";
    private String serviceType = "C32";
    private String action = "Execute";
    private String resourceLocality = "2.16.840.1.113883.3.467";   
    private List<String> allowedSensitivityActions = new ArrayList<String>(Arrays.asList("ETH","PSY","HIV"));
    private String role = "MD/Allopath";
    
    private String securityLevel = "R";
    private List<String> allowedResourceActions = new ArrayList<String>(Arrays.asList("DocumentQuery","DocumentRetrieve","DirectDocumentSend","RediscloseDocument", "NwHINDirectReceive", "NwHINDirectCollect"));

    private String endpoint = "http://localhost:8080/TestVAWebServices/SecuredDocumentServices?wsdl";
    private String feiEndpoint = "http://xds-demo.feisystems.com/Orchestrator/services/securedfilterc32service?wsdl";
    private String auditEndpoint = "http://localhost:8080/DS4PACSServices/DS4PAuditService?wsdl";
    private String userEndpoint = "http://localhost:8080/DS4PACSServices/UserProfile?wsdl";
    
    private UserAuthProfile userProfile = null;
    private String mostRecentSAMLAssertion = "";
    
    private String selectedOrg;
    private String selectedXDSReg;
    private String selectedXDSRep;
    private String selectedOrgId;
    
    private List<PatientRepositoryMapping> patientMappings = new ArrayList();
    /**
     * @return the selectedPatientName
     */
    public String getSelectedPatientName() {
        return selectedPatientName;
    }

    /**
     * @param selectedPatientName the selectedPatientName to set
     */
    public void setSelectedPatientName(String selectedPatientName) {
        this.selectedPatientName = selectedPatientName;
    }

    /**
     * @return the selectedPatientId
     */
    public String getSelectedPatientId() {
        return selectedPatientId;
    }

    /**
     * @param selectedPatientId the selectedPatientId to set
     */
    public void setSelectedPatientId(String selectedPatientId) {
        this.selectedPatientId = selectedPatientId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the organizationUnit
     */
    public String getOrganizationUnit() {
        return organizationUnit;
    }

    /**
     * @param organizationUnit the organizationUnit to set
     */
    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the providerHomeCommunityId
     */
    public String getProviderHomeCommunityId() {
        return providerHomeCommunityId;
    }

    /**
     * @param providerHomeCommunityId the providerHomeCommunityId to set
     */
    public void setProviderHomeCommunityId(String providerHomeCommunityId) {
        this.providerHomeCommunityId = providerHomeCommunityId;
    }

    /**
     * @return the purposeOfUse
     */
    public String getPurposeOfUse() {
        return purposeOfUse;
    }

    /**
     * @param purposeOfUse the purposeOfUse to set
     */
    public void setPurposeOfUse(String purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the resourceLocality
     */
    public String getResourceLocality() {
        return resourceLocality;
    }

    /**
     * @param resourceLocality the resourceLocality to set
     */
    public void setResourceLocality(String resourceLocality) {
        this.resourceLocality = resourceLocality;
    }

    /**
     * @return the allowedSensitivityActions
     */
    public List<String> getAllowedSensitivityActions() {
        return allowedSensitivityActions;
    }

    /**
     * @param allowedSensitivityActions the allowedSensitivityActions to set
     */
    public void setAllowedSensitivityActions(List<String> allowedSensitivityActions) {
        this.allowedSensitivityActions = allowedSensitivityActions;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the feiEndpoint
     */
    public String getFeiEndpoint() {
        return feiEndpoint;
    }

    /**
     * @param feiEndpoint the feiEndpoint to set
     */
    public void setFeiEndpoint(String feiEndpoint) {
        this.feiEndpoint = feiEndpoint;
    }

    /**
     * @return the auditEndpoint
     */
    public String getAuditEndpoint() {
        return auditEndpoint;
    }

    /**
     * @param auditEndpoint the auditEndpoint to set
     */
    public void setAuditEndpoint(String auditEndpoint) {
        this.auditEndpoint = auditEndpoint;
    }

    /**
     * @return the securityLevel
     */
    public String getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param securityLevel the securityLevel to set
     */
    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    /**
     * @return the allowedResourceActions
     */
    public List<String> getAllowedResourceActions() {
        return allowedResourceActions;
    }

    /**
     * @param allowedResourceActions the allowedResourceActions to set
     */
    public void setAllowedResourceActions(List<String> allowedResourceActions) {
        this.allowedResourceActions = allowedResourceActions;
    }

    /**
     * @return the userEndpoint
     */
    public String getUserEndpoint() {
        return userEndpoint;
    }

    /**
     * @param userEndpoint the userEndpoint to set
     */
    public void setUserEndpoint(String userEndpoint) {
        this.userEndpoint = userEndpoint;
    }

    /**
     * @return the userProfile
     */
    public UserAuthProfile getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile the userProfile to set
     */
    public void setUserProfile(UserAuthProfile userProfile) {
        this.userProfile = userProfile;
        this.setUserId(userProfile.getUserid());
        this.setProviderId(userProfile.getProviderid());
        // add other stuff
        
    }

    /**
     * @return the mostRecentSAMLAssertion
     */
    public String getMostRecentSAMLAssertion() {
        return mostRecentSAMLAssertion;
    }

    /**
     * @param mostRecentSAMLAssertion the mostRecentSAMLAssertion to set
     */
    public void setMostRecentSAMLAssertion(String mostRecentSAMLAssertion) {
        this.mostRecentSAMLAssertion = mostRecentSAMLAssertion;
    }

    /**
     * @return the patientMappings
     */
    public List<PatientRepositoryMapping> getPatientMappings() {
        
        if (patientMappings.isEmpty()) {
            patientMappings.add(new PatientRepositoryMapping("PUI100010060001", "Asample Patientone", "2.16.840.1.113883.3.467", "FEISystems", getFeiEndpoint(), getFeiEndpoint()));
            patientMappings.add(new PatientRepositoryMapping("PUI100010060007", "Asample Patienttwo", "2.16.840.1.113883.3.467", "FEISystems", getFeiEndpoint(), getFeiEndpoint()));
            patientMappings.add(new PatientRepositoryMapping("PUI100015060013", "Asample Patientthree", "2.16.840.1.113883.3.467", "FEISystems", getFeiEndpoint(), getFeiEndpoint()));
            patientMappings.add(new PatientRepositoryMapping("PUI100015060014", "Asample Patientfour", "2.16.840.1.113883.3.467", "FEISystems", getFeiEndpoint(), getFeiEndpoint()));
            patientMappings.add(new PatientRepositoryMapping("6", "Jericho Test", "1.2.3.4.5.6.7.8", "Jericho", "http://75.145.119.97/JerichoExchangeServices/XDSRegistry", "http://75.145.119.97/JerichoExchangeServices/XDSRepository"));
            patientMappings.add(new PatientRepositoryMapping("773-92-0342", "HIPAAT Test", "1.3.6.1.4.1.21367.2005.3.7", "HIPAAT", "http://98.143.106.135:8080/axis2/services/xdsregistryb", "http://98.143.106.135:8080/axis2/services/xdsrepositoryb"));
        }
        return patientMappings;
    }

    /**
     * @param patientMappings the patientMappings to set
     */
    public void setPatientMappings(List<PatientRepositoryMapping> patientMappings) {
        this.patientMappings = patientMappings;
    }
    
    public PatientRepositoryMapping getXDSInfo(String patientId) {
        PatientRepositoryMapping p = null;
        //System.out.println("******* PATIENTID IS ****** "+patientId);        
        List<PatientRepositoryMapping> pList = getPatientMappings();
        Iterator iter = pList.iterator();
        while (iter.hasNext()) {
            PatientRepositoryMapping pMap = (PatientRepositoryMapping)iter.next();
            String iId = pMap.getPatientId();
            //System.out.println("******* iid IS ****** "+iId);            
            if (iId.equals(patientId)) {
                //System.out.println("******* ITEM FOUND ****** "+iId);            
                
                p = pMap;
            }
        }
        return p;
    }

    /**
     * @return the selectedOrg
     */
    public String getSelectedOrg() {
        return selectedOrg;
    }

    /**
     * @param selectedOrg the selectedOrg to set
     */
    public void setSelectedOrg(String selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    /**
     * @return the selectedXDSReg
     */
    public String getSelectedXDSReg() {
        return selectedXDSReg;
    }

    /**
     * @param selectedXDSReg the selectedXDSReg to set
     */
    public void setSelectedXDSReg(String selectedXDSReg) {
        this.selectedXDSReg = selectedXDSReg;
    }

    /**
     * @return the selectedXDSRep
     */
    public String getSelectedXDSRep() {
        return selectedXDSRep;
    }

    /**
     * @param selectedXDSRep the selectedXDSRep to set
     */
    public void setSelectedXDSRep(String selectedXDSRep) {
        this.selectedXDSRep = selectedXDSRep;
    }

    /**
     * @return the selectedOrgId
     */
    public String getSelectedOrgId() {
        return selectedOrgId;
    }

    /**
     * @param selectedOrgId the selectedOrgId to set
     */
    public void setSelectedOrgId(String selectedOrgId) {
        this.selectedOrgId = selectedOrgId;
    }
    
}
