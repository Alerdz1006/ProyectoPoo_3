# 🏥 PROYECTO 2 - CENTRO DE ATENCION MEDICA VIRTUAL

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Swing](https://img.shields.io/badge/GUI-Java_Swing-green)
![Concurrency](https://img.shields.io/badge/Concurrency-PriorityBlockingQueue-orange)

Simulador de un sistema de atención médica con prioridades implementado en Java, que utiliza programación concurrente para gestionar pacientes y doctores.

## 📌 Características principales

- 🖥️ **Interfaz gráfica intuitiva** con Java Swing
- 🚑 **Tres niveles de prioridad** para pacientes
- ⏱️ **Tiempos de atención diferenciados** según gravedad
- 👨‍⚕️ **3 doctores virtuales** atendiendo concurrentemente
- 📊 **Panel de monitorización** en tiempo real
- ⚡ **Generación automática** de pacientes de prueba

## 🏗️ Estructura del proyecto
- src/
- ├── Main.java # Punto de entrada
- ├── AppUI.java # Interfaz gráfica (Swing)
- ├── Doctor.java # Lógica de los doctores
- └── Patient.java # Modelo de pacientes

text

## ⚙️ Requisitos

- Java JDK 17 o superior
- Maven (opcional para gestión de dependencias)

## 🚀 Cómo ejecutar

### Con IDE (recomendado)
1. Clona el repositorio
2. Abre el proyecto en IntelliJ/Eclipse
3. Ejecuta `Main.java`

 Uso del sistema
Registro manual:

Ingresa nombre y prioridad del paciente

Click en "Registrar Paciente"

Modo automático:

El sistema genera pacientes aleatorios cada 3 segundos

Monitorización:

Consulta el estado de los doctores en tiempo real

Revisa el histórico en el panel de registro

⏱️ Tiempos de atención
Prioridad	Tiempo de atención
🚨 Emergencia	8-10 segundos
⚠️ Urgente	5-7 segundos
🏥 Consulta general	1-5 segundos

📝 Notas adicionales
Los pacientes se atienden por prioridad y orden de llegada

Los doctores cambian de color según su estado (rojo=ocupado, verde=libre)

El sistema muestra tiempos de espera y atención para cada paciente
