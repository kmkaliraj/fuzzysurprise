package fuzzylogic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
     
public class subset {
	    private  int subsetnum;
	    private  String subsetname = null;
	    private  double leftbound = 0.0;
	    private double  rightbound = 0.0;
	    private List<fuzzyelement> subsetelements =  new ArrayList<fuzzyelement>();
	    private List<Integer> rid = new  ArrayList<Integer>();
	    
	    public subset(double lowerbound, double upperbound, int num, String name){
	    	this.leftbound = lowerbound;
	    	this.rightbound = upperbound;
	    	this.subsetnum = num;
	    	this.subsetname = name;
	    }
	     public  void addRid(int id){
	    	 this.rid.add(id);
	     }
	     public List<Integer> getRid(){
	    	 return this.rid;
	     }
	    	   
	    public subset(int num, String name)
	    {
	    	this.subsetnum = num;
	    	this.subsetname = name;
	    }
	    
	    
	    
	    public double getlowbound(){
	    	return this.leftbound;
	    }
	    public double gethighbound(){
	    	return this.rightbound;
	    }
	    public int getID(){
	    	return this.subsetnum;
	    }
	    public  String getSubsetName(){
	    	return this.subsetname;
	    }
	    public void addElement(fuzzyelement element ){
	    	subsetelements.add(element);
	    }
	    public List<fuzzyelement> getSubset(){
	    	return subsetelements;
	    }
	    public fuzzyelement getElement(int index){
	    	return subsetelements.get(index);
	    }
}