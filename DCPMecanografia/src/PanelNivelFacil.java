import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PanelNivelFacil {

    private String textoObjetivoFacil = "";  // Texto del nivel fácil
    private JTextArea areaTextoEscribirFacil;  // Área de texto para escribir
    private int tiempoRestanteFacil = 240;  // 4 minutos (240 segundos)
    private JLabel etiquetaContadorFacil, etiquetaPPM;  // Etiqueta del contador
    private Timer temporizadorFacil;  // Temporizador
    private boolean contadorIniciadoFacil = false;  // Estado del contador
    private long tiempoInicioFacil = 0;
    private int totalPulsacionesFacil = 0;  
    private JPanel panelTeclado;  // Panel del teclado
    private Map<String, JButton> botonesTeclado;  // Botones del teclado
    private int puntajeFacil = 0; 
    private JLabel etiquetaPuntajeFacil;  
    private int erroresFacil = 0; 
    private JLabel etiquetaErroresFacil;
    private JFrame marcoFacil;  
    private String nombreUsuario;
    
    public void mostrarNivelFacil(String nombreUsuario) {
    	this.nombreUsuario = nombreUsuario;    	
    	try {
    		marcoFacil = new JFrame("Nivel Fácil");
    		marcoFacil.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    		marcoFacil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		marcoFacil.setUndecorated(true);
    		marcoFacil.setLocationRelativeTo(null);

    		// Establecer el icono de la ventana
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));  
            marcoFacil.setIconImage(icono.getImage());  
    		
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

            JLabel tituloFacil = new JLabel("Nivel Fácil", SwingConstants.CENTER);
            tituloFacil.setFont(new Font("Arial", Font.BOLD, 40));
            tituloFacil.setForeground(new Color(0, 255, 255));
            tituloFacil.setBounds(570, 20, 800, 50);
            panel.add(tituloFacil);

            cargarTextoObjetivoFacil();

            JTextArea areaTextoFacil = new JTextArea(textoObjetivoFacil);
            areaTextoFacil.setFont(new Font("Arial", Font.PLAIN, 20));
            areaTextoFacil.setForeground(Color.WHITE);
            areaTextoFacil.setBackground(new Color(50, 50, 50));
            areaTextoFacil.setEditable(false);
            areaTextoFacil.setWrapStyleWord(true);
            areaTextoFacil.setLineWrap(true);
            areaTextoFacil.setFocusable(false);
            areaTextoFacil.setCaretColor(Color.WHITE);
            areaTextoFacil.setBounds(150, 100, marcoFacil.getWidth() - 300, 150);
            
            // Scroll vertical para el texto objetivo
            JScrollPane scrollObjetivoFacil = new JScrollPane(areaTextoFacil);
            scrollObjetivoFacil.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollObjetivoFacil.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollObjetivoFacil.setBounds(150, 100, marcoFacil.getWidth() - 300, 150); 
            panel.add(scrollObjetivoFacil);  

            JPanel panelContadorFacil = new JPanel();
            panelContadorFacil.setLayout(new BorderLayout());
            panelContadorFacil.setBounds(marcoFacil.getWidth() / 2 - 60, 260, 120, 40);
            panelContadorFacil.setBackground(new Color(50, 50, 50));
            panelContadorFacil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaContadorFacil = new JLabel("4:00", SwingConstants.CENTER);
            etiquetaContadorFacil.setFont(new Font("Arial", Font.BOLD, 20));
            etiquetaContadorFacil.setForeground(new Color(0, 255, 255));
            panelContadorFacil.add(etiquetaContadorFacil, BorderLayout.CENTER);
            panel.add(panelContadorFacil);

            JPanel panelPuntajeFacil = new JPanel();
            panelPuntajeFacil.setLayout(new BorderLayout());
            panelPuntajeFacil.setBounds(marcoFacil.getWidth() / 2 - 240, 260, 120, 40);
            panelPuntajeFacil.setBackground(new Color(50, 50, 50));
            panelPuntajeFacil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaPuntajeFacil = new JLabel("0 Puntos", SwingConstants.CENTER);
            etiquetaPuntajeFacil.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaPuntajeFacil.setForeground(new Color(0, 255, 255));
            panelPuntajeFacil.add(etiquetaPuntajeFacil, BorderLayout.CENTER);
            panel.add(panelPuntajeFacil);
            
            JPanel panelErroresFacil = new JPanel();
            panelErroresFacil.setLayout(new BorderLayout());
            panelErroresFacil.setBounds(marcoFacil.getWidth() / 2 - 400, 260, 120, 40);
            panelErroresFacil.setBackground(new Color(50, 50, 50));
            panelErroresFacil.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

            etiquetaErroresFacil = new JLabel("Errores: 0/5", SwingConstants.CENTER);
            etiquetaErroresFacil.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaErroresFacil.setForeground(Color.RED);
            panelErroresFacil.add(etiquetaErroresFacil, BorderLayout.CENTER);
            panel.add(panelErroresFacil);
            
            JPanel panelPPMFacil = new JPanel();
            panelPPMFacil.setLayout(new BorderLayout());
            panelPPMFacil.setBounds(marcoFacil.getWidth() / 2 + 100, 260, 120, 40);  
            panelPPMFacil.setBackground(new Color(50, 50, 50));
            panelPPMFacil.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

            etiquetaPPM = new JLabel("0 PPM", SwingConstants.CENTER);
            etiquetaPPM.setFont(new Font("Arial", Font.BOLD, 16));
            etiquetaPPM.setForeground(new Color(0, 255, 255));
            panelPPMFacil.add(etiquetaPPM, BorderLayout.CENTER);
            panel.add(panelPPMFacil);
            
            temporizadorFacil = new Timer(1000, e -> {
                if (tiempoRestanteFacil > 0) {
                    tiempoRestanteFacil--;
                    int minutos = tiempoRestanteFacil / 60;
                    int segundos = tiempoRestanteFacil % 60;
                    etiquetaContadorFacil.setText(String.format("%d:%02d", minutos, segundos));
                    
                    long tiempoTranscurridoMs = System.currentTimeMillis() - tiempoInicioFacil;
                    if (tiempoTranscurridoMs > 0) {
                        double minutosTranscurridos = tiempoTranscurridoMs / 60000.0;
                        int ppmCalculado = (int) (totalPulsacionesFacil / minutosTranscurridos);
                        etiquetaPPM.setText(ppmCalculado + " PPM");
                    }
                } else {
                    temporizadorFacil.stop();
                    JOptionPane.showMessageDialog(marcoFacil, "¡El tiempo se ha agotado!");
                }
            });

            
            areaTextoEscribirFacil = new JTextArea();
            areaTextoEscribirFacil.setFont(new Font("Arial", Font.PLAIN, 20));
            areaTextoEscribirFacil.setForeground(Color.WHITE);
            areaTextoEscribirFacil.setBackground(new Color(50, 50, 50));
            areaTextoEscribirFacil.setWrapStyleWord(true);
            areaTextoEscribirFacil.setLineWrap(true);
            areaTextoEscribirFacil.setCaretColor(Color.WHITE);
            areaTextoEscribirFacil.setBounds(150, 310, marcoFacil.getWidth() - 300, 150);
            areaTextoEscribirFacil.setComponentPopupMenu(null);  
            InputMap inputMap = areaTextoEscribirFacil.getInputMap();
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "none");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "none");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "none");
            ActionMap actionMap = areaTextoEscribirFacil.getActionMap();
            actionMap.put("copy", null);
            actionMap.put("paste", null);
            actionMap.put("cut", null);
            
            areaTextoEscribirFacil.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) e.consume();
                }
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) e.consume();
                }
            });
            
            areaTextoEscribirFacil.addKeyListener(new KeyAdapter() {
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
                     
            JScrollPane scrollUsuarioFacil = new JScrollPane(areaTextoEscribirFacil);
            scrollUsuarioFacil.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollUsuarioFacil.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollUsuarioFacil.setBounds(150, 310, marcoFacil.getWidth() - 300, 150); 
            panel.add(scrollUsuarioFacil); 
            
            panelTeclado = new JPanel();
            panelTeclado.setLayout(new GridLayout(3, 11, 5, 5));
            panelTeclado.setBounds(150, marcoFacil.getHeight() - 250, marcoFacil.getWidth() - 300, 120);
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

           
            JButton botonSalirFacil = new JButton("Salir");
            botonSalirFacil.setBackground(new Color(0, 255, 255));
            botonSalirFacil.setForeground(Color.BLACK);
            botonSalirFacil.setFont(new Font("Arial", Font.BOLD, 18));
            botonSalirFacil.setPreferredSize(new Dimension(160, 40));
            botonSalirFacil.setBounds(100, marcoFacil.getHeight() - 100, 160, 50);
            botonSalirFacil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	marcoFacil.dispose();
                    new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                }
            });

          
            JButton botonGuardarFacil = new JButton("Guardar");
            botonGuardarFacil.setBackground(new Color(0, 255, 255));
            botonGuardarFacil.setForeground(Color.BLACK);
            botonGuardarFacil.setFont(new Font("Arial", Font.BOLD, 18));
            botonGuardarFacil.setPreferredSize(new Dimension(160, 40));
            botonGuardarFacil.setBounds(marcoFacil.getWidth() - 260, marcoFacil.getHeight() - 100, 160, 50);
            botonGuardarFacil.setEnabled(false); 
            botonGuardarFacil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    int tiempoTranscurrido = 240 - tiempoRestanteFacil;
                    int ppm = Integer.parseInt(etiquetaPPM.getText().replace(" PPM", ""));
                    
                    guardarEstadisticasFacil(
                        nombreUsuario, 
                        "Fácil", 
                        puntajeFacil, 
                        erroresFacil, 
                        tiempoTranscurrido + " seg", 
                        ppm
                    );
                    
                    marcoFacil.dispose();
                    new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                }
            });

            areaTextoEscribirFacil.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    totalPulsacionesFacil += e.getLength();  
                    iniciarContadorSiNecesarioFacil();
                    actualizarColoresTecladoFacil();
                    
                    String textoUsuario = areaTextoEscribirFacil.getText();
                    int longitud = textoUsuario.length();

                    if (longitud >= 200) {
                        botonGuardarFacil.setEnabled(true);
                    }

                    if (longitud == 200 && textoUsuario.charAt(199) == '.') {
                        temporizadorFacil.stop(); // Detener el temporizador
                        JOptionPane.showMessageDialog(
                            marcoFacil, 
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

            panel.add(botonSalirFacil);
            panel.add(botonGuardarFacil);
            marcoFacil.add(panel);
            marcoFacil.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cargarTextoObjetivoFacil() {
        try {
            File archivoTexto = new File("src/textos.txt");
            Scanner lector = new Scanner(archivoTexto);
            boolean nivelFacil = false;
            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.equalsIgnoreCase("[NivelFacil]")) { 
                    nivelFacil = true;
                    continue;
                }
                if (nivelFacil) {
                    if (linea.contains("[NivelDificil]")) break;
                    textoObjetivoFacil += linea + " ";
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

    private void iniciarContadorSiNecesarioFacil() {
        if (!contadorIniciadoFacil) {
        	temporizadorFacil.start();
        	tiempoInicioFacil = System.currentTimeMillis();
            contadorIniciadoFacil = true;
        }
    }

    private void actualizarPPMFacil() {
        if (tiempoInicioFacil == 0) return;

        long segundosTranscurridosFacil = (System.currentTimeMillis() - tiempoInicioFacil) / 1000;
        if (segundosTranscurridosFacil == 0) return;

        int ppm = (int) ((totalPulsacionesFacil / (double) segundosTranscurridosFacil) * 60);
        etiquetaPPM.setText(String.format("%d PPM", ppm));
    }
    
    private void actualizarColoresTecladoFacil() {
    	if (erroresFacil >= 5) return;  
    	
    	String textoUsuario = areaTextoEscribirFacil.getText();       
    	int longitud = textoUsuario.length();
        
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
            
            if (longitud <= textoObjetivoFacil.length()) {
                caracterObjetivo = textoObjetivoFacil.charAt(longitud - 1);
            }

            if (Character.toLowerCase(caracterUsuario) == Character.toLowerCase(caracterObjetivo)) {
            	puntajeFacil += 10;
            } else {
            	puntajeFacil -= 50;
            	erroresFacil++;
            }
            
            etiquetaPuntajeFacil.setText(String.format("%d Puntos", puntajeFacil));
            etiquetaErroresFacil.setText(String.format("Errores: %d/5", erroresFacil));
        
            if (erroresFacil >= 5) {  
            	temporizadorFacil.stop();
                areaTextoEscribirFacil.setEnabled(false);
                JOptionPane.showMessageDialog(marcoFacil, "¡Has superado el límite de 5 errores!");
                marcoFacil.dispose();
                new PanelUsuario().mostrarPanelUsuario(nombreUsuario);
                return;  
            }
        }

        if (longitud > 0 && longitud <= textoObjetivoFacil.length()) {
            char caracterUsuario = textoUsuario.charAt(longitud - 1);
            char caracterObjetivo = textoObjetivoFacil.charAt(longitud - 1); 
            String teclaObjetivo = String.valueOf(caracterObjetivo).toUpperCase(); 
            String teclaUsuario = String.valueOf(caracterUsuario).toUpperCase(); 

            JButton botonObjetivo = botonesTeclado.get(teclaObjetivo);
            JButton botonUsuario = botonesTeclado.get(teclaUsuario);

            if (botonUsuario != null) {
                boolean esCorrecto = (caracterUsuario == caracterObjetivo);
                botonUsuario.setBackground(esCorrecto ? Color.GREEN : Color.RED);
                resetearTimer.start();
            }

            if (caracterUsuario == ' ' && caracterObjetivo == ' ') {
                JButton botonEspacio = botonesTeclado.get(" ");
                if (botonEspacio != null) {
                    botonEspacio.setBackground(Color.GREEN);
                    resetearTimer.start();
                }
            }
        }
    }
    
    public void guardarEstadisticasFacil(String nombreUsuario, String nivel, int puntos, int errores, String tiempo, int ppm) {
        try (FileWriter fw = new FileWriter("src/estadisticas.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            String estadistica = String.format(
                "%s|Nivel: %s|Puntos: %d|Errores: %d|Tiempo: %s|PPM: %d" + System.lineSeparator(), 
                nombreUsuario, nivel, puntos, errores, tiempo, ppm
            );
            
            pw.print(estadistica); 
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(marcoFacil, 
                    "Error al guardar: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
    }
}
    