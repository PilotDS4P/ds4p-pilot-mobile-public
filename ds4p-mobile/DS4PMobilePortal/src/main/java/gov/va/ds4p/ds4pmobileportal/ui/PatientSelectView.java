/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.ds4pmobileportal.ui;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import gov.va.ds4p.ds4pmobileportal.filter.AdminContext;
import gov.va.ds4p.ds4pmobileportal.session.PatientRepositoryMapping;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Duane DeCouteau
 */
public class PatientSelectView extends NavigationView {
    private ComboBox patientList;

    @Override
    public void attach() {
        super.attach();
        if (patientList == null) {
            buildView();
        }
    }
    
    private void buildView() {
        try {
            CssLayout content = new CssLayout();
            content.setWidth("100%");
            setCaption("Patient Selection");

            VerticalComponentGroup vGroup = new VerticalComponentGroup();

            Label selectaction = new Label(
                    "<div style='color:#333;'><p>You must select a patient first prior to demonstrating"
                                                    + " <b>Data Segementation for Privacy</b> use cases.</p>"
                                                    + "<p> After patient is selected you may perform a secured"
                                                    + " eHealth Exchange (pull) or eHealth Direct (push) demonstration.</p></div>",
                                    Label.CONTENT_XHTML);

            patientList = new ComboBox("Available Patients");
            populatePatientList();

            patientList.addListener(new ComboBox.ValueChangeListener() {

                @Override
                public void valueChange(ValueChangeEvent event) {
                    String val = (String)patientList.getValue();
                    String valName = (String)patientList.getItemCaption(val);
                    AdminContext.getSessionAttributes().setSelectedPatientId(val);
                    AdminContext.getSessionAttributes().setSelectedPatientName(valName);
                    setSessionValues(val);
                 }
            });

            patientList.setImmediate(true);
            patientList.setTextInputAllowed(false);
            vGroup.addComponent(selectaction);
            vGroup.addComponent(patientList);

            content.addComponent(vGroup);

            setContent(content);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void populatePatientList() {        
        List<PatientRepositoryMapping> pList = AdminContext.getSessionAttributes().getPatientMappings();
        Iterator iter = pList.iterator();
        while (iter.hasNext()) {
            PatientRepositoryMapping p = (PatientRepositoryMapping)iter.next();
            String pId = p.getPatientId();
            String pName = p.getDisplayName();
            patientList.addItem(pId);
            patientList.setItemCaption(pId, pName);   
        }   
    }
    
    private void setSessionValues(String val) {
        try {
            PatientRepositoryMapping obj = AdminContext.getSessionAttributes().getXDSInfo(val);
            //System.out.println("******* HC IS ****** "+obj.getHomeCommunityId());
            AdminContext.getSessionAttributes().setSelectedOrg(obj.getPatientType());
            AdminContext.getSessionAttributes().setSelectedOrgId(obj.getHomeCommunityId());
            AdminContext.getSessionAttributes().setSelectedXDSReg(obj.getRegEndpoint());
            AdminContext.getSessionAttributes().setSelectedXDSRep(obj.getRepEndpoint());
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
