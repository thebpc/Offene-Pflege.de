package entity.prescription;

import op.tools.SYSTools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tloehr
 * Date: 05.01.12
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class MedFactoryTools {
    public static ListCellRenderer getHerstellerRenderer(int maxlen) {
        final int max = maxlen;
        return new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean isSelected, boolean cellHasFocus) {
                String text;
                if (o == null) {
                    text = SYSTools.toHTML("<i>Keine Auswahl</i>");
                } else if (o instanceof MedFactory) {
                    text = ((MedFactory) o).getFirma() + SYSTools.catchNull(((MedFactory) o).getOrt(), ", ", "");
                } else {
                    text = o.toString();
                }
                if (max > 0) {
                    text = SYSTools.left(text, max);
                }
                return new DefaultListCellRenderer().getListCellRendererComponent(jList, text, i, isSelected, cellHasFocus);
            }
        };
    }
}