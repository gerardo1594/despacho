/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import conexionBBDD.ConexionMySQL;
import despacho.Abogados.Abogado;
import despacho.Clientes.Cliente;
import despacho.Clientes.ClienteContrario;
import despacho.Procedimiento;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author gerar
 */
public class AnadirSwing extends javax.swing.JPanel {
    private Procedimiento procedimiento;
    private String idPadre;
    private boolean anadido;
    private JDialog ventanaHijo;
    private PrincipalSwing ventanaPrincipal;
    /**
     * Creates new form AnadirSwing
     */
    public AnadirSwing() {
        initComponents();
        panelAbogados.setLayout(new BoxLayout(panelAbogados, BoxLayout.Y_AXIS));
        panelAbogadosContrarios.setLayout(new BoxLayout(panelAbogadosContrarios, BoxLayout.Y_AXIS));
        procedimiento = new Procedimiento();
        panelTree.setVisible(false);
        idPadre = null;
        anadido = false;
    }
    public AnadirSwing(Procedimiento p,PrincipalSwing ventanaPrincipal){
        this.ventanaPrincipal = ventanaPrincipal;
        initComponents();
        panelAbogados.setLayout(new BoxLayout(panelAbogados, BoxLayout.Y_AXIS));
        panelAbogadosContrarios.setLayout(new BoxLayout(panelAbogadosContrarios, BoxLayout.Y_AXIS));
        this.procedimiento = p;
        txtNAuto.setText(p.getnAuto());
        txtJuzgado.setText(p.getJuzgado());
        txtJuicio.setText(p.getJucio());
        txtCuantia.setText(p.getCuantia());
        panelAbogados.removeAll();
        lblTitle.setText("Procedimiento ");
        //panelAbogados.setSize(300, 1000);
        int tamano = 0;
        for (Cliente c : this.procedimiento.getClientes()) {
            JPanel panel = new ClienteLista(c,false);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(330,200));
            panelAbogados.add(panel);
            tamano += panel.getPreferredSize().getHeight();
        }
        panelAbogados.setPreferredSize(new Dimension(330,tamano));
        
        this.procedimiento = p;
        panelAbogadosContrarios.removeAll();
        tamano = 0;
        for (Cliente c : this.procedimiento.getClientesContrarios()) {
            JPanel panel = new ClienteLista(c,true);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(310,200));
            panelAbogadosContrarios.add(panel);
            tamano += 210;
        }
        panelAbogadosContrarios.setPreferredSize(new Dimension(330,tamano));
        btnGuardar.setVisible(false);
        
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Subprocedimientos");
        ConexionMySQL con = new ConexionMySQL();
        anadirARaiz(raiz,con,procedimiento.getId());
        DefaultTreeModel model = new DefaultTreeModel(raiz);
        TreeSubProc.setModel(model); 
           
        idPadre = null;
        anadido = false;
    }
    
    private void anadirARaiz(DefaultMutableTreeNode raizPadre,ConexionMySQL con,String id){
        ArrayList<HashMap<String,String>> datos = con.executeQuery("SELECT `IDProcedimientoHijo` FROM `procedimientopadrehijo` WHERE `IDProcedimientoPadre` = '"+id+"'");
        String idHijo;
        if(!datos.isEmpty()){
            for (HashMap<String, String> dato : datos) {                
                DefaultMutableTreeNode raiz1 = new DefaultMutableTreeNode("Raiz1");
                idHijo = dato.get("IDProcedimientoHijo");
                if(id.compareToIgnoreCase(idHijo) != 0){
                    raiz1.setUserObject(idHijo);
                    raizPadre.add(raiz1);
                    anadirARaiz(raiz1,con,idHijo);
                }                               
            }
        }
    }
    
    
    public AnadirSwing(String id,JDialog ventanaHijo){
        idPadre = id;
        this.ventanaHijo = ventanaHijo;
        anadido = false;
        initComponents();
        panelAbogados.setLayout(new BoxLayout(panelAbogados, BoxLayout.Y_AXIS));
        panelAbogadosContrarios.setLayout(new BoxLayout(panelAbogadosContrarios, BoxLayout.Y_AXIS));
        procedimiento = new Procedimiento();
        //inicioRapido();
        panelTree.setVisible(false);
    }

    private void inicioRapido(){
        String palabra = "123123123";
        procedimiento.setnAuto(palabra);
        txtNAuto.setText(palabra);
        palabra = "Civil";
        procedimiento.setJucio(palabra);
        txtJuicio.setText(palabra);
        palabra = "1000000";
        procedimiento.setCuantia(palabra);
        txtCuantia.setText(palabra);
        palabra = "Madrid";
        procedimiento.setJuzgado(palabra);
        txtJuzgado.setText(palabra);
        
        Cliente c = new Cliente("05333421L","Gerardo","Meiro");
        Abogado a = new Abogado("Fredy","Nordberg",null);
        c.setAbogado(a);
        procedimiento.addCliente(c);
        
        ClienteContrario cc = new ClienteContrario("34567890A","Contrario3","ApellidoC3");
        Abogado ac = new Abogado("AbogadoC2","ApellidoAC2","Mikael");
        cc.setAbogado(ac);
        procedimiento.addClienteContrario(cc);
        
        int tamano = 0;
        for (Cliente c1 : this.procedimiento.getClientes()) {
            JPanel panel = new ClienteLista(c1,false);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(330,200));
            panelAbogados.add(panel);
            tamano += panel.getPreferredSize().getHeight();
        }
        panelAbogados.setPreferredSize(new Dimension(330,tamano));
        
        panelAbogadosContrarios.removeAll();
        tamano = 0;
        for (Cliente c1 : this.procedimiento.getClientesContrarios()) {
            JPanel panel = new ClienteLista(c1,true);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(310,200));
            panelAbogadosContrarios.add(panel);
            tamano += 210;
        }
        panelAbogadosContrarios.setPreferredSize(new Dimension(330,tamano));
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNAuto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtJuicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCuantia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJuzgado = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAnadirAbog = new javax.swing.JButton();
        panelTree = new javax.swing.JPanel();
        scrollTree = new javax.swing.JScrollPane();
        TreeSubProc = new javax.swing.JTree();
        btnAnadirSubprocedimiento = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelAbogados = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnAnadirAbogContrario = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelAbogadosContrarios = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(784, 610));

        jLabel1.setText("Numero Auto:");

        txtNAuto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNAutoFocusLost(evt);
            }
        });
        txtNAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNAutoActionPerformed(evt);
            }
        });

        jLabel2.setText("Jucio:");

        txtJuicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJuicioFocusLost(evt);
            }
        });
        txtJuicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJuicioActionPerformed(evt);
            }
        });

        jLabel3.setText("Cuantia:");

        txtCuantia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCuantiaFocusLost(evt);
            }
        });
        txtCuantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuantiaActionPerformed(evt);
            }
        });

        jLabel4.setText("Juzgado:");

        txtJuzgado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJuzgadoFocusLost(evt);
            }
        });
        txtJuzgado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJuzgadoActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTitle.setText("Añadir Procedimiento");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Clientes");

        btnAnadirAbog.setText("Añadir");
        btnAnadirAbog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirAbogActionPerformed(evt);
            }
        });

        TreeSubProc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TreeSubProcMouseClicked(evt);
            }
        });
        scrollTree.setViewportView(TreeSubProc);

        btnAnadirSubprocedimiento.setText("Anadir Subprocedimiento");
        btnAnadirSubprocedimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirSubprocedimientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTreeLayout = new javax.swing.GroupLayout(panelTree);
        panelTree.setLayout(panelTreeLayout);
        panelTreeLayout.setHorizontalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAnadirSubprocedimiento)
            .addComponent(scrollTree, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelTreeLayout.setVerticalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeLayout.createSequentialGroup()
                .addComponent(btnAnadirSubprocedimiento)
                .addGap(0, 0, 0)
                .addComponent(scrollTree, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelAbogados.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panelAbogadosLayout = new javax.swing.GroupLayout(panelAbogados);
        panelAbogados.setLayout(panelAbogadosLayout);
        panelAbogadosLayout.setHorizontalGroup(
            panelAbogadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
        panelAbogadosLayout.setVerticalGroup(
            panelAbogadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelAbogados);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Clientes Contrarios");

        btnAnadirAbogContrario.setText("Añadir");
        btnAnadirAbogContrario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirAbogContrarioActionPerformed(evt);
            }
        });

        panelAbogadosContrarios.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panelAbogadosContrariosLayout = new javax.swing.GroupLayout(panelAbogadosContrarios);
        panelAbogadosContrarios.setLayout(panelAbogadosContrariosLayout);
        panelAbogadosContrariosLayout.setHorizontalGroup(
            panelAbogadosContrariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
        panelAbogadosContrariosLayout.setVerticalGroup(
            panelAbogadosContrariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(panelAbogadosContrarios);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitle)
                .addContainerGap(426, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNAuto, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(txtJuicio)
                            .addComponent(txtCuantia)
                            .addComponent(txtJuzgado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(panelTree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAnadirAbog))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAnadirAbogContrario))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNAuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCuantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtJuzgado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelTree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(btnAnadirAbog)
                            .addComponent(jLabel7)
                            .addComponent(btnAnadirAbogContrario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnGuardar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnadirAbogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirAbogActionPerformed
        // TODO add your handling code here:
        
        AnadirCliente ventana = new AnadirCliente(null,true,false,null);
        ventana.setVisible(true);
        Cliente cliente = ventana.getCliente();
        
        if(cliente != null){
            this.procedimiento.addCliente(cliente);
        }
        panelAbogados.removeAll();
        //panelAbogados.setSize(300, 1000);
        int tamano = 0;
        for (Cliente c : this.procedimiento.getClientes()) {
            JPanel panel = new ClienteLista(c,false);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(330,200));
            panelAbogados.add(panel);
            tamano += panel.getPreferredSize().getHeight();
        }
        panelAbogados.setPreferredSize(new Dimension(330,tamano));
        panelAbogados.revalidate();
        panelAbogados.repaint();
    }//GEN-LAST:event_btnAnadirAbogActionPerformed

    private void btnAnadirAbogContrarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirAbogContrarioActionPerformed
        // TODO add your handling code here:
        AnadirCliente ventana = new AnadirCliente(null,true,true,null);
        ventana.setVisible(true);
        Cliente cliente = ventana.getCliente();
        
        if(cliente != null){
            this.procedimiento.addClienteContrario((ClienteContrario) cliente);
        }
        panelAbogadosContrarios.removeAll();
        int tamano = 0;
        for (Cliente c : this.procedimiento.getClientesContrarios()) {
            JPanel panel = new ClienteLista(c,true);
            /*panel.setBackground(Color.red);
            panel.add(new JLabel("Nombre"));*/
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            //panel.setSize(330, 200);
            //panel.setPreferredSize(new Dimension(310,200));
            panelAbogadosContrarios.add(panel);
            tamano += 210;
        }
        panelAbogadosContrarios.setPreferredSize(new Dimension(330,tamano));
        //panelAbogados.add(new JLabel("Nombre"));
        panelAbogadosContrarios.revalidate();
        panelAbogadosContrarios.repaint();
    }//GEN-LAST:event_btnAnadirAbogContrarioActionPerformed
    
    public void guardar(){
        procedimiento.anadirABBDD();
        anadido = true;
        if(idPadre != null){
            procedimiento.anadirPadreBBDD(idPadre);     
            ventanaHijo.setVisible(false);
        }
    }
    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardar();       
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNAutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNAutoFocusLost
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setnAuto(texto.getText());
    }//GEN-LAST:event_txtNAutoFocusLost

    private void txtJuicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJuicioFocusLost
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setJucio(texto.getText());
    }//GEN-LAST:event_txtJuicioFocusLost

    private void txtCuantiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCuantiaFocusLost
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setCuantia(texto.getText());
    }//GEN-LAST:event_txtCuantiaFocusLost

    private void txtJuzgadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJuzgadoActionPerformed
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setJuzgado(texto.getText());
    }//GEN-LAST:event_txtJuzgadoActionPerformed

    private void txtJuzgadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJuzgadoFocusLost
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setJuzgado(texto.getText());
    }//GEN-LAST:event_txtJuzgadoFocusLost

    private void txtNAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNAutoActionPerformed
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setnAuto(texto.getText());
    }//GEN-LAST:event_txtNAutoActionPerformed

    private void txtJuicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJuicioActionPerformed
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setJucio(texto.getText());
    }//GEN-LAST:event_txtJuicioActionPerformed

    private void txtCuantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuantiaActionPerformed
        // TODO add your handling code here:
        JTextField texto = (JTextField) evt.getSource();
        procedimiento.setCuantia(texto.getText());
    }//GEN-LAST:event_txtCuantiaActionPerformed

    private void btnAnadirSubprocedimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirSubprocedimientoActionPerformed
        // TODO add your handling code here:
        AnadirSubprocedimientoSwing ventana = new AnadirSubprocedimientoSwing(null,true,procedimiento.getId());
        ventana.setVisible(true);
        if(ventana.getAnadido() == true){
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Raiz");
            ConexionMySQL con = new ConexionMySQL();
            anadirARaiz(raiz,con,procedimiento.getId());
            DefaultTreeModel model = new DefaultTreeModel(raiz);
            TreeSubProc.setModel(model);
            TreeSubProc.revalidate();
            TreeSubProc.repaint();
        }        
    }//GEN-LAST:event_btnAnadirSubprocedimientoActionPerformed

    private void TreeSubProcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TreeSubProcMouseClicked
        // TODO add your handling code here:
        /*if(evt.getClickCount()==1){
            System.out.println("Se ha hecho un click");
        }*/
        if(evt.getClickCount()==2){
            DefaultMutableTreeNode nodoSeleccionado;
            nodoSeleccionado = (DefaultMutableTreeNode) TreeSubProc.getLastSelectedPathComponent();
            String id = nodoSeleccionado.getUserObject().toString();
            if(id.compareToIgnoreCase("Subprocedimientos") != 0){
                ventanaPrincipal.buscar(id);
            }            
            System.out.println("Se ha hecho doble click");
            //ventanaPrincipal.buscar(selecc.);
        }
    }//GEN-LAST:event_TreeSubProcMouseClicked

    public boolean getAnadido(){
        return anadido;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree TreeSubProc;
    private javax.swing.JButton btnAnadirAbog;
    private javax.swing.JButton btnAnadirAbogContrario;
    private javax.swing.JButton btnAnadirSubprocedimiento;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelAbogados;
    private javax.swing.JPanel panelAbogadosContrarios;
    private javax.swing.JPanel panelTree;
    private javax.swing.JScrollPane scrollTree;
    private javax.swing.JTextField txtCuantia;
    private javax.swing.JTextField txtJuicio;
    private javax.swing.JTextField txtJuzgado;
    private javax.swing.JTextField txtNAuto;
    // End of variables declaration//GEN-END:variables
}
