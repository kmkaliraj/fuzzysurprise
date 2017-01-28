package frequentpatterns;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import fuzzylogic.*;

public class ContextApriori{
	
	private List<content> classcontent =  new ArrayList<content>();
	private List<ItemApriori> Attlist = new ArrayList<ItemApriori>();
	fuzzification f = new fuzzification(); 
	 List<List<String>> matrix = new ArrayList<List<String>>();
	public ContextApriori(fuzzification fuzzy){
		this.f = fuzzy;
		this.matrix = fuzzy.getMatrix();
	}
		
public void createItems(){	

	List<fuzzyset> fset = f.getAttList();	
	int c =1;	
	 
	for(int i=0; i< fset.size()-1;i++){
		fuzzyset fs = fset.get(i);
		int sid = fs.getId();
		for(subset s:fs.getfuzzysubset()){
			String name = s.getSubsetName();
			ItemApriori item = new ItemApriori(name,c++,sid);
			
			item.setbound(s.getlowbound(),s.gethighbound());
			
			Attlist.add(item);
		}
		
		
	}
	
fuzzyset s = fset.get(fset.size()-1);
int i=0;
  for(subset si: s.getfuzzysubset()){
	  content con = new content(si.getSubsetName(),i+1,s.getfuzzysubset().get(i).getRid()); 
	  for(ItemApriori item:Attlist)
		  con.add(item);
	 classcontent.add(con);
	 i++;
  } 
  for(content ct:classcontent){
	  List<Integer> rid = ct.getRowId();	  
	  for(Integer k:rid){		  
		  List<String> row = matrix.get(k);
		  ct.addValues(row);
	  }
	  
  }
}
public List<content> getclasscontent(){
	return this.classcontent;
}
public int getSize(){
	 int Size = 0;
	 for(content con:classcontent)
		 Size = Size + con.size();
	 return Size;
}
 

}

