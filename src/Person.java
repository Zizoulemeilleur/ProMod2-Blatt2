import java.io.Serializable;

/**
 * 
 * Klasse f�r die Erstellung von Personen
 * 
 * @version 1.0 ADRELI_THREAD
 * 
 */

// Klasse Person speichert Personen mit den Attributen
@SuppressWarnings("serial")
public class Person implements Serializable {
	/**
	 * �ber Konstruktor werden Daten gespeichert
	 */
	String name, vname, anrede, strasse, plz, ort, telefon, fax, bem;

	public Person(String name, String vname, String anrede, String strasse,
			String plz, String ort, String telefon, String fax, String bem) {
		this.name = name;
		this.vname = vname;
		this.anrede = anrede;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.telefon = telefon;
		this.fax = fax;
		this.bem = bem;
	}
}
