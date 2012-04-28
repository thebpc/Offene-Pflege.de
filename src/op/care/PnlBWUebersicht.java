/*
 * OffenePflege
 * Copyright (C) 2008 Torsten Löhr
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
package op.care;

import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.pane.CollapsiblePanes;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import entity.Bewohner;
import entity.BewohnerTools;
import op.OPDE;
import op.tools.GUITools;
import op.tools.NursingRecordsPanel;
import op.tools.SYSPrint;
import op.tools.SYSTools;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

/**
 * @author tloehr
 */
public class PnlBWUebersicht extends NursingRecordsPanel {

    private Bewohner bewohner;
    private CollapsiblePanes searchPanes;
    private JScrollPane jspSearch;
    private JCheckBox cbMedi;
    private JCheckBox cbBilanz;
    private JCheckBox cbBerichte;
    private JCheckBox cbBWInfo;
    private ItemListener itemListener;
    private MouseAdapter mouseAdapter;

    /**
     * Creates new form PnlBWUebersicht
     */
    public PnlBWUebersicht(Bewohner bewohner, JScrollPane jspSearch) {
        initComponents();
        this.jspSearch = jspSearch;
        initPanel();
        prepareSearchArea();
        change2Bewohner(bewohner);
    }

    private void initPanel() {
        txtUebersicht.setContentType("text/html");

        itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                reloadDisplay();
            }
        };

        mouseAdapter = GUITools.getHyperlinkStyleMouseAdapter();

        cbMedi = new JCheckBox("Medikamente", false);
        cbMedi.addItemListener(itemListener);
        cbMedi.addMouseListener(mouseAdapter);
        cbBerichte = new JCheckBox("Pflegeberichte", false);
        cbBerichte.addItemListener(itemListener);
        cbBerichte.addMouseListener(mouseAdapter);
        cbBilanz = new JCheckBox("Bilanz", true);
        cbBilanz.addItemListener(itemListener);
        cbBilanz.addMouseListener(mouseAdapter);
        cbBWInfo = new JCheckBox("Bewohner-Informationen", false);
        cbBWInfo.addItemListener(itemListener);
        cbBWInfo.addMouseListener(mouseAdapter);
    }

    @Override
    public void cleanup() {
        cbMedi.removeItemListener(itemListener);
        cbMedi.removeMouseListener(mouseAdapter);
        cbBerichte.removeItemListener(itemListener);
        cbBerichte.removeMouseListener(mouseAdapter);
        cbBilanz.removeItemListener(itemListener);
        cbBilanz.removeMouseListener(mouseAdapter);
        cbBWInfo.removeItemListener(itemListener);
        cbBWInfo.removeMouseListener(mouseAdapter);

        SYSTools.unregisterListeners(this);
    }

    @Override
    public void change2Bewohner(Bewohner bewohner) {
        this.bewohner = bewohner;
        OPDE.getDisplayManager().setMainMessage(BewohnerTools.getBWLabelText(bewohner));
        reloadDisplay();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jspHTML = new JScrollPane();
        txtUebersicht = new JTextPane();

        //======== this ========
        setLayout(new CardLayout());

        //======== jspHTML ========
        {

            //---- txtUebersicht ----
            txtUebersicht.setEditable(false);
            jspHTML.setViewportView(txtUebersicht);
        }
        add(jspHTML, "card1");
    }// </editor-fold>//GEN-END:initComponents

    public void reloadDisplay() {
        txtUebersicht.setText(DBHandling.getUeberleitung(bewohner, false, false, cbMedi.isSelected(), cbBilanz.isSelected(), cbBerichte.isSelected(), true, false, false, true, cbBWInfo.isSelected()));
        jspHTML.getViewport().setViewPosition(new Point(0, 0));
    }

    private void prepareSearchArea() {
        searchPanes = new CollapsiblePanes();
        searchPanes.setLayout(new JideBoxLayout(searchPanes, JideBoxLayout.Y_AXIS));


        CollapsiblePane searchPane = new CollapsiblePane("Bewohner-Übersicht");
        searchPane.setSlidingDirection(SwingConstants.SOUTH);
        searchPane.setStyle(CollapsiblePane.PLAIN_STYLE);
        searchPane.setCollapsible(false);

        try {
            searchPane.setCollapsed(false);
        } catch (PropertyVetoException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        JPanel mypanel = new JPanel();
        mypanel.setLayout(new VerticalLayout());

        JideButton printButton = GUITools.createHyperlinkButton("Drucken", new ImageIcon(getClass().getResource("/artwork/22x22/bw/printer.png")), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SYSPrint.print(new JFrame(), SYSTools.htmlUmlautConversion(DBHandling.getUeberleitung(bewohner, false, false, cbMedi.isSelected(), cbBilanz.isSelected(), cbBerichte.isSelected(), true, false, false, false, cbBWInfo.isSelected())), false);
            }
        });
        mypanel.add(printButton);

        mypanel.add(cbMedi);
        mypanel.add(cbBilanz);
        mypanel.add(cbBerichte);
        mypanel.add(cbBWInfo);
        mypanel.setBackground(Color.WHITE);

        searchPane.setContentPane(mypanel);
        searchPanes.add(searchPane);
        searchPanes.addExpansion();

        jspSearch.setViewportView(searchPanes);


    }

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        SYSPrint.print(this, SYSTools.htmlUmlautConversion(DBHandling.getUeberleitung(bewohner, false, false, cbMedi.isSelected(), cbBilanz.isSelected(), cbBerichte.isSelected(), true, false, false, false, cbBWInfo.isSelected())), false);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void cbMediActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMediActionPerformed
        reloadDisplay();
    }//GEN-LAST:event_cbMediActionPerformed

    private void cbBilanzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBilanzActionPerformed
        reloadDisplay();
    }//GEN-LAST:event_cbBilanzActionPerformed

    private void cbBerichteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBerichteActionPerformed
        reloadDisplay();
    }//GEN-LAST:event_cbBerichteActionPerformed

    private void cbBWInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBWInfoActionPerformed
        reloadDisplay();
    }//GEN-LAST:event_cbBWInfoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JScrollPane jspHTML;
    private JTextPane txtUebersicht;
    // End of variables declaration//GEN-END:variables
}
