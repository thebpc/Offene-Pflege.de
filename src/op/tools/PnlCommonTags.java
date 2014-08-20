/*
 * Created by JFormDesigner on Fri May 30 15:30:55 CEST 2014
 */

package op.tools;

import com.jidesoft.swing.AutoCompletion;
import com.jidesoft.swing.SelectAllUtils;
import entity.system.Commontags;
import entity.system.CommontagsTools;
import op.OPDE;
import org.apache.commons.collections.Closure;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Torsten Löhr
 */
public class PnlCommonTags extends JPanel {

    private final boolean editmode;
    HashMap<String, Commontags> mapAllTags = new HashMap<>();
    HashSet<Commontags> listSelectedTags;
    HashMap<Commontags, JButton> mapButtons;
    ArrayList<String> completionList;
    JTextField txtTags;
    AutoCompletion ac;

    private Closure editAction;
    int MAXLINE = 8;

    public PnlCommonTags(Collection<Commontags> listSelectedTags) {
        this(listSelectedTags, false);
    }

    public PnlCommonTags(Collection<Commontags> listSelectedTags, boolean editmode, int maxline) {
        this(listSelectedTags, editmode);
        MAXLINE = maxline;
    }

    public PnlCommonTags(Collection<Commontags> listSelectedTags, Closure editAction) {
        this(listSelectedTags, editAction != null);
        this.editAction = editAction;

    }

    public PnlCommonTags(Collection<Commontags> listSelectedTags, boolean editmode) {
        editAction = null;
        this.editmode = editmode;

        setLayout(new RiverLayout(10, 5));

        this.listSelectedTags = new HashSet<>(listSelectedTags);
        this.completionList = new ArrayList<>();

        initPanel();
    }

    public HashSet<Commontags> getListSelectedTags() {
        return listSelectedTags;
    }

    private void initPanel() {

        mapButtons = new HashMap<>();

        for (Commontags commontags : CommontagsTools.getAll()) {
            mapAllTags.put(commontags.getText(), commontags);
        }

        for (Commontags selectedTags : listSelectedTags) {
            add(createButton(selectedTags));
        }

        if (editmode) {
            txtTags = new JTextField(10);

            SelectAllUtils.install(txtTags);
            ac = new AutoCompletion(txtTags, mapAllTags.keySet().toArray(new String[]{}));

            ac.setStrict(false);
            ac.setStrictCompletion(false);
            txtTags.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cmbTagsActionPerformed(e);
                }
            });

            txtTags.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    cmbTagsActionPerformed(null);
                }
            });

            txtTags.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                        super.keyTyped(e);
                    } else {
                        e.consume();
                    }
                }
            });

            add(txtTags);
        }
    }

    private void cmbTagsActionPerformed(ActionEvent e) {

        if (!editmode) return;

        if (txtTags.getText().isEmpty()) return;
        if (txtTags.getText().length() > 100) return;

        final String enteredText = SYSTools.tidy(txtTags.getText()).toLowerCase();

        if (!mapAllTags.containsKey(enteredText)) {
            Commontags myNewCommontag = new Commontags(SYSTools.tidy(enteredText));
            mapAllTags.put(enteredText, myNewCommontag);
            ac.uninstallListeners();
            ac = new AutoCompletion(txtTags, mapAllTags.keySet().toArray(new String[]{}));
            ac.setStrict(false);
            ac.setStrictCompletion(false);
        }


        if (!listSelectedTags.contains(mapAllTags.get(enteredText))) {
            if (editAction != null) {
                ac.uninstallListeners();
                editAction.execute(mapAllTags.get(enteredText));
            } else {
                listSelectedTags.add(mapAllTags.get(enteredText));

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        if (listSelectedTags.size() % MAXLINE == 0) {
                            add(createButton(mapAllTags.get(enteredText)), RiverLayout.LINE_BREAK);
                        } else {
                            add(createButton(mapAllTags.get(enteredText)), RiverLayout.LEFT);
                        }

                        txtTags.setText("");
                        revalidate();
                        repaint();
                    }
                });
            }
        }

    }


    private JButton createButton(final Commontags commontag) {

        if (mapButtons.containsKey(commontag)) {
            OPDE.debug("shortcut");
            return mapButtons.get(commontag);
        }

        final JButton jButton = new JButton(commontag.getText(), editmode ? SYSConst.icon16tagPurpleDelete2 : SYSConst.icon16tagPurple);
        jButton.setFont(SYSConst.ARIAL12);
        jButton.setBorder(new RoundedBorder(10));
        jButton.setHorizontalTextPosition(SwingConstants.LEADING);
        jButton.setForeground(SYSConst.purple1[SYSConst.dark3]);

        if (editmode) {

            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (editAction != null) {
                        ac.uninstallListeners();
                        editAction.execute(commontag);
                    } else {

                        listSelectedTags.remove(commontag);
                        mapButtons.remove(commontag);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                removeAll();

                                add(txtTags);
                                int tagnum = 1;

                                for (JButton btn : mapButtons.values()) {
                                    if (tagnum % MAXLINE == 0) {
                                        add(btn, RiverLayout.LINE_BREAK);
                                    } else {
                                        add(btn, RiverLayout.LEFT);
                                    }
                                    tagnum++;
                                }

                                remove(jButton);
                                revalidate();
                                repaint();
                            }
                        });
                    }
                }
            });
        }
        mapButtons.put(commontag, jButton);

        return jButton;
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
