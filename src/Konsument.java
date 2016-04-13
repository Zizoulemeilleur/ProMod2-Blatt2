import java.io.IOException;
import java.io.PipedReader;

// so wie ich das verstanden habe kommt hier von unserer Main die views rein, das ConsoleStuff
class Konsument extends Thread {
	PipedReader pr;

	public Konsument(ThreadGroup group, PipedReader pr) {
		super(group, "Konsument");
		this.pr = pr;
	}

	@Override
	public void run() {
		while (isInterrupted() == false) {
			try {
				int x = pr.read();
				System.out.println(this.getName() + "\t" + "entnimmt: " + x);
			} catch (IOException io) {
				System.out.println("Konsument: Fehler beim Lesen");
			}
		}
	}
}