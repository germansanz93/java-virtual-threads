import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class VirtualThreads {

    public static void main (String[] args) {
		try{
			Thread.sleep(5000); // Sleep para darle tiempo a VisualVM de cargar
		} catch (Exception e) {
			System.out.println("Error");
		}
		long start = System.currentTimeMillis(); // Tiempo de inicio
		virtualThreads(60000);
		long end = System.currentTimeMillis(); // Tiempo de fin
		System.out.println("Total execution time: " + (end - start) + " milliseconds"); // Tiempo total de ejecución
	}
    
	private static void virtualThreads (int limit) {
		var counter = new AtomicInteger();
        	while (counter.get() < limit) {
            		Thread.startVirtualThread(()-> { // Creación de un hilo virtual
                		int count = counter.incrementAndGet();
                		System.out.println("Virtual Thread number = " + count);
                		LockSupport.park(); // Bloqueo del hilo
            		});
        	}
	}
}
