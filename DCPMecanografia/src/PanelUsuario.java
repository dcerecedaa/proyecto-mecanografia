import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PanelUsuario {

    public void mostrarPanelUsuario(String nombreUsuario) {
        try {
            // Crear la ventana del panel de usuario
            JFrame frame = new JFrame("Panel de Usuario - " + nombreUsuario);
            frame.setSize(800, 600);  // Definir el tamaño de la ventana a 800x600 píxeles
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Deshabilitar cierre por "X"
            frame.setResizable(false);  // Deshabilitar la capacidad de redimensionar la ventana
            frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

            // Deshabilitar los botones de maximizar y minimizar
            frame.setExtendedState(JFrame.NORMAL);  // No permitir maximizar
            frame.setUndecorated(true);  // Eliminar el marco de la ventana (sin botones de minimizar/maximizar)
            
            // Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));  // Cargar el ícono desde el recurso
            frame.setIconImage(icono.getImage());  // Establecer el icono de la ventana

            // Crear un panel principal con un fondo futurista
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Configurar el fondo del panel con un diseño futurista

                    // Crear un objeto Graphics2D para mejor control sobre el renderizado gráfico
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(30, 30, 30));  // Fondo oscuro
                    g2d.fillRect(0, 0, getWidth(), getHeight());  // Rellenar todo el panel con un color oscuro

                    // Dibujar líneas diagonales de color cyan semi-transparente
                    g2d.setColor(new Color(0, 255, 255, 120));  // Cyan semi-transparente
                    int numLines = 30;  // Número de líneas a dibujar
                    for (int i = 0; i < numLines; i++) {
                        double angle = Math.toRadians(i * 5);  // Definir el ángulo de cada línea
                        int startX = (int) (Math.cos(angle) * (i * 20));  // Coordenada X de inicio, calculada con coseno
                        int startY = (int) (Math.sin(angle) * (i * 20));  // Coordenada Y de inicio, calculada con seno
                        int endX = getWidth() - startX;  // Coordenada X de fin, ajustada al tamaño del panel
                        int endY = getHeight() - startY;  // Coordenada Y de fin, ajustada al tamaño del panel
                        g2d.setStroke(new BasicStroke(2));  // Establecer el grosor de la línea
                        g2d.drawLine(startX, startY, endX, endY);  // Dibujar la línea
                    }

                    // Agregar círculos de color cyan semi-transparente en ubicaciones aleatorias
                    g2d.setColor(new Color(0, 255, 255, 80));  // Círculos cian, semi-transparente
                    for (int i = 0; i < numLines; i++) {
                        int x = (int) (Math.random() * getWidth());  // Generar coordenada X aleatoria dentro del panel
                        int y = (int) (Math.random() * getHeight());  // Generar coordenada Y aleatoria dentro del panel
                        int radius = (int) (Math.random() * 50 + 20);  // Generar un radio aleatorio entre 20 y 70 píxeles
                        g2d.fillOval(x, y, radius, radius);  // Dibujar el círculo con la posición y tamaño calculados
                    }
                }
            };
            panel.setLayout(null);  // Usar layout nulo para permitir el posicionamiento absoluto de los componentes

            // Título de la ventana con texto brillante en cyan
            JLabel titulo = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 40));  // Fuente grande y en negrita para el título
            titulo.setForeground(new Color(0, 255, 255));  // Color cyan brillante para el texto
            titulo.setBounds(0, 50, 800, 50);  // Ubicación del título en la parte superior de la ventana
            panel.add(titulo);

            // Crear panel de opciones del usuario con botones
            JPanel panelOpciones = new JPanel();
            panelOpciones.setLayout(new GridLayout(3, 1, 10, 20));  // Tres botones en columna con espacio entre ellos
            panelOpciones.setBounds(150, 200, 500, 200);  // Ubicación del panel de botones
            panelOpciones.setOpaque(false);  // Hacer el panel transparente

            // Botón para elegir nivel
            JButton btnElegirNivel = new JButton("Elegir Nivel");
            btnElegirNivel.setBackground(new Color(0, 255, 255));  // Fondo cyan brillante
            btnElegirNivel.setForeground(Color.BLACK);  // Texto negro
            btnElegirNivel.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande para el texto
            btnElegirNivel.setPreferredSize(new Dimension(160, 40));  // Tamaño del botón
            btnElegirNivel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean panelAbierto = elegirNivel(nombreUsuario); // Llamar al método para elegir nivel
                    if (panelAbierto) {
                    	frame.dispose(); 
                    }
                }
            });

            // Botón para ver estadísticas
            JButton btnEstadisticas = new JButton("Ver Estadísticas");
            btnEstadisticas.setBackground(new Color(0, 255, 255));  // Fondo cyan brillante
            btnEstadisticas.setForeground(Color.BLACK);  // Texto negro
            btnEstadisticas.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande para el texto
            btnEstadisticas.setPreferredSize(new Dimension(160, 40));  // Tamaño del botón
            btnEstadisticas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarEstadisticas(nombreUsuario);  // Llamar al método para mostrar estadísticas
                }
            });

            // Botón para ver tutorial
            JButton btnTutorial = new JButton("Ver Tutorial");
            btnTutorial.setBackground(new Color(0, 255, 255));  // Fondo cyan brillante
            btnTutorial.setForeground(Color.BLACK);  // Texto negro
            btnTutorial.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande para el texto
            btnTutorial.setPreferredSize(new Dimension(160, 40));  // Tamaño del botón
            btnTutorial.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarTutorial();  // Llamar al método para mostrar el tutorial
                }
            });

            panelOpciones.add(btnElegirNivel);
            panelOpciones.add(btnEstadisticas);
            panelOpciones.add(btnTutorial);

            panel.add(panelOpciones);

            // Botón de "Salir"
            JButton btnSalir = new JButton("Salir");
            btnSalir.setBackground(new Color(0, 255, 255));  // Fondo cyan brillante
            btnSalir.setForeground(Color.BLACK);  // Texto negro
            btnSalir.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande para el texto
            btnSalir.setPreferredSize(new Dimension(160, 40));  // Tamaño del botón
            btnSalir.setBounds(320, 450, 160, 40);  // Asegurar que el botón de salir esté visible en la ventana
            btnSalir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();  // Cerrar el panel de usuario
                    new PanelLogin().mostrarPanelLogin();  // Volver al panel de bienvenida
                }
            });
            panel.add(btnSalir);

            // Añadir el panel al frame y hacer visible la ventana
            frame.add(panel);
            frame.setVisible(true);

            // Deshabilitar los botones de minimizar, maximizar y cerrar (X)
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    // No hacer nada cuando se intente cerrar la ventana
                }
            });

        } catch (Exception e) {
            e.printStackTrace();  // Manejar cualquier excepción
        }
    }

    // Método para elegir el nivel (Fácil o Difícil)
    private boolean elegirNivel(String nombreUsuario) {
        try {
            String[] niveles = {"Fácil", "Difícil"};
            String nivelSeleccionado = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un nivel:",
                    "Elegir Nivel",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    niveles,
                    niveles[0]
            );

            if (nivelSeleccionado != null) {
                if (nivelSeleccionado.equals("Fácil")) {
                    new PanelNivelFacil().mostrarNivelFacil(nombreUsuario);
                } else if (nivelSeleccionado.equals("Difícil")) {
                    new PanelNivelDificil().mostrarNivelDificil(nombreUsuario);
                }
                return true; // Nuevo panel abierto
            }
            return false; // Usuario canceló
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para mostrar las estadísticas del usuario
    private void mostrarEstadisticas(String nombreUsuario) {
        try {
            File archivoEstadisticas = new File("src/estadisticas.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivoEstadisticas));
            String linea;
            StringBuilder estadisticas = new StringBuilder();
            boolean encontrado = false;

            while ((linea = reader.readLine()) != null) {
                // Buscar todas las coincidencias de "nombreUsuario|..." en la línea
                int index = 0;
                while ((index = linea.indexOf(nombreUsuario + "|", index)) != -1) {
                    int endIndex = linea.indexOf(System.lineSeparator(), index);
                    if (endIndex == -1) endIndex = linea.length();
                    String posibleRegistro = linea.substring(index, endIndex);
                    
                    if (posibleRegistro.split("\\|").length == 6) {
                        estadisticas.append(formatearEstadisticas(posibleRegistro)).append("\n\n");
                        encontrado = true;
                    }
                    index = endIndex;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "No hay estadísticas para " + nombreUsuario);
            } else {
                JTextArea areaEstadisticas = new JTextArea(estadisticas.toString());
                areaEstadisticas.setEditable(false);
                JScrollPane scroll = new JScrollPane(areaEstadisticas);
                scroll.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(null, scroll, "Estadísticas de " + nombreUsuario, JOptionPane.PLAIN_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de estadísticas.");
        }
    }
    
    private String formatearEstadisticas(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length != 6) {
            return ""; // Ignora líneas inválidas
        }
        return String.format(
            "Nivel: %s\nPuntos: %s\nErrores: %s\nTiempo: %s\nPPM: %s",
            partes[1].replace("Nivel: ", ""), // Ej: "Fácil"
            partes[2].replace("Puntos: ", ""),
            partes[3].replace("Errores: ", ""),
            partes[4].replace("Tiempo: ", ""),
            partes[5].replace("PPM: ", "")
        );
    }
    
    // Mostrar el tutorial (imagen)
    private void mostrarTutorial() {
        try {
            ImageIcon tutorialImage = new ImageIcon("src/tutorial.png");
            JLabel labelTutorial = new JLabel(tutorialImage);
            JOptionPane.showMessageDialog(null, labelTutorial, "Tutorial de Mecanografía", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el tutorial.");
        }
    }
}
