package frequentpatterns;

import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

public class ItemApriori{
	//private HashFunction objHashFunction = new HashFunction();
	private String name;
	private  int id;
	private int sid;
	private double lowvalue =0.0;
	private double highvalue =0.0;
	private List<Double> values = new  ArrayList<Double>();
	
	
	public ItemApriori( String name,int i, int j){
		this.name = name;
		this.id =i;
		this.sid=j;
	} 
	
	public void setbound(double v1, double v2 ){
		this.lowvalue = v1;
		this.highvalue = v2;
		
	}
	
	public String getlow(){
		DecimalFormat format = new DecimalFormat();
		format.setMinimumFractionDigits(0); 
		format.setMaximumFractionDigits(2); 
		return format.format(this.lowvalue);		
		
	}
	
	public String gethigh(){
		DecimalFormat format = new DecimalFormat();
		format.setMinimumFractionDigits(0); 
		format.setMaximumFractionDigits(2); 
		return format.format(this.highvalue);	
		
	}
	
	/*public  void setId(int id){
		this.id=id;
	}*/
	public int getsid(){
		return this.sid;
	}
	public void add(double d){
		this.values.add(d);
	}
	
	public List<Double> getvalues(){
		return this.values;
	}
	
	public  String getname(){
		return name;
		
	}

	public int getId() {
		return id;
	}
      
	public String toString( )
{
		return ""+getname()+"[ "+getlow()+"-"+gethigh()+" ]";
	}
	public String toString1(){
		return ""+getId();
	}
	public boolean equals(Object object){
		ItemApriori item = (ItemApriori) object;
		if((item.getId() == this.getId())){
			return true;
		}
		return false;
	}
	
	public int hashCode()
	{
		String string = "" +getId();
		return string.hashCode();
	}
	public int getsize(){
		return this.values.size();
	}
}
