package integerProgramming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;

public class BaBKnapsack {
	 /**
		 * @param args
		 */ 
	    public static int[] solveProblem(String path) throws Throwable {
	        try {
	            return solve(path);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
			
	    }
		private static double bestVal = 0;
		private static ArrayList<Integer> bestCombo = new ArrayList<Integer>();
		private static int capacity = 0;
		private static long start_time = 0;
		private static Date Dat = new Date();
		private static ArrayList<KItem> my_items = new ArrayList<KItem>();
	    private static ArrayList<Integer> combo = new ArrayList<Integer>();
	    /**
	     * Read the instance, solve it, and print the solution in the standard output
	     */
		
		
		private static double calculateRelaxation(int toChoose,double cap){
			int weight=0;
			double val=0;
			int i=toChoose;
			while(true){
				if(i>=my_items.size())
					return val;
	        
				if(weight+my_items.get(i).weight>cap){
					break;
				} else{
					val=val+my_items.get(i).val;
					weight=weight+my_items.get(i).weight;
					i=i+1;
				}
			}
			val=val+(cap-weight)/my_items.get(i).weight * my_items.get(i).val;
			
			return val;
		}
		
		
		private static void FindSolution(int v, int w, int toChoose) throws Throwable {
			//System.out.println("#####RECURSION OUT");
			//System.out.println("combo"+combo.toString());
			//System.out.println("v="+v+" w="+w);
			if (Dat.getTime() -start_time >= 1000*3600*4.99){
	            throw new Exception("Time limit exceeded");
			}
	            
			if( (w>capacity) || (toChoose==my_items.size()) ){
				if(w<=capacity){
					if(v>bestVal){
						//System.out.println("THAT IS BETTER and nothing to choose: "+combo.toString()+" v="+v);
						bestCombo=new ArrayList<Integer>(combo);
						bestVal=v;
					}
				}else{
					//System.out.println("INFEASIBLE w="+w+" >"+capacity);
				}
				return;
			}
			
			if(v>bestVal){
				//System.out.println("THAT IS BETTER: "+combo.toString()+" v="+v);
				bestCombo=new ArrayList<Integer>(combo);
				bestVal=v;
			}
			
			double est=calculateRelaxation(toChoose,capacity-w)+v;
			//System.out.println("est="+est);
			
			if(bestVal>est){
				return;
	        }
	    
			combo.add(toChoose);
			//System.out.println("LEFTFROM"+combo.toString());
			FindSolution(v+my_items.get(toChoose).val,w+my_items.get(toChoose).weight,toChoose+1);
			combo.remove(combo.size()-1);
			//System.out.println("RIGHTFROM"+combo.toString());
			FindSolution(v,w,toChoose+1);
	    
			return;
		}
		
		
	    public static int[] solve(String fileName) throws Throwable {
	    
	        
	        // get the temp file name
	          
	        if(fileName == null)
	            throw new FileNotFoundException("No such file");
	        
	        // read the lines out of the file
	        List<String> lines = new ArrayList<String>();

	        BufferedReader input =  new BufferedReader(new FileReader(fileName));
	        try {
	            String line = null;
	            while (( line = input.readLine()) != null){
	                lines.add(line);
	            }
	        }
	        finally {
	            input.close();
	        }
	        
	        
	        // parse the data in the file
	        String[] firstLine = lines.get(0).split("\\s+");
	        int numberOfItems = Integer.parseInt(firstLine[0]);
	        capacity = Integer.parseInt(firstLine[1]);

	        my_items = new ArrayList<KItem>();
			
	        for(int i=1; i < numberOfItems+1; i++){
	        	String line = lines.get(i);
	        	String[] parts = line.split("\\s+");
	        	my_items.add(new KItem(Integer.parseInt(parts[1]),Integer.parseInt(parts[0]),i-1));
	        }

	        // a trivial greedy algorithm for filling the knapsack
	        // it takes items in-order until the knapsack is full
			
	    
			bestVal=0;
			bestCombo=new ArrayList<Integer>();
		
			start_time=Dat.getTime();
			
			//sort the items by density
			Collections.sort(my_items);
			
			//DEBUG
			/*for (int i=0; i< my_items.size();i++){
				System.out.println("id="+my_items.get(i).id+" v="+my_items.get(i).val+" w="+my_items.get(i).weight);
				System.out.println(" dens="+(double)(my_items.get(i).val)/(double)(my_items.get(i).weight));
			}*/
			try{
				FindSolution(0,0,0);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        // prepare the solution in the specified output format
	        System.out.println((int)Math.round(bestVal)+" 1");
	        int[] taken = new int[numberOfItems];
			for(int j=0; j<my_items.size(); j++){
				if (bestCombo.contains(j)){
					taken[my_items.get(j).id]=1;
				} else{
					taken[my_items.get(j).id]=0;
				}
			}
			
			for( int i=0; i<my_items.size(); i++){
				if(taken[i]>0){
					System.out.print(" 1");
				}else{
					System.out.print(" 0");
				}
			}
			
			return taken;
	        //System.out.println("");        
	    }
}
