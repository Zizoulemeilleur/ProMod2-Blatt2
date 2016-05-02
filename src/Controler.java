import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Controler {

	/**
	 * Main Methode startet die Threads und verbindet die Pipes
	 */
	public static void main(String[] args) {

		// Pipes initialisieren und Verbinden Für Übergabe der Objekte
		PipedInputStream pis1 = new PipedInputStream();
		PipedOutputStream pos1 = new PipedOutputStream();
		PipedInputStream pis2 = new PipedInputStream();
		PipedOutputStream pos2 = new PipedOutputStream();

		try {
			pis1.connect(pos1);
			pis2.connect(pos2);
		} catch (IOException e) {
			System.out.println("Fehler: " + e.getMessage());
		}

		// Threads werden initialisiert
		View vt = new View(pis1, pos2);
		Model mt = new Model(pos1, pis2);

		// Threads werden gestartet
		vt.start();
		mt.start();

	}

}
