package associationrules;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class RulesAgrawal {
	public final List<RuleAgrawal> rules = new ArrayList<RuleAgrawal>();  // rules
	BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	private final String name;
	protected String classname;
	
	
	
	public RulesAgrawal(String name,String cname){
		this.name = name;
		this.classname = cname;
	}
	
	public void printRules(int objectsCount){
		System.out.println(" ------- " + name + " -------");
	
		System.out.println("Working.....");
		int i=0;double p=0,sr=0;
		double k=0;
		double t=0;
		int j=1;
		
		
		String s[]=new String[10000];
		for(RuleAgrawal rule : rules){
			System.out.print(" \n rule " + i + ":  " + rule.toString()+" ==> "+this.classname);
			//System.out.print("  rule " + i + ":  " + rule.toString());
			//System.out.println(objectsCount);
			System.out.println("");
			double pa=rule.getRelativeSupportitem1(objectsCount);
			double pc=rule.getRelativeSupportitem2(objectsCount);
			
			if(i<=(rules.size()-1))
			{
				System.out.print("support :  " + rule.getRelativeSupport(objectsCount) +
					
					
					" (" + rule.getSupportAbsolu() + "/" + objectsCount + ") ");
			System.out.print("confidence :  " +rule.getConfidence());
			double sup= rule.getRelativeSupport(objectsCount);
			double con=rule.getConfidence();
			 p=(con*sup)/((con*sup)+((1-con)*(1-sup)));
			 sr=(p-sup)/sup;
			 System.out.println("\t surprising value:"+sr);
			 rule.setsvalue(sr);
			 /*if((j%2)!=0)
			{
			p=sup-pa*pc;
			 k=p;

			}			
			else
			{	
			t=(sup-pa*pc)+k;
	
			System.out.print("  Residual leverage value" + t);
			}
			j++;
			
			
			System.out.println("");
			if(t<0.4&&(j%2!=0)&&t<1)
			{System.out.println("unexpected items");
			
			System.out.println(rule.toString2());
			
			s[j]=rule.toString2();
			System.out.println(s[j]);
			}*/
		}
		
			
			i++;
		}
		System.out.println(" --------------------------------");
	

		//System.out.println("ssss"+s);
	}
	public void printsurprisingRules(double tvalue){
    double c=0;
		System.out.println("\n UnExpected Items");
            for(RuleAgrawal rule : rules){
            if(rule.getsvalue()> tvalue && rule.getsvalue() < 1 ){
			System.out.print("  \n rule " + c + ":  " + rule.toString());
			System.out.print(" \t Surprising value:"+rule.getsvalue());
            }c++;	
            }
	}
	
	public String toString(int nbObject){
		StringBuffer buffer = new StringBuffer(" ------- ");
		buffer.append(name);
		buffer.append(" -------\n");
		int i=0;
		for(RuleAgrawal rule : rules){
//			System.out.println("  L" + j + " ");
			buffer.append("   rule ");
			buffer.append(i);
			buffer.append(":  ");
			buffer.append(rule.toString());
			buffer.append("support");
			
		
			buffer.append(rule.getRelativeSupport(nbObject));
		
				double su=rule.getRelativeSupport(nbObject);
			

			
			buffer.append(" (");
			buffer.append(rule.getSupportAbsolu());
			
			buffer.append("/");
			buffer.append(nbObject);
			buffer.append(") ");
			buffer.append("confidence :  " );
			buffer.append(rule.getConfidence());
			//double conf=rule.getConfidence();
			//double p=su*conf;
			//buffer.append("Threshold value: ");
			//buffer.append(p);
			buffer.append("\n");
			
			i++;
			
		}
		return buffer.toString();
	}
	
	public void addRule(RuleAgrawal regle){
		rules.add(regle);
	}
	
	public int getRulesCount(){
		return rules.size();
	}

	public List<RuleAgrawal> getRules() {
		return rules;
	}
	
	
}
