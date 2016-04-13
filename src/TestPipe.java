import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

//Und hier ist die "ExtraKlasse" sozusagen mit der pipe scheiße
public class TestPipe {
	public static void main(String[] args) {
		try {
			PipedReader pr1 = new PipedReader();
			PipedWriter pw1 = new PipedWriter();
			pw1.connect(pr1); // Verbindung von PipedWriter und PipedReader
			ThreadGroup tg = new ThreadGroup("TG");
			Produzent produzent = new Produzent(tg, pw1);
			Konsument verbraucher = new Konsument(tg, pr1);
			produzent.start();
			verbraucher.start();
			Thread.sleep(20000);
			tg.interrupt();
		} catch (InterruptedException ie) {
		} catch (IOException io) {
			System.out.println("Fehler beim Verbinden der Pipe");
		}
	}
}
