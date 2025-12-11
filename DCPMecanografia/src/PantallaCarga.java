import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class PantallaCarga {

    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PantallaCarga().mostrarPantallaCarga();
            } catch (Exception e) {
                mostrarErrorYSalir(null, "Error inicial: " + e.getMessage());
            }
        });
    }

    public void mostrarPantallaCarga() {
        frame = new JFrame("Pantalla de Carga");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/fondo.png"));
            frame.setIconImage(icono.getImage());
        } catch (Exception e) {
            mostrarErrorYSalir(frame, "Error cargando recursos: " + e.getMessage());
        }

        JLabel fondoImagen = new JLabel("");
        fondoImagen.setBounds(0, 0, 800, 600);
        fondoImagen.setIcon(new ImageIcon(getClass().getResource("/fondo.png")));
        frame.add(fondoImagen);

        frame.setUndecorated(true);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMaximum(100);
        progressBar.setForeground(new Color(50, 201, 197));
        progressBar.setPreferredSize(new Dimension(500, 30));

        frame.add(progressBar, BorderLayout.SOUTH);
        frame.setVisible(true);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            private int progresoActual = 0;
            
            @Override
            protected Void doInBackground() throws Exception {
                String[] archivos = {"src/usuarios.txt", "src/textos.txt", "src/estadisticas.txt"};
                
                // Verificar existencia de todos los archivos primero
                for (String archivo : archivos) {
                    File f = new File(archivo);
                    if (!f.exists()) {
                        throw new Exception("Archivo no encontrado: " + archivo);
                    }
                }

                int totalPasos = 100;
                int pasosPorArchivo = totalPasos / archivos.length;
                int pasosExtra = totalPasos % archivos.length;
                
                for (int i = 0; i < archivos.length; i++) {
                    String archivo = archivos[i];
                    int pasos = pasosPorArchivo + (i < pasosExtra ? 1 : 0);
                    
                    // Simular progreso
                    for (int j = 0; j < pasos; j++) {
                        progresoActual++;
                        publish(progresoActual);
                        Thread.sleep(60); // Ajustado para 6 segundos totales
                    }
                    
                    // Validar contenido del archivo
                    File f = new File(archivo);
                    if (archivo.equals("src/usuarios.txt")) {
                        validarUsuarios(f);
                    }
                    else if (archivo.equals("src/textos.txt")) {
                        validarTextos(f);
                    }
                    else if (archivo.equals("src/estadisticas.txt")) {
                        validarEstadisticas(f);
                    }
                }
                
                // Asegurar 100%
                while (progresoActual < 100) {
                    progresoActual++;
                    publish(progresoActual);
                    Thread.sleep(10);
                }
                
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> progresos) {
                progressBar.setValue(progresos.get(progresos.size() - 1));
            }

            @Override
            protected void done() {
                try {
                    get();
                    new PanelBienvenida().mostrarPanelBienvenida();
                    frame.dispose();
                } catch (InterruptedException | ExecutionException e) {
                    mostrarErrorYSalir(frame, e.getCause().getMessage());
                }
            }
        };
        
        worker.execute();
    }

    private void validarUsuarios(File archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        int contadorAdmins = 0;
        int contadorUsuarios = 0;
        boolean enAdministradores = false;
        boolean enUsuarios = false;
        
        // Nueva expresión regular: Solo letras (mayúsculas/minúsculas) y números
        String regexPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            if (linea.equals("[ADMINISTRADORES]")) {
                enAdministradores = true;
                enUsuarios = false;
                continue;
            }
            else if (linea.equals("[USUARIOS]")) {
                enUsuarios = true;
                enAdministradores = false;
                continue;
            }

            if (enAdministradores) {
                if (!linea.equals("a,a")) {
                    throw new Exception("Admin inválido: " + linea);
                }
                contadorAdmins++;
            }
            else if (enUsuarios) {
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    throw new Exception("Formato inválido: " + linea);
                }
                
                // Validar nombre de usuario (solo letras y números)
                if (!partes[0].matches("^[a-zA-Z0-9]+$")) {
                    throw new Exception("Nombre de usuario inválido: " + partes[0]);
                }
                
                // Validar contraseña
                String password = partes[1];
                if (!password.matches(regexPassword)) {
                    throw new Exception("Contraseña inválida para " + partes[0] + 
                        ". Requisitos:\n- 6+ caracteres\n- Al menos 1 mayúscula\n- Al menos 1 minúscula\n- Al menos 1 número\n- Solo letras y números");
                }
                
                contadorUsuarios++;
            }
        }
        br.close();

        if (contadorAdmins != 1) throw new Exception("Debe haber exactamente 1 admin (a,a)");
        if (contadorUsuarios < 3) throw new Exception("Debe haber mínimo 3 usuarios registrados");
    }

    private void validarEstadisticas(File archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+\\|Nivel: (Fácil|Difícil)\\|Puntos: \\d+\\|Errores: \\d+\\|Tiempo: \\d+ seg\\|PPM: \\d+$";

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;
            
            if (!Pattern.matches(regex, linea)) {
                throw new Exception("Formato estadísticas inválido: " + linea);
            }
            
            String[] partes = linea.split("\\|");
            if (partes.length != 6) {
                throw new Exception("Formato incorrecto en estadísticas: " + linea);
            }
        }
        br.close();
    }

    private void validarTextos(File archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        StringBuilder textoFacil = new StringBuilder();
        StringBuilder textoDificil = new StringBuilder();
        String linea;
        boolean enFacil = false;
        boolean enDificil = false;

        while ((linea = br.readLine()) != null) {
            String lineaLimpia = linea.trim();
            
            if (lineaLimpia.equalsIgnoreCase("[NivelFacil]")) {
                enFacil = true;
                enDificil = false;
                continue;
            } else if (lineaLimpia.equalsIgnoreCase("[NivelDificil]")) {
                enDificil = true;
                enFacil = false;
                continue;
            }

            if (lineaLimpia.isEmpty()) continue;

            if (enFacil) {
                textoFacil.append(linea);
            } else if (enDificil) {
                textoDificil.append(linea);
            }
        }
        br.close();

        String txtFacil = textoFacil.toString();
        String txtDificil = textoDificil.toString();
        
        if (txtFacil.length() != 200) {
            throw new Exception("Nivel fácil requiere 200 caracteres exactos (Actual: " + txtFacil.length() + ")");
        }
        
        if (txtDificil.length() != 1000) {
            throw new Exception("Nivel difícil requiere 1000 caracteres exactos (Actual: " + txtDificil.length() + ")");
        }

        String regexTexto = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ,.!¡¿?;:()\\s—–-]+$";
        
        if (!txtFacil.matches(regexTexto)) {
            throw new Exception("Caracteres inválidos en nivel fácil: " + txtFacil.replaceAll(regexTexto, ""));
        }
        
        if (!txtDificil.matches(regexTexto)) {
            throw new Exception("Caracteres inválidos en nivel difícil: " + txtDificil.replaceAll(regexTexto, ""));
        }
    }

    private static void mostrarErrorYSalir(JFrame frame, String mensaje) {
        JOptionPane.showMessageDialog(frame, 
            mensaje, 
            "Error crítico", 
            JOptionPane.ERROR_MESSAGE);
        
        if (frame != null) frame.dispose();
        System.exit(1);
    }
}