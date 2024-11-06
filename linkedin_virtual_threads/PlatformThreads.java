import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class PlatformThreads {

    public static void main (String[] args) {
		try{
			Thread.sleep(5000); // Sleep para darle tiempo a VisualVM de cargar
		} catch (Exception e) {
			System.out.println("Error");
		}
		long start = System.currentTimeMillis(); // Tiempo de inicio
		platformThreads(60000);
		long end = System.currentTimeMillis(); // Tiempo de fin
		System.out.println("Total execution time: " + (end - start) + " milliseconds"); // Tiempo total de ejecución
	}

	private static void platformThreads (int limit) {	
		System.out.println("Hola");

		AtomicInteger counter = new AtomicInteger();
		while (counter.get() < limit) {
			new Thread(() -> { // Creación de un hilo de plataforma
				int count = counter.incrementAndGet();
				System.out.println("Platform thread number = " + count);
				LockSupport.park(); // Bloqueo del hilo
			}).start();
		}
	}

}
