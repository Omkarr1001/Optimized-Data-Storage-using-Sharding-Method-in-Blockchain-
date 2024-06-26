package bilinearpairing;

import com.mycompany.datasharding.VerifyDataIntegrityFrame;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import keygeneration.KeyGen;
import mongodboperation.MongDataExtractor;
import mongodboperation.Mongoinfo;

public class BilinearPairingInit extends Thread
{
    ArrayList temblockinfo=new MongDataExtractor().getBlockchaininfo(Mongoinfo.blockkeycollectionname, Mongoinfo.blockattributename);
    ArrayList orgdatainfo=new MongDataExtractor().getBlockchaininfo(Mongoinfo.datacollectionname, Mongoinfo.dataattributename);
    public ArrayList attributes=new ArrayList();
   
    public void run()
    {
        
        ArrayList currentdatainfo = new ArrayList();       
        attributes = Mongoinfo.dataattributename;
                
        int count = 0;
        int status=0;
        
        while (true)
        {
            System.out.println("thread is running...");
            
            currentdatainfo= new MongDataExtractor().getBlockchaininfo(Mongoinfo.datacollectionname, Mongoinfo.dataattributename);

            try
            {
                
                ArrayList dataverification=new ArrayList();
           
                 for (int i = 0; i <temblockinfo.size(); i++) 
             {
                 ArrayList temp=new ArrayList();
                 ArrayList row1= (ArrayList) temblockinfo.get(i);
                 String shardname=(String) row1.get(0);
                 String orgblockkey=(String) row1.get(1);
                 
                 temp.add(shardname);
                 
                 ArrayList sharddata=new ArrayList();
                 for (int j = 0; j <currentdatainfo.size(); j++) 
                 {
                     ArrayList row=(ArrayList) currentdatainfo.get(j);
                     String shardnum=(String) row.get(row.size()-1);
                     
                     if(shardnum.contains(shardname))
                     {
                         sharddata.add(row);
                     }
                 }
                 
                 ArrayList temblockKey=new ArrayList();
                 
                 for (int j = 0; j <sharddata.size(); j++) 
                 {
                     if(j==0)
                {
                    String blockinfo="";
                    ArrayList row = (ArrayList) sharddata.get(j);
                    for (int k = 0; k < row.size(); k++)
                    {
                        String rowvalue = (String) row.get(k);
                        blockinfo = blockinfo + rowvalue;
                    }
                    blockinfo=blockinfo.trim();
                    String blockkey= new KeyGen().getKey(blockinfo);
                    temblockKey.add(blockkey);

                }
                else
                {
                    String blockinfo="";
                    ArrayList row = (ArrayList) sharddata.get(j);
                    for (int k = 0; k < row.size(); k++)
                    {
                        String rowvalue = (String) row.get(k);
                        blockinfo = blockinfo + rowvalue;
                    }
                    blockinfo=blockinfo.trim();
                    blockinfo=blockinfo+temblockKey.get(temblockKey.size()-1);
                    String blockkey= new KeyGen().getKey(blockinfo);
                    temblockKey.add(blockkey);
                    
                }
                
                 }
                 
                 String terminalkey=(String) temblockKey.get(temblockKey.size()-1);
                 if(orgblockkey.equals(terminalkey))
              {
                  temp.add("Original");
                  dataverification.add(temp);
                  
              }
              else if(!orgblockkey.equals(terminalkey))
              {
                  temp.add("Tampered");
                  dataverification.add(temp);
              }
                 
             }
          
               //  System.out.println("Data Verfication: "+dataverification);
                 
               new TamperedDataFinder().gettamperedrow(orgdatainfo,currentdatainfo, dataverification, attributes);
             
               
             ((DefaultTableCellRenderer)VerifyDataIntegrityFrame.jTable1.getTableHeader().getDefaultRenderer())
           .setHorizontalAlignment((int) TOP_ALIGNMENT);
         
                    
            DefaultTableModel model = (DefaultTableModel) VerifyDataIntegrityFrame.jTable1.getModel();
             model.setRowCount(0);
       
           
           DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment((int) CENTER_ALIGNMENT);
           int x=VerifyDataIntegrityFrame.jTable1.getColumnCount();
        for(int i=0;i<x;i++)
        {
            VerifyDataIntegrityFrame.jTable1.getColumnModel().getColumn(i).setCellRenderer(rendar); 
        }
        
        VerifyDataIntegrityFrame.jTable1.setRowHeight(40);
        Object rowData[] = new Object[2];
       
         for(int i=0;i<dataverification.size();i++)
         {
            
             ArrayList temp=(ArrayList) dataverification.get(i);
             
             for(int j=0;j<temp.size();j++)
             {
             String str=(String) temp.get(j);
//             System.out.println(str);
             rowData[j]=str;
             }
             
             model.addRow(rowData);
             
                          
         }
           
            
                Thread.sleep(1000);
                 
            }
            catch (Exception e)
            {
                System.out.println("Exception in BilinearPairingInit Class is: "+e);
            }
        }
        
    }
    
    
    
    
     
    
}
