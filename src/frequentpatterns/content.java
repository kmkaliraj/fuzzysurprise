package  frequentpatterns;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class content{
	private List<ItemApriori> list = new ArrayList<ItemApriori>();
	private String name;
	private List<Integer> rid = new ArrayList<Integer>();
	private int id;	
	
	
	public content(String name, int i,List<Integer> list){
		this.name = name;
		this.id = i;
		this.rid = list;
	}
	public void addValues(List<String> row){
		//System.out.println("\n");
		for(int i=0; i<list.size();i++){
			double d = Double.parseDouble(row.get(i));
			this.getcontent().get(i).add(d);
		}
	}
	
	public void print(){
		System.out.println("class name: "+this.getname());
		for(int i=0;i<this.rid.size();i++){
			for(ItemApriori item:this.getcontent()){
				 double d = item.getvalues().get(i);
				 System.out.print(" "+d);
			}
			System.out.println("\n");
			
		}
	}
	
	
	
	public List<Integer> getRowId(){
		return this.rid;
	}
	
	public String getname(){
		return this.name;
	}
	public int getId(){
		return this.id;
	}
	public void add(ItemApriori item){
		this.list.add(item);
	}
	public  List<ItemApriori> getcontent(){
		return this.list;
	}
	public int size(){
		return this.rid.size();
	}
	
	
	
	}
