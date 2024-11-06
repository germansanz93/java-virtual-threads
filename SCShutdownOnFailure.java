import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class SCShutdownOnFailure {

    // Ejemplo de uso de StructuredTaskScope.ShutdownOnFailure
    // Caracteristica aun en preview, por lo que se debe compilar con --enable-preview -> javac --release 21 --enable-preview SCShutdownOnFailure.java
    // y ejecutar con --enable-preview -> java --enable-preview SCShutdownOnFailure

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        shutdownOnFailureDemo();
    }

    // StructuredTaskScope.ShutdownOnFailure
    // Si falla una tarea se propaga la cancelación a las demás.
    public static void shutdownOnFailureDemo() throws ExecutionException, InterruptedException {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<Integer> fail = scope.fork(() -> callFail()); // falla
            Subtask<Integer> succ = scope.fork(() -> callSuccess()); // cancela

            scope.join(); // unir los forks
            scope.throwIfFailed(); // si falla alguno propagar el error

            System.out.println("Result is inv = " + fail.state() + " ord = " + succ.state());
        }
    }

    private static Integer callFail() throws InterruptedException {
        System.out.println("callFail");
        throw new InterruptedException("Failing task"); // Simulando una tarea que falla
    }

    private static Integer callSuccess() throws InterruptedException {
        Thread.sleep(1000); // Simulando una tarea que toma tiempo
        System.out.println("callSuccess");
        return 2;
    }
    
}
