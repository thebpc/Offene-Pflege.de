/*
 * Created by JFormDesigner on Thu Feb 02 12:08:28 CET 2012
 */

package op.care;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.pane.CollapsiblePanes;
import entity.EntityTools;
import entity.info.ResInfo;
import entity.info.ResInfoTools;
import entity.info.Resident;
import op.OPDE;
import op.care.bhp.PnlBHP;
import op.care.dfn.PnlDFN;
import op.care.info.PnlInfo;
import op.care.info.PnlInformation;
import op.care.med.inventory.PnlInventory;
import op.care.nursingprocess.PnlNursingProcess;
import op.care.prescription.PnlPrescription;
import op.care.reports.PnlReport;
import op.care.sysfiles.PnlFiles;
import op.care.values.PnlValues;
import op.process.PnlProcess;
import op.tools.CleanablePanel;
import op.tools.GUITools;
import op.tools.NursingRecordsPanel;
import op.tools.SYSTools;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Torsten Löhr
 */
public class PnlCare extends NursingRecordsPanel {

    public static final String internalClassID = "nursingrecords.main";
    public static final int TAB_UEBERSICHT = 0;
    public static final int TAB_PB = 1;
    public static final int TAB_DFN = 2;
    public static final int TAB_BHP = 3;
    public static final int TAB_VITAL = 4;
    public static final int TAB_VERORDNUNG = 5;
    public static final int TAB_VORRAT = 6;
    public static final int TAB_INFO = 7;
    public static final int TAB_PPLANUNG = 8;
    public static final int TAB_VORGANG = 9;
    public static final int TAB_FILES = 10;
    public static final int TAB_INFO2 = 11;

    private boolean initPhase;
    private String[] tabs = new String[]{
            OPDE.lang.getString("nursingrecords.main.tab1"),
            OPDE.lang.getString("nursingrecords.main.tab2"),
            OPDE.lang.getString("nursingrecords.main.tab3"),
            OPDE.lang.getString("nursingrecords.main.tab4"),
            OPDE.lang.getString("nursingrecords.main.tab5"),
            OPDE.lang.getString("nursingrecords.main.tab6"),
            OPDE.lang.getString("nursingrecords.main.tab7"),
            OPDE.lang.getString("nursingrecords.main.tab8"),
            OPDE.lang.getString("nursingrecords.main.tab9"),
            OPDE.lang.getString("nursingrecords.main.tab10"),
            OPDE.lang.getString("nursingrecords.main.tab11")
//            OPDE.lang.getString("nursingrecords.main.tab12")
    };
    private Resident resident = null;
    private CollapsiblePanes searchPanes;
    private JScrollPane jspSearch;
    private NursingRecordsPanel previousPanel;

    public PnlCare(Resident resident, JScrollPane jspSearch) {
        initPhase = true;
        initComponents();
        this.jspSearch = jspSearch;
        this.resident = resident;
        initPanel();
        initPhase = false;
        jtpPflegeakteStateChanged(null);


//        EntityManager em = OPDE.createEM();
//        ResInfo info = em.find(ResInfo.class, 20149l);
//        em.close();
//
//        OPDE.debug(ResInfoTools.getContentAsHTML(info));
//
//

    }

    @Override
    public String getInternalClassID() {
        return ((CleanablePanel) jtpPflegeakte.getSelectedComponent()).getInternalClassID();
    }

    @Override
    public void cleanup() {
        for (int i = 0; i < jtpPflegeakte.getTabCount(); i++) {
            if (jtpPflegeakte.getComponentAt(i) != null && jtpPflegeakte.getComponentAt(i) instanceof CleanablePanel) {
                CleanablePanel cp = (CleanablePanel) jtpPflegeakte.getComponentAt(i);
                cp.cleanup();
                SYSTools.unregisterListeners((JComponent) jtpPflegeakte.getComponentAt(i));
            }
            jtpPflegeakte.setComponentAt(i, null);
        }
    }

    @Override
    public void switchResident(Resident res) {
        this.resident = EntityTools.find(Resident.class, res.getRID());
        GUITools.setResidentDisplay(resident);
        ((NursingRecordsPanel) jtpPflegeakte.getSelectedComponent()).switchResident(resident);
        jtpPflegeakte.setEnabledAt(TAB_VORRAT, resident.isCalcMediUPR1());
    }

    public void setJspSearch(JScrollPane jspSearch) {
        this.jspSearch = jspSearch;
    }

    @Override
    public void reload() {
        if (previousPanel != null) {
            previousPanel.reload();
        }
    }

    private void jtpPflegeakteStateChanged(ChangeEvent e) {
        if (initPhase) {
            return;
        }

        if (previousPanel != null) {
            previousPanel.cleanup();
        }

        switch (jtpPflegeakte.getSelectedIndex()) {
            case TAB_UEBERSICHT: {
                previousPanel = new PnlResOverview(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_UEBERSICHT, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlResOverview.internalClassID);
                break;
            }
            case TAB_PB: {
                previousPanel = new PnlReport(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_PB, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlResOverview.internalClassID);
                break;
            }
            case TAB_DFN: {
                previousPanel = new PnlDFN(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_DFN, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlReport.internalClassID);
                break;
            }
            case TAB_VITAL: {
                previousPanel = new PnlValues(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_VITAL, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlValues.internalClassID);
                break;
            }
            case TAB_INFO: {
                previousPanel = new PnlInformation(resident, jspSearch, this);
                jtpPflegeakte.setComponentAt(TAB_INFO, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlInformation.internalClassID);
                break;
            }
            case TAB_BHP: {
                previousPanel = new PnlBHP(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_BHP, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlBHP.internalClassID);
                break;
            }
            case TAB_PPLANUNG: {
                previousPanel = new PnlNursingProcess(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_PPLANUNG, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlNursingProcess.internalClassID);
                break;
            }
            case TAB_VERORDNUNG: {
                previousPanel = new PnlPrescription(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_VERORDNUNG, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlPrescription.internalClassID);
                break;
            }
            case TAB_VORRAT: {
                previousPanel = new PnlInventory(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_VORRAT, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlInventory.internalClassID);
                break;
            }
            case TAB_VORGANG: {
                previousPanel = new PnlProcess(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_VORGANG, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlProcess.internalClassID);
                break;
            }
            case TAB_FILES: {
                previousPanel = new PnlFiles(resident, jspSearch);
                jtpPflegeakte.setComponentAt(TAB_FILES, previousPanel);
                OPDE.getMainframe().setCurrentClassname(PnlFiles.internalClassID);
                break;
            }
//            case TAB_INFO2: {
//                previousPanel = new PnlInformation(resident, jspSearch, this);
//                jtpPflegeakte.setComponentAt(TAB_INFO2, previousPanel);
//                OPDE.getMainframe().setCurrentClassname(PnlInformation.internalClassID);
//                break;
//            }
            default: {
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        jtpPflegeakte = new JTabbedPane();

        //======== this ========
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FormLayout(
                    "default:grow",
                    "default:grow"));

            //======== jtpPflegeakte ========
            {
                jtpPflegeakte.setTabPlacement(SwingConstants.BOTTOM);
                jtpPflegeakte.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        jtpPflegeakteStateChanged(e);
                    }
                });
            }
            panel1.add(jtpPflegeakte, CC.xy(1, 1, CC.FILL, CC.FILL));
        }
        add(panel1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void initPanel() {
        for (int i = 0; i < tabs.length; i++) {
            jtpPflegeakte.add(tabs[i], new JPanel());
        }
        jtpPflegeakte.setEnabledAt(TAB_FILES, OPDE.isFTPworking());
        jtpPflegeakte.setEnabledAt(TAB_VORRAT, resident.isCalcMediUPR1());
//        jtpPflegeakte.setEnabledAt(TAB_INFO2, OPDE.isDebug() && OPDE.isAdmin());
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JTabbedPane jtpPflegeakte;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
