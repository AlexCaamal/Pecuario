/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Models.conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import view.main;

/**
 *
 * @author CCNAR
 */
public class ControladorLote implements ActionListener, KeyListener{
    
    main mn;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    LocalDateTime dia = LocalDateTime.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fecha = dia.format(formato);

    public ControladorLote(main mn) {
        this.mn = mn;
        this.mn.btn_AceptarLote.addActionListener(this);
        this.mn.btn_nuevo.addActionListener(this);
        this.mn.btn_editarLote.addActionListener(this);
        this.mn.btn_registrarLote.addActionListener(this);
        this.mn.txt_lote.addKeyListener(this);
        this.mn.txt_fecha.addKeyListener(this);
        this.mn.txt_HembrasIniciadas.addKeyListener(this);
        this.mn.txt_MachosIniciados.addKeyListener(this);
        this.mn.txt_HembrasAlojadas.addKeyListener(this);
        this.mn.txt_MachosAlojados.addKeyListener(this);
        this.mn.cbx_lotes.addActionListener(this);
        mn.btn_config.addActionListener(this);
        cargarLotes();
        cargarUltimos();
        verAdvertencias(false, true);
        botones(true, true, true, false, false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource() == mn.btn_AceptarLote){
          if(mn.txt_lote.getText().isEmpty()){
              JOptionPane.showMessageDialog(mn, "Insegre el Lote");
              mn.txt_lote.requestFocus(true);
          }
          
      }else if(e.getSource() == mn.btn_nuevo){
          if (mn.btn_nuevo.getText().equals("Nuevo")) {
              limpiar();
              buscarUltimoLote();
              verAdvertencias(true, true);
              botones(false, true, false, false, true);
              mn.btn_nuevo.setText("Cancelar");
          }else if(mn.btn_nuevo.getText().equals("Cancelar")){
              verAdvertencias(false, false);
              cargarUltimos();
              botones(true, true, true, false, false);
              mn.btn_nuevo.setText("Nuevo");
          }
          
      }else if(e.getSource() == mn.btn_editarLote){
          ActualizarLote();
      }else if(e.getSource() == mn.btn_registrarLote){
          if(verificarExiteLote()){
              registrarLote();
          }else{
              JOptionPane.showMessageDialog(mn, "El lote ya se encuentra Registrado");
          }
      }else if(e.getSource() == mn.cbx_lotes){
          cargarLotes();
          limpiar();
          cargarUltimos();
      }else if(e.getSource() == mn.btn_config){
          cargarUltimos();
          botones(true, true, true, false, false);
          mn.Lote.setSize(450, 450);
          mn.Lote.setLocationRelativeTo(mn);
          mn.Lote.setVisible(true);
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
     }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == mn.txt_lote || e.getSource() == mn.txt_fecha ||e.getSource() == mn.txt_HembrasIniciadas || 
           e.getSource() == mn.txt_MachosIniciados){
            if(mn.btn_nuevo.getText().equals("Nuevo")){
                botones(true, true, true, true, false);
            }else if(mn.btn_nuevo.getText().equals("Cancelar")){
                 botones(true, true, false, false, true);
            }
        }
    }
    
    public void verAdvertencias(boolean lb_advertencia1, boolean lb_advertencia2){
        mn.lb_advertencia1.setVisible(lb_advertencia1);
        mn.lb_advertencia2.setVisible(lb_advertencia2);
    }
    
    public void botones(boolean btn_AceptarLote,boolean btn_nuevo,boolean btn_eliminarLote,boolean btn_editarLote,boolean btn_registrarLote){
        this.mn.btn_AceptarLote.setVisible(btn_AceptarLote);
        this.mn.btn_nuevo.setVisible(btn_nuevo);
        this.mn.btn_editarLote.setVisible(btn_editarLote);
        this.mn.btn_registrarLote.setVisible(btn_registrarLote);
    }
    
    public void limpiar() {
        mn.txt_lote.setText("");
        try {
            Date fechaNacimiento = dateFormat.parse(fecha);
            mn.Calen_fechaNacimiento.setDate(fechaNacimiento);
        } catch (Exception ev) {
            System.out.println("error en jCalendar " + ev.getMessage());
        }
        mn.txt_HembrasIniciadas.setText("");
        mn.txt_MachosIniciados.setText("");
        mn.txt_HembrasAlojadas.setText("");
        mn.txt_MachosAlojados.setText("");
        mn.txt_HembrasIniciadas.requestFocus(true);
    }

    public void cargarLotes() {
        try {
            mn.cbx_lotes.removeAllItems();
            String estado = "Activo";
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            ps = con.prepareStatement("SELECT lote FROM lote WHERE Estado = ? ORDER BY lote desc");
            ps.setString(1, estado);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                String lote = rs.getString("lote");
                mn.cbx_lotes.addItem(lote);
                i++;
            }rs.close();con.close();
        } catch (Exception er) {
            System.err.println("Error en cbx: " + er.toString());
        }
    }
    
    public void cargarUltimos(){
        String lote = mn.cbx_lotes.getSelectedItem().toString();
        try {
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            ps = con.prepareStatement("SELECT id_lote, lote, FechaDeNacimiento, HembrasInicio, MachosInicio, HembrasAlojadas, MachosAlojados FROM lote WHERE lote = ?");
            ps.setString(1, lote);
            rs = ps.executeQuery();
            while (rs.next()) {
                mn.lb_idLote.setText(rs.getString("id_lote"));
                mn.txt_lote.setText(rs.getString("lote"));
                mn.lb_loteAnt.setText(rs.getString("lote"));
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = formato.parse((rs.getString("FechaDeNacimiento")));
                try {
                    mn.Calen_fechaNacimiento.setDate(fechaNacimiento);
                } catch (Exception ev) {
                    System.out.println("error en jCalendar " + ev.getMessage());
                }
                String fecha = dateFormat.format(mn.Calen_fechaNacimiento.getCalendar().getTime());
                mn.txt_HembrasIniciadas.setText(rs.getString("HembrasInicio"));
                mn.txt_MachosIniciados.setText(rs.getString("MachosInicio"));
                mn.txt_HembrasAlojadas.setText(rs.getString("HembrasAlojadas"));
                mn.txt_MachosAlojados.setText(rs.getString("MachosAlojados"));
            }rs.close();con.close();
        } catch (Exception er) {
            System.err.println("Error en cargarUltimoTXT: " + er.toString());
        }
    }
    
    public void buscarUltimoLote(){
        try {
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            ps = con.prepareStatement("SELECT MAX(lote) as lote FROM lote");
            rs = ps.executeQuery();
            while (rs.next()) {
                mn.txt_lote.setText(""+(rs.getInt("lote")+1));
            }rs.close();con.close();
        } catch (Exception er) {
            System.err.println("Error en buscarUltimoLote: " + er.toString());
        }
    }
    
    public void registrarLote(){
        if (mn.txt_lote.getText().isEmpty() || mn.txt_HembrasAlojadas.getText().isEmpty() 
                || mn.txt_MachosAlojados.getText().isEmpty()) {
              JOptionPane.showMessageDialog(mn, "Dijite el lote, Machos Alojados y Hembras Alojadas");
        } else {
            try {
                String lote = mn.txt_lote.getText();
                String fecha = dateFormat.format(mn.Calen_fechaNacimiento.getCalendar().getTime());
                String HembrasIniciadas = mn.txt_HembrasIniciadas.getText();
                int MachosIniciados =Integer.parseInt(mn.txt_MachosIniciados.getText());
                int HembrasAlojadas = Integer.parseInt(mn.txt_HembrasAlojadas.getText());
                int MachosAlojados = Integer.parseInt(mn.txt_MachosAlojados.getText());
                
                PreparedStatement ps;
                ResultSet rs;
                Connection con = conexion.establecerConnection();
                ps = con.prepareStatement("INSERT INTO lote (lote, FechaDeNacimiento, HembrasInicio, MachosInicio, HembrasAlojadas, MachosAlojados) "
                        + "VALUES(?,?,?,?,?,?)");
                ps.setString(1, lote);
                ps.setString(2, fecha);
                ps.setString(3, HembrasIniciadas);
                ps.setInt(4, MachosIniciados);
                ps.setInt(5, HembrasAlojadas);
                ps.setInt(6, MachosAlojados);
                ps.executeUpdate();
                con.close();ps.close();
                JOptionPane.showMessageDialog(mn, "Se registro correctamente!!");
            } catch (Exception er) {
                System.err.println("Error en registrarLote: " + er.toString());
                JOptionPane.showMessageDialog(mn, "Error. Verifique que los datos estes correctos...");
            }
        }
    }
    
    public boolean verificarExiteLote() {
        String resultLote = "";
        String lote = mn.txt_lote.getText();
        try {
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "SELECT lote FROM lote WHERE lote = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setString(1, lote);
            rs = ps.executeQuery();
            while (rs.next()) {
                resultLote = rs.getString("lote");
            }
        } catch (Exception e) {
            System.out.println("error en UpdateRegistro " + e.getMessage());
        }
        return resultLote.equals("");
    }
    
    public void ActualizarLote(){
        if (mn.txt_lote.getText().isEmpty() || mn.txt_HembrasAlojadas.getText().isEmpty() 
                || mn.txt_MachosAlojados.getText().isEmpty()) {
              JOptionPane.showMessageDialog(mn, "Dijite el lote, Machos Alojados y Hembras Alojadas");
        } else {
            try{
                String resultLote = "";
                int idLote = Integer.parseInt(mn.lb_idLote.getText());
                String loteAnt = mn.lb_loteAnt.getText();
                String lote = mn.txt_lote.getText();
                String fecha = dateFormat.format(mn.Calen_fechaNacimiento.getCalendar().getTime());
                String HembrasIniciadas = mn.txt_HembrasIniciadas.getText();
                int MachosIniciados =Integer.parseInt(mn.txt_MachosIniciados.getText());
                int HembrasAlojadas = Integer.parseInt(mn.txt_HembrasAlojadas.getText());
                int MachosAlojados = Integer.parseInt(mn.txt_MachosAlojados.getText());
                try {
                    PreparedStatement ps;
                    ResultSet rs;
                    Connection con = conexion.establecerConnection();
                    String sqlInsertar = "SELECT lote FROM lote WHERE lote = ?";
                    ps = con.prepareStatement(sqlInsertar);
                    ps.setString(1, lote);
                    rs = ps.executeQuery();
                    while (rs.next()) {                    
                        resultLote = rs.getString("lote");
                    }
                } catch (Exception e) {
                    System.out.println("error en UpdateRegistro " + e.getMessage());
                }
                
                if(!resultLote.equals("")){
                    JOptionPane.showMessageDialog(mn, "El Lote ya se encuentra registrado.");
                }else{
                    try {
                        PreparedStatement ps;
                        ResultSet rs;
                        Connection con = conexion.establecerConnection();
                        ps = con.prepareStatement("UPDATE lote SET lote = ?, FechaDeNacimiento = ?, HembrasInicio = ?, MachosInicio = ?, HembrasAlojadas = ?, MachosAlojados = ? "
                                + "WHERE id_lote = ?");
                        ps.setString(1, lote);
                        ps.setString(2, fecha);
                        ps.setString(3, HembrasIniciadas);
                        ps.setInt(4, MachosIniciados);
                        ps.setInt(5, HembrasAlojadas);
                        ps.setInt(6, MachosAlojados);
                        ps.setInt(7, idLote);
                        ps.executeUpdate();
                        con.close();
                        ps.close();
                        UpdateRegistro(lote, loteAnt);
                        JOptionPane.showMessageDialog(mn, "Se Modifico correctamente!!");
                    } catch (Exception er) {
                        System.err.println("Error en registrarLote: " + er.toString());
                        JOptionPane.showMessageDialog(mn, "Error. Verifique que los datos estes correctos...");
                    }
                }
        }catch(Exception eN){
             JOptionPane.showMessageDialog(mn, "Error. Verifique que los datos estes correctos...");
        }
      }        
    }
  
    public void UpdateRegistro(String loteActual, String loteAnterior) {
        String fecha = mn.txt_fecha.getText();
        int idRegistro = 0;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "UPDATE registros SET id_lote = ? WHERE id_lote = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setString(1, loteActual);
            ps.setString(2, loteAnterior);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error en UpdateRegistro " + e.getMessage());
        }
    }
}
