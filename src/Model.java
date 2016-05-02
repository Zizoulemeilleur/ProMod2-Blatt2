import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Diese Klasse erbt von Thread und ist fuer Speichern, Laden und Loeschen von
 * Files zustaendig
 * 
 * @version 1.0 ADRELI_THREAD
 * 
 */

public class Model extends Thread {

	// Array Person
	public static LinkedList<Person> daten;

	// Scanner
	Scanner scanInt;
	Scanner scanString;

	// Variablen
	int auswahl;

	// Pipes
	PipedInputStream pis;
	PipedOutputStream pos;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Model(PipedOutputStream pos, PipedInputStream pis) {
		this.pos = pos;
		this.pis = pis;
	}

	// ////////////////////////////////////
	@Override
	public void run() {
		get();
	}

	// ////////////////////////////////////

	/**
	 * Die Methode empf�ngt bei jeder Eingabe im View-Thread die Auswahl des
	 * Menues und f�hrt die jeweilige Methode aus
	 */
	@SuppressWarnings("unchecked")
	public void get() {
		try {
			auswahl = pis.read(); // Auswahl aus View-Thread einlesen
		} catch (IOException e) {
			System.out.println("Fehler: " + e.getMessage());
		}

		// Nur bei Auswahl von 3, 4 oder 6 ruft
		// der Write-Thread seine Methoden auf
		// Es wird immer wieder von der View-Klasse die Auswahl empfangen
		switch (auswahl) {
		case 1:
			get();
			break;
		case 2:
			get();
			break;
		case 3:
			try {
				ois = new ObjectInputStream(pis);
				daten = (LinkedList<Person>) ois.readObject();
			} catch (Exception e) {
				System.out.println("Fehler: " + e.getMessage());
			}
			safe();
			get();
			break;
		case 4:
			laden();
			try {
				oos = new ObjectOutputStream(pos);
				oos.writeObject(daten);
			} catch (Exception e) {
				System.out.println("Fehler: " + e.getMessage());
			}
			get();
			break;
		case 5:
			get();
			break;
		case 6:
			loeschen();
			get();
			break;
		case 7:
			get();
			try {
				pos.close();
				pis.close();
			} catch (IOException e) {
				System.out.println("Fehler: " + e.getMessage());
			}
			break;
		default:
			get();
			break;
		}
	}

	/**
	 * Diese Methode speichert die Datens�tze in einer CSV-File
	 */
	public void safe() {
		try {
			File f = new File("adreli.csv");
			if (!f.exists()) {
				f.createNewFile();
			}
		}

		catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
			System.out.println();
		}// Ende Datei erstellen

		// In Datei schreiben
		try {
			FileWriter datei;

			/*
			 * Alle Daten (bzw. Personen) der ArraysList in die Datei
			 * untereinander speichern
			 */
			for (Person p : daten) {
				datei = new FileWriter("adreli.csv", true);
				datei.write(p.name + ";" + p.vname + ";" + p.anrede + ";"
						+ p.strasse + ";" + p.plz + ";" + p.ort + ";"
						+ p.telefon + ";" + p.fax + ";" + p.bem);
				datei.append(System.getProperty("line.separator"));
				datei.close();// Writer-Stream wieder schliessen
			}
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}

		// Ende in Datei schreiben

	}// Ende safe()-Methode

	/**
	 * Diese Methode l�dt Datens�tze aus einer existierenden CSV-File
	 */
	public void laden() {
		// Scanner initialisieren
		scanInt = new Scanner(System.in);
		scanString = new Scanner(System.in);

		try {
			try {
				// eindim. Array deklarieren als Zwischenspeicher
				String[] liste = null;

				// ArrayList wird gefuellt
				daten = new LinkedList<Person>();

				FileReader fr = new FileReader("adreli.csv");
				BufferedReader br = new BufferedReader(fr);

				String zeile = null;
				while ((zeile = br.readLine()) != null) {
					liste = zeile.split(";");
					Person p = new Person(liste[0], liste[1], liste[2],
							liste[3], liste[4], liste[5], liste[6], liste[7],
							liste[8]);
					daten.add(p);
				}

				fr.close();
				br.close();
			}

			catch (IOException e) {
				System.out.println("Fehler bei Laden: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode l�scht eine CSV-File
	 */
	public void loeschen() {
		// Scanner initialisieren
		scanString = new Scanner(System.in);

		try {
			File datei = new File("adreli.csv");
			if (datei.exists()) {
				datei.delete();// Datei loeschen
			}
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}
}
