import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

/*
 * Clase que representa un doctor que atiende pacientes de la cola de prioridad.
 * Implementa Runnable para ejecutarse en un hilo separado.
 */
public class Doctor implements Runnable {
    private final String name;
    private final PriorityBlockingQueue<Patient> queue;
    private final AtomicInteger servedCount;
    private final AppUI ui;
    private volatile boolean running = true;

    //Constructor del doctor
    public Doctor(String name, PriorityBlockingQueue<Patient> queue,
                  AtomicInteger servedCount, AppUI ui) {
        this.name = name;
        this.queue = queue;
        this.servedCount = servedCount;
        this.ui = ui;
    }

    //Detiene la ejecución del hilo del doctor.
    public void stop() { running = false; }

    //Metodo principal que ejecuta el hilo del doctor
    @Override
    public void run() {
        Random rnd = new Random();
        try {
            while (running) {
                Patient p = queue.take();
                long wait = System.currentTimeMillis() - p.getEnqueueTime();
                servedCount.incrementAndGet();
                ui.updateDoctorStatus(name, "Atendiendo: " + p);

                // Tiempo de atención basado en la prioridad
                int attentionTime;
                switch (p.getPriority()) {
                    case Emergencia:
                        attentionTime = 8000 + rnd.nextInt(2000); // 8-10 segundos
                        break;
                    case Urgente:
                        attentionTime = 5000 + rnd.nextInt(2000); // 5-7 segundos
                        break;
                    case Consulta_general:
                    default:
                        attentionTime = 1000 + rnd.nextInt(4000); // 1-5 segundos
                        break;
                }

                Thread.sleep(attentionTime);

                ui.updateDoctorStatus(name, "Libre");
                ui.addLog("Doctor " + name + " atendió a " + p.getName() +
                        " ("+p.getPriority()+") Esperó: " + wait/1000.0 + "s" +
                        " Tiempo atención: " + attentionTime/1000.0 + "s");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}