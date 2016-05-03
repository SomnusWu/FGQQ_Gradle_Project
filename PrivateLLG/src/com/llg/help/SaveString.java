package com.llg.help;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.llg.privateproject.utils.CommonUtils;

public class SaveString {
	private   String path = "";
	//保存字符串到文件中
	String content;
	public SaveString (){
		
	}
	public  void saveAsFileWriter(String content) {
		
	 FileWriter fwriter = null;
	  path=CommonUtils.createSDCardDir()+"/homepage.txt";
	 try {
		 File path1=new File(path);
		 if (!path1.exists()) {
				path1.mkdirs();
			}
	  fwriter = new FileWriter(path1);

		 fwriter.write(content);
	 } catch (IOException ex) {
	  ex.printStackTrace();
	 } finally {
	  try {
	   fwriter.flush();
	   fwriter.close();
	  } catch (IOException ex) {
	   ex.printStackTrace();
	  }
	 }
	}


	/**
	     * 以行为单位读取文件，常用于读面向行的格式化文件
	     */
	    public  void readFileByLines(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                System.out.println("line?????????????????????????????????? " + line + ": " + tempString);
	                content=tempString;
	                line++;
	            }
	        
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
}