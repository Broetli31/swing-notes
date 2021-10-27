package model;

/**
 * Dies ist eine Modelklasse, die eine Notiz versucht darzustellen. Sie kann den Titel,
 * den Text und eine Kategorie speichern.
 *
 * @author Ciro Brodmann
 */
public class Notiz {

    private String titel;
    private String text;
    private String kategorie;

    /**
     * Erzeugt ein Notizenobjekt, Constructor
     * Die Kategorie wird nicht eingefügt, da sie kein zwingender Teil ist.
     * @param titel der Titel der Notiz
     * @param text der Text der Notiz
     */
    public Notiz(String titel, String text) {
        this.titel = titel;
        this.text = text;
        this.kategorie = "kein";
    }

    /**
     * Gibt den Titel der Notiz zurück
     * @return Titel
     */
    public String getTitel() {
        return this.titel;
    }

    /**
     * Legt den Titel der Notiz fest
     * @param titel der neue Titel
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * Gibt den Text der Notiz zurück
     * @return Text
     */
    public String getText() {
        return this.text;
    }

    /**
     * legt den Text der Notiz fest.
     * @param text der neue Text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gibt die Kategorie der Notiz zurück
     * @return Kategorie
     */
    public String getKategorie() {
        return this.kategorie;
    }

    /**
     * Legt die Kategorie der Notiz fest
     * @param kat die neue Kategorie.
     */
    public void setKategorie(String kat) {
        this.kategorie = kat;
    }
}
