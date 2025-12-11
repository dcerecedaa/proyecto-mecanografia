import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;
import java.io.*;

public class PanelLogin {

    public void mostrarPanelLogin() {
        try {
            // Crear la ventana de login
            JFrame frame = new JFrame();  // El título predeterminado se elimina completamente
            frame.setSize(800, 600);  // Tamaño de la ventana
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Deshabilitar cierre por "X"
            frame.setResizable(false);  // Deshabilitar redimensionamiento de la ventana
            frame.setLocationRelativeTo(null); // Centrar ventana en la pantalla

            // Deshabilitar los botones de maximizar y minimizar
            frame.setExtendedState(JFrame.NORMAL);  // No permitir maximizar
            frame.setUndecorated(true);  // Quitar el marco, incluyendo los botones de minimizar/maximizar

            // Camuflar el título predeterminado con el mismo color de fondo
            frame.setBackground(new Color(30, 30, 30));  // Fondo oscuro general
            
            // Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));  // Cargar el ícono desde el directorio src
            frame.setIconImage(icono.getImage());  // Establecer el ícono de la ventana

            // Crear el panel principal con un fondo futurista
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);  // Llamamos al método de la clase JPanel para asegurarnos de que el panel se dibuje correctamente
                    // Fondo oscuro, con colores vivos de líneas
                    Graphics2D g2d = (Graphics2D) g;  // Convertimos Graphics en Graphics2D para más control en el dibujo
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Mejora la calidad del dibujo
                    g2d.setColor(new Color(30, 30, 30));  // Fondo oscuro
                    g2d.fillRect(0, 0, getWidth(), getHeight());  // Rellenamos el fondo con el color oscuro

                    // Líneas diagonales de color cyan
                    g2d.setColor(new Color(0, 255, 255, 120));  // Cyan semi-transparente
                    int numLines = 30;  // Líneas de diferentes tamaños
                    for (int i = 0; i < numLines; i++) {
                        double angle = Math.toRadians(i * 5);  // Cada línea tiene un ángulo ligeramente diferente
                        int startX = (int) (Math.cos(angle) * (i * 20));  // Coordenada de inicio en X
                        int startY = (int) (Math.sin(angle) * (i * 20));  // Coordenada de inicio en Y
                        int endX = getWidth() - startX;  // Coordenada de fin en X
                        int endY = getHeight() - startY;  // Coordenada de fin en Y
                        g2d.setStroke(new BasicStroke(2));  // Grosor de las líneas
                        g2d.drawLine(startX, startY, endX, endY);  // Dibujamos la línea con las coordenadas calculadas
                    }

                    // Agregar círculos de color cyan
                    g2d.setColor(new Color(0, 255, 255, 80));  // Círculos de color cian, semi-transparente
                    for (int i = 0; i < numLines; i++) {
                        int x = (int) (Math.random() * getWidth());  // Coordenada aleatoria en X
                        int y = (int) (Math.random() * getHeight());  // Coordenada aleatoria en Y
                        int radius = (int) (Math.random() * 50 + 20);  // Radio aleatorio para el círculo
                        g2d.fillOval(x, y, radius, radius);  // Dibujar el círculo con las coordenadas y el tamaño calculados
                    }
                }
            };
            panel.setLayout(null);  // Usamos layout nulo para poder ubicar los elementos en coordenadas específicas

            // Título de la ventana con color brillante cyan
            JLabel titulo = new JLabel("Iniciar sesión", SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 40));
            titulo.setForeground(new Color(0, 255, 255));  // Cyan brillante
            titulo.setBounds(0, 70, 800, 50);  // Ubicamos el título un poco más abajo
            panel.add(titulo);  // Añadir el título al panel

            // Crear el panel para los campos de texto
            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new GridLayout(3, 2, 15, 15));  // Más espacio entre los componentes
            loginPanel.setBounds(150, 200, 500, 150);  // Ajustamos la ubicación del formulario de login
            loginPanel.setOpaque(false);  // Hacer el panel de login transparente

            JLabel lblUsuario = new JLabel("Usuario: ");
            lblUsuario.setFont(new Font("Arial", Font.BOLD, 20));  // Aumentamos el tamaño del texto para "Usuario"
            lblUsuario.setForeground(new Color(0, 255, 255));  // Cyan brillante

            JLabel lblContrasena = new JLabel("Contraseña: ");
            lblContrasena.setFont(new Font("Arial", Font.BOLD, 20));  // Aumentamos el tamaño del texto para "Contraseña"
            lblContrasena.setForeground(new Color(0, 255, 255));  // Cyan brillante

            JTextField txtUsuario = new JTextField();
            txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));  // Fuente más grande para el usuario
            txtUsuario.setForeground(new Color(0, 255, 255));  // Texto cyan brillante
            txtUsuario.setBackground(new Color(50, 50, 50));  // Fondo oscuro del campo de texto

            JPasswordField txtContrasena = new JPasswordField();
            txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));  // Fuente más grande para la contraseña
            txtContrasena.setForeground(new Color(0, 255, 255));  // Texto cyan brillante
            txtContrasena.setBackground(new Color(50, 50, 50));  // Fondo oscuro del campo de texto

            loginPanel.add(lblUsuario);  // Añadir etiqueta de usuario al panel de login
            loginPanel.add(txtUsuario);  // Añadir campo de texto de usuario
            loginPanel.add(lblContrasena);  // Añadir etiqueta de contraseña al panel de login
            loginPanel.add(txtContrasena);  // Añadir campo de texto de contraseña

            // Añadir loginPanel al panel principal
            panel.add(loginPanel);

            // Crear panel para los botones de "Iniciar Sesión" y "Salir"
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));  // Más espacio entre botones
            buttonPanel.setOpaque(false);  // Hacer el panel transparente

            // Botón para iniciar sesión
            JButton btnIniciarSesion = new JButton("Iniciar Sesión");
            btnIniciarSesion.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnIniciarSesion.setForeground(Color.BLACK);  // Texto negro
            btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 18));  // Aumentamos el tamaño del texto
            btnIniciarSesion.setPreferredSize(new Dimension(160, 40));  // Botón más pequeño
            btnIniciarSesion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String usuario = txtUsuario.getText();
                    String contrasena = new String(txtContrasena.getPassword());

                    // Verificar que los campos no estén vacíos
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Por favor, ingrese las credenciales.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Verificar las credenciales
                        if (verificarCredenciales(usuario, contrasena)) {
                            if (usuario.equals("a")) {
                                // Si es admin, ir al Panel de Administrador
                                new PanelAdministrador().mostrarPanelAdministrador(); 
                            } else {
                                // Si es usuario, ir al Panel de Usuario
                                new PanelUsuario().mostrarPanelUsuario(usuario); 
                            }
                            frame.dispose();  // Cerrar la ventana de login
                        } else {
                            JOptionPane.showMessageDialog(frame, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            // Botón para salir
            JButton btnSalir = new JButton("Salir");
            btnSalir.setBackground(new Color(0, 255, 255));  // Cyan brillante
            btnSalir.setForeground(Color.BLACK);  // Texto negro
            btnSalir.setFont(new Font("Arial", Font.BOLD, 18));  // Aumentamos el tamaño del texto
            btnSalir.setPreferredSize(new Dimension(160, 40));  // Botón más pequeño
            btnSalir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();  // Cerrar el panel de login
                    new PanelBienvenida().mostrarPanelBienvenida();  // Volver al panel de bienvenida
                }
            });

            // Añadir los botones al panel de botones
            buttonPanel.add(btnIniciarSesion);
            buttonPanel.add(btnSalir);

            // Posicionar el panel de botones
            buttonPanel.setBounds(150, 400, 500, 60);  // Subimos un poco más los botones
            panel.add(buttonPanel);

            // Añadir el panel principal al frame
            frame.add(panel);

            // Hacer la ventana visible
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();  // Capturar y mostrar errores en consola
        }
    }

    // Método para verificar las credenciales con los datos del archivo usuarios.txt
    private boolean verificarCredenciales(String usuario, String contrasena) {
        try {
            // Leer archivo usuarios.txt
            File archivo = new File("src/usuarios.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            boolean esUsuario = false;  // Variable para controlar en qué sección estamos

            while ((linea = reader.readLine()) != null) {
                // Detectar sección de administradores o usuarios
                if (linea.equals("[ADMINISTRADORES]")) {
                    esUsuario = false;
                } else if (linea.equals("[USUARIOS]")) {
                    esUsuario = true;
                } else {
                    // Validar credenciales
                    String[] credenciales = linea.split(",");
                    if (esUsuario) {
                        // Verificar en la sección de usuarios
                        if (credenciales[0].equals(usuario) && credenciales[1].equals(contrasena)) {
                            reader.close();
                            return true;  // Credenciales correctas
                        }
                    } else {
                        // Verificar en la sección de administradores
                        if (credenciales[0].equals(usuario) && credenciales[1].equals(contrasena)) {
                            reader.close();
                            return true;  // Credenciales correctas
                        }
                    }
                }
            }
            reader.close();  // Cerrar archivo después de leer
        } catch (IOException e) {
            e.printStackTrace();  // Capturar y mostrar errores en consola
        }
        return false;  // Si no se encontraron coincidencias
    }
}
