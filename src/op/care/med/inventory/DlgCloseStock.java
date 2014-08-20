/*
 * OffenePflege
 * Copyright (C) 2006-2012 Torsten Löhr
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License V2 as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to 
 * the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA
 * www.offene-pflege.de
 * ------------------------ 
 * Auf deutsch (freie Übersetzung. Rechtlich gilt die englische Version)
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der GNU General Public License, 
 * wie von der Free Software Foundation veröffentlicht, weitergeben und/oder modifizieren, gemäß Version 2 der Lizenz.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen von Nutzen sein wird, aber 
 * OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben. Falls nicht, 
 * schreiben Sie an die Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA.
 * 
 */
package op.care.med.inventory;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import entity.prescription.DosageFormTools;
import entity.prescription.MedStock;
import entity.prescription.MedStockTools;
import entity.prescription.MedStockTransactionTools;
import op.OPDE;
import op.care.prescription.PnlPrescription;
import op.threads.DisplayManager;
import op.tools.MyJDialog;
import op.tools.SYSConst;
import op.tools.SYSTools;
import org.apache.commons.collections.Closure;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;

/**
 * @author tloehr
 */
public class DlgCloseStock extends MyJDialog {

    private MedStock medStock;
    private Closure actionBlock;
    public static final String internalClassID = PnlPrescription.internalClassID + ".dlgCloseStock";

    /**
     * Creates new form DlgBestandAnbruch
     */
    public DlgCloseStock(MedStock medStock, Closure actionBlock) {
        super(false);
        this.actionBlock = actionBlock;
        this.medStock = medStock;
        initComponents();
        initDialog();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the PrinterForm Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        txtInfo = new JTextPane();
        rbLeer = new JRadioButton();
        rbStellen = new JRadioButton();
        txtLetzte = new JTextField();
        lblEinheiten = new JLabel();
        rbAbgelaufen = new JRadioButton();
        jSeparator1 = new JSeparator();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        rbGefallen = new JRadioButton();
        cmbBestID = new JComboBox();
        panel1 = new JPanel();
        btnClose = new JButton();
        btnOk = new JButton();

        //======== this ========
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        //======== jPanel1 ========
        {
            jPanel1.setBorder(null);
            jPanel1.setLayout(new FormLayout(
                "14dlu, $lcgap, 145dlu, $lcgap, 41dlu, $lcgap, 93dlu, $lcgap, 14dlu",
                "14dlu, $lgap, fill:70dlu:grow, 4*($lgap, fill:default), $lgap, $rgap, $lgap, fill:default, $lgap, $rgap, $lgap, default, $lgap, 14dlu"));

            //======== jScrollPane1 ========
            {

                //---- txtInfo ----
                txtInfo.setEditable(false);
                txtInfo.setFont(new Font("Arial", Font.PLAIN, 14));
                jScrollPane1.setViewportView(txtInfo);
            }
            jPanel1.add(jScrollPane1, CC.xywh(3, 3, 5, 1));

            //---- rbLeer ----
            rbLeer.setSelected(true);
            rbLeer.setText("Die Packung ist nun leer");
            rbLeer.setFont(new Font("Arial", Font.PLAIN, 14));
            rbLeer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rbLeerActionPerformed(e);
                }
            });
            jPanel1.add(rbLeer, CC.xy(3, 5));

            //---- rbStellen ----
            rbStellen.setText("Beim Vorab Stellen haben Sie die letzten ");
            rbStellen.setFont(new Font("Arial", Font.PLAIN, 14));
            rbStellen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rbStellenActionPerformed(e);
                }
            });
            jPanel1.add(rbStellen, CC.xywh(3, 7, 2, 1));

            //---- txtLetzte ----
            txtLetzte.setText("jTextField1");
            txtLetzte.setFont(new Font("Arial", Font.PLAIN, 14));
            txtLetzte.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    txtLetzteFocusLost(e);
                }
            });
            jPanel1.add(txtLetzte, CC.xy(5, 7));

            //---- lblEinheiten ----
            lblEinheiten.setText("Einheiten verbraucht.");
            lblEinheiten.setFont(new Font("Arial", Font.PLAIN, 14));
            jPanel1.add(lblEinheiten, CC.xy(7, 7));

            //---- rbAbgelaufen ----
            rbAbgelaufen.setText("Die Packung ist abgelaufen oder wird nicht mehr ben\u00f6tigt. Bereit zur Entsorgung.");
            rbAbgelaufen.setFont(new Font("Arial", Font.PLAIN, 14));
            rbAbgelaufen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rbAbgelaufenActionPerformed(e);
                }
            });
            jPanel1.add(rbAbgelaufen, CC.xywh(3, 9, 5, 1));
            jPanel1.add(jSeparator1, CC.xywh(3, 13, 5, 1));

            //---- jLabel2 ----
            jLabel2.setText("Als n\u00e4chstes Packung soll die Nummer");
            jLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
            jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
            jPanel1.add(jLabel2, CC.xy(3, 15));

            //---- jLabel3 ----
            jLabel3.setText("angebrochen werden.");
            jLabel3.setFont(new Font("Arial", Font.PLAIN, 14));
            jPanel1.add(jLabel3, CC.xy(7, 15));

            //---- rbGefallen ----
            rbGefallen.setText("<html>Die Packung ist <font color=\"red\">runter gefallen</font> oder <font color=\"red\">verschwunden</font> und muss ausgebucht werden.</html>");
            rbGefallen.setFont(new Font("Arial", Font.PLAIN, 14));
            rbGefallen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rbGefallenActionPerformed(e);
                }
            });
            jPanel1.add(rbGefallen, CC.xywh(3, 11, 5, 1));

            //---- cmbBestID ----
            cmbBestID.setModel(new DefaultComboBoxModel(new String[] {
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4"
            }));
            cmbBestID.setFont(new Font("Arial", Font.PLAIN, 14));
            cmbBestID.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    cmbBestIDItemStateChanged(e);
                }
            });
            jPanel1.add(cmbBestID, CC.xy(5, 15));

            //======== panel1 ========
            {
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                //---- btnClose ----
                btnClose.setIcon(new ImageIcon(getClass().getResource("/artwork/22x22/cancel.png")));
                btnClose.setText(null);
                btnClose.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnCloseActionPerformed(e);
                    }
                });
                panel1.add(btnClose);

                //---- btnOk ----
                btnOk.setIcon(new ImageIcon(getClass().getResource("/artwork/22x22/apply.png")));
                btnOk.setText(null);
                btnOk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnOkActionPerformed(e);
                    }
                });
                panel1.add(btnOk);
            }
            jPanel1.add(panel1, CC.xy(7, 19, CC.RIGHT, CC.DEFAULT));
        }
        contentPane.add(jPanel1);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rbLeer);
        buttonGroup1.add(rbStellen);
        buttonGroup1.add(rbAbgelaufen);
        buttonGroup1.add(rbGefallen);
    }// </editor-fold>//GEN-END:initComponents

    private void rbAbgelaufenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAbgelaufenActionPerformed
        txtLetzte.setEnabled(rbStellen.isSelected());
    }//GEN-LAST:event_rbAbgelaufenActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        medStock = null;
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    @Override
    public void dispose() {
        actionBlock.execute(medStock);
        SYSTools.unregisterListeners(this);
        super.dispose();
    }

    private void initDialog() {
        String text = SYSTools.xx(internalClassID + ".youWantToClose1a") + medStock.getID() + SYSTools.xx(internalClassID + ".youWantToClose1b");
        text += "<br/>" + MedStockTools.getTextASHTML(medStock) + "</br>";
        text += "<br/>" + SYSTools.xx(internalClassID + ".chooseAReason") + ":";
        txtInfo.setContentType("text/html");
        txtInfo.setText(SYSTools.toHTML(SYSConst.html_div(text)));

        EntityManager em = OPDE.createEM();
        Query query = em.createQuery(" " +
                " SELECT b FROM MedStock b " +
                " WHERE b.inventory = :inventory AND b.out = :out AND b.opened = :opened " +
                " ORDER BY b.in, b.id ");

        query.setParameter("inventory", medStock.getInventory());
        query.setParameter("out", SYSConst.DATE_UNTIL_FURTHER_NOTICE);
        query.setParameter("opened", SYSConst.DATE_UNTIL_FURTHER_NOTICE);
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel(query.getResultList().toArray());
        dcbm.insertElementAt(SYSTools.xx("misc.msg.none"), 0);
        cmbBestID.setModel(dcbm);
        cmbBestID.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
                String text = o instanceof MedStock ? ((MedStock) o).getID().toString() : o.toString();
                return new JLabel(text);
            }
        });
        em.close();

        int index = Math.min(2, cmbBestID.getItemCount());
        cmbBestID.setSelectedIndex(index - 1);

        lblEinheiten.setText(SYSConst.UNITS[medStock.getTradeForm().getDosageForm().getPackUnit()] + " " + SYSTools.xx("misc.msg.usedup"));
        txtLetzte.setText("");
        txtLetzte.setEnabled(false);
        // Das mit dem Vorabstellen nur bei Formen, die auf Stück basieren also APV = 1
        rbStellen.setEnabled(medStock.getTradeForm().getDosageForm().getUPRState() == DosageFormTools.STATE_UPR1);
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        save();//GEN-LAST:event_btnOkActionPerformed
    }

    private void rbStellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbStellenActionPerformed
        txtLetzte.setEnabled(true);
        txtLetzte.requestFocus();
    }//GEN-LAST:event_rbStellenActionPerformed

    private void rbLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLeerActionPerformed
        txtLetzte.setEnabled(rbStellen.isSelected());
    }//GEN-LAST:event_rbLeerActionPerformed

    private void txtLetzteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLetzteFocusLost
        try {
            double inhalt = Double.parseDouble(txtLetzte.getText().replace(",", "."));
            if (inhalt <= 0d) {
                txtLetzte.setText("1");
            }
        } catch (NumberFormatException ex) {
            txtLetzte.setText("1");
        }
    }//GEN-LAST:event_txtLetzteFocusLost

    private void cmbBestIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBestIDItemStateChanged
        if (cmbBestID.getSelectedIndex() == 0) {
            cmbBestID.setToolTipText(null);
        } else {
            MedStock myBestand = (MedStock) cmbBestID.getSelectedItem();
            cmbBestID.setToolTipText(SYSTools.toHTML(MedStockTools.getTextASHTML(myBestand)));
        }
    }//GEN-LAST:event_cmbBestIDItemStateChanged

    private void rbGefallenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGefallenActionPerformed
        txtLetzte.setEnabled(rbStellen.isSelected());
    }//GEN-LAST:event_rbGefallenActionPerformed

    private void save() {
        EntityManager em = OPDE.createEM();
        try {
            em.getTransaction().begin();

            final MedStock myStock = em.merge(medStock);
            em.lock(myStock, LockModeType.OPTIMISTIC);
            em.lock(em.merge(myStock.getInventory().getResident()), LockModeType.OPTIMISTIC);
            em.lock(em.merge(myStock.getInventory()), LockModeType.OPTIMISTIC);

            OPDE.important("StockID: " + myStock.getID() + " " + SYSTools.xx("misc.msg.closed"));
            OPDE.important("UID: " + OPDE.getLogin().getUser().getUID());

            MedStock nextBest = null;
            if (cmbBestID.getSelectedIndex() > 0) {
                nextBest = em.merge((MedStock) cmbBestID.getSelectedItem());
                OPDE.important(SYSTools.xx(internalClassID + ".LOG.STATE_EDIT_EMPTY_SOON1") + ": " + nextBest.getID());
                em.lock(nextBest, LockModeType.OPTIMISTIC);
                myStock.setNextStock(nextBest);
            }

            if (rbStellen.isSelected()) {
                BigDecimal inhalt = new BigDecimal(Double.parseDouble(txtLetzte.getText().replace(",", ".")));
                MedStockTools.setStockTo(em, myStock, inhalt, SYSTools.xx(internalClassID + ".TX.STATE_EDIT_EMPTY_SOON"), MedStockTransactionTools.STATE_EDIT_EMPTY_SOON);
                myStock.setState(MedStockTools.STATE_WILL_BE_CLOSED_SOON);
                OPDE.important(SYSTools.xx(internalClassID + ".LOG.STATE_EDIT_EMPTY_SOON1") + ": " + inhalt);
            } else {
                if (rbGefallen.isSelected()) {
                    MedStockTools.close(em, myStock, SYSTools.xx(internalClassID + ".TX.STATE_EDIT_EMPTY_BROKEN_OR_LOST"), MedStockTransactionTools.STATE_EDIT_EMPTY_BROKEN_OR_LOST);
                    OPDE.important(SYSTools.xx(internalClassID + ".LOG.STATE_EDIT_EMPTY_BROKEN_OR_LOST"));
                } else if (rbAbgelaufen.isSelected()) {
                    MedStockTools.close(em, myStock, SYSTools.xx(internalClassID + ".TX.STATE_EDIT_EMPTY_PAST_EXPIRY"), MedStockTransactionTools.STATE_EDIT_EMPTY_PAST_EXPIRY);
                    OPDE.important(SYSTools.xx(internalClassID + ".LOG.STATE_EDIT_EMPTY_PAST_EXPIRY"));
                } else {
                    MedStockTools.close(em, myStock, SYSTools.xx(internalClassID + ".TX.STATE_EDIT_EMPTY_NOW"), MedStockTransactionTools.STATE_EDIT_EMPTY_NOW);
                    OPDE.important(SYSTools.xx(internalClassID + ".LOG.STATE_EDIT_EMPTY_NOW"));
                }
            }

            em.getTransaction().commit();

            medStock = myStock;

        } catch (javax.persistence.OptimisticLockException ole) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (ole.getMessage().indexOf("Class> entity.info.Resident") > -1) {
                OPDE.getMainframe().emptyFrame();
                OPDE.getMainframe().afterLogin();
            }
            OPDE.getDisplayManager().addSubMessage(DisplayManager.getLockMessage());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            OPDE.fatal(e);
        } finally {
            em.close();
        }

        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextPane txtInfo;
    private JRadioButton rbLeer;
    private JRadioButton rbStellen;
    private JTextField txtLetzte;
    private JLabel lblEinheiten;
    private JRadioButton rbAbgelaufen;
    private JSeparator jSeparator1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JRadioButton rbGefallen;
    private JComboBox cmbBestID;
    private JPanel panel1;
    private JButton btnClose;
    private JButton btnOk;
    // End of variables declaration//GEN-END:variables
}
