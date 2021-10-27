package model;

import javax.swing.*;
import java.util.Vector;

/**
 * Diese Modelklasse sammelt Objekte des Typs "Notizen" und beinhaltet die entsprechenden Klassen,
 * die für die Kommunikation mit den GUI-Elementen benötigt werden.
 * (extends DefaultListModel<Notiz>)
 *
 * @author Ciro Brodmann
 */
public class NotizenModel extends DefaultListModel<Notiz> {

    private Vector<Notiz> notizen;

    public NotizenModel() {
        notizen = new Vector<Notiz>();
        notizen.add(new Notiz("Wilkommen bei \"Notizen\"", "Diese App kann Notizen verwalten\n\n" +
                "Sie können oben eine Kategorie aus der Liste auswählen. Rechts daneben finden Sie einen" +
                " Knopf, mit welchem Sie eine neue Kategorie einfügen können.\n\n" +
                "Im Textfeld darunter können Sie einen Titel eingeben.\n" +
                "Im darauf folgenden Textfeld, in dem sich Momentan dieser Text befindet, geben Sie" +
                " den Inhalt der Notiz ein\n\n" +
                "Wenn Sie mit dem Erfassen oder Bearbeiten einer Notiz fertig sind, drücken sie unten rechts" +
                " auf \"Speichern\", um die Änderungen zu sichern, oder auf \"Abbrechen\", um die Änderungen" +
                "zu verwerfen.\n\n" +
                "Die Notizen werden nach Beendigung des Programms gelöscht.\n\n" +
                "Dies war ein Projekt zur GUI-Erstellung für das Informatikmodul 120."));
        notizen.add(new Notiz("Hallo", "ich bin eine Notiz"));
        notizen.get(0).setKategorie("Arbeit");
    }

    /**
     * Fügt eine Notiz zur Sammlung hinzu und benachrichtigt die GUI-Elemente
     * @param notiz die einzufügende Notiz
     */
    @Override
    public void addElement(Notiz notiz) {
        notizen.add(notiz);
        super.fireIntervalAdded(this, 0, this.notizen.size());
    }

    /**
     * Entfernt die Notiz an der entsprechenden Stelle und benachrichtigt die GUI-Elemente.
     * @param index die Stelle
     */
    @Override
    public void removeElementAt(int index) {
        notizen.removeElementAt(index);
        super.fireIntervalRemoved(this, index, index);
    }

    /**
     * Gibt die Notiz an der gewünschten Stelle zurück.
     * @param index die gewünschte Stelle
     * @return Notiz
     */
    @Override
    public Notiz getElementAt(int index) {
        return notizen.get(index);
    }

    /**
     * Teilt den GUI-Elementen mit, dass sich eine Notiz verändert hat.
     * @param index die Koordinaten des veränderten Objekts
     */
    public void elementChanged(int index) {
        super.fireContentsChanged(this, index, index);
    }

    /**
     * Gibt die Anzahl Notizen zurück
     * @return die Anzahl Notizen
     */
    @Override
    public int getSize() {
        return this.notizen.size();
    }
}
