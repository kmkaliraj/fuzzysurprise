package fuzzylogic;

import java.io.*;
import java.math.*; 
import java.text.DecimalFormat;


public class fuzzyelement{
	   private double F_item ;
	   private String value = null;
	   private double Lvalue = 0.0;
	   private int id;
	   public  fuzzyelement(double item ){
		   this.F_item = item;
	   }
	   public  fuzzyelement(String item, int i){
		   this.value = item;
		   this.id = i;
	   }
	   
	public void setItem(double d){
		this.F_item = d;
	}
	public double getItem(){
		return F_item;
	}
	public int getId(){
		return this.id;
	}
	public void setLvalue( double value){
		this.Lvalue = value;
	}
	public String getLvalue(){
		DecimalFormat format = new DecimalFormat();
		format.setMinimumFractionDigits(0); 
		format.setMaximumFractionDigits(3); 
		return  ""+format.format(Lvalue)+"\t";
	}
	public String toString(){
		return ""+getItem();
	}
	public String toString1(){
		
		return value;
}
}