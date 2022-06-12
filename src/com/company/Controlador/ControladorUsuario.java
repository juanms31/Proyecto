package com.company.Controlador;

import com.company.BaseDatos.CRUDSettings;
import com.company.BaseDatos.CRUDUsuario;
import com.company.Entidades.Settings;
import com.company.Entidades.Usuario;
import com.company.Vistas.ViewInicio;
import com.company.Vistas.ViewUsuario;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class ControladorUsuario {

    //Constructor
    public ControladorUsuario(Usuario usuario) {
        crudUsuario = new CRUDUsuario();
        crudSettings = new CRUDSettings();

        settingsArrayList = crudSettings.getAll();
        Settings settings = getSettings(usuario);
        viewInicio = new ViewInicio(this, usuario, settings);
    }

    public ControladorUsuario() {
        crudUsuario = new CRUDUsuario();
    }


    //region CRUD

    public ArrayList<Usuario> getUsers(){
        return crudUsuario.getAll();
    }

    public boolean createUsuario(Usuario usuario){
        try {
            int idUsuario = crudUsuario.createUsuario(usuario);
            usuario.setId(idUsuario);

            Settings settings = new Settings();

            settings.setId_usuario(idUsuario);
            settings.setTipoFondo("Imagen");
            settings.setUrlFondo("src/com/company/Images/Fondos/fondo0.jpg");

            int idSettings = crudSettings.createSettings(settings);

            settings.setId(idSettings);

            crudSettings.updateSettings(settings);

            ShowMessage("CORRECTO", "Usuario " + usuario.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ShowErrorMessage("ERROR", "No se ha podido agregar el registro");
            return false;
        }

    }

    private Settings getSettings(Usuario usuario) {
        for (Settings settings : settingsArrayList){
            if(settings.getId_usuario() == usuario.getId()){return settings;}
        }
        return new Settings();
    }

    public void updateUsuario(Usuario usuario, Settings settings) {
        if (crudUsuario.updateUsuario(usuario)){
            if(crudSettings.updateSettings(settings)){
                try {
                    Thread.sleep(100);
                    ShowMessage("Correcto", "Actualizando configuracion.");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion


    //region Variables
    private CRUDUsuario crudUsuario;
    private CRUDSettings crudSettings = new CRUDSettings();
    private ViewUsuario viewUsuario;
    private ViewInicio viewInicio;

    private ArrayList<Settings> settingsArrayList;


    //endregion

}
