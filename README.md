Simulador de Centro de Atención Médica Virtual
Descripción
Sistema de simulación de atención médica con prioridades desarrollado en Java, que utiliza programación concurrente para gestionar pacientes y doctores en un entorno virtual.

Características principales
✔ Interfaz gráfica con Java Swing
✔ Gestión de prioridades (Emergencia, Urgente, Consulta general)
✔ Sistema de colas con PriorityBlockingQueue
✔ Tiempos de atención diferenciados según prioridad del paciente
✔ Visualización en tiempo real del estado de los doctores
✔ Registro detallado de todas las actividades

Estructura del proyecto
text
CentroMedicoVirtual/
├── src/
│   ├── Main.java            # Punto de entrada del programa
│   ├── AppUI.java           # Interfaz gráfica principal
│   ├── Doctor.java          # Lógica de atención de doctores
│   └── Patient.java         # Modelo de paciente con prioridades
Requisitos previos
Java JDK 11 o superior

IDE recomendado: IntelliJ IDEA o Eclipse (opcional)

Pasos de ejecución
Ejecutar desde IDE
Clonar el repositorio (si está disponible)

Abrir el proyecto en tu IDE

Buscar y ejecutar la clase Main.java

Ejecutar desde línea de comandos
Compilar:

bash
javac src/*.java -d bin/
Ejecutar:

bash
java -cp bin/ Main
Uso del sistema
Registro de pacientes:

Ingresar nombre y seleccionar prioridad

Hacer clic en "Registrar Paciente"

Visualización de doctores:

Ver estado actual (Libre/Atendiendo) en pestaña "Consultorios"

Los doctores cambian de color según su estado

Monitorización:

Todos los eventos se registran en la pestaña "Panel de Control"

Se muestra tiempo de espera y atención para cada paciente

Funcionamiento automático:

El sistema genera pacientes aleatorios cada 3 segundos

Tres doctores atienden pacientes según su prioridad

Prioridades y tiempos de atención
🚨 Emergencia: 8-10 segundos de atención

⚠️ Urgente: 5-7 segundos de atención

🏥 Consulta general: 1-5 segundos de atención

El sistema prioriza automáticamente a los pacientes según su condición médica.
