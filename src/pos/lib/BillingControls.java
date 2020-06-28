package pos.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author kanishka
 */
public class BillingControls {
    
   
    
            Connection conn = DbConnect.connect();
    
//SEARCH ITEM     
public  ResultSet searchQuery(String plu_code) throws SQLException{

    String pre = ("SELECT * FROM `item_master` where plu_code ='"+plu_code+"'");
    PreparedStatement pst = conn.prepareStatement(pre);
        
    ResultSet rs = pst.executeQuery();
    
        return rs;
      
    }          

//TO GET THE RECEIPTNO
     public int getReceiptno()throws SQLException{
         
         int count=0;
         
        String pre = ("SELECT max(receiptno) FROM `pos_mas`;");
        PreparedStatement pst = conn.prepareStatement(pre);
        ResultSet rs = pst.executeQuery();
        rs.next();
         count = rs.getInt(1);
         count = count+1;
         return count;   
        }
     
       //TO GET THE SEQ NUMBER
    public int seqCount()throws SQLException{
    
        String pre = ("SELECT count(*) FROM `tmp_det`;");
        PreparedStatement pst = conn.prepareStatement(pre);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count;   
        }
     
     //ADD ITEMS TO THE BILL
    public  boolean insertItems(String query)throws SQLException{
        
               boolean status = true;
        
        String pre = (query);
        PreparedStatement pst = conn.prepareStatement(pre);
        
        status = pst.execute();
        pst.close();
        
                  
            return status;
           
        
    }
     
    
        //DISPLAY ADDED ITEMS
    public ResultSet displayAddItemsQuery()throws SQLException{
        
        String stat = ("SELECT * FROM `tmp_det`WHERE status ='VALID'");
        PreparedStatement pst = conn.prepareStatement(stat);
        ResultSet rs = pst.executeQuery();
        
        
        return rs;
                         
    }
    
      //DISPLAY BILL NET AMOUNT
       public float netAmount()throws SQLException{

           float netamt = 0;

           String sum = ("SELECT sum(qty*price) FROM `tmp_det` WHERE status ='VALID'");
           PreparedStatement pst2 = conn.prepareStatement(sum);
           ResultSet rs2 = pst2.executeQuery();

           while (rs2.next()) {
               netamt = rs2.getInt(1);
           }

           return netamt;
       }
       
       
       //BILL FINALIZE QUERY    
       public  void billFinalizeQuery(int recno,float netamount, float cashamount)throws SQLException{
           
           float changeamt = cashamount-netamount;
           
           Statement st = conn.createStatement();
           
       
               
                String queryDet = ("INSERT INTO pos_det SELECT * FROM tmp_det;");
                String queryMas = ("INSERT INTO `pos_mas`(`receiptno`, `net_amt`, `cash_amt`, `chang_amt`, `status`) VALUES ("+recno+","+netamount+","+cashamount+","+changeamt+",'VALID')");
                String queryDel = ("DELETE FROM `tmp_det`;");
                        
                st.addBatch(queryDet);
                st.addBatch(queryMas);
                st.addBatch(queryDel);
                st.executeBatch();
                st.close();
                
                
       }
       
          public  void billCANCEL(int recno,float netamount)throws SQLException{
           
                             
                Statement st = conn.createStatement();
               
                String queryDetCan = ("UPDATE tmp_det SET status = 'CAN';");
                String queryDet = ("INSERT INTO pos_det SELECT * FROM tmp_det;");
                String queryMas = ("INSERT INTO `pos_mas`(`receiptno`, `net_amt`, `cash_amt`, `chang_amt`, `status`) VALUES ("+recno+","+netamount+",0,0,'CAN');");
                String queryDel = ("DELETE FROM `tmp_det`;");
                    
                st.addBatch(queryDetCan);
                st.addBatch(queryDet);
                st.addBatch(queryMas);
                st.addBatch(queryDel);
                st.executeBatch();
                st.close();               
          }
       
       
          
          
          
       public int checkUnfinzlizeBIll()throws SQLException{
                    
           int count ;
           
            String stat = ("SELECT count(*) as count FROM `tmp_det`");
            PreparedStatement pst1 = conn.prepareStatement(stat);
            ResultSet rs = pst1.executeQuery();
            rs.next();
            count = rs.getInt(1);
           
            
            return count;
       }
       
       
       public boolean itemCancelQuery(int rowValue)throws SQLException{
                    
                boolean status = true;
                      
            String query = ("UPDATE tmp_det SET status = 'CAN' WHERE seq_no ="+rowValue);
            PreparedStatement pst = conn.prepareStatement(query);
            status = pst.execute();
            pst.close();
           
      
            return status;
       }
}