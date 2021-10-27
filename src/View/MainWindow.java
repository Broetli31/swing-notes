package View;
/*
import com.apple.laf.AquaTextAreaUI;
import com.apple.laf.AquaTextFieldBorder;
import com.sun.codemodel.internal.JOp;
import com.sun.org.apache.bcel.internal.generic.Select;
 */


import model.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Das ist das Hauptfenster der Notizenapplikation.
 *
 * @author Ciro Brodmann
 */
public class MainWindow extends JFrame {

    NotizenModel notizenModel;
    KategorieModel kategorieModel;

    boolean istNeu, nichtAktivieren; 
    // nichtAktivieren ist da, damit es möglich wird, den "Speichern"-Knopf zu aktivieren, wenn etwas in der Combobox
    // geändert wird. Wenn dies nicht gesetzt würde, würde jedes Mal, wenn man eine Notiz auswählt und somit der Wert
    // der gespeicherten Notiz in die ComboBox übertragen wird, der Knopf fälschlicherweise aktiviert.

    JButton speichern, abbrechen, neueKat, hinzufuegen, loeschen;
    JComboBox<String> kategorien;
    JList<Notiz> notizenAuswahl;
    JTextField titelfeld;
    JTextArea textfeld, startanweisungen;
    //JLabel startanweisungen;
    JScrollPane scrollSeitenleiste, scrollTextfeld;
    JPanel seitenleiste, kategorieMenu, eingabe, abbrSpeich, abbr, hinzufEntf, entf, katEingabe,
            hauptpanel, cardContainer;
    // Das Hauptpanel wurde eingesetzt, damit ein gewisser Abstand zum Fensterrand eingefügt werden kann.
    CardLayout cardLayout;

    /**
     * Der Konstruktor des Hauptfensters
     * @param notizModel das Notizenmodel
     * @param katModel das Kategorienmodel
     */
    public MainWindow(NotizenModel notizModel, KategorieModel katModel) {

        // Festlegen der "Eckdaten" des Fensters
        super("Notizen");

        this.notizenModel = notizModel;
        this.kategorieModel = katModel;

        setBackground(Color.lightGray);
        setResizable(true);
        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(700, 550));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // Ab hier werden die einzelnen Komponenten aufgesetzt (Ausgenommen JPanels, die kommen später
        // Das Ganze ist nach Objekt gegliedert

        istNeu = false;

        speichern = new JButton("Speichern");
        speichern.addActionListener(new SubmitButtonsListener());
        speichern.setEnabled(false);

        // this.getRootPane().setDefaultButton(speichern); ---> Ist verwirrend, man meint, man könne Enter
        //                                                      drücken, um die Notiz zu sichern.

        abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new SubmitButtonsListener());
        abbrechen.setEnabled(false);

        neueKat = new JButton("Neue Kategorie erfassen");
        neueKat.addActionListener(new neueKatListener());

        hinzufuegen = new JButton("+");
        hinzufuegen.setPreferredSize(new Dimension(25, 23)); // Für korrekte Darstellung Windows: Breite: 41,
        //                                                                    unter Mac: Breite: 25 (Sieht besser aus)
        hinzufuegen.addActionListener(new AddRemoveButtonsListener());

        loeschen = new JButton("–");
        loeschen.setPreferredSize(new Dimension(25, 23));
        loeschen.addActionListener(new AddRemoveButtonsListener());
        loeschen.setEnabled(false);

        kategorien = new JComboBox<String>(kategorieModel);
        kategorien.setSelectedIndex(0);
        kategorien.addItemListener(new KategorieSelectListener());

        notizenAuswahl = new JList<Notiz>(notizenModel);
        notizenAuswahl.setCellRenderer(new NotizenRenderer());
        notizenAuswahl.addMouseListener(new SelectListener());
        notizenAuswahl.setFixedCellWidth(195);
        notizenAuswahl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollSeitenleiste = new JScrollPane(notizenAuswahl);
        scrollSeitenleiste.setPreferredSize(new Dimension(200, 0));
        scrollSeitenleiste.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollSeitenleiste.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        titelfeld = new JTextField();
        titelfeld.setFont(titelfeld.getFont().deriveFont(Font.BOLD));
        titelfeld.setBorder(new CompoundBorder(new LineBorder(Color.lightGray, 1), new EmptyBorder(4, 4, 4, 4)));
        titelfeld.addKeyListener(new TextEingabeListener());

        textfeld = new JTextArea();
        textfeld.setBorder(new EmptyBorder(4, 4, 4, 4));
        textfeld.setLineWrap(true);
        textfeld.setWrapStyleWord(true);
        textfeld.addKeyListener(new TextEingabeListener());

        scrollTextfeld = new JScrollPane(textfeld);
        scrollTextfeld.setBorder(new LineBorder(Color.lightGray, 1));

        startanweisungen = new JTextArea();
        startanweisungen.setText("Sie haben noch keine Notiz ausgewählt.\n\n" +
                "Um eine Notiz anzuzeigen, drücken Sie in der Seitenleiste auf ein Element.\n\n" +
                "Um eine Notiz anzulegen, drücken Sie unterhalb der Seitenleiste auf '+'.\n\n" +
                "Um eine Notiz wieder zu löschen, wählen Sie sie in der Seitenleiste aus und drücken Sie danach auf '-'.");
        startanweisungen.setEditable(false);
        startanweisungen.setLineWrap(true);
        startanweisungen.setOpaque(false);
        startanweisungen.setBorder(new EmptyBorder(20, 20, 20, 20));
        startanweisungen.setWrapStyleWord(true);


        // Ab hier werden die JPanels aufgesetzt

        hauptpanel = new JPanel(new BorderLayout());
        hauptpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        seitenleiste = new JPanel(new BorderLayout());
        seitenleiste.setBorder(new LineBorder(Color.lightGray, 1));
        kategorieMenu = new JPanel(new BorderLayout());
        eingabe = new JPanel(new BorderLayout(5, 5));
        abbrSpeich = new JPanel(new BorderLayout(5, 0));
        abbr = new JPanel(new BorderLayout());
        hinzufEntf = new JPanel(new BorderLayout());
        entf = new JPanel(new BorderLayout());
        katEingabe = new JPanel(new BorderLayout(5, 5));
        katEingabe.setBorder(new EmptyBorder(0, 10, 0, 0));
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);


        // Ab hier werden die JPanels mit den Objekten bestückt und ineinander verschachtelt

        entf.add(loeschen, BorderLayout.WEST);

        hinzufEntf.add(hinzufuegen, BorderLayout.WEST);
        hinzufEntf.add(entf, BorderLayout.CENTER);

        seitenleiste.add(scrollSeitenleiste, BorderLayout.CENTER);
        seitenleiste.add(hinzufEntf, BorderLayout.SOUTH);

        kategorieMenu.add(kategorien, BorderLayout.CENTER);
        kategorieMenu.add(neueKat, BorderLayout.EAST);

        abbr.add(abbrechen, BorderLayout.EAST);

        abbrSpeich.add(speichern, BorderLayout.EAST);
        abbrSpeich.add(abbr, BorderLayout.CENTER);

        eingabe.add(titelfeld, BorderLayout.NORTH);
        eingabe.add(scrollTextfeld, BorderLayout.CENTER);
        eingabe.add(abbrSpeich, BorderLayout.SOUTH);

        katEingabe.add(kategorieMenu, BorderLayout.NORTH);
        katEingabe.add(eingabe, BorderLayout.CENTER);

        cardContainer.add(startanweisungen, "Hilfe");
        cardContainer.add(katEingabe, "Eingabefelder");

        hauptpanel.add(seitenleiste, BorderLayout.WEST);
        hauptpanel.add(cardContainer, BorderLayout.CENTER);

        this.getContentPane().add(hauptpanel);


        // Hier noch die letzten benötigten Befehle

        pack();
        setVisible(true);
    }

    /**
     * Fügt Werte aus den Modelklassen in die Textfelder und die ComboBox ein.
     */
    private void insertValuesToFields() {
        nichtAktivieren = true;

        Notiz notiz = notizenAuswahl.getSelectedValue();

        int indexOfKat = kategorieModel.containsKat(notiz.getKategorie());
        if (indexOfKat >= 0) {
            kategorien.setSelectedIndex(indexOfKat);
        } else {
            kategorien.setSelectedIndex(0);
        }

        titelfeld.setText(notiz.getTitel());
        textfeld.setText(notiz.getText());

        nichtAktivieren = false;
    }

    /**
     * Wird verwendet, um die Titelleiste auch in den Listenerklassen festlegen zu können
     * @param titel den zu setzenden Fenstertitel
     */
    private void setFensterTitel(String titel) {
        this.setTitle(titel);
    }

    /**
     * Dieser Mouselistener reagiert, wenn eine Notiz in der Seitenleiste angewählt wurde.
     * Er setzt die Werte in die Felder ein und macht, dass alles relevante sichtbar wird.
     */
    class SelectListener extends MouseAdapter {


        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            loeschen.setEnabled(true);
            cardLayout.last(cardContainer);
            istNeu = false;
            abbrechen.setEnabled(false);
            speichern.setEnabled(false);
            setFensterTitel("Notizen");

            insertValuesToFields();
        }
    }

    /**
     * Dieser Listener reagiert, wenn sich die Kategorie verändert, und aktiviert, wenn es denn eine bereits erstellte
     * Notiz ist, die Knöpfe "Speichern" und "Abbrechen"
     *
     * Des Weiteren passt er den Fenstertitel den Umständen an.
     */
    class KategorieSelectListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (
                    !istNeu &&
                    !nichtAktivieren &&
                    kategorien.getSelectedItem().equals(kategorieModel.getElementAt(kategorien.getSelectedIndex()))
            ) {
                speichern.setEnabled(true);
                abbrechen.setEnabled(true);

                if (!istNeu) {
                    setFensterTitel("Notizen - Bearbeitet");
                } else {
                    setFensterTitel("Notizen - Neue Notiz");
                }
            }
        }
    }

    /**
     * Dieser Listener reagiert, wenn "Speichern" oder "Abbrechen" gedrückt wird.
     * Er setzt die neuen Werte in das Model ein oder setzt die Änderungen zurück.
     * Einige Knöpfe werden aktiviert oder deaktiviert, je nach Umständen.
     *
     * Er erzeugt ausserdem eine Fehlermeldung, wenn Text und/oder Titel leer sind.
     */
    class SubmitButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Speichern")) {
                if (titelfeld.getText().equals("") || textfeld.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Titel und/oder Textfeld dürfen nicht leer sein\n\n" +
                            "Bitte füllen Sie das leergelassene Feld oder die\nleergelassenen Felder aus.", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (!istNeu) {
                        Notiz notiz = notizenAuswahl.getSelectedValue();

                        notiz.setKategorie(kategorien.getSelectedItem().toString());
                        notiz.setText(textfeld.getText());
                        notiz.setTitel(titelfeld.getText());

                        notizenModel.elementChanged(notizenAuswahl.getSelectedIndex());

                        abbrechen.setEnabled(false);
                        speichern.setEnabled(false);
                    } else {
                        Notiz notiz = new Notiz(titelfeld.getText(), textfeld.getText());
                        notiz.setKategorie(kategorien.getSelectedItem().toString());

                        notizenModel.addElement(notiz);

                        notizenAuswahl.setSelectedIndex(notizenModel.getSize() - 1);
                        istNeu = false;
                        abbrechen.setEnabled(false);
                        speichern.setEnabled(false);
                        loeschen.setEnabled(true);

                    }

                    setFensterTitel("Notizen");

                }

            } else if (e.getActionCommand().equals("Abbrechen")){
                if (!istNeu) {
                    insertValuesToFields();
                } else {
                    notizenAuswahl.clearSelection();
                    cardLayout.first(cardContainer);
                    istNeu = false;
                    loeschen.setEnabled(false);
                }

                speichern.setEnabled(false);
                abbrechen.setEnabled(false);
                setFensterTitel("Notizen");
            }
        }

    }

    /**
     * Dieser Listener reagiert auf die "+/-"-Knöpfe am unteren Ende der Seitenleiste.
     * Er macht das GUI für eine neue Notiz sichtbar oder löscht eine Notiz und wechselt
     * dann wieder zu den Tipps.
     */
    class AddRemoveButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("+")) {
                istNeu = true;

                kategorien.setSelectedIndex(0);
                titelfeld.setText("");
                textfeld.setText("");
                cardLayout.last(cardContainer);
                notizenAuswahl.clearSelection();
                abbrechen.setEnabled(true);
                loeschen.setEnabled(false);
                setFensterTitel("Notizen - Neue Notiz");

            } else if (e.getActionCommand().equals("–")) {
                notizenModel.removeElementAt(notizenAuswahl.getSelectedIndex());
                notizenAuswahl.clearSelection();
                loeschen.setEnabled(false);
                cardLayout.first(cardContainer);
            }
        }
    }

    /**
     * Dieser Listener reagiert darauf, wenn man den Knopf "Neue Kategorie Erfassen" drückt.
     * Er erzeugt ebenfalls das Eingabefenster und die dazugehörige Fehlermeldung.
     */
    class neueKatListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String neueKat = JOptionPane.showInputDialog(null, "Geben Sie hier den Namen der neuen Kategorie ein", "Neue Kategorie erfassen", JOptionPane.QUESTION_MESSAGE);

            if (neueKat != null && !neueKat.equals("")) {
                kategorieModel.addElement(neueKat);
                kategorien.setSelectedItem(neueKat);
            } else if (neueKat == null) {
                // macht nichts
            } else if (neueKat.equals("")) {
                JOptionPane.showMessageDialog(null, "Das Eingabefeld für eine neue Kategorie darf nicht leer sein.", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    /**
     * Dieser Listener reagiert, wenn etwas in ein Textfeld eingegeben wird, und aktiviert die Knöpfe
     * "Speichern" und "Abbrechen"
     * Ausserdem passt es den Fenstertitel den Umständen entsprechend an.
     */
    class TextEingabeListener extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);

            speichern.setEnabled(true);
            abbrechen.setEnabled(true);

            if (!istNeu) {
                setFensterTitel("Notizen - Bearbeitet");
            } else {
                setFensterTitel("Notizen - Neue Notiz");
            }
        }
    }

}