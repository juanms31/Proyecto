package com.company;

import com.company.Vistas.ViewBaseDatos;
import com.company.Vistas.ViewInicio;
import com.company.Vistas.ViewLogin;
import com.company.Vistas.ViewWelcome;

import java.awt.*;
import java.io.IOException;
import java.util.logging.*;

public class Main {

    private static final Logger LOG_RAIZ = Logger.getLogger("com.company");
//    private static final Logger LOG_SUBNIVEL = Logger.getLogger("com.company.BaseDatos");

    //Log para el main
    private static final Logger LOGGER = Logger.getLogger("com.company.Main");

    public static void main(String[] args) {

        try {
            //Log en consola
            Handler consoleHandler = new ConsoleHandler();

            //Log en archivo
            Handler fileHandler = new FileHandler("./com.company.log", false);

            //Formato de los datos >> En nuestro caso texto plano
            //Se puede pasar a formato xml (ver en documentacion)
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);

            //Asignamos los manejadores al log raiz
            LOG_RAIZ.addHandler(consoleHandler);
            LOG_RAIZ.addHandler(fileHandler);

            consoleHandler.setLevel(Level.SEVERE);
            fileHandler.setLevel(Level.ALL);

            LOGGER.log(Level.INFO, "Main inicializado");

            LOGGER.log(Level.INFO, "LLamando a la vista principal");
            //ENTRADA AL PROGRAMA

           ViewWelcome welcome = new ViewWelcome();

           // ViewBaseDatos viewBaseDatos = new ViewBaseDatos();
//
//        ViewInicio viewInicio = new ViewInicio();
//          viewInicio.setVisible(true);
//            ViewLogin viewLogin = new ViewLogin();
            LOGGER.log(Level.INFO, "Vista principal inicializado con exito");

        } catch (HeadlessException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }


    }
}
