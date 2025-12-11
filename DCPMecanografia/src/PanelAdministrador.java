import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PanelAdministrador {

    public void mostrarPanelAdministrador() {
        try {
            // Crear la ventana del panel de administrador
            JFrame frame = new JFrame("Panel de Administrador");
            frame.setSize(800, 600);  // Definir el tamaño de la ventana
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Deshabilitar cierre por "X"
            frame.setResizable(false);  // Deshabilitar la opción de redimensionar la ventana
            frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

            // Deshabilitar los botones de maximizar y minimizar
            frame.setExtendedState(JFrame.NORMAL);  // No permitir maximizar la ventana
            frame.setUndecorated(true);  // Eliminar los bordes y botones de la ventana

            // Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));  // Cargar el ícono desde el directorio src
            frame.setIconImage(icono.getImage());  // Establecer el ícono en la ventana

            // Crear el panel principal con fondo futurista
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);  // Llamar al método original para que el panel se dibuje correctamente
                    // Dibujar un fondo oscuro con líneas y círculos futuristas
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Mejora la calidad del dibujo
                    g2d.setColor(new Color(30, 30, 30));  // Fondo oscuro
                    g2d.fillRect(0, 0, getWidth(), getHeight());  // Rellenar todo el fondo con el color oscuro

                    // Dibujar líneas diagonales de color cyan
                    g2d.setColor(new Color(0, 255, 255, 120));  // Cyan semi-transparente
                    int numLines = 30;  // Número de líneas diagonales
                    for (int i = 0; i < numLines; i++) {
                        double angle = Math.toRadians(i * 5);  // Ángulo de cada línea
                        int startX = (int) (Math.cos(angle) * (i * 20));  // Coordenada de inicio en X
                        int startY = (int) (Math.sin(angle) * (i * 20));  // Coordenada de inicio en Y
                        int endX = getWidth() - startX;  // Coordenada final en X
                        int endY = getHeight() - startY;  // Coordenada final en Y
                        g2d.setStroke(new BasicStroke(2));  // Establecer el grosor de las líneas
                        g2d.drawLine(startX, startY, endX, endY);  // Dibujar la línea
                    }

                    // Dibujar círculos de color cyan
                    g2d.setColor(new Color(0, 255, 255, 80));  // Círculos de color cian semi-transparente
                    for (int i = 0; i < numLines; i++) {
                        int x = (int) (Math.random() * getWidth());  // Coordenada aleatoria en X
                        int y = (int) (Math.random() * getHeight());  // Coordenada aleatoria en Y
                        int radius = (int) (Math.random() * 50 + 20);  // Radio aleatorio para los círculos
                        g2d.fillOval(x, y, radius, radius);  // Dibujar el círculo
                    }
                }
            };
            panel.setLayout(null);  // Usar un layout nulo para colocar los componentes con coordenadas específicas

            // Título de la ventana con texto en cyan brillante
            JLabel titulo = new JLabel("Panel de Administrador", SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 40));  // Establecer fuente del título
            titulo.setForeground(new Color(0, 255, 255));  // Color cyan brillante para el título
            titulo.setBounds(0, 50, 800, 50);  // Colocar el título en la parte superior de la ventana
            panel.add(titulo);

            // Crear botones para las acciones del administrador
            // Botón para mostrar la lista de usuarios
            JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
            btnMostrarUsuarios.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnMostrarUsuarios.setForeground(Color.BLACK);  // Texto negro
            btnMostrarUsuarios.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande
            btnMostrarUsuarios.setPreferredSize(new Dimension(250, 50));  // Tamaño personalizado
            btnMostrarUsuarios.setBounds(275, 150, 250, 50);  // Ubicación del botón
            btnMostrarUsuarios.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarUsuarios();  // Llamar al método que muestra los usuarios
                }
            });
            panel.add(btnMostrarUsuarios);

            // Botón para dar de alta a un usuario
            JButton btnAlta = new JButton("Dar de Alta Usuario");
            btnAlta.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnAlta.setForeground(Color.BLACK);  // Texto negro
            btnAlta.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande
            btnAlta.setPreferredSize(new Dimension(250, 50));  // Tamaño personalizado
            btnAlta.setBounds(275, 220, 250, 50);  // Ubicación del botón
            btnAlta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    darDeAltaUsuario();  // Llamar al método que permite dar de alta un nuevo usuario
                }
            });
            panel.add(btnAlta);

            // Botón para dar de baja a un usuario
            JButton btnBaja = new JButton("Dar de Baja Usuario");
            btnBaja.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnBaja.setForeground(Color.BLACK);  // Texto negro
            btnBaja.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande
            btnBaja.setPreferredSize(new Dimension(250, 50));  // Tamaño personalizado
            btnBaja.setBounds(275, 290, 250, 50);  // Ubicación del botón
            btnBaja.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    darDeBajaUsuario();  // Llamar al método que permite dar de baja un usuario
                }
            });
            panel.add(btnBaja);

            // Botón para editar los textos de los niveles
            JButton btnEditarTextos = new JButton("Editar Textos de Niveles");
            btnEditarTextos.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnEditarTextos.setForeground(Color.BLACK);  // Texto negro
            btnEditarTextos.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande
            btnEditarTextos.setPreferredSize(new Dimension(250, 50));  // Tamaño personalizado
            btnEditarTextos.setBounds(275, 360, 250, 50);  // Ubicación del botón
            btnEditarTextos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editarTextosNiveles();  // Llamar al método que permite editar los textos de los niveles
                }
            });
            panel.add(btnEditarTextos);

            // Botón de "Salir" para cerrar el panel de administrador
            JButton btnSalir = new JButton("Salir");
            btnSalir.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnSalir.setForeground(Color.BLACK);  // Texto negro
            btnSalir.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente más grande
            btnSalir.setPreferredSize(new Dimension(250, 50));  // Tamaño personalizado
            btnSalir.setBounds(320, 450, 160, 40);  // Ubicación del botón de salir
            btnSalir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();  // Cerrar el panel de administrador
                    new PanelBienvenida().mostrarPanelBienvenida();  // Volver al panel de bienvenida
                }
            });
            panel.add(btnSalir);

            // Añadir el panel a la ventana
            frame.add(panel);

            // Hacer la ventana visible
            frame.setVisible(true);

            // Deshabilitar el cierre de la ventana con la "X"
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    // No hacer nada si se intenta cerrar la ventana
                }
            });

        } catch (Exception e) {
            e.printStackTrace();  // Mostrar el error si ocurre alguna excepción
        }
    }

    // Método para mostrar los usuarios registrados
    private void mostrarUsuarios() {
        try {
            // Crear una ventana emergente para mostrar los usuarios
            JDialog dialog = new JDialog();
            dialog.setTitle("Usuarios Registrados");
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null);

            // Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));
            dialog.setIconImage(icono.getImage());

            JTextArea areaUsuarios = new JTextArea();
            areaUsuarios.setEditable(false);  // No permitir editar el área de texto
            areaUsuarios.setBackground(new Color(220, 220, 220));  // Fondo claro para el área de texto
            areaUsuarios.setFont(new Font("Arial", Font.PLAIN, 18));  // Fuente grande para los usuarios
            JScrollPane scrollUsuarios = new JScrollPane(areaUsuarios);  // Añadir scroll al área de texto
            dialog.add(scrollUsuarios);

            // Cargar y mostrar los usuarios
            cargarUsuarios(areaUsuarios);

            dialog.setVisible(true);  // Mostrar la ventana emergente
        } catch (Exception e) {
            e.printStackTrace();  // Capturar excepciones
        }
    }

    // Método para cargar los usuarios desde el archivo
    private void cargarUsuarios(JTextArea areaUsuarios) {
        try {
            File archivo = new File("src/usuarios.txt");  // Ruta del archivo de usuarios
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            boolean esUsuario = false;
            areaUsuarios.setText("");  // Limpiar el área de texto

            // Leer el archivo línea por línea
            while ((linea = reader.readLine()) != null) {
                if (linea.equals("[USUARIOS]")) {
                    esUsuario = true;  // Señalar que estamos en la sección de usuarios
                } else if (esUsuario && !linea.trim().isEmpty()) {
                    areaUsuarios.append(linea + "\n");  // Añadir cada usuario al área de texto
                }
            }
            reader.close();  // Cerrar el archivo después de leer
        } catch (IOException e) {
            e.printStackTrace();  // Capturar excepciones
        }
    }

    // Método para dar de alta un nuevo usuario
    private void darDeAltaUsuario() {
        try {
            // Leer los usuarios existentes desde el archivo
            File archivo = new File("src/usuarios.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            StringBuilder lineas = new StringBuilder();
            boolean esUsuario = false;
            int numUsuarios = 0;

            // Leer el archivo y contar cuántos usuarios existen
            while ((linea = reader.readLine()) != null) {
                lineas.append(linea).append("\n");
                if (linea.equals("[USUARIOS]")) {
                    esUsuario = true;
                }
                // Contar solo las líneas que contienen usuarios
                if (esUsuario && !linea.trim().isEmpty() && !linea.startsWith("[") && !linea.startsWith(" ")) {
                    numUsuarios++;
                }
            }
            reader.close();

            // Validar si ya existen 5 usuarios
            if (numUsuarios >= 5) {
                JOptionPane.showMessageDialog(null, "No se pueden dar más de 5 usuarios.");
                return;
            }

            // Pedir al administrador el nombre y la contraseña del nuevo usuario
            String nombre = JOptionPane.showInputDialog("Nombre del nuevo usuario:");
            if (nombre == null || nombre.isEmpty()) return;  // Validar que el nombre no esté vacío

            String contrasena = JOptionPane.showInputDialog("Contraseña del nuevo usuario:");
            if (contrasena == null || contrasena.isEmpty()) return;  // Validar que la contraseña no esté vacía

            // Escribir el nuevo usuario en el archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true));
            writer.write(nombre + "," + contrasena + "\n");  // Guardar usuario con nombre y contraseña
            writer.close();

            JOptionPane.showMessageDialog(null, "Usuario dado de alta con éxito.");  // Confirmación
        } catch (IOException e) {
            e.printStackTrace();  // Capturar cualquier error
        }
    }

    private void darDeBajaUsuario() {
        try {
            // Leer los usuarios actuales desde el archivo
            File archivo = new File("src/usuarios.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            StringBuilder lineas = new StringBuilder();
            boolean esUsuario = false;
            int numUsuarios = 0;

            // Leer el archivo y contar el número de usuarios
            while ((linea = reader.readLine()) != null) {
                lineas.append(linea).append("\n");
                if (linea.equals("[USUARIOS]")) {
                    esUsuario = true;
                }
                // Contar solo las líneas con usuarios
                if (esUsuario && !linea.trim().isEmpty() && !linea.startsWith("[") && !linea.startsWith(" ")) {
                    numUsuarios++;
                }
            }
            reader.close();

            // Si hay menos de 3 usuarios, no permitir eliminar usuarios
            if (numUsuarios <= 3) {
                JOptionPane.showMessageDialog(null, "No se pueden eliminar usuarios, hay 3 o menos.");
                return;
            }

            // Solicitar al administrador el nombre del usuario a eliminar
            String usuarioAEliminar = JOptionPane.showInputDialog("Nombre del usuario a eliminar:");
            if (usuarioAEliminar == null || usuarioAEliminar.isEmpty()) return;

            // Validar que no sea el admin
            if (usuarioAEliminar.equals("a")) {
                JOptionPane.showMessageDialog(null, "No puedes eliminar al administrador principal");
                return;
            }

            // Eliminar al usuario especificado del archivo
            BufferedReader reader2 = new BufferedReader(new FileReader(archivo));
            StringBuilder lineasFinales = new StringBuilder();
            boolean enUsuarios = false;

            while ((linea = reader2.readLine()) != null) {
                if (linea.equals("[ADMINISTRADORES]")) {
                    enUsuarios = false;
                    lineasFinales.append(linea).append("\n");
                } 
                else if (linea.equals("[USUARIOS]")) {
                    enUsuarios = true;
                    lineasFinales.append(linea).append("\n");
                }
                else {
                    if (enUsuarios && linea.startsWith(usuarioAEliminar + ",")) {
                        continue;
                    }
                    lineasFinales.append(linea).append("\n");
                }
            }
            reader2.close();

            // Escribir el archivo con los usuarios restantes
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(lineasFinales.toString());
            writer.close();

            JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para editar los textos de los niveles
    private void editarTextosNiveles() {
        try {
            // Mostrar un cuadro de diálogo para elegir el nivel
            String[] niveles = {"NivelFacil", "NivelDificil"};
            String nivelSeleccionado = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un nivel:",
                    "Elegir Nivel",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    niveles,
                    niveles[0]
            );

            // Si se selecciona un nivel válido, abrir el panel de edición correspondiente
            if (nivelSeleccionado != null) {
                Lecciones.mostrarPanelEdicion(nivelSeleccionado);  // Llamar a la clase Lecciones para la edición
            }

        } catch (Exception e) {
            e.printStackTrace();  // Capturar excepciones si ocurren errores
        }
    }
}
