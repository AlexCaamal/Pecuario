/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Models.conexion;
import view.mascotas_r;
import view.main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author William Filiberto
 */
public class dueños extends javax.swing.JFrame {
    /**
     * Creates new form dueños
     */
    public dueños() {
        initComponents();
        conexion conecta= new conexion();
        conecta.llenaCombo("mascotas", "id", lista_mascota);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombre_d = new javax.swing.JTextField();
        label_na = new javax.swing.JLabel();
        telefono_d = new javax.swing.JTextField();
        label_na1 = new javax.swing.JLabel();
        label_na2 = new javax.swing.JLabel();
        direccion_d = new javax.swing.JTextField();
        label_na3 = new javax.swing.JLabel();
        lista_mascota = new javax.swing.JComboBox<>();
        link = new javax.swing.JLabel();
        subir_dueño = new javax.swing.JButton();
        nuevo_d = new javax.swing.JButton();
        cerrar_d = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nombre_d.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nombre_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombre_dActionPerformed(evt);
            }
        });

        label_na.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        label_na.setText("Nombre");

        telefono_d.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        telefono_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefono_dActionPerformed(evt);
            }
        });

        label_na1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        label_na1.setText("Teléfono");

        label_na2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        label_na2.setText("Dirección");

        direccion_d.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        direccion_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccion_dActionPerformed(evt);
            }
        });

        label_na3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        label_na3.setText("Mascota");

        lista_mascota.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lista_mascota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Número de mascota" }));

        link.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        link.setText("Registro de dueños");
        link.setToolTipText("");

        subir_dueño.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        subir_dueño.setText("Guardar");
        subir_dueño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subir_dueñoActionPerformed(evt);
            }
        });

        nuevo_d.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nuevo_d.setText("Limpiar");

        cerrar_d.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cerrar_d.setText("Cerrar");
        cerrar_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrar_dActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label_na2)
                                        .addComponent(label_na3))
                                    .addGap(46, 46, 46)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(direccion_d)
                                        .addComponent(lista_mascota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(label_na1)
                                            .addGap(52, 52, 52))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(label_na)
                                            .addGap(37, 37, 37)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nombre_d)
                                        .addComponent(telefono_d, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(link)
                                .addGap(92, 92, 92)))
                        .addGap(77, 77, 77))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nuevo_d)
                        .addGap(31, 31, 31)
                        .addComponent(subir_dueño)
                        .addGap(30, 30, 30)
                        .addComponent(cerrar_d)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(link)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_d, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_na))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefono_d, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_na1))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccion_d, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_na2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_na3)
                    .addComponent(lista_mascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subir_dueño)
                    .addComponent(nuevo_d)
                    .addComponent(cerrar_d))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombre_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombre_dActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombre_dActionPerformed

    private void telefono_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefono_dActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefono_dActionPerformed

    private void direccion_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccion_dActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccion_dActionPerformed

    private void cerrar_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar_dActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cerrar_dActionPerformed

    private void subir_dueñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subir_dueñoActionPerformed
        // TODO add your handling code here:
        Statement st;
        conexion con = new conexion();
        Connection conexion = con.conectar();
                //select * from tipo where nombre_tipo = 'pupi'
                mascotas_r m= new mascotas_r();
                Object v = lista_mascota.getSelectedItem();
                String sql = "insert into dueño(nombre_dueño,telefono,direccion,fk_mascota) values "
                        + "('"+nombre_d.getText()+"','"+telefono_d.getText()+"','"+direccion_d.getText()+"','"+v+"')";
                System.out.println(sql);
        try{
            st = conexion.createStatement();
            st.executeUpdate(sql);
            
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error " +e.toString());
        }
        JOptionPane.showMessageDialog(null, "Borrado de la base de datos");
        main os = new main();
        os.mostrar("dueños");
    }//GEN-LAST:event_subir_dueñoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dueños.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dueños.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dueños.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dueños.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dueños().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton cerrar_d;
    public javax.swing.JTextField direccion_d;
    private javax.swing.JLabel label_na;
    private javax.swing.JLabel label_na1;
    private javax.swing.JLabel label_na2;
    private javax.swing.JLabel label_na3;
    private javax.swing.JLabel link;
    public javax.swing.JComboBox<String> lista_mascota;
    public javax.swing.JTextField nombre_d;
    public javax.swing.JButton nuevo_d;
    public javax.swing.JButton subir_dueño;
    public javax.swing.JTextField telefono_d;
    // End of variables declaration//GEN-END:variables
}
