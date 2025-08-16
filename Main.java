import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingUtilities;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Clase principal que inicia la aplicación.
 * Configura los componentes principales:
 * - Cola para pacientes
 * - Interfaz gráfica
 * - Doctores (hilos de atención)
 * - Generador automático de pacientes
 */
public class Main {
    /*
     * Punto de entrada de la app
     */
    public static void main(String[] args) {
        PriorityBlockingQueue<Patient> queue = new PriorityBlockingQueue<>();
        AtomicInteger totalServed = new AtomicInteger();

        SwingUtilities.invokeLater(() -> {
            AppUI ui = new AppUI(queue, totalServed);
            ui.setVisible(true);

            // pool para 3 doctores ingresados manualmente (llegada por hilos de manera aleatoria)
            ExecutorService doctorsPool = Executors.newFixedThreadPool(3);
            doctorsPool.submit(new Doctor("Dr. Joshua", queue, totalServed, ui));
            doctorsPool.submit(new Doctor("Dr. Diego", queue, totalServed, ui));
            doctorsPool.submit(new Doctor("Dr. Angel", queue, totalServed, ui));

            // genera pacientes de manera aleatoria cada 3 segundos
            ScheduledExecutorService arrivals = Executors.newSingleThreadScheduledExecutor();
            arrivals.scheduleAtFixedRate(() -> {
                String name = "Paciente-" + (int)(Math.random()*1000);
                Patient.Priority pr = Patient.Priority.values()[(int)(Math.random()*3)];
                queue.offer(new Patient(name, pr));
                ui.addLog("Llegada aleatoria: " + name + " ["+pr+"]");
            }, 2, 3, TimeUnit.SECONDS);
        });
    }
}