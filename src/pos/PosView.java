package pos;
import pos.lib.DbConnect;
import pos.lib.BillingControls;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kanishka
 */
public class PosView extends javax.swing.JFrame {

    BillingControls billControl = new BillingControls();
    Connection conn = null;
       
     
    
    public PosView() {
        initComponents();
        DefaultTableModel tableModel =  (DefaultTableModel) table.getModel();
    
          conn = DbConnect.connect();     
        try {
            checkUnfinzlizeBIll();
        } catch (SQLException ex) {
            Logger.getLogger(PosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    //CLEAR TEXT FIELDS
    private void clear(){

        String clear = "";

         plu_1.setText(clear);
         desc_1.setText(clear);
         qty_1.setText(clear);
         price_1.setText(clear);
         plu_1.requestFocus();      
     }
    
     //BILL FINALIZE AND CANCEL CLEAR
    private void allClear(){
        DefaultTableModel tableModel =  (DefaultTableModel) table.getModel();
        
         String txt = "";

         plu_1.setText(txt);
         desc_1.setText(txt);
         qty_1.setText(txt);
         price_1.setText(txt);
         NetAmountTextField.setText(txt);
         CashAmountTextField.setText(txt);
         //TABLE CLEAR
         while(tableModel.getRowCount() > 0)
                    {
                    tableModel.removeRow(0);
                        }
         plu_1.requestFocus();      
     }
      
    //DISPLAY ADDED ITEMS
    private void displayAddItems()throws SQLException{
        
       DefaultTableModel tableModel =  (DefaultTableModel) table.getModel();
    
       ResultSet display = billControl.displayAddItemsQuery();
       
       
        while(tableModel.getRowCount() > 0)
                    {
                    tableModel.removeRow(0);
                        }
                         while(display.next()) {
                             int rec = display.getInt(1);
                             int seq2 = display.getInt(2);
                             int plu = display.getInt(3);
                             String desc2 = display.getString(4);
                             int qty2 = display.getInt(5);
                             int price2 = display.getInt(6);
                             int total_price = display.getInt(5)*display.getInt(6);
                             tableModel.addRow(new Object[]{rec,seq2,plu,desc2,price2,qty2,total_price});                               
                             }
                         display.close();
                         
    }
    
            
       
      //BILL FINALIZE
       private  void billFinalize(int recno,float netamount, float cashamount)throws SQLException{
           
           float changeamt = cashamount-netamount;
           
           if(netamount>cashamount){
                
                JOptionPane.showMessageDialog(rootPane,"Cash Balance Not Enough");
                
            }else{
               
                billControl.billFinalizeQuery(recno, netamount, cashamount);
                    
                JOptionPane.showMessageDialog(rootPane,"Balance Rs "+changeamt);                 
                allClear();
                ReciptnoLabel.setText(Integer.toString(billControl.getReceiptno()));
   
           }       
           
       }
       
       //BILL CANCEL
       private  void billCANCEL(int recno,float netamount)throws SQLException{
           
          
               if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                   
                billControl.billCANCEL(recno, netamount);
                allClear();   
                ReciptnoLabel.setText(Integer.toString(billControl.getReceiptno()));
                            } else{
                                //Null
                                    }  
                            } 
       
         //CHECK UNFINALIZE BILLS
        private void checkUnfinzlizeBIll()throws SQLException{
        
           
            if(billControl.checkUnfinzlizeBIll()>=1){
                
                int dialogButton = JOptionPane.showConfirmDialog (null, "There is a unfinalize bill. Do you want to recall it?","WARNING",JOptionPane.YES_NO_OPTION);
                
                if (dialogButton == JOptionPane.YES_OPTION) {
                    displayAddItems();
                    NetAmountTextField.setText(Float.toString(billControl.netAmount()));
                    ReciptnoLabel.setText(Integer.toString(billControl.getReceiptno()));
                    
                }else{
                    
                    billCANCEL(billControl.getReceiptno(), billControl.netAmount());
                
             } 
                
      
                }else{
                ReciptnoLabel.setText(Integer.toString(billControl.getReceiptno()));
            }
        }
        
        
        private  void  itemCancel(int row) throws SQLException{
          DefaultTableModel tableModel =  (DefaultTableModel) table.getModel();   
           if(billControl.itemCancelQuery(row)==false){
               displayAddItems();
               String netamt1 =  Float.toString(billControl.netAmount());
               NetAmountTextField.setText(netamt1);
               
           }else{
               
               JOptionPane.showMessageDialog(rootPane,"Unable to cancel selected item");
               
           }
           
            
        }
        
        
        
        
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        plu_1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        desc_1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        qty_1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        price_1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        ReciptnoLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        NetAmountTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        CashAmountTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POS");
        setResizable(false);

        jLabel1.setText("PLU ");

        plu_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plu_1ActionPerformed(evt);
            }
        });
        plu_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plu_1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                plu_1KeyReleased(evt);
            }
        });

        jLabel2.setText("Description");

        desc_1.setEditable(false);

        jLabel3.setText("Qty");

        qty_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qty_1KeyPressed(evt);
            }
        });

        jLabel4.setText("Price");

        price_1.setEditable(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Receipt No", "Seq No", "PLU Code", "Description", "Unit Price", "Qty", "Total Qty Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(70);
            table.getColumnModel().getColumn(0).setMaxWidth(70);
            table.getColumnModel().getColumn(1).setMinWidth(40);
            table.getColumnModel().getColumn(1).setMaxWidth(40);
            table.getColumnModel().getColumn(2).setMinWidth(60);
            table.getColumnModel().getColumn(2).setMaxWidth(60);
            table.getColumnModel().getColumn(3).setMinWidth(350);
            table.getColumnModel().getColumn(3).setMaxWidth(350);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel5.setText("Receipt No");

        ReciptnoLabel.setText("1");

        jLabel7.setText("Net Amount ");

        NetAmountTextField.setEditable(false);

        jButton1.setText("Bill Finalize");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Cash Amount");

        jButton2.setText("Cancel Full Bill");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Item cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(plu_1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(desc_1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(qty_1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(price_1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(ReciptnoLabel))))
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jButton2)
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CashAmountTextField)
                            .addComponent(NetAmountTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ReciptnoLabel))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(plu_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(desc_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(qty_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(price_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(NetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CashAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void plu_1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plu_1KeyReleased
       
    }//GEN-LAST:event_plu_1KeyReleased

    private void plu_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plu_1KeyPressed
        
        String plu_code = plu_1.getText();
               
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            
            try {
                
                ResultSet search = billControl.searchQuery(plu_code);
                
                
                if(search.next()){
                    
                    String desc = search.getString(2);
                    desc_1.setText(desc);
                    
                    String price = search.getString(3);
                    price_1.setText(price);
                    
                    qty_1.requestFocus();
                    search.close();
                   
                }else{
                    
                    JOptionPane.showMessageDialog(rootPane,"Invalid PLU Code");
                    search.close();
                }    
            } catch (SQLException ex) {
                Logger.getLogger(PosView.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }      
    }//GEN-LAST:event_plu_1KeyPressed

    private void plu_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plu_1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plu_1ActionPerformed

    private void qty_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qty_1KeyPressed
           
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c ==KeyEvent.VK_DELETE)){
            
            evt.consume();
        }
        
                String plucode = plu_1.getText();
                String desc = desc_1.getText();
                String qty = qty_1.getText();
                String price = price_1.getText();
               
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
              
             
                 try{ 
                     
                    String query = "INSERT INTO `tmp_det`(`receiptno`, `seq_no`, `plu_code`, `decription`, `qty`, `price`, `status`) VALUES ("+billControl.getReceiptno()+","+billControl.seqCount()+","+plucode+",'"+desc+"',"+qty+","+price+",'VALID')";
                     
                       
                    if(billControl.insertItems(query)== false){
                       
                       displayAddItems();
                       String netamt1 =  Float.toString(billControl.netAmount());
                       NetAmountTextField.setText(netamt1);
                       clear();
                       
                   }else{
                       
                       JOptionPane.showMessageDialog(rootPane,"Error occured in adding items");
                       
                   }
                       
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(rootPane,e);
            
        }
      }        
    }//GEN-LAST:event_qty_1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
            float netamount = Float.parseFloat(NetAmountTextField.getText());
            float cashamount = Float.parseFloat(CashAmountTextField.getText());
            
        try {
            billFinalize(billControl.getReceiptno(),netamount,cashamount);
        } catch (SQLException ex) {
            Logger.getLogger(PosView.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        float netamount = Float.parseFloat(NetAmountTextField.getText());
        
        try {
            billCANCEL(billControl.getReceiptno(),netamount);
        } catch (SQLException ex) {
            Logger.getLogger(PosView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        try {
            DefaultTableModel tableModel =  (DefaultTableModel) table.getModel();
            
            int selectedrow = table.getSelectedRow();
            
            itemCancel(selectedrow);
        } catch (SQLException ex) {
            Logger.getLogger(PosView.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
        
    }//GEN-LAST:event_jButton3ActionPerformed
 
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
            java.util.logging.Logger.getLogger(PosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new PosView().setVisible(true);
                
                   
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CashAmountTextField;
    private javax.swing.JTextField NetAmountTextField;
    private javax.swing.JLabel ReciptnoLabel;
    private javax.swing.JTextField desc_1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField plu_1;
    private javax.swing.JTextField price_1;
    private javax.swing.JTextField qty_1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
