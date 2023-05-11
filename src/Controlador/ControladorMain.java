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
    String fecha = "";
    

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
        mn.txt_roto.addKeyListener(this);
        mn.txt_cantHembras.addKeyListener(this);
        mn.txt_canMachos.addKeyListener(this);
        mn.tb_main.addMouseListener(this);
         
        fecha = dia.format(formato);
        buscarIdDespues();
        cargarDatosMain();
        cargarDatosTxtField();
        calcularPromedioTotal();
        calcularPromedioINC();
        calcularTotalHuevos();
        calcularPromedioDiaHembra();
        calcularPromedioDiaMacho();
        calcularGrsHembra();
        calcularGrsMacho();
    }
    /*
        TRUNCATE TABLE `registros`;
        ALTER TABLE `registros` AUTO_INCREMENT=0;
    */
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mn.btn_Agregar){
            try {
                if(insertarRegistro() && insertarExistencia() && insertarMortalidad() && 
                    insertarAlimentos() && insertarProduccion() ){
                    JOptionPane.showMessageDialog(mn, "Se ingreso Correctamente el Registro");
                    cargarDatosMain();
                }else{
                    JOptionPane.showMessageDialog(mn, "Ocurrio un problema. Verifique");
                }
            } catch (Exception ex) {
                System.out.println("error en btn_Agregar "+ex.getMessage());
            }
            
        }else if(e.getSource() == mn.btn_VerGenral){
            try {
                fecha = dia.format(formato);
                cargarDatosMain();
                limpiarCampos();
                cargarDatosTxtField();
                mn.btn_acepConfigProd.setVisible(false);
                mn.btn_aceptedConfigAli.setVisible(false);
                mn.btn_configMort.setVisible(false);
                mn.btn_aceptGeneral.setVisible(false);
                mn.btn_modDatos.setVisible(false);
                mn.btn_modMort.setVisible(false);
                mn.btn_modAlimen.setVisible(false);
                mn.btn_modProd.setVisible(false);
            } catch (Exception ex) {
                System.out.println("error en btn_Agregar " + ex.getMessage());
            }
            
        }else if(e.getSource() == mn.btn_modDatos){
            cargarDatos();
            
        }else if(e.getSource() == mn.btn_modMort){
            cargarMortalidad();
            
        }else if(e.getSource() == mn.btn_modAlimen){
            cargarAlimentos();
            
        }else if(e.getSource() == mn.btn_modProd){
            cargarProduccion();
            
        }else if(e.getSource() == mn.btn_aceptGeneral){
            if(UpdateRegistro() && UpdateExistencia()){
                cargarDatos();
                mn.btn_aceptGeneral.setVisible(false);
                JOptionPane.showMessageDialog(mn, "Se modifico correctamente");
            }else{
                JOptionPane.showMessageDialog(mn, "Ocurrio un error. Verifique los datos.");
            }
            
        }else if(e.getSource() == mn.btn_configMort){
            if(UpdateMortalidad()){
                cargarMortalidad();
                mn.btn_configMort.setVisible(false);
                JOptionPane.showMessageDialog(mn, "Se modifico correctamente");
            }else{
                JOptionPane.showMessageDialog(mn, "Ocurrio un error. Verifique los datos.");
            }
            
        }else if(e.getSource() == mn.btn_aceptedConfigAli){
            if(UpdateAlimentos()){
                cargarAlimentos();
                mn.btn_aceptedConfigAli.setVisible(false);
                JOptionPane.showMessageDialog(mn, "Se modifico correctamente");
            }else{
                JOptionPane.showMessageDialog(mn, "Ocurrio un error. Verifique los datos.");
            }
            
        }else if(e.getSource() == mn.btn_acepConfigProd){
            if(UpdateProduccion()){
                cargarProduccion();
                mn.btn_acepConfigProd.setVisible(false);
                JOptionPane.showMessageDialog(mn, "Se modifico correctamente");
            }else{
                JOptionPane.showMessageDialog(mn, "Ocurrio un error. Verifique los datos.");
            }
            
        }else if(e.getSource() == mn.btn_modDatos){
            cargarDatos();
            
        }else if(e.getSource() == mn.btn_modMort){
            cargarMortalidad();
            
        }else if(e.getSource() == mn.btn_modAlimen){
            cargarAlimentos();
            
        }else if(e.getSource() == mn.btn_modProd){
            cargarProduccion();
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == mn.tb_main){
            try{
                int fila = mn.tb_main.getSelectedRow();
                String fechasql = mn.tb_main.getValueAt(fila, 0).toString();
                fecha = fechasql;
                cargarDatosTxtField();
            }catch(Exception ex){
                System.out.println("Error en mouseClicked "+e.toString());
            }
        }
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
         
     }else if(e.getSource() == mn.txt_cantHembras){
         calcularPromedioINC();
         calcularPromedioTotal();
         
     }else if(e.getSource() == mn.txt_canMachos){
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
        modeloTabla.addColumn("selHembras");
        modeloTabla.addColumn("VentasHembras");
        modeloTabla.addColumn("Machos Muertos");
        modeloTabla.addColumn("selMachos");
        modeloTabla.addColumn("VentasMachos");
        modeloTabla.addColumn("kgHembras");
        modeloTabla.addColumn("kgMachos");
        modeloTabla.addColumn("Total Huevos");
        modeloTabla.addColumn("Incubables");
        modeloTabla.addColumn("Huevos rotos");
        mn.tb_main.setModel(modeloTabla);
        int[] ancho = {100, 80, 100, 100, 120, 100, 110, 120, 100, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            mn.tb_main.getColumnModel().getColumn(i).setPreferredWidth(ancho[i]);
        }
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, ex.edad, ex.CantHembras, ex.CanMachos, "
                    + "mo.diaHembra, mo.selHembra, ventasHembras, mo.diaMachos, mo.selMachos, mo.ventasMachos, ali.kgHembras, ali.kgMachos, pro.totalHuevos, "
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
    
    public void cargarDatos(){
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
        mn.tb_main.setModel(modeloTabla);
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, ex.edad, ex.CantHembras, ex.CanMachos "
                    + "FROM registros re "
                    + "INNER JOIN  existencia ex on re.id_registro = ex.id_registro ";
            
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
            System.err.println("Error en Cargartabla cargarDatos: " + e.toString());
        }
    }
    
    public void cargarMortalidad(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hembras Muertas");
        modeloTabla.addColumn("%");
        modeloTabla.addColumn("Selección");
        modeloTabla.addColumn("Ventas");
        modeloTabla.addColumn("Machos Muertos ");
        modeloTabla.addColumn("%");
        modeloTabla.addColumn("Selección");
        modeloTabla.addColumn("Ventas");
        mn.tb_main.setModel(modeloTabla);
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, mo.diaHembra, mo.promedioHembra, mo.selHembra, mo.ventasHembras, mo.diaMachos, mo.promedioMachos, "
                    + "mo.selMachos, mo.ventasMachos "
                    + "FROM registros re "
                    + "INNER JOIN mortalidad mo on re.id_registro = mo.id_registro ";
            
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
            System.err.println("Error en Cargartabla cargarMortalidad: " + e.toString());
        }
    }
    
    public void cargarAlimentos(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("KG Hembras");
        modeloTabla.addColumn("grs Hembras");
        modeloTabla.addColumn("KG Machos");
        modeloTabla.addColumn("grs Machos");
        mn.tb_main.setModel(modeloTabla);
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, ali.kgHembras, ali.grsHembras, ali.kgMachos, ali.grsMachos "
                    + "FROM registros re "
                    + "INNER JOIN alimentos ali on re.id_registro = ali.id_registro ";
            
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
            System.err.println("Error en Cargartabla cargarAlimentos: " + e.toString());
        }
    }
    
    public void cargarProduccion(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Incubable");
        modeloTabla.addColumn("%");
        modeloTabla.addColumn("Comercio");
        modeloTabla.addColumn("Rotos");
        modeloTabla.addColumn("Total de Huevos");
        modeloTabla.addColumn("%");
        mn.tb_main.setModel(modeloTabla);
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.fecha, pro.incubable, pro.promedioInc, pro.comercio, pro.roto, pro.totalHuevos, pro.promedioTotal "
                    + "FROM registros re "
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
            System.err.println("Error en Cargartabla cargarProduccion: " + e.toString());
        }
    }
    
    public void limpiarCampos() {
        mn.LB_ID.setText("");
        mn.txt_fecha.setText("");
        mn.txtEdad.setText("");
        mn.txt_cantHembras.setText("");
        mn.txt_canMachos.setText("");
        mn.txt_diaHembra.setText("0");
        mn.txt_promedioHembra.setText("0");
        mn.txt_selHembra.setText("0");
        mn.txt_vetntasHembra.setText("0");
        mn.txt_diaMacho.setText("0");
        mn.txt_promedioMacho.setText("0.0");
        mn.txt_selMacho.setText("0");
        mn.txt_ventasMachos.setText("0");
        mn.txt_kgHembra.setText("0");
        mn.txt_kgMacho.setText("0");
        mn.txt_grsMacho.setText("0.0");
        mn.txt_total1.setText("0");
        mn.txt_promedioTotal1.setText("0.0");
        mn.txt_INC.setText("0");
        mn.txt_promedioINC.setText("0.0");
        mn.txt_comercio.setText("0");
        mn.txt_roto.setText("0");
    }
   
    public void cargarDatosTxtField() {
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT re.id_registro as id_registro, re.fecha as fecha, ex.edad as edad, ex.CantHembras, ex.CanMachos, "
                    + "mo.diaHembra, mo.promedioHembra,mo.selHembra, mo.ventasHembras, mo.diaMachos, "
                    + "mo.promedioMachos, mo.selMachos, mo.ventasMachos, ali.kgHembras, ali.grsHembras, "
                    + "ali.kgMachos, ali.grsMachos, pro.totalHuevos, pro.promedioTotal, pro.incubable, pro.promedioInc, "
                    + "pro.comercio, pro.roto "
                    + "FROM registros re "
                    + "INNER JOIN  existencia ex on re.id_registro = ex.id_registro "
                    + "INNER JOIN mortalidad mo on re.id_registro = mo.id_registro "
                    + "INNER JOIN alimentos ali on re.id_registro = ali.id_registro "
                    + "INNER JOIN produccion pro on re.id_registro = pro.id_registro "
                    + "WHERE re.fecha= ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
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
                mn.txt_kgHembra.setText(rs.getString("kgHembras"));
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
                calcularCantidadHMMortalidad();
                mn.btn_Agregar.setEnabled(true);
            }else{
                mn.btn_Agregar.setEnabled(false);
                mn.btn_modAlimen.setVisible(true);
                mn.btn_modDatos.setVisible(true);
                mn.btn_modProd.setVisible(true);
                mn.btn_modMort.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Error en Cargartabla cargarDatosTxtField: " + e.getMessage());
        }
    }
    
    public void calcularPromedioDiaHembra(){
         if(mn.txt_diaHembra.getText().isEmpty()){
            mn.txt_promedioHembra.setText("0.0");
        }else {
            Float hembrasMuertas = Float.parseFloat(mn.txt_diaHembra.getText());
            Float cantVivosAnterior = Float.parseFloat(mn.lb_cantAloHembras.getText());
            Float result = hembrasMuertas / cantVivosAnterior * 100;
            mn.txt_promedioHembra.setText("" + result);
        }
    }
    
     public void calcularPromedioDiaMacho(){
        if(mn.txt_diaMacho.getText().isEmpty()){
            mn.txt_promedioMacho.setText("0.0");
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
            JOptionPane.showMessageDialog(mn, "Digite solo Números.");
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
    
    public void buscarIdDespues(){
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sql = "SELECT MAX(id_registro) as max FROM registros";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                mn.LB_IdDespues.setText(""+(rs.getInt("max")+1));
            }rs.close();con.close();
        } catch (Exception e) {
            System.out.println("error en buscarIdDespues "+e.getMessage());
        }
    }
    
    public boolean insertarRegistro(){
        String fecha = mn.txt_fecha.getText();
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "INSERT INTO registros (fecha) VALUES (?)";
            ps = con.prepareStatement(sqlInsertar);
            ps.setString(1, fecha);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en insertarRegistro "+e.getMessage());
            return false;
        }
    }
    
    public boolean insertarExistencia() {
        try {
            if(mn.txtEdad.getText().isEmpty() || mn.txt_cantHembras.getText().isEmpty() || mn.txt_canMachos.getText().isEmpty()){
                JOptionPane.showMessageDialog(mn, "Digite la edad, Cantidad de Hembra o Cantidad de Machos.");
                return false;
            } else {
                int edad = Integer.parseInt(mn.txtEdad.getText());
                int id = Integer.parseInt(mn.LB_IdDespues.getText());
                int cantHembras = Integer.parseInt(mn.txt_cantHembras.getText());
                int cantMachos = Integer.parseInt(mn.txt_canMachos.getText());
                PreparedStatement ps;
                ResultSet rs;
                Connection con = conexion.establecerConnection();
                String sqlInsertar = "INSERT INTO existencia (CanMachos, CantHembras, edad, id_registro) VALUES (?,?,?,?)";
                ps = con.prepareStatement(sqlInsertar);
                ps.setInt(1, cantMachos);
                ps.setInt(2, cantHembras);
                ps.setInt(3, edad);
                ps.setInt(4, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("error en insertarExistencia " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarMortalidad(){
        try {
            int MuerteHembra = Integer.parseInt(mn.txt_diaHembra.getText());
            int MuerteMacho = Integer.parseInt(mn.txt_diaMacho.getText());
            int id = Integer.parseInt(mn.LB_IdDespues.getText());
            Float promedioH = Float.parseFloat(mn.txt_promedioHembra.getText());
            Float promedioM = Float.parseFloat(mn.txt_promedioMacho.getText());
            int selHembras = Integer.parseInt(mn.txt_selHembra.getText());
            int selHombre = Integer.parseInt(mn.txt_selMacho.getText());
            int ventaHembra = Integer.parseInt(mn.txt_vetntasHembra.getText());
            int ventaMachos = Integer.parseInt(mn.txt_ventasMachos.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "INSERT INTO mortalidad (diaHembra, promedioHembra, selHembra, ventasHembras, "
                    + "diaMachos, promedioMachos, selMachos, ventasMachos, id_registro) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, MuerteHembra);
            ps.setFloat(2, promedioH);
            ps.setInt(3, selHembras);
            ps.setInt(4, ventaHembra);
            ps.setInt(5, MuerteMacho);
            ps.setFloat(6, promedioM);
            ps.setInt(7, selHombre);
            ps.setInt(8, ventaMachos);
            ps.setInt(9, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en insertarMortalidad " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarAlimentos(){
        try {
            int kgHembra = Integer.parseInt(mn.txt_kgHembra.getText());
            int kgMacho = Integer.parseInt(mn.txt_kgMacho.getText());
            int id = Integer.parseInt(mn.LB_IdDespues.getText());
            Float grsH = Float.parseFloat(mn.txt_grsHembra.getText());
            Float grsM = Float.parseFloat(mn.txt_grsMacho.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "INSERT INTO alimentos (kgHembras, grsHembras, "
                    + "kgMachos, grsMachos,id_registro) "
                    + "VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, kgHembra);
            ps.setFloat(2, grsH);
            ps.setInt(3, kgMacho);
            ps.setFloat(4, grsM);
            ps.setInt(5, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en insertarAlimentos " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarProduccion(){
        try {
            int totalHuevo = Integer.parseInt(mn.txt_total1.getText());
            Float promedioTotal = Float.parseFloat(mn.txt_promedioTotal1.getText());
            int id = Integer.parseInt(mn.LB_IdDespues.getText());
            int inc = Integer.parseInt(mn.txt_INC.getText());
            Float promInc = Float.parseFloat(mn.txt_promedioINC.getText());
            int comercio = Integer.parseInt(mn.txt_comercio.getText());
            int roto = Integer.parseInt(mn.txt_roto.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "INSERT INTO produccion (totalHuevos, promedioTotal, "
                    + "incubable, promedioInc, comercio, roto, id_registro) "
                    + "VALUES (?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, totalHuevo);
            ps.setFloat(2, promedioTotal);
            ps.setInt(3, inc);
            ps.setFloat(4, promInc);
            ps.setInt(5, comercio);
            ps.setInt(6, roto);
            ps.setInt(7, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en insertarProduccion " + e.getMessage());
            return false;
        }
    }
    
    public boolean UpdateRegistro(){
        String fecha = mn.txt_fecha.getText();
        int id = Integer.parseInt(mn.LB_ID.getText());
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "UPDATE registros SET fecha = ? WHERE id_registro = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setString(1, fecha);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en UpdateRegistro "+e.getMessage());
            return false;
        }
    }
    
    public boolean UpdateExistencia() {
        try {
            if(mn.txtEdad.getText().isEmpty() || mn.txt_cantHembras.getText().isEmpty() || mn.txt_canMachos.getText().isEmpty()){
                JOptionPane.showMessageDialog(mn, "Digite la edad, Cantidad de Hembra o Cantidad de Machos.");
                return false;
            } else {
                int id = Integer.parseInt(mn.LB_ID.getText());
                int edad = Integer.parseInt(mn.txtEdad.getText());
                int cantHembras = Integer.parseInt(mn.txt_cantHembras.getText());
                int cantMachos = Integer.parseInt(mn.txt_canMachos.getText());
                PreparedStatement ps;
                ResultSet rs;
                Connection con = conexion.establecerConnection();
                String sqlInsertar = "UPDATE existencia SET CanMachos = ?, CantHembras = ?, edad = ? WHERE id_registro = ?";
                ps = con.prepareStatement(sqlInsertar);
                ps.setInt(1, cantMachos);
                ps.setInt(2, cantHembras);
                ps.setInt(3, edad);
                ps.setInt(4, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("error en UpdateExistencia " + e.getMessage());
            return false;
        }
    }
    
    public boolean UpdateMortalidad(){
        try {
            int MuerteHembra = Integer.parseInt(mn.txt_diaHembra.getText());
            int MuerteMacho = Integer.parseInt(mn.txt_diaMacho.getText());
            int id = Integer.parseInt(mn.LB_ID.getText());
            Float promedioH = Float.parseFloat(mn.txt_promedioHembra.getText());
            Float promedioM = Float.parseFloat(mn.txt_promedioMacho.getText());
            int selHembras = Integer.parseInt(mn.txt_selHembra.getText());
            int selHombre = Integer.parseInt(mn.txt_selMacho.getText());
            int ventaHembra = Integer.parseInt(mn.txt_vetntasHembra.getText());
            int ventaMachos = Integer.parseInt(mn.txt_ventasMachos.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "UPDATE mortalidad  SET diaHembra = ?, promedioHembra = ?, selHembra = ?, ventasHembras = ?, "
                    + "diaMachos = ?, promedioMachos = ?, selMachos = ?, ventasMachos = ? WHERE id_registro = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, MuerteHembra);
            ps.setFloat(2, promedioH);
            ps.setInt(3, selHembras);
            ps.setInt(4, ventaHembra);
            ps.setInt(5, MuerteMacho);
            ps.setFloat(6, promedioM);
            ps.setInt(7, selHombre);
            ps.setInt(8, ventaMachos);
            ps.setInt(9, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en UpdateMortalidad " + e.getMessage());
            return false;
        }
    }
    
    public boolean UpdateAlimentos(){
        try {
            int kgHembra = Integer.parseInt(mn.txt_kgHembra.getText());
            int kgMacho = Integer.parseInt(mn.txt_kgMacho.getText());
            int id = Integer.parseInt(mn.LB_ID.getText());
            Float grsH = Float.parseFloat(mn.txt_grsHembra.getText());
            Float grsM = Float.parseFloat(mn.txt_grsMacho.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "UPDATE alimentos SET kgHembras = ?, grsHembras = ?, "
                    + "kgMachos = ?, grsMachos = ? WHERE id_registro = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, kgHembra);
            ps.setFloat(2, grsH);
            ps.setInt(3, kgMacho);
            ps.setFloat(4, grsM);
            ps.setInt(5, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en UpdateAlimentos " + e.getMessage());
            return false;
        }
    }
    
    public boolean UpdateProduccion(){
        try {
            int totalHuevo = Integer.parseInt(mn.txt_total1.getText());
            Float promedioTotal = Float.parseFloat(mn.txt_promedioTotal1.getText());
            int id = Integer.parseInt(mn.LB_ID.getText());
            int inc = Integer.parseInt(mn.txt_INC.getText());
            Float promInc = Float.parseFloat(mn.txt_promedioINC.getText());
            int comercio = Integer.parseInt(mn.txt_comercio.getText());
            int roto = Integer.parseInt(mn.txt_roto.getText());
            
            PreparedStatement ps;
            ResultSet rs;
            Connection con = conexion.establecerConnection();
            String sqlInsertar = "UPDATE produccion SET totalHuevos = ?, promedioTotal = ?, "
                    + "incubable = ?, promedioInc = ?, comercio = ?, roto = ? WHERE id_registro = ?";
            ps = con.prepareStatement(sqlInsertar);
            ps.setInt(1, totalHuevo);
            ps.setFloat(2, promedioTotal);
            ps.setInt(3, inc);
            ps.setFloat(4, promInc);
            ps.setInt(5, comercio);
            ps.setInt(6, roto);
            ps.setInt(7, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("error en UpdateProduccion " + e.getMessage());
            return false;
        }
    }
    
    public void calcularCantidadHMMortalidad(){
        int id = 0; 
        if(mn.LB_IdDespues.getText().isEmpty()){
            id = 0;
        }else{
            id = Integer.parseInt(mn.LB_IdDespues.getText())-1;
        }
        int muerteH = 0, muerteM = 0;
        int vivosH = 0, vivosM = 0;
        if(id == 0){
            mn.txt_cantHembras.setText(mn.lb_cantAloHembras.getText());
            mn.txt_canMachos.setText(mn.lb_cantAloMachos.getText());
        } else {
            try {
                PreparedStatement ps;
                ResultSet rs;
                Connection con = conexion.establecerConnection();
                String sqlInsertar = "SELECT diaHembra, selHembra,ventasHembras, diaMachos, selMachos, ventasMachos FROM mortalidad WHERE id_registro = ?";
                ps = con.prepareStatement(sqlInsertar);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    muerteH = (rs.getInt("diaHembra") + rs.getInt("selHembra") + rs.getInt("ventasHembras"));
                    muerteM = (rs.getInt("diaMachos") + rs.getInt("selMachos") + rs.getInt("ventasMachos"));
                }
                rs.close();
                con.close();
            } catch (Exception e) {
                System.out.println("error calcularCantidadHMMortalidad " + e.getMessage());
            }
            
            try {
                PreparedStatement ps;
                ResultSet rs;
                Connection con = conexion.establecerConnection();
                String sqlInsertar = "SELECT CanMachos, CantHembras FROM existencia WHERE id_registro = ?";
                ps = con.prepareStatement(sqlInsertar);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    vivosH = rs.getInt("CantHembras") ;
                    vivosM = rs.getInt("CanMachos");
                } rs.close(); con.close();
            } catch (Exception e) {
                System.out.println("error calcularCantidadHMMortalidad " + e.getMessage());
            }
            mn.txt_cantHembras.setText(""+(vivosH - muerteH));
            mn.txt_canMachos.setText(""+(vivosM - muerteM));
        }
    }
}

