package model;

import javax.swing.*;
import java.util.Vector;

/**
 * Eine Model-Klasse, um Kategorien zu speichern. Sie beinhaltet die entsprechenden Klassen,
 * die für die Kommunikation mit den GUI-Elementen benötigt werden.
 * (Extends DefaultComboBoxModel<String>)
 *
 * @author Ciro Brodmann
 */
public class KategorieModel extends DefaultComboBoxModel<String> {

    private Vector<String> kategorien;

    /**
     * Constructor, fügt einige vorgefertigte Werte in das Model
     */
    public KategorieModel() {
        this.kategorien = new Vector<String>();
        kategorien.add("Keine Kategorie");
        kategorien.add("Arbeit");
        kategorien.add("Freizeit");
        kategorien.add("Kochen");
        kategorien.add("Games");
    }

    /**
     * gibt die Menge an gespeicherten Kategorien zurück
     * @return die Anzahl Kategorien
     */
    @Override
    public int getSize() {
        return kategorien.size();
    }

    /**
     * Gibt die Kategorie an der gewünschten Stelle zurück
     * @param index die gewünschte Stelle
     * @return die gewünschte Kategorie
     */
    @Override
    public String getElementAt(int index) {
        return kategorien.get(index);
    }

    /**
     * fügt eine Kategorie zur Sammlung hinzu und benachrichtigt die GUI-Elemente
     * @param kat die einzufügende Kategorie
     */
    @Override
    public void addElement(String kat) {
        kategorien.add(kat);
        super.fireIntervalAdded(this, kategorien.size() - 1, kategorien.size() - 1);
    }

    /**
     * Überprüft, ob sich eine Kategorie in der Sammlung befindet.
     * @param kat der Wert der gesuchten Kategorie
     * @return -1, wenn die Kategorie nicht gefunden wurde, den Index, wenn eine gefunden wurde.
     */
    public int containsKat(String kat) {
        int contains = -1;

        for (int i = 0; i < kategorien.size(); i++) {
            if (kategorien.get(i).equals(kat)) {
                contains = i;
                break;
            }
        }

        return contains;
    }
}
