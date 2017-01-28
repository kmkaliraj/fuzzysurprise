package fuzzylogic;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;


public class fuzzification {
	
	private final  List<List<String>> M_Matrix = new  ArrayList<List<String>>();
	private final List<String> classvalues = new ArrayList<String>();
	private final List<String> classname = new ArrayList<String>();
	private final List<fuzzyset> attlist = new ArrayList<fuzzyset>();
	private int tnum = 0;
	private int cid =1;

	public void loadFile(String path) throws IOException {
		
		String thisLine;
		
		BufferedReader myInput = null;
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			myInput = new BufferedReader(new InputStreamReader(fin));
			int i=0;
			while ((thisLine = myInput.readLine()) != null) {
				if(thisLine.charAt(0) != '#'){ 
					if(i==0){
						 StringTokenizer st = new StringTokenizer(thisLine);
						 int c =1;
						 while (st.hasMoreTokens()){
					     	 String attr = st.nextToken();
					     	fuzzyset attribute = new fuzzyset(attr,c++);
					     	attlist.add(attribute);
					     	
					}i++;
						 }
					else{
					addObject(thisLine,i);
					i++;
					tnum = tnum+i;
					}
					
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(myInput != null){
				myInput.close();
			}
	    }//for(String s:classname)
	    	//System.out.println(s);
		
			}
	
	public void addObject(String attribute, int i){
		 
		StringTokenizer st = new StringTokenizer(attribute);
           int count = 0 ;
	      while (st.hasMoreTokens()){
	     	 String att = st.nextToken();
	     	fuzzyelement item = null;
	     	 if(count == attlist.size()-1){
	     		 item = new fuzzyelement(att,cid++);
	     	if(!(classname.contains(att)))
	     	   classname.add(att); 
	     	 }
	     	 else
	     	 item = new fuzzyelement(Double.parseDouble(att));
	     	    attlist.get(count).addAttributeElements(item);
	     		count++; 
	     	 }
	      //tnum = tnum+count;
		  }
		
public void fuzzyfier()throws IOException{
	    List<String> row = new LinkedList<String>();
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    double overlap = 0;
	    //int num =0;
	    
	    
	     for(int i=0; i<attlist.size();i++){
	   
	    	 if(i< attlist.size()-1){
	    		 attlist.get(i).setvalue();
		    	 attlist.get(i).calculateMean();
		    	 System.out.println("Mean: "+attlist.get(i).getmean());
		    	 attlist.get(i).caculateDeviation();
		    	 System.out.println("Deviation: "+attlist.get(i).getdeviation());
		    	 System.out.println("Lowvalue: "+attlist.get(i).getlowvalue());
		    	 System.out.println("highvalue: "+attlist.get(i).gethighvalue());
		    	 System.out.println("\n Enter the overlap value [0 - 1]:");
		    	overlap =Double.parseDouble((br.readLine()));
		    	// System.out.println("\n Enter the number of categories:");
		    	// num =Integer.parseInt((br.readLine()));
		    	 attlist.get(i).setbound(overlap);
		    	 //attlist.get(i).copyvalues();
		    	// System.out.println("hi");
		    	 
		    	 attlist.get(i).addFuzzyValues();	
		    	 //attlist.get(i).printsubset();
		    	  attlist.get(i).createSubmatrix();
		    	 // attlist.get(i).print(); String a =attlist.get(i).getname() ;
		    		//System.out.println("Enter the numer of class labels:");
		    		//int num =Integer.parseInt((br.readLine()));
	    	 }
	    	 else{  
	    		 String a = attlist.get(i).getname();
		    		for(int c=0; c<classname.size(); c++){
		    			String name = classname.get(c);
		    			System.out.println("class name:"+" "+name);		    			
		    			subset set = new subset( (c+1),a+"-"+name);
		    			attlist.get(i).getfuzzysubset().add(set) ; 
		    		}
	    	 
	    		//System.out.println("Enter the name  class value:");
  			   //String name = br.readLine();
  			   attlist.get(i).addclass(classname);
  			   //attlist.get(i).printsubset();
  			   attlist.get(i).createclassmatrix();
	    		 
	    	 }	    	 
	    	 
	    	 for(int j=0;j<attlist.get(i).getfuzzysubset().size();j++)
	    		 row.add(attlist.get(i).getfuzzysubset().get(j).getSubsetName());
	     }
	     M_Matrix.add(row);
		
	}
public void createMatrix(){
	
	for(int j=0; j<attlist.get(1).getsize();j++){
		List<String> row = new ArrayList<String>();
	for(int i=0;i<attlist.size(); i++){
		fuzzyset f = attlist.get(i);
		row.addAll(f.getsubrow(j));
	}     
                M_Matrix.add(row); 
                }
}

public void printMatrix(){
	 
	System.out.println("\n\n\t\t\t\t\t\t\t.............. Minable Matrix..................");
	  
	System.out.print("\n");
	for(List<String> linguistic:M_Matrix){
		  
		  for(String lingvalue:linguistic){
			  System.out.print("\t "+lingvalue);
		  }
		  System.out.print("\n");
		  }
	
}/* public int size(){
	return this.tnum;*/

public List<List<String>> getMatrix(){
	return this.M_Matrix;
}
public List<fuzzyset> getAttList(){
	return this.attlist;
}

}