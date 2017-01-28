package frequentpatterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AlgoApriori {

	protected Itemsets frequentItemsets = new Itemsets("FREQUENT ITEMSETS");
	protected List<ItemApriori> context;
	protected int k; // level
    protected int size;
	protected int totalCandidateCount = 0;
	protected int databaseScanCount = 0;
	protected long startTimestamp;
	protected double minsupport = 0;
	protected int  tSize =0;

	public AlgoApriori(content context,int s) {
		this.context = context.getcontent();
		this.size = context.size();
		this.tSize = s;
	}

	public Itemsets runAlgorithm() {
		resetStats();

		// Generate candidates with size k = 1 (all itemsets of size 1)
		k=1;
		List<ItemsetApriori> candidatesSize1 = generateCandidateSize1();
		totalCandidateCount+=candidatesSize1.size();

		calculateSupportForEachCandidate(candidatesSize1);
		 this.minsupport = calculateMinSupport(this.size,candidatesSize1);
		System.out.println("minimum support:"+minsupport);
		addEmptySetToLevel0IfNecessary(candidatesSize1);

		// To build level 1, we keep only the frequent candidates.
		// We scan the database one time to calculate the support of each candidate.
		List<ItemsetApriori> level = createLevelWithFrequentCandidates(minsupport,
				candidatesSize1);

		k = 2;
		// While the level is not empty
		while (!level.isEmpty()  ) {
			// Generate candidates of size K
			List<ItemsetApriori> candidatesK = generateCandidateSizeK(level);
			//totalCandidateCount+=candidatesK.size();

			// We scan the database one time to calculate the support
			// of each candidates.
			calculateSupportForEachCandidate(candidatesK);
			this.minsupport = calculateMinSupport(this.size,candidatesK);
			System.out.println("minimum support:"+minsupport);
			// We build the level k+1 with all the candidates that have
			// a support higher than the minsup threshold.
			List<ItemsetApriori> levelK = createLevelWithFrequentCandidates(
					minsupport, candidatesK);
			
			level = levelK; // We keep only the last level... 
			k++;
		}
		return frequentItemsets; // Return all frequent itemsets found!
	}

	/**
	 * Add the empty set to level 0 if no itemset of level 1 have a support
	 * that is equal to the number of transactions in the binary context.
	 * @param candidatesSize1 Candidates of level 1.
	 */
	protected void addEmptySetToLevel0IfNecessary(List<ItemsetApriori> candidatesSize1) {
		for(ItemsetApriori candidate : candidatesSize1){
			if(candidate.getAbsoluteSupport() == context.size()){
				return;
			}
		}
		ItemsetApriori emptySet = new ItemsetApriori();
		emptySet.setTransactioncount(context.size());
		frequentItemsets.addItemset(emptySet, 0); // ajout ensemble vide
	}

	protected List<ItemsetApriori> createLevelWithFrequentCandidates(double minsupp,List<ItemsetApriori> candidatesK) {
		List<ItemsetApriori> levelK = new ArrayList<ItemsetApriori>();
		for (ItemsetApriori candidate : candidatesK) { 
			if (Double.parseDouble(candidate.getRelativeSupport(this.size)) >= minsupp) {
				levelK.add(candidate);
				frequentItemsets.addItemset(candidate, k);
			}
		}
		return levelK;
	}

	protected void calculateSupportForEachCandidate(
			List<ItemsetApriori> candidatesK) {
		databaseScanCount++;
		//for (ItemsetApriori transaction : context) {
			// For each candidate of level K, we increase its support
			// if it is included in the transaction.
			for (ItemsetApriori candidate : candidatesK) {
				//if (candidate.includedIn(transaction)) {
					candidate.calculatesupport(size);
					//System.out.println("item:support:"+candidate.getRelativeSupport(this.size));
				//}
			}
		}
	//}
	
	public double calculateMinSupport(int size,List<ItemsetApriori> candidatesK){
		double msupport = 0.0;
		double temp = 0.0;
		double i = 1;
		for (ItemsetApriori candidate : candidatesK){ 
		     temp = temp + Double.parseDouble(candidate.getRelativeSupport(size));  
		     i++;
		}
		
		msupport =(temp/i);
		return msupport*((double)this.size/(double)this.tSize);
	}
	// Based on the description of Pasquier 99: "Efficient mining..."
	protected List<ItemsetApriori> generateCandidateSize1() {
		List<ItemApriori> list =this.context;
		Collections.sort(list, new Comparator<ItemApriori>(){
			public int compare(ItemApriori o1, ItemApriori o2) {
				if(o1.getId() == o2.getId()){
					return 0;
				}
				return o1.getId() - o2.getId();
			}
		});
		List<ItemsetApriori> candidates = new ArrayList<ItemsetApriori>(); // liste  d'itemsets
		for (ItemApriori item : list) {
			ItemsetApriori itemset = new ItemsetApriori();
			//System.out.println("item:"+item.getname());
			itemset.addItem(item);
			candidates.add(itemset);
		}
		return candidates;
	}

	protected List<ItemsetApriori> generateCandidateSizeK(List<ItemsetApriori> levelK_1) {
		List<ItemsetApriori> candidates = new ArrayList<ItemsetApriori>();

		// For each itemset I1 and I2 of level k-1
loop1:	for(int i=0; i< levelK_1.size(); i++){
			ItemsetApriori itemset1 = (ItemsetApriori)levelK_1.get(i);
loop2:		for(int j=i+1; j< levelK_1.size(); j++){
				ItemsetApriori itemset2 = (ItemsetApriori)levelK_1.get(j);
				
				// we compare items of itemset1  and itemset2.
				// If they have all the same k-1 items and the last item of itemset1 is smaller than
				// the last item of itemset2, we will combine them to generate a candidate
				for(int k=0; k< itemset1.size(); k++){
					
					//if(itemset1.getItems().get(k).getsid()!=itemset2.get(k).getsid()){
				
					// if they are the last items
					if(k == itemset1.size()-1){ 
						// the one from itemset1 should be smaller (lexical order) 
						// and different from the one of itemset2
						if(itemset1.getItems().get(k).getId() >= itemset2.get(k).getId()){  
							continue loop1;
						}
						if(itemset1.getItems().get(k).getsid() == itemset2.get(k).getsid()){
							continue loop1;
						}
                         }
					// if they are not the last items, and 
					else if(itemset1.getItems().get(k).getId() < itemset2.get(k).getId()){ 
						continue loop2; // we continue searching
					}
					else if(itemset1.getItems().get(k).getId() > itemset2.get(k).getId()){ 
						continue loop1;  // we stop searching:  because of lexical order
					}
					
				}
					//else 
						//continue loop1;
				//}
				// Now we will combine itemset1 and itemset2
				ItemApriori missing = itemset2.get(itemset2.size()-1);
				
				// Create a new candidate by combining itemset1 and itemset2
				ItemsetApriori candidate = new ItemsetApriori();
				for(int k=0; k < itemset1.size(); k++){
					candidate.addItem(itemset1.get(k));
				}
				candidate.addItem(missing);

				// The candidate is tested to see if its subsets of size k-1 are included in
				// level k-1 (they are frequent).
				if(allSubsetsOfSizeK_1AreFrequent(candidate,levelK_1)){
					candidates.add(candidate);
				}
			}
		}
		return candidates;
	}

	protected boolean allSubsetsOfSizeK_1AreFrequent(ItemsetApriori candidate, List<ItemsetApriori> levelK_1) {
		// To generate all the set of size K-1, we will proceed
		// by removing each item, one by one.
		if(candidate.size() == 1){
			return true;
		}
		for(ItemApriori item : candidate.getItems()){
			ItemsetApriori subset = candidate.cloneItemSetMinusOneItem(item);
			boolean found = false;
			for(ItemsetApriori itemset : levelK_1){
				if(itemset.isEqualTo(subset)){
					found = true;
					break;
				}
			}
			if(found == false){
				return false;
			}
		}
		return true;
	}

	protected void resetStats() {
		startTimestamp = System.currentTimeMillis();
		totalCandidateCount = 0;
		databaseScanCount = 0;
	}

	public void printStats() {
		System.out
				.println("=============  APRIORI - STATS =============");
		long temps = System.currentTimeMillis() - startTimestamp;
//		System.out.println(" Total time ~ " + temps + " ms");
		System.out.println(" Transactions count from database : "
				+ context.size());
		System.out.println(" Candidates count : " + totalCandidateCount);
		System.out.println(" Database scan count : " + databaseScanCount);
		System.out.println(" The algorithm stopped at size " + (k - 1)
				+ ", because there is no candidate");
		System.out.println(" Frequent itemsets count : " + frequentItemsets.getItemsetsCount()); 
		frequentItemsets.printItemsets(context.size());

		System.out.println(" Total time ~ " + temps + " ms");
		System.out
				.println("===================================================");
	}

	public Itemsets getItemsets() {
		return frequentItemsets;
	}
}
