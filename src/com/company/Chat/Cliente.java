package com.company.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente extends JFrame {

    /**
     * La clase cliente es un socket que puede enviar y recibir mensajes
     * Dispone de interfaz grafica donde visualizaremos los mensajes
     * El propio panel sera el encargado de crear nuevos usuarios, la idea es
     * simular la creacion de un grupo de whatsapp donde el administrador va añadiendo a personas
     * Esta clase necesita implementar un hilo de ejecucion para que este continuamente alerta de recibir
     * mensajes, podríamos haber extendido de Thread directamente,
     * pero para ver nuevas formas de implementar hilos, crearemos el hilo sobre el propio metodo [recibirMensaje()]
     */
    // --- ATRIBUTOS GRAFICOS ---//
    private String[][] coloresPaneles = {{"#4DEA0D","#50B129"},{"#E0BBF2","#911EC9"},{"#F48686","#EB2424"}}; //verde, morado rojo
    private String[] coloresTexto = {"green", "purple", "red"};
    private JPanel panelPrincipal;
    private JTextField areaTexto;
    private JButton btnEnviar;
    private JScrollPane myScrollPane;
    public JLabel etiquetaMensaje;
    private JPanel panelInterno;
    private JMenuBar mb;
    private JMenu menu1;
    private JMenuItem mi1,mi2,mi3;

    // --- ATRIBUTOS SOCKET--- //
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //USUARIO
    private static AtomicInteger idusuario = new AtomicInteger(0);
    String nombreUsuario;
    private int contadorId;


    public Cliente(Socket socket, String nombreUsuario){
       // contadorId = idusuario.getAndIncrement();
        contadorId = 1;
        if (idusuario.get() == 3){
            idusuario.set(0);
        }
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombreUsuario = nombreUsuario;
        } catch (IOException e) {
            cerrarSocket(this.socket, bufferedReader, bufferedWriter);
        }
        initWindow();
        add(panelPrincipal);
        setVisible(true);
        setTitle(nombreUsuario);
        recibirMensaje();
    }

    public void initWindow(){
        //BASICO
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        //Modificamos colores panel
        panelPrincipal.setBackground(Color.decode(coloresPaneles[contadorId][0]));
        panelInterno.setBackground(Color.decode(coloresPaneles[contadorId][1]));
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areaTexto.getText().length() == 0) return;
                enviarMensajeServidor();
            }
        });
    }

    public void enviarMensajeServidor() {
        try {
            String mensajePropio;
            String mensaje = areaTexto.getText();
            areaTexto.setText("");
            mensajePropio = "<html><DIV align='right' width='550'>"
                    + "<html><FONT COLOR='" + coloresTexto[2] + "'>"
                    + "<html><P>" + mensaje + "</FONT></DIV>";
            etiquetaMensaje.setText(etiquetaMensaje.getText() + mensajePropio);

            mensaje = "<html><FONT COLOR='" + coloresTexto[2] + "'>"
                    + "<html>" + nombreUsuario + ": "
                    + mensaje + "</FONT><br/>";
            bufferedWriter.write(mensaje);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            areaTexto.setText("");
        } catch (IOException ex) {
            cerrarSocket(socket, bufferedReader, bufferedWriter);
        }
    }

    public void enviarPrimerMensaje(){
        try {
            bufferedWriter.write(nombreUsuario);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            cerrarSocket(socket, bufferedReader, bufferedWriter);
        }
    }

    public void recibirMensaje(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeAlGrupo;
                while (socket.isConnected()){
                    try {
                        mensajeAlGrupo = bufferedReader.readLine();
                        System.out.println(mensajeAlGrupo + nombreUsuario);
                        etiquetaMensaje.setText(etiquetaMensaje.getText() + mensajeAlGrupo);
                    } catch (IOException e) {
                        cerrarSocket(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }


    public void cerrarSocket(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

