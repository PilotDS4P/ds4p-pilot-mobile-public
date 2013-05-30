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

/**
 *
 * @author Duane DeCouteau
 */
public class PatientRepositoryMapping {
    
    private String patientId;
    private String displayName;
    private String homeCommunityId;
    private String patientType;
    private String regEndpoint;
    private String repEndpoint;
    
    public PatientRepositoryMapping() {
        
    }
    
    public PatientRepositoryMapping(String patientId, String displayName, String homeCommunity, String patientType, String regEndpoint, String repEndpoint) {
        this.patientId = patientId;
        this.displayName = displayName;
        this.homeCommunityId = homeCommunity;
        this.patientType = patientType;
        this.regEndpoint = regEndpoint;
        this.repEndpoint = repEndpoint;        
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
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the homeCommunityId
     */
    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    /**
     * @param homeCommunityId the homeCommunityId to set
     */
    public void setHomeCommunityId(String homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    /**
     * @return the patientType
     */
    public String getPatientType() {
        return patientType;
    }

    /**
     * @param patientType the patientType to set
     */
    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    /**
     * @return the regEndpoint
     */
    public String getRegEndpoint() {
        return regEndpoint;
    }

    /**
     * @param regEndpoint the regEndpoint to set
     */
    public void setRegEndpoint(String regEndpoint) {
        this.regEndpoint = regEndpoint;
    }

    /**
     * @return the repEndpoint
     */
    public String getRepEndpoint() {
        return repEndpoint;
    }

    /**
     * @param repEndpoint the repEndpoint to set
     */
    public void setRepEndpoint(String repEndpoint) {
        this.repEndpoint = repEndpoint;
    }
    
    
    
    
}
