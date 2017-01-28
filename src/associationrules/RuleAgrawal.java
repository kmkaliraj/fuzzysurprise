package associationrules;

import frequentpatterns.ItemsetApriori;


public class RuleAgrawal {
	private ItemsetApriori itemset1; // antecedent
	private ItemsetApriori itemset2; // consequent
	private int transactionCount; // absolute support
	private double confidence;
	private double svalue;
	public RuleAgrawal(ItemsetApriori itemset1, ItemsetApriori itemset2, double d, double confidence){
		this.itemset1 = itemset1;
		this.itemset2 = itemset2;
		this.transactionCount =  (int) d;
		this.confidence = confidence;
	}
	public double getRelativeSupportitem1(int objectCount) {
		double a=Double.parseDouble(itemset1.getRelativeSupport( objectCount));
		System.out.println("support value of antecednt:"+a);
		return a;
		}
	public double getRelativeSupportitem2(int objectCount) {
		double b=Double.parseDouble(itemset2.getRelativeSupport(objectCount)); 
		System.out.println("support value of consequent:"+b);
		return b;
		}
	public void setsvalue(double sr){
		this.svalue=sr;
	}
	public double getsvalue(){
		return svalue;
	}
	
	public double getRelativeSupport(int objectCount) {
		return ((double)transactionCount) / ((double) objectCount);
	}
	
	public int getSupportAbsolu(){
		return transactionCount;
	}

	public double getConfidence() {
		return confidence;
	}
	
	public void print(){
		System.out.println(toString());
	
	}
	public String toString()
	{ //int i,counter=1;
		String s1=itemset1.toString();
		String s2=itemset2.toString();
		String s=s1+s2;
		int n=s.length();
		
		/*String[] arr=s.split(" ");
		String arr1[];
		
        System.out.println("Array :"+arr.length);
        for(i=0;i<arr.length;i++)
        {
            System.out.println("array"+i+"  :"+arr[i]);
            counter++;
            
            arr1=arr;
            }
        System.out.println("here");
        
        if(counter==1)
        { //arr1[]=arr;
        String temp[];
        temp=arr;
        arr1=arr2;
        arr2=temp;
        }
        else
        {
        	String arr2[]=arr;
        
      //call(arr1,arr2);
        }
        
        */
        
        
		
			
		return itemset1.toString() +  " ==> " + itemset2.toString();
		
			
		
		
		
	}
	public String toString2()
	{ //int i,counter=1;
		String s1=itemset1.toString();
		String s2=itemset2.toString();
		String s=s1+s2;
		int n=s.length();
		
		/*String[] arr=s.split(" ");
		String arr1[];
		
        System.out.println("Array :"+arr.length);
        for(i=0;i<arr.length;i++)
        {
            System.out.println("array"+i+"  :"+arr[i]);
            counter++;
            
            arr1=arr;
            }
        System.out.println("here");
        
        if(counter==1)
        { //arr1[]=arr;
        String temp[];
        temp=arr;
        arr1=arr2;
        arr2=temp;
        }
        else
        {
        	String arr2[]=arr;
        
      //call(arr1,arr2);
        }
        
        */
        
        
		
			
		return s;
		
			
		
		
		
	}
	public String call()
	{String s1=itemset1.toString();
	String s2=itemset2.toString();
	String s=s1+s2;
		return s;
	}
	/*void call(String a[],String b[])
	 {

	    // public static boolean equal(int[] list1, int[] list2) {
	
	 

	      if(a.length == b.length)
	      {
	           Arrays.sort(list1);
	
	           return true;
	      }
	      else
	      {
	           Arrays.sort(list2);
	
	           return false;
	      }
	 }*/
	public ItemsetApriori getItemset1() {
		return itemset1;
	}

	public ItemsetApriori getItemset2() {
		return itemset2;
	}

}
