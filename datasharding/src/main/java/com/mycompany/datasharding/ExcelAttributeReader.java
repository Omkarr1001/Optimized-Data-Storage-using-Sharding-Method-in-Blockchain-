package com.mycompany.datasharding;

import java.io.File;
import java.util.ArrayList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class ExcelAttributeReader 
{    
     public ArrayList getAttributesName(String path)
    {
        ArrayList m=new ArrayList();

      try
        {
            Workbook ReadExcel = Workbook.getWorkbook(new File(path));
            Sheet sheet = ReadExcel.getSheet(0);
            int a=sheet.getColumns();
            int b=sheet.getRows();
                    
            for (int i=0;i<1;i++)
            {
                ArrayList in=new ArrayList();
                for(int j=0;j<a;j++)
                {

                Cell a1 = sheet.getCell(j,i); /* Get the first cell of Column A , 0 maps to A */

                String ed = a1.getContents();
                ed=ed.trim();
                ed=ed.toLowerCase();

                m.add(ed);
                }
           
               
            }
              ReadExcel.close();
            }
        catch (Exception i)
        {
            System.out.println("Exception in ExcelAttributeReader Class in  "+i);
        }

        m.add("shardnum");
        return m;
        
    }
        
}
    
