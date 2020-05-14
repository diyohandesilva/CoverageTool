package agent;

import java.util.HashMap;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class CollectCoverage {
	
	public static HashMap<String, IntLinkedOpenHashSet> linesCovered;
	public static HashMap<String, HashMap<String, IntLinkedOpenHashSet>> testCases;
	public static String testName;
	
	//Constructors
	public CollectCoverage() {
		linesCovered = new HashMap<String, IntLinkedOpenHashSet>();
		testCases = new HashMap<String, HashMap<String, IntLinkedOpenHashSet>>();
		testName = "";
	}

	//Accessors
	public HashMap<String, IntLinkedOpenHashSet> getLinesCovered() {return linesCovered;}
	public HashMap<String, HashMap<String, IntLinkedOpenHashSet>> getTestCases() {return testCases;}
	public String getTestName() {return testName;}
	
	//Mutators
	public void setLinesCovered(HashMap<String, IntLinkedOpenHashSet> lc) {linesCovered = lc;}
	public void setTestCases(HashMap<String, HashMap<String, IntLinkedOpenHashSet>> tc) {testCases = tc;}
	public void setTestName(String tn) {testName = tn;}
	
	
	public static void addCoveredLine(String name, Integer line) {
		
		//The lines covered is empty
		if(linesCovered == null || linesCovered.isEmpty() == true)
			return;
		
		IntLinkedOpenHashSet covered_lines = linesCovered.get(name);
		
		//If linesCovered has no values for the specified class name
		if(covered_lines == null) {
			int[] new_set = {line};
			linesCovered.put(name, new IntLinkedOpenHashSet(new_set));
			return;
		}
		
		//If adding lines to existing linesCovered
		linesCovered.get(name).add(line);
		
	}
}
