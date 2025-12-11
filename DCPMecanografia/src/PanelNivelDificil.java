import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PanelNivelDificil {

    private String textoObjetivoDificil = "";  // Texto del nivel fácil
    private JTextArea areaTextoEscribirDificil;  // Área de texto para escribir
    private int tiempoRestanteDificil = 180;  // 3 minutos (180 segundos)
    private JLabel etiquetaContadorDificil, etiquetaPPM;  // Etiqueta del contador
    private Timer temporizadorDificil;  // Temporizador
    private boolean contadorIniciadoDificil = false;  // Estado del contador
    private long tiempoInicioDificil = 0;
    private int totalPulsacionesDificil = 0;  
    private JPanel panelTeclado;  // Panel del teclado
    private Map<String, JButton> botonesTeclado;  // Botones del teclado
    private int puntajeDificil = 0; 
    private JLabel etiquetaPuntajeDificil;  
    private int erroresDificil = 0; 
    private JLabel etiquetaErroresDificil;
    private JFrame marcoDificil;  
    private String nombreUsuario;
    
    public void mostrarNivelDificil(String nombreUsuario) {
    	this.nombreUsuario = nombreUsuario;    	
    	try {
    		marcoDificil = new JFrame("Nivel Difícil");
    		marcoDificil.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    		marcoDificil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		marcoDificil.setUndecorated(true);
    		marcoDificil.setLocationRelativeTo(null);

    		// Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));  // Cargar el ícono desde el recurso
            marcoDificil.setIconImage(icono.getImage());  // Establecer el icono de la ventana
    		
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(30, 30, 30));
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    g2d.setColor(new Color(0, 255, 255, 120));
                    for (int i = 0; i < 30; i++) {
                        double angulo = Math.toRadians(i * 5);
                        int inicioX = (int) (Math.cos(angulo) * (i * 20));
                        int inicioY = (int) (Math.sin(angulo) * (i * 20));
                        int finX = getWidth() - inicioX;
                        int finY = getHeight() - inicioY;
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(inicioX, inicioY, finX, finY);
                    }
                }
            };
            panel.setLayout(null);

            JLabel tituloDificil = new JLabel("Nivel Difícil", SwingConstants.CENTER);
            tituloDificil.setFont(new Font("Arial", Font.BOLD, 40));
            tituloDificil.setForeground(new Color(0, 255, 255));
            tituloDificil.setBounds(570, 20, 800, 50);
            panel.add(tituloDificil);

            cargarTextoObjetivoDificil();

            JTextArea areaTextoDificil = new JTextArea(textoObjetivoDificil);
            areaTextoDificil.setFont(new Font("Arial", Font.PLAIN, 20));
            areaTextoDificil.setForeground(Color.WHITE);
            areaTextoDificil.setBackground(new Color(50, 50, 50));
            areaTextoDificil.setEditable(false);
            areaTextoDificil.setWrapStyleWord(true);
            areaTextoDificil.setLineWrap(true);
            areaTextoDificil.setFocusable(false);
            areaTextoDificil.setCaretColor(Color.WHITE);
            areaTextoDificil.setBounds(150, 100, marcoDificil.getWidth() - 300, 150);
            
            // Scroll vertical para el texto objetivo
            JScrollPane scrollObjetivoDificil = new JScrollPane(areaTextoDificil);
            scrollObjetivoDificil.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollObjetivoDificil.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollObjetivoDificil.setBounds(150, 100, marcoDificil.getWidth() - 300, 150); // Mismas coordenadas que el JTextArea
            panel.add(scrollObjetivoDificil);  // Reemplaza panel.add(areaTextoFacil);

            JPanel panelContadorDificil = new JPanel();
            panelContadorDificil.setLayout(new BorderLayout());
            panelContadorDificil.setBounds(marcoDificil.getWidth() / 2 - 60, 260, 120, 40);
            panelContadorDificil.setBackground(new Color(50, 50, 50));
            panelContadorDificil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaContadorDificil = new JLabel("3:00", SwingConstants.CENTER);
            etiquetaContadorDificil.setFont(new Font("Arial", Font.BOLD, 20));
            etiquetaContadorDificil.setForeground(new Color(0, 255, 255));
            panelContadorDificil.add(etiquetaContadorDificil, BorderLayout.CENTER);
            panel.add(panelContadorDificil);

            JPanel panelPuntajeDificil = new JPanel();
            panelPuntajeDificil.setLayout(new BorderLayout());
            panelPuntajeDificil.setBounds(marcoDificil.getWidth() / 2 - 240, 260, 120, 40);
            panelPuntajeDificil.setBackground(new Color(50, 50, 50));
            panelPuntajeDificil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaPuntajeDificil = new JLabel("0 Puntos", SwingConstants.CENTER);
            etiquetaPuntajeDificil.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaPuntajeDificil.setForeground(new Color(0, 255, 255));
            panelPuntajeDificil.add(etiquetaPuntajeDificil, BorderLayout.CENTER);
            panel.add(panelPuntajeDificil);
            
            JPanel panelErroresDificil = new JPanel();
            panelErroresDificil.setLayout(new BorderLayout());
            panelErroresDificil.setBounds(marcoDificil.getWidth() / 2 - 400, 260, 120, 40);
            panelErroresDificil.setBackground(new Color(50, 50, 50));
            panelErroresDificil.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

            etiquetaErroresDificil = new JLabel("Errores: 0/3", SwingConstants.CENTER);
            etiquetaErroresDificil.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaErroresDificil.setForeground(Color.RED);
            panelErroresDificil.add(etiquetaErroresDificil, BorderLayout.CENTER);
            panel.add(panelErroresDificil);
            
            JPanel panelPPMDificil = new JPanel();
            panelPPMDificil.setLayout(new BorderLayout());
            panelPPMDificil.setBounds(marcoDificil.getWidth() / 2 + 100, 260, 120, 40);  
            panelPPMDificil.setBackground(new Color(50, 50, 50));
            panelPPMDificil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaPPM = new JLabel("0 PPM", SwingConstants.CENTER);
            etiquetaPPM.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaPPM.setForeground(new Color(0, 255, 255));
            panelPPMDificil.add(etiquetaPPM, BorderLayout.CENTER);
            panel.add(panelPPMDificil);
            
            temporizadorDificil = new Timer(1000, e -> {
                if (tiempoRestanteDificil > 0) {
                	tiempoRestanteDificil--;
                    int minutos = tiempoRestanteDificil / 60;
                    int segundos = tiempoRestanteDificil % 60;
                    etiquetaContadorDificil.setText(String.format("%d:%02d", minutos, segundos));
                    
                    // Calcular PPM basado en tiempo transcurrido real
                    long tiempoTranscurridoMs = System.currentTimeMillis() - tiempoInicioDificil;
                    if (tiempoTranscurridoMs > 0) {
                        double minutosTranscurridos = tiempoTranscurridoMs / 60000.0;
                        int ppmCalculado = (int) (totalPulsacionesDificil / minutosTranscurridos);
                        etiquetaPPM.setText(ppmCalculado + " PPM");
                    }
                } else {
                	temporizadorDificil.stop();
                    JOptionPane.showMessageDialog(marcoDificil, "¡El tiempo se ha agotado!");
                }
            });

            
            areaTextoEscribirDificil = new JTextArea();
            areaTextoEscribirDificil.setFont(new Font("Arial", Font.PLAIN, 20));
            areaTextoEscribirDificil.setForeground(Color.WHITE);
            areaTextoEscribirDificil.setBackground(new Color(50, 50, 50));
            areaTextoEscribirDificil.setWrapStyleWord(true);
            areaTextoEscribirDificil.setLineWrap(true);
            areaTextoEscribirDificil.setCaretColor(Color.WHITE);
            areaTextoEscribirDificil.setBounds(150, 310, marcoDificil.getWidth() - 300, 150);
            areaTextoEscribirDificil.setComponentPopupMenu(null);  
            InputMap inputMap = areaTextoEscribirDificil.getInputMap();
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "none");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "none");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "none");
            ActionMap actionMap = areaTextoEscribirDificil.getActionMap();
            actionMap.put("copy", null);
            actionMap.put("paste", null);
            actionMap.put("cut", null);
            
            // Bloquear clic derecho manualmente
            areaTextoEscribirDificil.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) e.consume();
                }
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) e.consume();
                }
            });
            
            // Bloquear teclas de borrado (Backspace, Delete, Ctrl+X)
            areaTextoEscribirDificil.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    boolean isBackspace = keyCode == KeyEvent.VK_BACK_SPACE;
                    boolean isDelete = keyCode == KeyEvent.VK_DELETE;
                    boolean isCut = (e.isControlDown() && keyCode == KeyEvent.VK_X);

                    if (isBackspace || isDelete || isCut) {
                        e.consume(); // Ignorar la tecla
                    }
                }
            });
                     
            // Scroll vertical para el texto del usuario
            JScrollPane scrollUsuarioDificil = new JScrollPane(areaTextoEscribirDificil);
            scrollUsuarioDificil.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollUsuarioDificil.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollUsuarioDificil.setBounds(150, 310, marcoDificil.getWidth() - 300, 150); // Mismas coordenadas que el JTextArea
            panel.add(scrollUsuarioDificil);  // Reemplaza panel.add(areaTextoEscribirFacil);
            
            panelTeclado = new JPanel();
            panelTeclado.setLayout(new GridLayout(3, 11, 5, 5));
            panelTeclado.setBounds(150, marcoDificil.getHeight() - 250, marcoDificil.getWidth() - 300, 120);
            panelTeclado.setBackground(new Color(30, 30, 30));

            botonesTeclado = new HashMap<>();

            String[] teclasLinea1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
            String[] teclasLinea2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"};
            String[] teclasLinea3 = {"Z", "X", "C", "V", "B", "N", "M", ",", ".", " "};

            for (String tecla : teclasLinea1) {
                JButton boton = crearBoton(tecla);
                botonesTeclado.put(tecla, boton);
                panelTeclado.add(boton);
            }

            for (String tecla : teclasLinea2) {
                JButton boton = crearBoton(tecla);
                botonesTeclado.put(tecla, boton);
                panelTeclado.add(boton);
            }

            for (String tecla : teclasLinea3) {
                JButton boton = crearBoton(tecla);
                botonesTeclado.put(tecla, boton);
                panelTeclado.add(boton);
            }

            panel.add(panelTeclado);

           
            JButton botonSalirDificil = new JButton("Salir");
            botonSalirDificil.setBackground(new Color(0, 255, 255));
            botonSalirDificil.setForeground(Color.BLACK);
            botonSalirDificil.setFont(new Font("Arial", Font.BOLD, 18));
            botonSalirDificil.setPreferredSize(new Dimension(160, 40));
            botonSalirDificil.setBounds(100, marcoDificil.getHeight() - 100, 160, 50);
            botonSalirDificil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	marcoDificil.dispose();
                    new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                }
            });

            
         // En el método mostrarNivelFacil, donde se crea el botón:
            JButton botonGuardarDificil = new JButton("Guardar");
            botonGuardarDificil.setBackground(new Color(0, 255, 255));
            botonGuardarDificil.setForeground(Color.BLACK);
            botonGuardarDificil.setFont(new Font("Arial", Font.BOLD, 18));
            botonGuardarDificil.setPreferredSize(new Dimension(160, 40));
            botonGuardarDificil.setBounds(marcoDificil.getWidth() - 260, marcoDificil.getHeight() - 100, 160, 50);
            botonGuardarDificil.setEnabled(false); // Deshabilitado inicialmente
            botonGuardarDificil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Calcular tiempo transcurrido y PPM
                    int tiempoTranscurrido = 180 - tiempoRestanteDificil;
                    int ppm = Integer.parseInt(etiquetaPPM.getText().replace(" PPM", ""));
                    
                    // Llamar al método con los parámetros correctos
                    guardarEstadisticasDificil(
                        nombreUsuario, 
                        "Difícil", 
                        puntajeDificil, 
                        erroresDificil, 
                        tiempoTranscurrido + " seg", 
                        ppm
                    );
                    
                    marcoDificil.dispose();
                    new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                }
            });

            areaTextoEscribirDificil.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    totalPulsacionesDificil += e.getLength();  
                    iniciarContadorSiNecesarioDificil();
                    actualizarColoresTecladoDificil();
                    
                    String textoUsuario = areaTextoEscribirDificil.getText();
                    int longitud = textoUsuario.length();

                    // Habilitar botón al alcanzar 200 caracteres (con o sin punto)
                    if (longitud >= 1000) {
                        botonGuardarDificil.setEnabled(true);
                    }

                    // Verificar si el texto tiene 200 caracteres y termina en punto
                    if (longitud == 1000 && textoUsuario.charAt(999) == '.') {
                        temporizadorDificil.stop(); // Detener el temporizador
                        JOptionPane.showMessageDialog(
                            marcoDificil, 
                            "¡Nivel Completado! Felicidades.\nHaz clic en Guardar para registrar tus estadísticas.", 
                            "Éxito", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {} 

                @Override
                public void changedUpdate(DocumentEvent e) {}
            });

            panel.add(botonSalirDificil);
            panel.add(botonGuardarDificil);
            marcoDificil.add(panel);
            marcoDificil.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cargarTextoObjetivoDificil() {
        try {
            File archivoTexto = new File("src/textos.txt");
            Scanner lector = new Scanner(archivoTexto);
            boolean nivelDificil = false;
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.equalsIgnoreCase("[NivelDificil]")) { 
                    nivelDificil = true;
                    continue;
                }
                if (nivelDificil) {
                    if (linea.contains("[NivelFacil]")) break;
                    textoObjetivoDificil += linea + " "; // Mantener texto original
                }
            }
            lector.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(60, 60));
        return boton;
    }

    private void iniciarContadorSiNecesarioDificil() {
        if (!contadorIniciadoDificil) {
        	temporizadorDificil.start();
        	tiempoInicioDificil = System.currentTimeMillis(); // Guardar el tiempo de inicio
        	contadorIniciadoDificil = true;
        }
    }

    private void actualizarPPMDificil() {
        if (tiempoInicioDificil == 0) return;

        long segundosTranscurridosDificil = (System.currentTimeMillis() - tiempoInicioDificil) / 1000;
        if (segundosTranscurridosDificil == 0) return;

        int ppm = (int) ((totalPulsacionesDificil / (double) segundosTranscurridosDificil) * 60);
        etiquetaPPM.setText(String.format("%d PPM", ppm));
    }
    
    private void actualizarColoresTecladoDificil() {
    	if (erroresDificil >= 3) return;  
    	
    	String textoUsuario = areaTextoEscribirDificil.getText();       
    	int longitud = textoUsuario.length();
        
        // Reiniciar colores después de un breve retraso
        Timer resetearTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton boton : botonesTeclado.values()) {
                    boton.setBackground(new Color(50, 50, 50));
                }
            }
        });
        resetearTimer.setRepeats(false);
        
        if (longitud > 0) {
            char caracterUsuario = textoUsuario.charAt(longitud - 1);
            char caracterObjetivo = ' ';
            
            if (longitud <= textoObjetivoDificil.length()) {
                caracterObjetivo = textoObjetivoDificil.charAt(longitud - 1);
            }

            if (Character.toLowerCase(caracterUsuario) == Character.toLowerCase(caracterObjetivo)) {
            	puntajeDificil += 10;
            } else {
            	puntajeDificil -= 50;
            	erroresDificil++;
            }
            
            etiquetaPuntajeDificil.setText(String.format("%d Puntos", puntajeDificil));
            etiquetaErroresDificil.setText(String.format("Errores: %d/3", erroresDificil));
        
            if (erroresDificil >= 3) {  
            	temporizadorDificil.stop();
            	areaTextoEscribirDificil.setEnabled(false);
                JOptionPane.showMessageDialog(marcoDificil, "¡Has superado el límite de 3 errores!");
                marcoDificil.dispose();
                new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                return;  
            }
        }

        // Comparar cada carácter correctamente
        if (longitud > 0 && longitud <= textoObjetivoDificil.length()) {
            char caracterUsuario = textoUsuario.charAt(longitud - 1);
            char caracterObjetivo = textoObjetivoDificil.charAt(longitud - 1); // Respeta mayúsculas/minúsculas original
            String teclaObjetivo = String.valueOf(caracterObjetivo).toUpperCase(); // Tecla en mayúsculas (como está en el teclado)
            String teclaUsuario = String.valueOf(caracterUsuario).toUpperCase(); // Tecla presionada en mayúsculas

            JButton botonObjetivo = botonesTeclado.get(teclaObjetivo);
            JButton botonUsuario = botonesTeclado.get(teclaUsuario);

            // Marcar la tecla del usuario como roja si no coincide
            if (botonUsuario != null) {
                boolean esCorrecto = (caracterUsuario == caracterObjetivo);
                botonUsuario.setBackground(esCorrecto ? Color.GREEN : Color.RED);
                resetearTimer.start();
            }

            // Manejo especial para la barra espaciadora
            if (caracterUsuario == ' ' && caracterObjetivo == ' ') {
                JButton botonEspacio = botonesTeclado.get(" ");
                if (botonEspacio != null) {
                    botonEspacio.setBackground(Color.GREEN);
                    resetearTimer.start();
                }
            }
        }
    }
    
    public void guardarEstadisticasDificil(String nombreUsuario, String nivel, int puntos, int errores, String tiempo, int ppm) {
        try (FileWriter fw = new FileWriter("src/estadisticas.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            // Añadir System.lineSeparator() para garantizar el salto de línea
            String estadistica = String.format(
                "%s|Nivel: %s|Puntos: %d|Errores: %d|Tiempo: %s|PPM: %d" + System.lineSeparator(), 
                nombreUsuario, nivel, puntos, errores, tiempo, ppm
            );
            
            pw.print(estadistica); // Usar print en lugar de println para evitar duplicar saltos
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(marcoDificil, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
    