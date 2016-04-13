
public class AufrufErsterThread {
	public static void main(String[] args) {
		ersterThread t1 = new ersterThread(1, 12);
		ersterThread t2 = new ersterThread(2, 10);
		ersterThread t3 = new ersterThread(3, 7);
		t1.start();
		t2.start();
		t3.start();
		while (t1.isAlive() || t2.isAlive() || t3.isAlive()) {
			System.out.println("Hauptprogramm ist aktiv");
		}
	}
}
