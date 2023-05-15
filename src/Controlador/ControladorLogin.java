package Controlador;

import Models.conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.Login;
import view.main;

public class ControladorLogin implements ActionListener, MouseListener, KeyListener {

    Login lg;
    
    String Cargo = "";
    String user2 = "";
    int id[];
    String FechaEvento[];

    public ControladorLogin(Login lg) {
        this.lg = lg;
        this.lg.btn_Cancelar.addActionListener(this);
        this.lg.btn_Ingresar.addActionListener(this);
        this.lg.txt_Contra.addKeyListener(this);
        this.lg.btn_Ingresar.addKeyListener(this);
        this.lg.btn_Cancelar.addMouseListener(this);
        this.lg.btn_Ingresar.addMouseListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lg.btn_Cancelar) {
            int op = JOptionPane.showConfirmDialog(null, "¿ Desea Salir ?", "Salir", JOptionPane.INFORMATION_MESSAGE);
            if (op == 1) {
                lg.txt_user.requestFocus(true);
            } else {
                JOptionPane.showMessageDialog(null, "Saliendo");
                System.exit(0);
            }
        } else if (e.getSource() == lg.btn_Ingresar) {
            if (lg.txt_Contra.getText().isEmpty() || lg.txt_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campos Vacios", "Verificar Campos", JOptionPane.ERROR_MESSAGE);
            } else {
                if (verificarUser() == true) {
                    JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña Incorrectas", "Error al Ingresar", JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    lg.txt_user.requestFocus(true);
                } else {
                    main mn = new main();
                    lg.dispose();
                    mn.setVisible(true);
                }
            }

        }
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
        if (e.getSource() == lg.btn_Cancelar) {
            lg.btn_Cancelar.setBackground(Color.red);
        } else if (e.getSource() == lg.btn_Ingresar) {
            lg.btn_Ingresar.setBackground(Color.red);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == lg.btn_Cancelar) {
            lg.btn_Cancelar.setBackground(Color.white);
        } else if (e.getSource() == lg.btn_Ingresar) {
            lg.btn_Ingresar.setBackground(Color.white);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == lg.txt_Contra) {
                if (lg.txt_Contra.getText().toString().isEmpty() || lg.txt_user.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Campos Vacios", "Verificar Campos", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (verificarUser() == true) {
                        JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña Incorrectas", "Error al Ingresar", JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        lg.txt_user.requestFocus(true);
                    } else {
                       main mn = new main();
                       lg.dispose();
                       mn.setVisible(true);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean verificarUser() {
        try {
            String user = lg.txt_user.getText();
            String contra = lg.txt_Contra.getText().toString();

            Connection conectar = conexion.establecerConnection();
            ResultSet rs;
            PreparedStatement ps = conectar.prepareStatement("SELECT nombre FROM user WHERE usuario = ? AND contraseña = ?");
            ps.setString(1, user);
            ps.setString(2, contra);
            rs = ps.executeQuery();
            while (rs.next()) {
                user2 = (rs.getString("nombre"));
            }
            rs.close();
            if (user2.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("error Verificar login " + e.getMessage());
            return true;
        }
    }

    public void limpiar() {
        lg.txt_Contra.setText("");
        lg.txt_user.setText("");
    }
}
