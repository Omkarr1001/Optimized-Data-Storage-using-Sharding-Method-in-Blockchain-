package bilinearpairing;

import com.mycompany.datasharding.VerifyDataIntegrityFrame;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import keygeneration.KeyGen;
import mongodboperation.MongoDBUpdater;
import mongodboperation.Mongoinfo;

public class TamperedDataFinder {
     ArrayList tamepereddata=new ArrayList();
    
    
    void gettamperedrow(ArrayList orgdatainfo, ArrayList currentdatainfo, ArrayList dataverification, ArrayList attributes)
    {
        ArrayList tampereddata=new ArrayList();
        int size=orgdatainfo.size();
        
        ArrayList tamperdata=new ArrayList();
        
        for (int i = 0; i <dataverification.size(); i++) 
        {
            ArrayList row=(ArrayList) dataverification.get(i);
            String status=(String) row.get(1);
            status=status.trim();
            
            if(status=="Tampered")
            {
                String shardname=(String) row.get(0);
                
                ArrayList orgsharddata=new ArrayList();
                ArrayList cursharddata=new ArrayList();
                
                for (int j = 0; j <size; j++) 
                {
                   ArrayList row1=(ArrayList) orgdatainfo.get(j);
                   ArrayList row2=(ArrayList) currentdatainfo.get(j);
                   
                   String orgsharnum1=(String) row1.get(row1.size()-1);
                   String cursharnum1=(String) row2.get(row2.size()-1);
                   
                   if(orgsharnum1.contains(shardname))
                   {
                       orgsharddata.add(row1);
                   }
                   if(cursharnum1.contains(shardname))
                   {
                       cursharddata.add(row2);
                   }
                }
                
              
//                System.out.println("attributes: "+attributes);


              //  System.out.println("Orgshrddata");
             //   orgsharddata.forEach(name->System.out.println(name));
                
              //  System.out.println("Currentdata");
              //  cursharddata.forEach(name->System.out.println(name));
                
                String orgtemkey="";
                String currtemkey="";
                for (int j = 0; j <orgsharddata.size(); j++) 
                {
                    if(j==0)
                    {
                    String blockinfo1="";
                    String blockinfo2="";
                    ArrayList row3 = (ArrayList) orgsharddata.get(j);
                    ArrayList row4 = (ArrayList) cursharddata.get(j);
                    for (int k = 0; k < row3.size(); k++)
                    {
                        String rowvalue1 = (String) row3.get(k);
                        blockinfo1 = blockinfo1 + rowvalue1;
                        
                        String rowvalue2 = (String) row4.get(k);
                        blockinfo2 = blockinfo2 + rowvalue2;
                    }
                    blockinfo1=blockinfo1.trim();
                    blockinfo1=blockinfo1+orgtemkey;
                    blockinfo2=blockinfo2.trim();
                    blockinfo2=blockinfo2+currtemkey;
                    
                    
                    String oblockkey= new KeyGen().getKey(blockinfo1);
                    orgtemkey=oblockkey;
                    String cblockkey= new KeyGen().getKey(blockinfo2);
                    currtemkey=cblockkey;
                    
                    if(!oblockkey.equals(cblockkey))
                    {
                      getTamperedAttributes(orgsharddata,cursharddata,attributes, shardname) ; 
                    }
                      
                    }
                    else
                    {
                        String blockinfo1="";
                    String blockinfo2="";
                    ArrayList row3 = (ArrayList) orgsharddata.get(j);
                    ArrayList row4 = (ArrayList) cursharddata.get(j);
                    for (int k = 0; k < row3.size(); k++)
                    {
                        String rowvalue1 = (String) row3.get(k);
                        blockinfo1 = blockinfo1 + rowvalue1;
                        
                        String rowvalue2 = (String) row4.get(k);
                        blockinfo2 = blockinfo2 + rowvalue2;
                    }
                    blockinfo1=blockinfo1.trim();
                    blockinfo2=blockinfo2.trim();
                    
                    
                    String oblockkey= new KeyGen().getKey(blockinfo1);
                    
                    orgtemkey=oblockkey;
                    String cblockkey= new KeyGen().getKey(blockinfo2);
                    currtemkey=cblockkey;
                    
                    if(!oblockkey.equals(cblockkey))
                    {
                       //  System.out.println("Oblockkey: "+oblockkey);
                      //  System.out.println("cblockkey: "+cblockkey);
                      getTamperedAttributes(orgsharddata,cursharddata,attributes, shardname) ; 
                    }
                        
                    }
                    
                }
            }
        }
        
    }
    
    void getTamperedAttributes(ArrayList orgsharddata,ArrayList cursharddata,ArrayList attributes, String shardname)
    {
        
        for (int i = 0; i < orgsharddata.size(); i++) 
        {
           ArrayList row1=(ArrayList) orgsharddata.get(i);
           ArrayList row2=(ArrayList) cursharddata.get(i);
           
            for (int j = 0; j <row1.size(); j++) 
            {
                ArrayList temp=new ArrayList(); 
                String str1 = (String) row1.get(j);
                String str2 = (String) row2.get(j);
                if (!str1.equals(str2)) 
                {
                    temp.add(shardname);
                    temp.add(row1.get(0));
                    temp.add((String) attributes.get(j));
                   MongoDBUpdater mb=new MongoDBUpdater();
                   mb.getupdate(Mongoinfo.datacollectionname, row1, (String) row1.get(0), temp, j ); 
                    
                }

                if(temp.size()!=0)
                {
                   tamepereddata.add(temp); 
                }
                
                
                
            }
              
        }
        
        HashSet hs=new HashSet();
        hs.addAll(tamepereddata);
        tamepereddata.clear();
        tamepereddata.addAll(hs);
        
     //   System.out.println("Tameperd Data: "+tat);
        
         ((DefaultTableCellRenderer)VerifyDataIntegrityFrame.jTable2.getTableHeader().getDefaultRenderer())
           .setHorizontalAlignment((int) TOP_ALIGNMENT);
         
                    
            DefaultTableModel model = (DefaultTableModel) VerifyDataIntegrityFrame.jTable2.getModel();
             model.setRowCount(0);
       
           
           DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment((int) CENTER_ALIGNMENT);
           int x=VerifyDataIntegrityFrame.jTable2.getColumnCount();
        for(int i=0;i<x;i++)
        {
            VerifyDataIntegrityFrame.jTable2.getColumnModel().getColumn(i).setCellRenderer(rendar); 
        }
        
        VerifyDataIntegrityFrame.jTable2.setRowHeight(40);
        Object rowData[] = new Object[3];
       
         for(int i=0;i<tamepereddata.size();i++)
         {
            
             ArrayList temp=(ArrayList) tamepereddata.get(i);
             
             for(int j=0;j<temp.size();j++)
             {
             String str=(String) temp.get(j);
//             System.out.println(str);
             rowData[j]=str;
             }
             
             model.addRow(rowData);
             
                          
         }
       
        
    }
    
    
}
