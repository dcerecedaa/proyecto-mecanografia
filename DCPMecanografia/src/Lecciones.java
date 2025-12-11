import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Lecciones {

    public static String cargarTextoNivel(String nivel) {
        try {

            File archivo = new File("src/textos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            boolean encontrado = false;
            StringBuilder texto = new StringBuilder();

            while ((linea = reader.readLine()) != null) {

                if (linea.equals("[" + nivel + "]")) {
                    encontrado = true;
                } else if (encontrado && linea.trim().isEmpty()) {
                    break;  
                } else if (encontrado) {
                   
                    texto.append(linea).append("\n");
                }
            }
            reader.close();
            return texto.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";  
        }
    }


    private static boolean esTextoValido(String texto, String nivel) {
        
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            JOptionPane.showMessageDialog(null, "Pautas incorrectas.");
            return false;  
        }

       
        if (nivel.equals("Facil") && texto.length() > 300) {
            JOptionPane.showMessageDialog(null, "Pautas incorrectas.");
            return false;  
        } else if (nivel.equals("Dificil") && texto.length() > 1000) {
            JOptionPane.showMessageDialog(null, "Pautas incorrectas.");
            return false;  
        }

        return true;  
    }

    public static void guardarTextoNivel(String nivel, String nuevoTexto) {
    
        if (!esTextoValido(nuevoTexto, nivel)) {
            return;  
        }

        try {
           
            File archivo = new File("src/textos.txt");
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            StringBuilder contenidoFinal = new StringBuilder();
            String linea;
            boolean encontrado = false;
            boolean nivelEditado = false;

            while ((linea = reader.readLine()) != null) {
                if (linea.equals("[" + nivel + "]")) {
                    
                    encontrado = true;
                    contenidoFinal.append(linea).append("\n");  
                    contenidoFinal.append(nuevoTexto).append("\n"); 
                    nivelEditado = true;
                } else if (encontrado && linea.trim().isEmpty()) {
                    
                    contenidoFinal.append(linea); 
                    encontrado = false;
                } else if (!encontrado) {
                 
                    contenidoFinal.append(linea).append("\n");
                }
            }
            reader.close();

           
            if (!nivelEditado) {
                contenidoFinal.append("[").append(nivel).append("]\n").append(nuevoTexto).append("\n");
            }

          
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(contenidoFinal.toString());
            writer.close();
            
            
            JOptionPane.showMessageDialog(null, "Texto guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void mostrarPanelEdicion(String nivelSeleccionado) {
    
    
        JFrame editarFrame = new JFrame("Editar Texto - " + nivelSeleccionado);
        editarFrame.setSize(600, 400);  
        editarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        editarFrame.setLocationRelativeTo(null);  

       
        ImageIcon icono = new ImageIcon("src/fondo.png");
        editarFrame.setIconImage(icono.getImage());  

       
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));  
        textArea.setLineWrap(true);  
        textArea.setWrapStyleWord(true);  
        JScrollPane scrollPane = new JScrollPane(textArea);  
        scrollPane.setBounds(50, 50, 500, 200);  

       
        String textoActual = cargarTextoNivel(nivelSeleccionado);
        textArea.setText(textoActual);  

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null); 
        // Botón de guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));  
        btnGuardar.setBounds(100, 300, 150, 40);  
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                guardarTextoNivel(nivelSeleccionado, textArea.getText());
                editarFrame.dispose();  
            }
        });
        panel.add(btnGuardar);  

        // Botón de salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));  
        btnSalir.setBounds(350, 300, 150, 40);  
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarFrame.dispose(); 
            }
        });
        panel.add(btnSalir); 

  
        panel.add(scrollPane);
        editarFrame.add(panel);  
        editarFrame.setVisible(true);  
    }
}
