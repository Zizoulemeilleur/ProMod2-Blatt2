import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Diese Klasse erbt von der Klasse Thread und kommuniziert mit dem Benutzer
 * 
 * @version 1.0 ADRELI_THREAD
 * 
 */

public class View extends Thread {

	// Scanner
	Scanner scanInt;
	Scanner scanString;

	// Variablen
	int auswahl;

	// Daten
	LinkedList<Person> daten;

	// Pipes
	PipedInputStream pis;
	PipedOutputStream pos;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public View(PipedInputStream pis, PipedOutputStream pos) {
		this.pis = pis;
		this.pos = pos;
	}

	// ////////////////////////////////////
	/**
	 * Die run-Methode ruft das Menue auf
	 */
	@Override
	public void run() {
		menue();
	}

	// ////////////////////////////////////

	/**
	 * Diese Methode zeigt das Menue an, nimmt die Auswahl auf, sendet die
	 * Auswahl via Pipe zum Write-Thread und f�hrt die jeweilige Methode aus
	 */
	public void menue() {
		// Scanner initialisieren
		scanInt = new Scanner(System.in);

		System.out.println("-------ADRELI - Adressverwaltung-------");
		System.out.println("\tWollen Sie...");
		System.out.println();
		System.out.println("eine neue Person aufnehmen:......> 1");
		System.out.println("Records auflisten:...............> 2");
		System.out.println("Records in einer Datei sichern:..> 3");
		System.out.println("Records in einer Datei laden:....> 4");
		System.out.println("Sortieren:.......................> 5");
		System.out.println("Eine Datei loeschen:.............> 6");
		System.out.println("Das Programm verlassen:..........> 7");

		System.out.println();// Abstand
		System.out.print("Ihre Auswahl: ");
		auswahl = scanInt.nextInt();

		// Eingabe wird gesendet
		try {
			pos.write(auswahl);
		} catch (Exception e) {
			System.out.println("Fehler in Menue: " + e.getMessage());
		}

		switch (auswahl) {
		case 1:
			aufnehmen();
			menue();
			break;
		case 2:
			anzeigen();
			menue();
			break;
		case 3:
			speichern();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			System.out.println();
			menue();
			break;
		case 4:
			laden();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			System.out.println();
			menue();
			break;
		case 5:
			sort();
			menue();
			break;
		case 6:
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			System.out.println();
			menue();
			break;
		case 7:
			try {
				pos.close();
				pis.close();
			} catch (IOException e) {
				System.out.println("Fehler: " + e.getMessage());
			}
			System.out.println("Das Programm wird verlassen");
			System.exit(-1);

		default:
			System.out.println("Fehler bei der Eingabe");
			menue();
			break;
		}
	}

	/**
	 * Diese Methode nimmt die Daten auf und �berpr�ft die Eingabe auf
	 * Korrektheit
	 */
	public void aufnehmen() {
		// Scanner initialisieren
		scanInt = new Scanner(System.in);
		scanString = new Scanner(System.in);

		daten = new LinkedList<Person>();

		try {
			// NAME
			Matcher name_ok;
			String name;
			do {
				Pattern pattern = Pattern.compile("[A-Z���][a-z����]+");
				System.out.print("Name: ");
				name = scanString.nextLine();
				// Name auf richtige Eingabe pruefen
				name_ok = pattern.matcher(name);
				if (name_ok.matches() == false) {
					System.out.println("Bitte Namen korrekt eingeben!");
					System.out.println("Bsp: Mustermann");
				}

			} while (name_ok.matches() == false);
			// solange ausf�hren bis Name korrekt

			// VORNAME
			Matcher vname_ok;
			String vname;
			do {
				Pattern pattern = Pattern.compile("[A-Z���][a-z����]+");
				System.out.print("Vorname: ");
				vname = scanString.nextLine();
				// Vorname auf richtige Eingabe pruefen
				vname_ok = pattern.matcher(vname);
				if (vname_ok.matches() == false) {
					System.out.println("Bitte Vornamen korrekt eingeben!");
					System.out.println("Bsp: Max");
				}
			} while (vname_ok.matches() == false);
			// solange ausf�hren bis Vorname korrekt

			// ANREDE
			Matcher anrede_ok;
			String anrede;
			do {
				Pattern pattern = Pattern.compile("Herr|Frau");
				System.out.print("Anrede: ");
				anrede = scanString.nextLine();
				// Anrede auf richtige Eingabe pruefen
				anrede_ok = pattern.matcher(anrede);
				if (anrede_ok.matches() == false) {
					System.out.println("Bitte Anrede korrekt eingeben!");
					System.out.println("Bsp: Herr oder Frau");
				}
			} while (anrede_ok.matches() == false);
			// solange ausf�hren bis Anrede korrekt

			// STRASSE
			Matcher strasse_ok;
			String strasse;
			do {
				Pattern pattern = Pattern.compile("[a-zA-Z������ \\.]+ [0-9]+");
				System.out.print("Strasse: ");
				strasse = scanString.nextLine();
				// Strasse auf richtige Eingabe pruefen
				strasse_ok = pattern.matcher(strasse);
				if (strasse_ok.matches() == false) {
					System.out.println("Bitte Strasse korrekt eingeben!");
					System.out.println("Bsp: Musterstr. 12");
				}
			} while (strasse_ok.matches() == false);
			// solange ausf�hren bis Strasse korrekt

			// PLZ
			Matcher plz_ok;
			String plz;
			do {
				Pattern pattern = Pattern.compile("[0-9]{5}");
				System.out.print("PLZ: ");
				plz = scanString.nextLine();
				// PLZ auf richtige Eingabe pruefen
				plz_ok = pattern.matcher(plz);
				if (plz_ok.matches() == false) {
					System.out.println("Bitte PLZ korrekt eingeben!");
					System.out.println("Bsp: D78120");
				}
			} while (plz_ok.matches() == false);
			// solange ausf�hren bis PLZ korrekt

			// ORT
			Matcher ort_ok;
			String ort;
			do {
				Pattern pattern = Pattern.compile("[A-Z���][a-z�������]+");
				System.out.print("Ort: ");
				ort = scanString.nextLine();
				// Ort auf richtige Eingabe pruefen
				ort_ok = pattern.matcher(ort);
				if (ort_ok.matches() == false) {
					System.out.println("Bitte Ort korrekt eingeben!");
					System.out.println("Bsp: Furtwangen");
				}
			} while (ort_ok.matches() == false);
			// solange ausf�hren bis Ort korrekt

			// TELEFON
			Matcher telefon_ok;
			String telefon;
			do {
				Pattern pattern = Pattern.compile("[0-9].{3,14}");
				System.out.print("Telefon: ");
				telefon = scanString.nextLine();
				// Telefon auf richtige Eingabe pruefen
				telefon_ok = pattern.matcher(telefon);
				if (telefon_ok.matches() == false) {
					System.out.println("Bitte Telefonnr. korrekt eingeben!");
					System.out.println("Bsp: 0234-343232");
				}
			} while (telefon_ok.matches() == false);
			// solange ausf�hren bis Telefon korrekt

			// FAX
			Matcher fax_ok;
			String fax;
			do {
				Pattern pattern = Pattern.compile("[0-9].{3,14}");
				System.out.print("Fax: ");
				fax = scanString.nextLine();
				// Fax auf richtige Eingabe pruefen
				fax_ok = pattern.matcher(fax);
				if (fax_ok.matches() == false) {
					System.out.println("Bitte Fax korrekt eingeben!");
					System.out.println("Bsp: 0234-343232");
				}
			} while (fax_ok.matches() == false);
			// solange ausf�hren bis Fax korrekt

			// BEMERKUNG
			Matcher bem_ok;
			String bem;
			do {
				Pattern pattern = Pattern.compile("[^;]{4,300}");
				System.out.print("Bemerkung: ");
				bem = scanString.nextLine();
				// Bemerkung auf richtige Eingabe pruefen
				bem_ok = pattern.matcher(bem);
				if (bem_ok.matches() == false) {
					System.out.println("Bitte Bemerkung korrekt eingeben!");
					System.out.println("Mind 4 und max 300 zeichen");
				}
			} while (bem_ok.matches() == false);
			// solange ausf�hren bis Bemerkung korrekt

			System.out.println(); // Abstand

			// Abfragen ob Eingabe stimmt
			System.out.print("Stimmts? J/N : ");
			String a = scanString.nextLine().toUpperCase();
			if (a.equals("J")) {
				Person p = new Person(name, vname, anrede, strasse, plz, ort,
						telefon, fax, bem);
				daten.addLast(p); // Am Ende der verketteten Liste anfuegen
			} else if (a.equals("N")) {
				aufnehmen();
				return;
			} else {
				System.out.println("Falsche Eingabe. J oder N!");
				return;
			}

			// Abfragen ob noch eine Person aufgenommen werden soll
			System.out.print("Noch eine Person? J/N : ");
			String b = scanString.nextLine().toUpperCase();
			if (b.equals("J")) {
				// neue aufnehmen
				aufnehmen();
			} else if (b.equals("N")) {
				// keine aufnehmen-->zurueck zu main()
				System.out.println();// Abstand
				return;
			} else {
				System.out.println("Falsche Eingabe. J oder N!");
				return;
			}
		}

		catch (Exception e) {
			System.out.println(); // Abstand
			System.out.println("Falsche Eingabe");
			e.printStackTrace();
			System.out.println(); // Abstand
		}
	}

	/**
	 * Diese Methode zeigt die im Puffer gespeicherten Datens�tze an
	 */
	public void anzeigen() {
		// Scanner initialisieren
		scanInt = new Scanner(System.in);
		scanString = new Scanner(System.in);

		int zaehler = 1;

		try {
			// ArrayList ausgeben
			// Soviele Personen p in daten vorhanden sind wird ausgegeben
			for (Person p : daten) {
				System.out.println("----------------------");
				System.out.println(zaehler + ". Datensatz");
				System.out.println();// Abstand
				System.out.println("Name: " + p.name);
				System.out.println("Vorname: " + p.vname);
				System.out.println("Anrede: " + p.anrede);
				System.out.println("Strasse: " + p.strasse);
				System.out.println("PLZ: " + p.plz);
				System.out.println("Ort: " + p.ort);
				System.out.println("Telefon: " + p.telefon);
				System.out.println("Fax: " + p.fax);
				System.out.println("Bemerkung: " + p.bem);
				System.out.println("----------------------");
				System.out.println("Naechster Datensatz: <ENTER> druecken!");
				scanString.nextLine();
				zaehler++;
			}
		} catch (NullPointerException npex) {
			System.out.println();
			System.out.println("Noch keine Datens�tze vorhanden!");
			System.out.println();
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
			System.out.println();
		}
	}

	/**
	 * Diese Methode schickt beim Speichern die LinkedList vom View-Thread an
	 * den Write-Thread
	 */
	public void speichern() {
		try {
			oos = new ObjectOutputStream(pos);
			oos.writeObject(daten);
		} catch (IOException e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}

	/**
	 * Diese Methode empf�ngt beim Laden die LinkedList von Write
	 */
	@SuppressWarnings("unchecked")
	public void laden() {
		try {
			ois = new ObjectInputStream(pis);
			daten = (LinkedList<Person>) ois.readObject();
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}

	// ArrayList sortieren
	// Innere Klasse Sortieren
	class Sortieren implements Comparator<Person> {
		public int compare(Person p1, Person p2) {
			return p1.name.compareTo(p2.name);
		}
	}

	// Methode Sortieren
	/**
	 * Diese Methode sortiert die Datens�tze nach Name
	 */
	public void sort() {

		try {
			// Sortieren aufrufen
			Collections.sort(daten, new Sortieren());
			System.out.println("erfolgreich sortiert!");
			System.out.println();// Abstand
		} catch (NullPointerException npex) {
			System.out.println("Noch keine Datens�tze vorhanden!");
			System.out.println();
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}// Ende Sortieren
}
