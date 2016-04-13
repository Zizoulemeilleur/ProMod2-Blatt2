class ersterThread extends Thread {
	int thread_nr, a;
	long fak = 1;

	ersterThread(int nummer, int fakultaet) {
		thread_nr = nummer;
		a = fakultaet;
	}

	@Override
	public synchronized void run() {
		for (int x = 1; x <= a; x++) {
			fak *= x;
			System.out.println("Thread " + thread_nr + ":    " + x + "! = "
					+ fak);
			Thread.currentThread().yield(); // unterbricht den aktuellen Thread
		}
	}
}
