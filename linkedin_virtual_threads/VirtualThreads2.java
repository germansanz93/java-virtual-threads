import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

public class VirtualThreads2 {

    // Ejemplo de uso de virtual threads para realizar varias tareas en paralelo y
    // obtener los resultados.
    // Se utiliza Executors.newVirtualThreadPerTaskExecutor() para crear un Executor
    // que utiliza virtual threads.

    public static void main(String[] args) {
        virtualThreadsDemo();
    }

    private static void virtualThreadsDemo() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> firstFuture = executor.submit(() -> call1());
            Future<Integer> secondFuture = executor.submit(() -> call2());

            Integer first = firstFuture.get();
            Integer second = secondFuture.get();

            System.out.println("Result is first = " + first + " second = " + second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static Integer call1() {
        System.out.println("call1");
        LockSupport.parkNanos(1_000_000_000); // Simulando una tarea que toma tiempo
        return 1;
    }

    private static Integer call2() {
        System.out.println("call2");
        LockSupport.parkNanos(2_000_000_000); // Simulando una tarea que toma tiempo
        return 2;
    }
}
