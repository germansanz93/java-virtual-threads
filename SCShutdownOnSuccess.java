import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class SCShutdownOnSuccess {
// Ejemplo de uso de StructuredTaskScope.ShutdownOnSuccess
    // Caracteristica aun en preview, por lo que se debe compilar con --enable-preview -> javac --release 21 --enable-preview SCShutdownOnSuccess.java
    // y ejecutar con --enable-preview -> java --enable-preview SCShutdownOnSuccess

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(shutdownOnSuccessDemo());
    }

    // StructuredTaskScope.ShutdownOnSuccess
    // Ante la resolucion de la primera de las tareas las otras se cancelan
    public static Integer shutdownOnSuccessDemo() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Integer>()) {
            Subtask<Integer> t1 = scope.fork(() -> callSuccess1()); //resuelve
            Subtask<Integer> t2 = scope.fork(() -> callSuccess2()); //cancela
            Subtask<Integer> t3 = scope.fork(() -> callSuccess3()); //cancela
    
            scope.join();// unir los forks
    
            System.out.println("resultado t1="+t1.state()+"t2="+t2.state()+"t3="+t3.state()); // Se muestran los estados de las subtasks y se aprecia que solo esta success la primera
    
            return scope.result();
        }
    }

    private static Integer callSuccess1() throws InterruptedException {
        System.out.println("callSuccess1");
        Thread.sleep(1000);// Simulando una tarea que toma tiempo
        return 1;
    }

    private static Integer callSuccess2() throws InterruptedException {
        System.out.println("callSuccess2");
        Thread.sleep(2000);// Simulando una tarea que toma tiempo
        return 2;
    }

    private static Integer callSuccess3() throws InterruptedException {
        System.out.println("callSuccess3");
        Thread.sleep(3000);// Simulando una tarea que toma tiempo
        return 3;
    }
    
}