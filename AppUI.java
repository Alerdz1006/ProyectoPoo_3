import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Clase principal de la interfaz gráfica del sistema de atención médica.
 * Extiende JFrame para crear la ventana principal con tres pestañas:
 * - Recepción: Para registrar nuevos pacientes
 * - Consultorios: Muestra el estado actual de los doctores
 * - Panel de Control: Muestra el registro de actividades del sistema
 *
 * Utiliza SwingUtilities.invokeLater para garantizar la seguridad en hilos.
 */
public class AppUI extends JFrame {
    //Cola de prioridad compartida con los doctores para gestionar pacientes
    private final PriorityBlockingQueue<Patient> queue;

    //Contador atómico de pacientes atendidos (compartido con los doctores)
    private final AtomicInteger servedCount;

    //Área de texto para mostrar el registro de actividades del sistema
    private JTextArea logArea;

    //Modelo de datos para la lista de doctores y sus estados
    private DefaultListModel<String> doctorsModel;

    //Constructor principal de la interfaz de usuario
    public AppUI(PriorityBlockingQueue<Patient> queue, AtomicInteger servedCount) {
        this.queue = queue;
        this.servedCount = servedCount;
        setupUI();
    }

    /*
     * Configura todos los componentes de la interfaz gráfica.
     * Crea las tres pestañas principales con sus respectivos componentes.
     */
    private void setupUI() {
        // Configuración básica de la ventana principal
        setTitle("Centro de Atención Médica Virtual");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración de estilos generales
        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Button.font", customFont);
        UIManager.put("Label.font", customFont);
        UIManager.put("ComboBox.font", customFont);

        // Creación del panel de pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(customFont.deriveFont(Font.BOLD, 14));

        // ------------------- Pestaña de Recepción -------------------
        JPanel receptionPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        receptionPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        receptionPanel.setBackground(new Color(245, 249, 252));

        // Componentes para registro de pacientes
        JLabel nameLabel = new JLabel("<html><font color='#2c3e50'><b>Nombre Paciente:</b></font></html>");
        JTextField nameField = new JTextField();
        nameField.setFont(customFont);

        JLabel priorityLabel = new JLabel("<html><font color='#2c3e50'><b>Prioridad:</b></font></html>");
        JComboBox<Patient.Priority> priorityBox = new JComboBox<>(Patient.Priority.values());

        // Renderer personalizado para el JComboBox de prioridades
        priorityBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText("<html><font color='#2c3e50'>" + value.toString() + "</font></html>");
                return this;
            }
        });

        // Botón para registrar pacientes
        JButton addBtn = new JButton("<html><font color='white'><b>Registrar Paciente</b></font></html>");
        addBtn.setBackground(new Color(44, 62, 80));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // ActionListener para el botón de registro
        addBtn.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Patient p = new Patient(name, (Patient.Priority)priorityBox.getSelectedItem());
                queue.offer(p);
                addLog("<html><font color='#27ae60'>Registrado:</font> " + p + "</html>");
                nameField.setText("");
            }
        });

        // Añadir componentes al panel de recepción
        receptionPanel.add(nameLabel);
        receptionPanel.add(nameField);
        receptionPanel.add(priorityLabel);
        receptionPanel.add(priorityBox);
        receptionPanel.add(addBtn);
        tabs.addTab("<html><font color='#2c3e50'><b>Recepción</b></font></html>", receptionPanel);

        // ------------------- Pestaña de Consultorios -------------------
        JPanel doctorsPanel = new JPanel(new BorderLayout());
        doctorsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        doctorsPanel.setBackground(new Color(245, 249, 252));

        doctorsModel = new DefaultListModel<>();
        JList<String> doctorList = new JList<>(doctorsModel);

        // Renderer personalizado para la lista de doctores (colores según estado)
        doctorList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value.toString().contains("Libre")) {
                    setText("<html><font color='#27ae60'>" + value + "</font></html>"); // Verde
                } else if (value.toString().contains("Atendiendo")) {
                    setText("<html><font color='#e74c3c'>" + value + "</font></html>"); // Rojo
                } else {
                    setText("<html><font color='#2c3e50'>" + value + "</font></html>"); // Gris oscuro
                }
                return this;
            }
        });
        doctorList.setFont(customFont);

        doctorsPanel.add(new JScrollPane(doctorList), BorderLayout.CENTER);
        tabs.addTab("<html><font color='#2c3e50'><b>Consultorios</b></font></html>", doctorsPanel);

        // ------------------- Pestaña de Panel de Control -------------------
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        controlPanel.setBackground(new Color(245, 249, 252));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(customFont);
        logArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 226)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        logArea.setBackground(Color.WHITE);

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                "<html><font color='#2c3e50'><b>Registro de Actividades</b></font></html>"
        ));

        controlPanel.add(logScroll, BorderLayout.CENTER);
        tabs.addTab("<html><font color='#2c3e50'><b>Panel de Control</b></font></html>", controlPanel);

        // Añadir el panel de pestañas a la ventana principal
        add(tabs, BorderLayout.CENTER);
    }

    //Actualiza el estado de un doctor en la interfaz gráfica.
    public void updateDoctorStatus(String doctorName, String status) {
        SwingUtilities.invokeLater(() -> {
            boolean found = false;
            // Buscar si el doctor ya está en la lista
            for (int i = 0; i < doctorsModel.size(); i++) {
                if (doctorsModel.get(i).startsWith(doctorName)) {
                    doctorsModel.set(i, "<html><b>" + doctorName + "</b> - " + status + "</html>");
                    found = true;
                    break;
                }
            }
            // Si no se encontró, añadir nuevo elemento
            if (!found) {
                doctorsModel.addElement("<html><b>" + doctorName + "</b> - " + status + "</html>");
            }
        });
    }

    //Añade un mensaje al registro de actividades en el panel de control
    public void addLog(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            // Auto-scroll al final del texto
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}