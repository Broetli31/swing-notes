package View;

import javafx.scene.control.ListCell;
import model.Notiz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ein Renderer, um die Notizen in einer JList folgendermassen darstellen zu lassen.
 * <bold>Titel</bold>
 *       Text
 *
 * @author Ciro Brodmann
 */
public class NotizenRenderer implements ListCellRenderer<Notiz> {


    /**
     * Default Constructor
     */
    public NotizenRenderer() {

    }

    /**
     * Gibt ein korrekt gelayoutetes Objekt zurück, welches die Werte der entsprechenden Notiz enthaltet.
     * @param list Die Liste, in welcher die Objekte dargestellt werden
     * @param value eine Notiz
     * @param index die Stelle der Notiz in der Liste
     * @param isSelected zeigt an, ob die Notiz angewählt ist
     * @param cellHasFocus zeigt an, ob die Notiz "fokussiert" ist
     * @return Ein JPanel mit Titel und Text der Notiz
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Notiz> list, Notiz value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel eintrag = new JPanel(new BorderLayout());
        JLabel titel, text;

        //list.setFixedCellWidth(195); -> Bessere Ergebnisse, wenn man das im MainWindow macht.
        //                                (Punkte, die auftauchen, wenn der Text zu lang ist,
        //                                verschieben sich, wenn eine Scrollbar da ist)

        titel = new JLabel(value.getTitel());
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 13.0f));

        text = new JLabel(value.getText());
        text.setFont(text.getFont().deriveFont(13.0f));

        if (isSelected) {
            eintrag.setBackground(list.getSelectionBackground());
            eintrag.setForeground(list.getSelectionForeground());
            titel.setForeground(Color.WHITE);
            text.setForeground(Color.WHITE);
        } else {
            eintrag.setBackground(list.getBackground());
            eintrag.setForeground(list.getForeground());
            text.setForeground(Color.BLACK);
            text.setForeground(Color.BLACK);
        }

        eintrag.setBorder(new EmptyBorder(4, 4, 5 ,2));

        eintrag.add(titel, BorderLayout.NORTH);
        eintrag.add(text, BorderLayout.SOUTH);

        return eintrag;
    }
}
