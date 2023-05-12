/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Models.conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import view.main;

/**
 *
 * @author CCNAR
 */
public class ControladorLote implements ActionListener{
    
    main mn;

    public ControladorLote(main mn) {
        this.mn = mn;
        this.mn.btn_AceptarLote.addActionListener(this);
        this.mn.btn_nuevo.addActionListener(this);
        this.mn.btn_eliminarLote.addActionListener(this);
        this.mn.btn_editarLote.addActionListener(this);
        this.mn.btn_registrarLote.addActionListener(this);
        cargarLotes();
        cargarUltimos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == mn.btn_AceptarLote){
          
      }else if(e.getSource() == mn.btn_AceptarLote){
          if(mn.txt_lote.getText().isEmpty()){
              JOptionPane.showMessageDialog(mn, "Insegre el Lote");
              mn.txt_lote.requestFocus(true);
          }
          
      }else if(e.getSource() == mn.btn_nuevo){
          limpiar();
          
      }else if(e.getSource() == mn.btn_eliminarLote){
          
      }else if(e.getSource() == mn.btn_editarLote){
          
      }else if(e.getSource() == mn.btn_registrarLote){
          
      }else if(e.getSource() == mn.cbx_lotes){
          limpiar();
          cargarUltimos();
      }
    }
    
    public void limpiar(){
        mn.txt_lote.setText("");
        mn.txt_HembrasIniciadas.setText("");
        mn.txt_fechaNacimiento.setText("");
        mn.txt_MachosIniciados.setText("");
        mn.txt_HembrasAlojadas.setText("");
        mn.txt_MachosAlojados.setText("");
    }

    public void cargarLotes() {
        try {
            mn.cbx_lotes.removeAllItems();
            String estado = "Activo";
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            ps = con.prepareStatement("SELECT lote FROM lote WHERE Estado = ?");
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
            ps = con.prepareStatement("SELECT lote, FechaDeNacimiento, HembrasInicio, MachosInicio, HembrasAlojadas, MachosAlojados FROM lote WHERE lote = ?");
            ps.setString(1, lote);
            rs = ps.executeQuery();
            while (rs.next()) {
                mn.txt_lote.setText(rs.getString("lote"));
                mn.txt_fechaNacimiento.setText(rs.getString("FechaDeNacimiento"));
                mn.txt_HembrasIniciadas.setText(rs.getString("HembrasInicio"));
                mn.txt_MachosIniciados.setText(rs.getString("MachosInicio"));
                mn.txt_HembrasAlojadas.setText(rs.getString("HembrasAlojadas"));
                mn.txt_MachosAlojados.setText(rs.getString("MachosAlojados"));
            }rs.close();con.close();
        } catch (Exception er) {
            System.err.println("Error en cargarUltimoTXT: " + er.toString());
        }
    }
}
