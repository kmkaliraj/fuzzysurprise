package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import associationrules.*;
import frequentpatterns.*;
import fuzzylogic.*;

public class mainfile{
	 
	public static void main (String [] arg) throws Exception {
		
		
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the file name:");
		String fname = br.readLine();
		fuzzification context = new fuzzification();
		try {
			context.loadFile(fileToPath(fname));
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		} 
		context.fuzzyfier();
		//System.out.println(" bye");
		context.createMatrix();
		context.printMatrix();
		
		ContextApriori con = new ContextApriori(context);
		con.createItems();
		int tsize = con.getSize();
		System.out.println("Total records: "+tsize);
		//System.out.println("Enter minimum support value:");
		//double minsupp = Double.parseDouble(br.readLine());

		System.out.println("Enter minimum confidence value:");
		double  minconf =Double.parseDouble(br.readLine()) ;
		System.out.println("Enter the threshold value:");
		double  tvalue =Double.parseDouble(br.readLine()) ;
		long startTime = System.currentTimeMillis();
				for(int i=0; i<con.getclasscontent().size();i++){
		content data = con.getclasscontent().get(i);
			System.out.println("size:"+data.size());
			//data.print();
		AlgoApriori apriori = new AlgoApriori(data,tsize);
		Itemsets patterns = apriori.runAlgorithm();
		patterns.printItemsets(data.size());
		
		AlgoAgrawalFaster94 algoAgrawal = new AlgoAgrawalFaster94(minconf);
		RulesAgrawal rules = algoAgrawal.runAlgorithm(patterns,data.getname());
		rules.printRules(data.size());
		rules.printsurprisingRules(tvalue);
		  
		}
		
		long endTime = System.currentTimeMillis();
		
		//System.out.println("\n end time time: "+ endTime);
		System.out.println("\n Total Execution time: "+ (endTime-startTime));
		
		}
		public static String fileToPath(String filename) throws UnsupportedEncodingException{
			URL url = mainfile.class.getResource(filename);
			 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");	
}
}