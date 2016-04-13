import java.io.IOException;
import java.io.PipedWriter;

class Produzent extends Thread {
	PipedWriter pw;
	int x = 0;

	public Produzent(ThreadGroup group, PipedWriter pw) {
		super(group, "Produzent");
		this.pw = pw;
	}

	@Override
	public void run() {
		while (isInterrupted() == false) {
			// int x = (int)(100 * Math.random());
			x++;
			try {
				pw.write(x);
				System.out.println(this.getName() + "\t" + "produziert: " + x);
			} catch (IOException io) {
				System.out.println("Produzent: Fehler beim Schreiben");
			}
		}
	}
}
