package fuzzylogic;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
//import java.math.*;
import java.util.LinkedList;

public  class fuzzyset {
	//private  double overlap = 0.03;
	private int sid;
	private String AttrName = null;
	private  double lowvalue;	
	private  double highvalue;
	private double mean;
	private  double deviation;
	private  List<fuzzyelement> fuzzyattribute = new ArrayList<fuzzyelement>();
	private  List<subset> fuzzysubset = new ArrayList<subset>();
	private List<List<String>> submatrix = new ArrayList<List<String>>();
	
	
	public fuzzyset(String name, int id){
		 this.AttrName = name;
		 this.sid = id;
	}
	public int getId(){
		return this.sid;
	}
	
	public String getname(){
		return this.AttrName;
	}
	public int getsize(){
		return this.fuzzyattribute.size();
	}
	public double getlowvalue(){
		return this.lowvalue;
	}
	public double gethighvalue(){
		return this.highvalue;
	}
	
	
	public double getmean(){
		return this.mean;
	}
	public double getdeviation(){
		return this.deviation;
	}
	public void addAttributeElements(fuzzyelement element){
		fuzzyattribute.add(element);
	}
	public List<fuzzyelement> getAttributeElements(){
		return fuzzyattribute;
	}
	public void setvalue(){
		double lvalue = (fuzzyattribute.get(0)).getItem();
		double hvalue = (fuzzyattribute.get(1)).getItem();
		double temp = 0;
		if(hvalue < lvalue)
		{
			temp = lvalue;
			lvalue = hvalue;
			hvalue = temp;
		}
		for( int i=2; i<fuzzyattribute.size();i++){
			temp = (fuzzyattribute.get(i)).getItem();			
			if( temp < lvalue) lvalue = temp;
			if(temp > hvalue) hvalue = temp;
		}
		this.lowvalue = lvalue;
		this.highvalue = hvalue;
		
	} 
	public void calculateMean(){
		
	double temp = 0;
	int i;
	  for(  i = 0; i < fuzzyattribute.size(); i++){
		   temp = temp + (fuzzyattribute.get(i)).getItem(); 
	  }
	  this.mean = ( temp / (i+1));
	}
	public void  caculateDeviation(){
		double temp = 0;
		double hold = 0;
		int i;
		for( i=0; i<fuzzyattribute.size();i++){
			hold = (fuzzyattribute.get(i)).getItem();
			temp = temp + ((hold - mean) * (hold - mean));
		}
		this.deviation = Math.sqrt( temp/(i+1));
	}
	 public void setbound(double overlap) throws IOException{
		 //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 double lb = 0;
		 double hb = 0;
		 String name = null;
		 for(int i=0; i<3;i++){
			 
		//System.out.println("\n Enter the name of the subset:");
		    //  name = br.readLine();
			 if(i==0){
				 name = "low";
				 lb = lowvalue;
				 hb = mean - deviation/2.0 + mean*overlap;
			 }
			 else if(i==2){
				 name = "high";
				 lb =(mean + deviation/2.0) - (mean*overlap);
				  hb = highvalue;
			 }
			 else {
				  name = "medium";
				  lb =  mean - (deviation/2.0) - mean*overlap;
				  hb = mean + deviation/2.0 + mean*overlap;
			 }
			 System.out.println("subsetname:"+AttrName+"-"+name+"\tlow:"+lb+"\thigh:"+hb);
			 subset set = new subset(lb,hb,i+1,AttrName+"-"+name);
			 fuzzysubset.add(set);
		 }
		
	 }
	 public double memberFunction(double value, double lb, double hb){
		 double support = 0;
		 support = (value - hb)/(lb-hb);
		 return support;
	 }
	/*public void copyvalues(){
		 for(fuzzyelement item: fuzzyattribute ){
		for(subset set: fuzzysubset)
			set.addElement(item);
		 }
	 }*/
	
	 public void addFuzzyValues(){
		//List<String> row = new LinkedList<String>();
		 double val = 0;
		 int i =0 ,k=0;
		 subset temp, next ,old;
		//fuzzyelement e1 =new fuzzyelement(0.0);
		//fuzzyelement e2 =new fuzzyelement(1.0);
		 for( i=0;i<fuzzyattribute.size();i++) {
			 val = fuzzyattribute.get(i).getItem();			 
			// System.out.println(val);
			 for( int j =0;j<fuzzysubset.size();j++){
				// temp =  fuzzysubset.get(j);
				// next = fuzzysubset.get(j+1);
				 if(j==0){
					fuzzyelement e1 =new fuzzyelement(0.0);
					 fuzzyelement e2 =new fuzzyelement(1.0);
					 temp =  fuzzysubset.get(j);
					 next = fuzzysubset.get(j+1);
					 if(val>= temp.getlowbound()&& val<= next.getlowbound()){
						 e1 = fuzzyattribute.get(i);
						 e1.setLvalue(1.0);
						 fuzzysubset.get(j).addElement(e1);
						 //System.out.println(fuzzysubset.get(j).getSubsetName()+":"+e1.getItem()+"\t"+e1.getLvalue());
					 }
					 else if(val > next.getlowbound()&&val <temp.gethighbound()){
						 double support = memberFunction(val,next.getlowbound(),temp.gethighbound());
						 e1 = fuzzyattribute.get(i);
						 //e2 = fuzzyattribute.get(i);
						 e1.setLvalue(support);
						 e2.setLvalue(1.0-support);
						 e2.setItem(fuzzyattribute.get(i).getItem());
						 fuzzysubset.get(j).addElement(e1);
						 fuzzysubset.get(j+1).addElement(e2);
						 //System.out.println(fuzzysubset.get(j).getSubsetName()+":"+e1.getItem()+"\t"+e1.getLvalue());
						 //System.out.println(fuzzysubset.get(j+1).getSubsetName()+":"+e2.getItem()+"\t"+e2.getLvalue());
					 }
				 }
				 
				 else if(j < fuzzysubset.size()-1&& j>0){
					 fuzzyelement e1 =new fuzzyelement(0.0);
					  fuzzyelement e2 =new fuzzyelement(0.0);
					 temp =  fuzzysubset.get(j);
					 next = fuzzysubset.get(j+1);
					 old = fuzzysubset.get(j-1);
					 if(val >= old.gethighbound()&& val<= next.getlowbound()){
						 e1 = fuzzyattribute.get(i);
						 e1.setLvalue(1.0);
						 fuzzysubset.get(j).addElement(e1);
						// System.out.println(fuzzysubset.get(j).getSubsetName()+":"+e1.getItem()+"\t"+e1.getLvalue());
						 
					 }
					 else if(val > next.getlowbound()&&val <temp.gethighbound()){
						 double support = memberFunction(val,next.getlowbound(),temp.gethighbound());
						 e1 = fuzzyattribute.get(i);
						 //e2 = fuzzyattribute.get(i);
						 e1.setLvalue(support);
						 e2.setLvalue(1.0-support);
						 e2.setItem(fuzzyattribute.get(i).getItem());
						 fuzzysubset.get(j).addElement(e1);
						 fuzzysubset.get(j+1).addElement(e2);
						// System.out.println(fuzzysubset.get(j).getSubsetName()+":"+e1.getItem()+"\t"+e1.getLvalue());
						 //System.out.println(fuzzysubset.get(j+1).getSubsetName()+":"+e2.getItem()+"\t"+e2.getLvalue());
					 }
					 
				 } if(j==fuzzysubset.size()-1){
					 fuzzyelement e1 =new fuzzyelement(0.0);
					 temp =  fuzzysubset.get(j);
					 old = fuzzysubset.get(j-1);
					 if(val >= old.gethighbound()&& val<= temp.gethighbound()){
						 e1 = fuzzyattribute.get(i);
						 e1.setLvalue(1.0);
						 fuzzysubset.get(j).addElement(e1);
						//System.out.println(fuzzysubset.get(j).getSubsetName()+":"+e1.getItem()+"\t"+e1.getLvalue());
				 
				 
					 } 
			
			 
		 } k=j;
					 
				 } 
			 			 
			 } 
		 }
		 
		
	 public void addclass(List<String> match){
		 fuzzyelement e = null;
		 for( int i=0;i<fuzzyattribute.size();i++){
			 e = fuzzyattribute.get(i);
			 int index = match.indexOf(e.toString1());
			 e.setLvalue(1.0);
			 fuzzysubset.get(index).addElement(e);
			 fuzzysubset.get(index).addRid(e.getId());
			 /*if(e.toString1().equals(match)){
			 e.setLvalue(1.0);
			 fuzzysubset.get(0).addElement(e);
			 }
			 else{
				 e.setLvalue(1.0);
				 fuzzysubset.get(1).addElement(e);
			 }*/
			   
					 }
		
	 }
			
	 
	 
	 public void createSubmatrix(){
		 
		 for(fuzzyelement f:fuzzyattribute){
			 List<String> row = new ArrayList<String>();
			 String s = null;
			 for(int i=0; i<fuzzysubset.size();i++){
				 List<fuzzyelement> sub =fuzzysubset.get(i).getSubset();
				 s = contains(f,sub);
				 //System.out.print(" "+s); 
				 row.add(s);
			 }
			 submatrix.add(row);
			// printrow(row);
			// row.add(s);	
			 
		 }System.out.print("\n");
		 
	 }
public void createclassmatrix(){
		 
		 for(fuzzyelement f:fuzzyattribute){
			 List<String> row = new ArrayList<String>();
			 String s = null;
			 for(int i=0; i<fuzzysubset.size();i++){
				 List<fuzzyelement> sub =fuzzysubset.get(i).getSubset();
				 s = contains1(f,sub);
				 //System.out.print(" "+s); 
				 row.add(s);
			 }
			 submatrix.add(row);
			// printrow(row);
			// row.add(s);	
			 
		 }System.out.print("\n");
		 
	 }


	 public void printrow(List<String> row){
		 for(String s: row)
			 System.out.print(" "+s);
		 System.out.print("\n");
	 }
	 public subset getsubset(int index){
		 return fuzzysubset.get(index);
	 }
	 public List<fuzzyelement> getFuzzyattribute(){
		 return this.fuzzyattribute;
	 }
	 public List<subset> getfuzzysubset(){
	      return this.fuzzysubset;
	} 
	 public void print(){
		
		 for(List<String> linguistic:submatrix){
			  
			  for(String lingvalue:linguistic){
				  System.out.print(" "+lingvalue);
			  }
			  System.out.print("\n");
			  }
		
	}
	 public  List<List<String>> getSubMatrix(){
		 return this.submatrix;
	 }
	 public String contains(fuzzyelement f, List<fuzzyelement> subset)
	 { 
		 String s=""+0;
		 for(fuzzyelement f1:subset){
			 if(f.getItem()== f1.getItem()) 
				 s= f1.getLvalue();		  
		 }
		 return s;
			 
	 }
	 public String contains1(fuzzyelement f, List<fuzzyelement> subset)
	 { 
		 String s=""+0;
		 for(fuzzyelement f1:subset){
			 if(f.getId()== f1.getId()) 
				 s= f1.getLvalue();		  
		 }
		 return s;
			 
	 }
	 public void printsubset(){
			for(subset s: fuzzysubset){
				System.out.println("\t\t\t\t"+s.getSubsetName());
				for(fuzzyelement f:s.getSubset()){
					System.out.print("\n "+f.getId()+":"+f.getLvalue());
				}System.out.print("\n");
				
			}
		}
	 public List<String> getsubrow(int index){
		 return this.submatrix.get(index);
	 }
	 public List<List<String>> getsubmatrix(){
		 return this.submatrix;
	 }
}
    
