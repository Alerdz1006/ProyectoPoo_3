import java.util.concurrent.atomic.AtomicLong;

/*
 * Clase que representa un paciente en el sistema de atención médica.
 */
public class Patient implements Comparable<Patient> {

    /*
     * definicion de nivel de prioridad de pacienes
     */
    public enum Priority {
        // Pacientes en estado de emergencia (máxima prioridad)
        Emergencia,
        //Pacientes urgentes (prioridad media)
        Urgente,
        // Pacientes de consulta general (prioridad baja)
        Consulta_general
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    private final long id;
    private final String name;
    private final Priority priority;
    private final long enqueueTime;

    //Constructor para crear un nuevo paciente
    public Patient(String name, Priority priority) {
        this.id = COUNTER.incrementAndGet();
        this.name = name;
        this.priority = priority;
        this.enqueueTime = System.currentTimeMillis();
    }

    //getters

    //Nombre del paciente
    public String getName() { return name; }

    //Prioridad del paciente
    public Priority getPriority() { return priority; }

    //Tiempo en que el apciente fue ingresado en milisegundos
    public long getEnqueueTime() { return enqueueTime; }

    //Compara pacientes por prioridad y tiempo de llegada
    @Override
    public int compareTo(Patient other) {
        int p = this.priority.ordinal() - other.priority.ordinal();
        if (p != 0) return p;
        return Long.compare(this.id, other.id);
    }

    //info del paciente en string
    @Override
    public String toString() {
        return "["+priority+"] " + name + " (ID:" + id + ")";
    }
}