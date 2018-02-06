package common.frame.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

 public class ExcelProvider implements Iterator<Object[]>{
	
	 private Workbook book = null;
	 private Sheet sheet = null;
	 private int rowNum = 0;
	 private int curRowNo = 0;
	 private int columnNum = 0;
	 private String[] columnnName;

	 public ExcelProvider(Object aa, String sheetName, String envTestID)
	 {
	     try
	     {
	    	 
//	    	 String patha = new File("./").getCanonicalPath();
//	    	 File pathc = new File(System.getProperty("user.dir"));
//	    	 
//	    	 System.out.println("canonicalpath="+patha);
//	    	 System.out.println("user.dir="+pathc);
	    	 
//	         this.book = Workbook.getWorkbook(
//	         new File(new File("./").getCanonicalPath() + 
//	          "\\src\\main\\resources\\datadriver\\" + envTestID + "\\" +  aa.getClass().getSimpleName() + ".xls"));
//	         System.out.println("数据驱动url:" + 
//	          new File(new StringBuilder(String.valueOf(new File("./").getCanonicalPath()))
//	          .append("\\src\\main\\resources\\datadriver\\").append(envTestID).append("\\").append(aa.getClass().getSimpleName())
//	          .append(".xls").toString()));
	    	 
	         this.book = Workbook.getWorkbook(
	         new File(new File("./").getCanonicalPath() + "//testdata//"+ envTestID + "//"+ aa.getClass().getSimpleName() + ".xls"));
	         System.out.println("数据驱动url:" + 
	          new File(new StringBuilder(String.valueOf(new File("./").getCanonicalPath()))
	          .append("//testdata//").append(envTestID)
	          .append("//").append(aa.getClass().getSimpleName())
	          .append(".xls").toString()));
	    	 
	         
	        this.sheet = this.book.getSheet(sheetName);
	  
	        this.rowNum = this.sheet.getRows();
	  
	        Cell[] c = this.sheet.getRow(0);
	        this.columnNum = c.length;
	        this.columnnName = new String[c.length];
	        for (int i = 0; i < c.length; ++i) {
	          this.columnnName[i] = c[i].getContents().toString();
	        }
	  
	        this.curRowNo += 1;
	      } catch (BiffException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  
	    public boolean hasNext()
	    {
	      if ((this.rowNum == 0) || (this.curRowNo >= this.rowNum)) {
	        this.book.close();
	        return false;
	      }
	      return true;
	    }
	  
	   public Object[] next()
	    {
	     Cell[] c = this.sheet.getRow(this.curRowNo);
	     Map s = new HashMap();
	  
	      for (int i = 0; i < this.columnNum; ++i) {
	        s.put(this.columnnName[i], c[i].getContents().toString());
	      }
	      this.curRowNo += 1;
	      return new Object[] { s };
	    }
	 
	    public void remove()
	    {
	      throw new UnsupportedOperationException("remove unsupported.");
	    }
	 	 
}
