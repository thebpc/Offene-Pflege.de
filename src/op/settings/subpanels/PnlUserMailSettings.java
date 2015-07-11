package op.settings.subpanels;

import entity.system.SYSPropsTools;
import gui.PnlBeanEditor;
import gui.interfaces.DefaultPanel;
import gui.interfaces.YesNoToggleButton;
import op.OPDE;
import op.settings.databeans.MailSettingsBean;
import op.system.EMailSystem;
import op.system.Recipient;
import op.threads.DisplayMessage;
import op.tools.SYSTools;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by tloehr on 11.07.15.
 */
public class PnlUserMailSettings extends DefaultPanel {

    boolean checkInProgress = false;
    boolean lastCheckOk = false;
    Logger logger = Logger.getLogger(getClass());

    public PnlUserMailSettings() {
        super("opde.settings.personal.mail");
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        try {
            final PnlBeanEditor<MailSettingsBean> pbe = new PnlBeanEditor<>(() -> new MailSettingsBean(OPDE.getProps()), MailSettingsBean.class);
            pbe.setCustomPanel(getButtonPanel(pbe));
            pbe.addDataChangeListener(evt -> SYSPropsTools.storeProps(evt.getData().toProperties(new Properties())));
            add(pbe);
        } catch (Exception e) {
            OPDE.fatal(logger, e);
        }

    }


    private JPanel getButtonPanel(PnlBeanEditor<MailSettingsBean> pbe) {


        final JButton btnTestmail = new JButton(SYSTools.xx("opde.settings.global.mail.btnTestmail"));
        btnTestmail.addActionListener(e -> {
            try {
                if (checkInProgress) return;

                pbe.broadcast();

                final Recipient testRecipient = new Recipient(OPDE.getProps().getProperty(SYSPropsTools.KEY_MAIL_RECIPIENT), OPDE.getProps().getProperty(SYSPropsTools.KEY_MAIL_RECIPIENT_PERSONAL));
                OPDE.getDisplayManager().addSubMessage(new DisplayMessage("opde.settings.global.mail.testing", DisplayMessage.WAIT_TIL_NEXT_MESSAGE));

                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() {
                        lastCheckOk = false;
                        return EMailSystem.sendMail(SYSTools.xx("opde.settings.global.mail.testsubject"), SYSTools.xx("opde.settings.global.mail.testbody"), testRecipient, null);
                    }

                    @Override
                    protected void done() {
                        checkInProgress = false;

                        try {
                            lastCheckOk = (Boolean) get();
                            if (lastCheckOk) {
                                OPDE.getDisplayManager().addSubMessage(new DisplayMessage("opde.settings.global.mail.success"));
                            } else {
                                OPDE.getDisplayManager().addSubMessage(new DisplayMessage("opde.settings.global.mail.fail", DisplayMessage.WARNING));
                            }
                            tbActive.setSelected(lastCheckOk);
                        } catch (InterruptedException e) {
                            logger.debug(e);
                        } catch (ExecutionException e) {
                            OPDE.fatal(logger, e);
                        }
                    }
                };

                worker.execute();

            } catch (ConstraintViolationException cve) {
                String violations = "";
                for (ConstraintViolation cv : cve.getConstraintViolations()) {
                    violations += cv.getMessage() + "; ";
                }
                OPDE.getDisplayManager().addSubMessage(new DisplayMessage(violations, DisplayMessage.WARNING));
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            reload();
        });
        //        buttonPanel.add(btnTestmail);


        tbActive.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && !lastCheckOk) {
                OPDE.getDisplayManager().addSubMessage(new DisplayMessage("opde.settings.global.mail.yet.untested", DisplayMessage.WARNING));
            }
            SYSPropsTools.storeProp(SYSPropsTools.KEY_MAIL_SYSTEM_ACTIVE, Boolean.toString(e.getStateChange() == ItemEvent.SELECTED));
        });

        //        buttonPanel.add(tbActive);


        Box line = Box.createHorizontalBox();
        line.add(Box.createHorizontalGlue());
        line.add(btnTestmail);
        line.add(Box.createHorizontalGlue());
        line.add(tbActive);

        Box page = Box.createVerticalBox();
        page.add(Box.createRigidArea(new Dimension(0, 10)));
        page.add(new JSeparator());
        page.add(Box.createRigidArea(new Dimension(0, 10)));
        page.add(line);
        page.add(Box.createVerticalGlue());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(page);

        return content;

    }

}
