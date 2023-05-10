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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.main;

public class ControladorMain implements ActionListener, MouseListener, KeyListener{

    main mn;
     LocalDateTime dia = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = dia.format(formato);

    public ControladorMain(main mn) {
        this.mn = mn;
        mn.btn_Agregar.addActionListener(this);
        mn.btn_VerGenral.addActionListener(this);
        mn.btn_acepConfigProd.addActionListener(this);
        mn.btn_aceptedConfigAli.addActionListener(this);
        mn.btn_aceptGeneral.addActionListener(this);
        mn.btn_configMort.addActionListener(this);
        mn.btn_modAlimen.addActionListener(this);
        mn.btn_modDatos.addActionListener(this);
        mn.btn_modProd.addActionListener(this);
        mn.btn_modMort.addActionListener(this);
        mn.txt_diaHembra.addKeyListener(this);
        mn.txt_diaMacho.addKeyListener(this);
        mn.txt_kgHembra.addKeyListener(this);
        mn.txt_kgMacho.addKeyListener(this);
        mn.txt_comercio.addKeyListener(this);
        mn.txt_roto.addKeyListener(this);
        mn.txt_INC.addKeyListener(this);
        mn.tb_main.addMouseListener(this);
        cargarDatosMain();
        cargarDatosTxtField();
        calcularPromedioTotal();
        calcularPromedioINC();
        calcularTotalHuevos();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
  
    }

    @Override
    public void mouseClicked(MouseEvent e) {
  
    }

    @Override
    public void mousePressed(MouseEvent e) {
   
    }

    @Override
    public void mouseReleased(MouseEvent e) {
   
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
   
    }

    @Override
    public void keyPressed(KeyEvent e) {
   
    }

    @Override
    public void keyReleased(KeyEvent e) {
     if(e.getSource() == mn.txt_diaHembra){
         calcularPromedioDiaHembra();
         
     }else if(e.getSource() == mn.txt_diaMacho){
         calcularPromedioDiaMacho();
         
     }if(e.getSource() == mn.txt_kgHembra){
         calcularGrsHembra();
         
     }else if(e.getSource() == mn.txt_kgMacho){
         calcularGrsMacho();
         
     }else if(e.getSource() == mn.txt_comercio){
          calcularTotalHuevos();
          calcularPromedioINC();
          calcularPromedioTotal();
         
     }if(e.getSource() == mn.txt_roto){
         calcularTotalHuevos();
         calcularPromedioINC();
         calcularPromedioTotal();
         
     }else if(e.getSource() == mn.txt_INC){
         calcularTotalHuevos();
         calcularPromedioINC();
         calcularPromedioTotal();
     }
    }
    
    public void cargarDatosMain(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Edad");
        modeloTabla.addColumn("Cant. Hembras");
        modeloTabla.addColumn("Cant. Machos");
        modeloTabla.addColumn("Hembras Muertas");
        modeloTabla.addColumn("Machos Muertos");
        modeloTabla.addColumn("kgHembras");
        modeloTabla.addColumn("kgMachos");
        modeloTabla.addColumn("Total Huevos");
        modeloTabla.addColumn("Incubables");
        modeloTabla.addColumn("Huevos rotos");
        mn.tb_main.setModel(modeloTabla);
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, ex.edad, ex.CantHembras, ex.CanMachos, "
                    + "mo.diaHembra, mo.diaMachos, ali.kgHembras, ali.kgMachos, pro.totalHuevos, "
                    + "pro.incubable, pro.roto "
                    + "FROM registros re "
                    + "INNER JOIN  existencia ex on re.id_registro = ex.id_registro "
                    + "INNER JOIN mortalidad mo on re.id_registro = mo.id_registro "
                    + "INNER JOIN alimentos ali on re.id_registro = ali.id_registro "
                    + "INNER JOIN produccion pro on re.id_registro = pro.id_registro ";
            
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                rsmd = rs.getMetaData();
                columnas = rsmd.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[columnas];
                    for (int i = 0; i < columnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloTabla.addRow(fila);
                }rs.close();con.close();
        } catch (Exception e) {
            System.err.println("Error en Cargartabla cargarDatosMain: " + e.toString());
        }
    }
   
    public void cargarDatosTxtField() {
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.id_registro, re.fecha, ex.edad, ex.CantHembras, ex.CanMachos, "
                    + "mo.diaHembra, mo.promedioHembra,mo.selHembra, mo.ventasHembras, mo.diaMachos, "
                    + "mo.promedioMachos, mo.selMachos, mo.ventasMachos, ali.kgHembras, ali.grsHembras, "
                    + "ali.kgMachos, ali.grsMachos, pro.totalHuevos, pro.promedioTotal, pro.incubable, pro.promedioInc, "
                    + "pro.comercio, pro.roto "
                    + "FROM registros re "
                    + "INNER JOIN  existencia ex on re.id_registro = ex.id_registro "
                    + "INNER JOIN mortalidad mo on re.id_registro = mo.id_registro "
                    + "INNER JOIN alimentos ali on re.id_registro = ali.id_registro "
                    + "INNER JOIN produccion pro on re.id_registro = pro.id_registro "
                    + "WHERE re.fecha= " + fecha;

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mn.LB_ID.setText(rs.getString("id_registro"));
                mn.txt_fecha.setText(rs.getString("fecha"));
                mn.txtEdad.setText(rs.getString("edad"));
                mn.txt_cantHembras.setText(rs.getString("CantHembras"));
                mn.txt_canMachos.setText(rs.getString("CanMachos"));
                mn.txt_diaHembra.setText(rs.getString("diaHembra"));
                mn.txt_promedioHembra.setText(rs.getString("promedioHembra"));
                mn.txt_selHembra.setText(rs.getString("selHembra"));
                mn.txt_vetntasHembra.setText(rs.getString("ventasHembras"));
                mn.txt_diaMacho.setText(rs.getString("diaMachos"));
                mn.txt_promedioMacho.setText(rs.getString("promedioMachos"));
                mn.txt_selMacho.setText(rs.getString("selMachos"));
                mn.txt_ventasMachos.setText(rs.getString("ventasMachos"));
                mn.txt_kgMacho.setText(rs.getString("kgMachos"));
                mn.txt_grsMacho.setText(rs.getString("grsMachos"));
                mn.txt_total1.setText(rs.getString("totalHuevos"));
                mn.txt_promedioTotal1.setText(rs.getString("promedioTotal"));
                mn.txt_INC.setText(rs.getString("incubable"));
                mn.txt_promedioINC.setText(rs.getString("promedioInc"));
                mn.txt_comercio.setText(rs.getString("comercio"));
                mn.txt_roto.setText(rs.getString("roto"));
            }rs.close();con.close();
            
            if(mn.txt_fecha.getText().isEmpty()){
                mn.txt_fecha.setText(fecha);
                mn.btn_Agregar.setEnabled(true);
            }else{
                mn.btn_Agregar.setEnabled(false);
                mn.btn_modAlimen.setVisible(true);
                mn.btn_modDatos.setVisible(true);
                mn.btn_modProd.setVisible(true);
                mn.btn_modMort.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Error en Cargartabla cargarDatosTxtField: " + e.toString());
        }
    }
    
    public void calcularPromedioDiaHembra(){
         if(mn.txt_diaHembra.getText().isEmpty()){
            mn.txt_promedioHembra.setText("0");
        }else {
            Float hembrasMuertas = Float.parseFloat(mn.txt_diaHembra.getText());
            Float cantVivosAnterior = Float.parseFloat(mn.lb_cantAloHembras.getText());
            Float result = hembrasMuertas / cantVivosAnterior * 100;
            mn.txt_promedioHembra.setText("" + result);
        }
    }
    
     public void calcularPromedioDiaMacho(){
        if(mn.txt_diaMacho.getText().isEmpty()){
            mn.txt_promedioMacho.setText("0");
        } else {
            Float hembrasMuertas = Float.parseFloat(mn.txt_diaMacho.getText());
            Float cantVivosAnterior = Float.parseFloat(mn.lb_cantAloMachos.getText());
            Float result = hembrasMuertas / cantVivosAnterior * 100;
            mn.txt_promedioMacho.setText("" + result);
        }
    }
     
    public void calcularGrsHembra() {
        if (mn.txt_kgHembra.getText().isEmpty() || mn.txt_cantHembras.getText().isEmpty()) {
            mn.txt_grsHembra.setText("0.0");
        } else {
            Float kg = Float.parseFloat(mn.txt_kgHembra.getText());
            Float cantVivos = Float.parseFloat(mn.txt_cantHembras.getText());
            Float result = kg / cantVivos;
            mn.txt_grsHembra.setText("" + result);
        }
    }

    public void calcularGrsMacho() {
        if (mn.txt_kgMacho.getText().isEmpty() || mn.txt_cantHembras.getText().isEmpty()) {
            mn.txt_grsMacho.setText("0.0");
        } else {
            Float kgMacho = Float.parseFloat(mn.txt_kgMacho.getText());
            Float cantVivos = Float.parseFloat(mn.txt_canMachos.getText());
            Float result = kgMacho / cantVivos;
            mn.txt_grsMacho.setText("" + result);
        }
    }
    
    public void calcularTotalHuevos() {
        int inc = 0;
        int roto = 0;
        int comercio = 0;
        try{
            if( mn.txt_roto.getText().isEmpty()){  roto = 0;  }else{roto = Integer.parseInt(mn.txt_roto.getText());}
            if( mn.txt_comercio.getText().isEmpty()){  comercio = 0;  }else{comercio = Integer.parseInt(mn.txt_comercio.getText());}
            if( mn.txt_INC.getText().isEmpty()){  inc = 0;  }else{inc = Integer.parseInt(mn.txt_INC.getText());}
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(mn, "Digite solo Nuúmeros.");
        }
        int totalHuevo = inc + roto + comercio;
        mn.txt_total1.setText("" + totalHuevo);
    }
    
    public void calcularPromedioINC() {
        if (mn.txt_INC.getText().isEmpty() || mn.txt_total1.getText().isEmpty()) {
            mn.txt_promedioINC.setText("0.0");
        } else {
            if(mn.txt_INC.getText().equals("0")){
                 mn.txt_promedioINC.setText("0.0");
            }else{
                Float total= Float.parseFloat(mn.txt_total1.getText());
                Float inc = Float.parseFloat(mn.txt_INC.getText());
                Float result = inc*100 / total;
                mn.txt_promedioINC.setText("" + result);
            }
        }
    }
    
    public void calcularPromedioTotal() {
        if (mn.txt_total1.getText().isEmpty() || mn.txt_cantHembras.getText().isEmpty()) {
            mn.txt_promedioTotal1.setText("0.0");
        } else {
            if(mn.txt_total1.getText().equals("0")){
               mn.txt_promedioTotal1.setText("0.0");
            }else{
                Float total= Float.parseFloat(mn.txt_total1.getText());
                Float hembras = Float.parseFloat(mn.txt_cantHembras.getText());
                Float result = total / hembras;
                mn.txt_promedioTotal1.setText("" + result);
            }
        }
    }
 
}
