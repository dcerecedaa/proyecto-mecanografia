import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;

public class PanelBienvenida {

    public void mostrarPanelBienvenida() {
        try {
            // Se crea una ventana llamada "Bienvenido" con tamaño 800x600
            JFrame frame = new JFrame("Bienvenido");
            frame.setSize(800, 600);  // Tamaño de la ventana
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Evitar que se cierre con la "X"
            frame.setResizable(false);  // No permitir que se cambie el tamaño de la ventana
            frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

            // Deshabilitar los botones de maximizar y minimizar
            frame.setExtendedState(JFrame.NORMAL);  // No se puede maximizar la ventana
            frame.setUndecorated(true);  // Quitar la barra de título de la ventana

            // Se asigna un ícono a la ventana desde un archivo de imagen
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));
            frame.setIconImage(icono.getImage());  // Establecer el ícono de la ventana

            // Crear el panel principal donde se van a añadir los elementos
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);  // Llama al método de la clase JPanel para asegurar que se dibuje correctamente el panel
                    // Dibuja un fondo oscuro con líneas y círculos de color cian
                    Graphics2D g2d = (Graphics2D) g;  // Convierte el objeto Graphics en un Graphics2D, para tener más control
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Mejora la calidad del dibujo
                    g2d.setColor(new Color(30, 30, 30));  // Fondo oscuro
                    g2d.fillRect(0, 0, getWidth(), getHeight());  // Rellenar el fondo con el color oscuro

                    // Dibuja líneas diagonales de color cian
                    g2d.setColor(new Color(0, 255, 255, 120));  // Color cian semi-transparente
                    int numLines = 30;  // Cantidad de líneas a dibujar
                    for (int i = 0; i < numLines; i++) {
                        double angle = Math.toRadians(i * 5);  // Calcular un ángulo diferente para cada línea
                        int startX = (int) (Math.cos(angle) * (i * 20));  // Calcula la posición de inicio en X
                        int startY = (int) (Math.sin(angle) * (i * 20));  // Calcula la posición de inicio en Y
                        int endX = getWidth() - startX;  // Calcula la posición de fin en X
                        int endY = getHeight() - startY;  // Calcula la posición de fin en Y
                        g2d.setStroke(new BasicStroke(2));  // Define el grosor de la línea
                        g2d.drawLine(startX, startY, endX, endY);  // Dibuja la línea con las posiciones calculadas
                    }

                    // Dibuja círculos de color cian
                    g2d.setColor(new Color(0, 255, 255, 80));  // Círculos cian semi-transparente
                    for (int i = 0; i < numLines; i++) {
                        int x = (int) (Math.random() * getWidth());  // Coordenada aleatoria en X
                        int y = (int) (Math.random() * getHeight());  // Coordenada aleatoria en Y
                        int radius = (int) (Math.random() * 50 + 20);  // Radio aleatorio para el círculo
                        g2d.fillOval(x, y, radius, radius);  // Dibuja un círculo con la posición y tamaño calculados
                    }
                }
            };
            panel.setLayout(null);  // Usar layout nulo para colocar los elementos con coordenadas

            // Título de la ventana con color brillante cyan
            JLabel titulo = new JLabel("Bienvenido a la aplicación", SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 40));
            titulo.setForeground(new Color(0, 255, 255));  // Cyan brillante
            titulo.setBounds(0, 100, 800, 50);  // Ubicamos el título un poco más abajo
            panel.add(titulo);  // Añadir el título al panel

            // Crear dos botones: "Jugar" y "Salir"
            JButton btnJugar = new JButton("Jugar");
            JButton btnSalir = new JButton("Salir");

            // Establecer estilo para los botones
            btnJugar.setFont(new Font("Arial", Font.BOLD, 20));  // Estilo de letra
            btnJugar.setForeground(Color.BLACK);  // Texto negro
            btnJugar.setBackground(new Color(0, 255, 255));  // Fondo cian brillante
            btnJugar.setPreferredSize(new Dimension(200, 50));  // Tamaño del botón
            btnJugar.setFocusPainted(false);  // Quitar el borde de enfoque
            btnJugar.setBorderPainted(false);  // Quitar el borde
            btnJugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Establecer el cursor tipo mano

            btnSalir.setFont(new Font("Arial", Font.BOLD, 20));  // Estilo de letra
            btnSalir.setForeground(Color.BLACK);  // Texto negro
            btnSalir.setBackground(new Color(0, 255, 255));  // Fondo cian brillante
            btnSalir.setPreferredSize(new Dimension(200, 50));  // Tamaño del botón
            btnSalir.setFocusPainted(false);  // Quitar el borde de enfoque
            btnSalir.setBorderPainted(false);  // Quitar el borde
            btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Establecer el cursor tipo mano

            // Crear un panel para los botones con una disposición centrada
            JPanel botonesPanel = new JPanel();
            botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));  // Botones centrados
            botonesPanel.setBounds(0, 250, 800, 100);  // Ubicación de los botones en la parte inferior
            botonesPanel.setOpaque(false);  // Hacer el panel de botones transparente
            botonesPanel.add(btnJugar);  // Añadir el botón "Jugar"
            botonesPanel.add(btnSalir);  // Añadir el botón "Salir"

            // Acción para el botón "Jugar"
            btnJugar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new PanelLogin().mostrarPanelLogin();  // Mostrar el panel de login
                    frame.dispose();  // Cerrar la ventana de bienvenida
                }
            });

            // Acción para el botón "Salir"
            btnSalir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);  // Cerrar la aplicación
                }
            });

            // Añadir el panel de botones al panel principal
            panel.add(botonesPanel);

            // Añadir el panel principal al frame
            frame.add(panel);

            // Hacer visible la ventana
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();  // Si ocurre un error, se imprime en la consola
        }
    }
}
