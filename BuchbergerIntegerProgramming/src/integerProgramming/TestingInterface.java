package integerProgramming;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import gurobi.*;

public class TestingInterface {
	/**********TESTS**********/
	
	private static void VectorsAndOrders(){
		//tests vectors initialization and orders
		//set up a binomial
		ArrayList<Integer> bufAr = new ArrayList<Integer>();
		bufAr.add(0);
		bufAr.add(1);
		bufAr.add(-2);
		bufAr.add(2);
		VectorBinomial vb1 = new VectorBinomial(bufAr ,0);
		System.out.println("The first one "+vb1.toString());

		//set up another binomial
		bufAr= new ArrayList<Integer>(bufAr);
		bufAr.set(0,0);
		bufAr.set(1,0);
		bufAr.set(2,-1);
		bufAr.set(3,1);
		VectorBinomial vb2 = new VectorBinomial(bufAr ,0);
		System.out.println("The second one "+vb2.toString());
		
		//set up example grading
		bufAr= new ArrayList<Integer>(bufAr);
		bufAr.set(0,0);
		bufAr.set(1,0);
		bufAr.set(2,0);
		bufAr.set(3,0);
		Vector grading = new Vector(bufAr);
		System.out.println("Grading "+grading.toString());
		
		//ComparisonTests
		System.out.println("*****Comparisons(1 with 2)****");
		try {
			System.out.println("Total "+vb1.CompareTotally(vb2));
			System.out.println("Lex "+vb1.CompareLex(vb2));
			System.out.println("RevLex "+vb1.CompareRevLex(vb2));
			System.out.println("GLex "+vb1.CompareGLex(vb2,grading));
			System.out.println("GRevLex "+vb1.CompareGRevLex(vb2,grading));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TransformationTest
		ArrayList<VectorBinomial> vecSet = new ArrayList<VectorBinomial>();
		vecSet.add(vb1);
		vecSet.add(vb2);
		System.out.println("*****Transformation >0****");
		try {
			for(int i=0; i<vecSet.size(); i++){
				vecSet.get(i).TransformToPlus(grading);
			}
			System.out.println(vecSet.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ReductionTest g1,g2 >Lex 0, answer 0,1,0,2
		ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
		VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
		g1.vec.add(1);
		g1.vec.add(0);
		g1.vec.add(-1);
		g1.vec.add(0);
		gs.add(g1);
		
		g1= new VectorBinomial(new ArrayList<Integer>(),0);
		g1.vec.add(0);
		g1.vec.add(2);
		g1.vec.add(1);
		g1.vec.add(0);
		gs.add(g1);
		
		VectorBinomial f = new VectorBinomial(new ArrayList<Integer>(),0);
		f.vec.add(5);
		f.vec.add(-3);
		f.vec.add(-6);
		f.vec.add(2);
		
		System.out.println("*****Reduction****");
		try {
			System.out.println(f.toString());
			System.out.println("by");
			System.out.println(gs.toString());
			
			f.ReduceByList(gs, grading);
			
			System.out.println("Result: "+f.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void GrobnerTest(ArrayList<String> outp){
		System.out.println("***************GROBNER TEST*************");
		//t,s0,x1,x2,s1,s2
		//c=[0,3,2,0,0]
		Vector grading= new Vector(new ArrayList<Integer>());
		
		grading.vec.add(2);
		grading.vec.add(19);
		grading.vec.add(45);
		grading.vec.add(11);
		grading.vec.add(1);
		grading.vec.add(1);
		grading.vec.add(1);
		
		ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
		VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),1);
		g1.vec.add(0);//t
		g1.vec.add(-1);//x1
		g1.vec.add(0);//x2
		g1.vec.add(0);//x3
		g1.vec.add(1);//s0
		g1.vec.add(1);//s1
		g1.vec.add(0);//s2
		g1.vec.add(0);//s3
		gs.add(g1);
		
		g1= new VectorBinomial(new ArrayList<Integer>(),1);
		g1.vec.add(0);//t
		g1.vec.add(0);//x1
		g1.vec.add(-1);//x2
		g1.vec.add(0);//x3
		g1.vec.add(3);//s0
		g1.vec.add(0);//s1
		g1.vec.add(1);//s2
		g1.vec.add(0);//s3
		gs.add(g1);
		/***/
		
		g1= new VectorBinomial(new ArrayList<Integer>(),1);
		g1.vec.add(0);//t
		g1.vec.add(0);//x1
		g1.vec.add(0);//x2
		g1.vec.add(-1);//x3
		g1.vec.add(5);//s0
		g1.vec.add(0);//s1
		g1.vec.add(0);//s2
		g1.vec.add(1);//s3
		gs.add(g1);
		/***/
		
		
		g1= new VectorBinomial(new ArrayList<Integer>(),1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		g1.vec.add(1);
		gs.add(g1);
		
		System.out.println("Vectors:");
		System.out.println(gs);
		
		try {
			
			long gbTime = System.nanoTime();
			long totalTime=0;
			ArrayList<VectorBinomial> grobBasis = null;//TIME MEASUREMENT staff
			for(int i=0; i<100; i++){
				grobBasis = VectorBinomial.BuchbergerAlgorithm(outp,gs, grading);
				long snt=System.nanoTime();
				totalTime+= snt-gbTime;
				gbTime=snt;
			}
			
			System.out.println("GrobnerBasis(only with valuable variables) timeForGB(aver over 100)="+totalTime/100+"nsec was: 1200-1600e+03");
			
			grobBasis = VectorBinomial.FilterBasis(grobBasis);
			for (VectorBinomial v: grobBasis){
				System.out.println(v.toString());
			}
			Vector feasSolution = new Vector(new ArrayList<Integer>());
			feasSolution.vec.add(0);
			feasSolution.vec.add(0);
			feasSolution.vec.add(0);
			feasSolution.vec.add(7);
			feasSolution.vec.add(1);
			feasSolution.vec.add(1);
			feasSolution.vec.add(1);
			
			System.out.println("FeasibleSolution: "+feasSolution);
			totalTime=0;
			gbTime = System.nanoTime();
			for (int i=0; i< 100; i++){
				feasSolution.FindNormalForm(grobBasis);
				long snt = System.nanoTime();
				totalTime+= snt - gbTime;
				gbTime = snt;
			}
			
			System.out.println("was(20-37 e+03)exTime(aver over 100)="+totalTime/100+"nsec . OptimalSolution: "+feasSolution);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*******************KNAPSACK****************/
	private static ArrayList<VectorBinomial> FindKnapsackBoundedGB(ArrayList<String> outp,String fileName) throws IOException {
		// TODO Auto-generated method stub
		
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
        int capacity = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
        ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
        
        grading.vec.add(1);//for s0
		for(int i=1; i <= numberOfItems; i++){
        	grading.vec.add(0);
        	grading.vec.add(0
        			);
        }
		ArrayList<Integer> weights = new ArrayList<Integer>();
        for(int i=0; i < numberOfItems; i++){
        	String line = lines.get(i+1);
        	String[] parts = line.split("\\s+");
        	VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
        	
        	//g1.vec.add(0);
        	g1.vec.add(Integer.parseInt(parts[1]));
        	
        	for (int j=1; j<i; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(-1);
    		for (int j=i+1; j<numberOfItems+i; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(1);
    		for (int j=0; j<numberOfItems-i; j++){
        		g1.vec.add(0);
        	}
    		gs.add(g1);
    		
    		grading.vec.set(i,1+weights.get(i));
    		grading.vec.set(i+numberOfItems,Integer.parseInt(parts[0])+1);
        }
        System.out.println(grading);
        VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),1);//adding 1-tf
        
        for (int i=0;i<= numberOfItems;i++){
        	g1.vec.add(-1);
        	g1.vec.add(-1);
        }
        gs.add(g1);
		
        try {
        	long start = System.currentTimeMillis();
			ArrayList<VectorBinomial> gB = VectorBinomial.FindKnapsackGB(weights, grading);
			
			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			feasSolution.vec.add(capacity);//i.e. take NOTHING
			for(int i=0; i< numberOfItems;i++){
				feasSolution.vec.add(0);
			}
			for(int i=0; i< numberOfItems;i++){
				feasSolution.vec.add(1);
			}
			
			System.out.println("FeasibleSolution: "+feasSolution);
			feasSolution.FindNormalForm(gB);
			System.out.println("OptimalSolution: "+feasSolution);
			outp.add(""+((double)(System.currentTimeMillis()-start))/1000);
			
			int maxdeg=0;
			for(VectorBinomial v: gB){
				int deg=0;
				for(int i=0;i<v.Size();i++){
					if(v.get(i)>0){
						deg+=v.get(i);
					}
				}
				if(deg>maxdeg){
					maxdeg=deg;
				}
			}
			outp.add(""+maxdeg);
			return gB;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;     
		
	}
	
	
	private static ArrayList<Integer> SolveKnapsack(ArrayList<String> outp,String fileName) throws Throwable {
		// TODO Auto-generated method stub
		
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
        int capacity = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
        ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
        
        grading.vec.add(1);//for s0
		for(int i=1; i <= numberOfItems; i++){
        	grading.vec.add(0);
        	grading.vec.add(0);
        }
		
        for(int i=1; i <= numberOfItems; i++){
        	String line = lines.get(i);
        	String[] parts = line.split("\\s+");
        	VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);

        	g1.vec.add(Integer.parseInt(parts[1]));
        	
        	for (int j=1; j<i; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(-1);
    		for (int j=i+1; j<numberOfItems+i; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(1);
    		for (int j=0; j<numberOfItems-i; j++){
        		g1.vec.add(0);
        	}
    		gs.add(g1);
    		
    		grading.vec.set(i,1+g1.vec.get(0));
    		grading.vec.set(i+numberOfItems,Integer.parseInt(parts[0])+1);
        }
        System.out.println(grading);
        
		
        try {
        	long start = System.currentTimeMillis();
			ArrayList<VectorBinomial> gB = VectorBinomial.BuchbergerAlgorithm(outp,gs, grading);
			gB=VectorBinomial.MinimizeBasis(gB);
			outp.add(gB.size()+"");
			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			feasSolution.vec.add(capacity);//i.e. take NOTHING
			for(int i=0; i< numberOfItems;i++){
				feasSolution.vec.add(0);
			}
			for(int i=0; i< numberOfItems;i++){
				feasSolution.vec.add(1);
			}
			
			System.out.println("FeasibleSolution: "+feasSolution);
			feasSolution.FindNormalForm(gB);
			System.out.println("OptimalSolution: "+feasSolution);
			outp.add(""+((double)(System.currentTimeMillis()-start))/1000);
			System.out.println("CORRECTNESS");
			int[] bab=BaBKnapsack.solveProblem(fileName);
			int test=0;
			for(int i=0;i<bab.length;i++){
				test=feasSolution.get(i+1)-bab[i];
				if(test!=0){
					System.out.println("NO!!!!");
				}
			}
			if(test==0){
				System.out.println("YES!");
			}
			int maxdeg=0;
			int maxcoord=0;
			for(VectorBinomial v: gB){
				int deg=0;
				for(int i=0;i<v.Size();i++){
					if(v.get(i)>0){
						deg+=v.get(i);
						if(v.get(i)>maxcoord){
							maxcoord=v.get(i);
						}
					}
				}
				if(deg>maxdeg){
					maxdeg=deg;
				}
			}
			outp.add(""+maxdeg);
			outp.add(""+maxcoord);
			return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;     
		
	}
	
	private static void SampleKnapsack(int maxweight,int N, int Nsamples){
		//N items
		File dir = new File("./data/knapsack_samples/samples_"+N+"/");
		dir.mkdirs();
		
		for(int k=0;k<Nsamples;k++){

			try{
				FileWriter fw = new FileWriter("./data/knapsack_samples/samples_"+N+"/testlist.txt",true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("./data/knapsack_samples/samples_"+N+"/sample_"+N+"_"+k+".txt\n");
				bw.close();
				
    			PrintWriter writer = new PrintWriter("./data/knapsack_samples/samples_"+N+"/sample_"+N+"_"+k+".txt", "UTF-8");
    			int K=(int)Math.round(Math.random()*N*maxweight/2);
    			while(K<=0){
    				K=(int)Math.round(Math.random()*N*maxweight/2);
    			}
    			writer.println(N+" "+K);
    			
    			
    			for(int i=0;i<N;i++){
    				int v=(int)Math.round(Math.random()*50+10);
        			int w=(int)Math.round(Math.random()*maxweight);
        			while(w<=0){
        				w=(int)Math.round(Math.random()*maxweight);
        			}
    				writer.println(v+" "+w);
    			}
				writer.close();
	
			} catch (IOException e) {
	   			// do something
			}
		}
	}
	/****************************VCOVER*****************************/
	/*private static ArrayList<Integer> SolveMoney(){
		
		Vector grading = new Vector(new ArrayList<Integer>());
		grading.vec.add(2);
		grading.vec.add(2);
		grading.vec.add(2);
		grading.vec.add(2);
		
		grading.vec.add(1);
		grading.vec.add(1);
		grading.vec.add(1);
		grading.vec.add(1);
		
		ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
		VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
		
		g1.vec.add(2);
		g1.vec.add(-1);
		g1.vec.add(0);
		g1.vec.add(0);
		g1.vec.add(-2);
		g1.vec.add(1);
		g1.vec.add(0);
		g1.vec.add(0);
		gs.add(g1);
		
		g1= new VectorBinomial(new ArrayList<Integer>(),0);
		g1.vec.add(5);
		g1.vec.add(0);
		g1.vec.add(-1);
		g1.vec.add(0);
		g1.vec.add(-5);
		g1.vec.add(0);
		g1.vec.add(1);
		g1.vec.add(0);
		gs.add(g1);
		
		g1= new VectorBinomial(new ArrayList<Integer>(),0);
		g1.vec.add(10);
		g1.vec.add(0);
		g1.vec.add(0);
		g1.vec.add(-1);
		g1.vec.add(-10);
		g1.vec.add(0);
		g1.vec.add(0);
		g1.vec.add(1);
		gs.add(g1);
		
		try {
        	long startTime = System.currentTimeMillis();
        	
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.MinimizeBasis(VectorBinomial.BuchbergerAlgorithm(outp,gs, grading));
			System.out.println("Minimized GB: "+gB.size());
			System.out.println("gB");
			for(int i=0;i<gB.size();i++){
				System.out.println(gB.get(i));
			}

			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			//i.e. take ALL
			feasSolution.vec.add(500);
			feasSolution.vec.add(0);
			feasSolution.vec.add(0);
			feasSolution.vec.add(0);
			feasSolution.vec.add(100);
			feasSolution.vec.add(100);
			feasSolution.vec.add(50);
			feasSolution.vec.add(10);
			
			
			System.out.println("FeasibleSolution: "+feasSolution);
			feasSolution.FindNormalForm(gB);
			System.out.println("OptimalSolution: "+feasSolution);
			long endTime   = System.currentTimeMillis();
        	long totalTime = endTime - startTime;
        	System.out.println("Execution Time:"+(double)(totalTime)/1000);//seconds
			return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}*/
	
	private static ArrayList<Integer> SolveVertexCover(ArrayList<String> outp,String fileName) throws IOException {
		
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
        //System.out.println(lines.size());
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int N = Integer.parseInt(firstLine[0]);
        int E = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
        ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
        
        
		for(int i=1; i <= N; i++){
        	grading.vec.add(Integer.parseInt(lines.get(i)));
        	VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
        	for(int j=0; j<N+E; j++){
        		g1.vec.add(0);
        	}
        	g1.set(i-1,1);
        	gs.add(g1);
        	
        }
		
		ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
        for(int i=N+1; i <=N+E; i++){
        	String line = lines.get(i);
        	String[] parts = line.split("\\s+");
        	
        	ArrayList<Integer> edge = new ArrayList<Integer>();
        	edge.add(Integer.parseInt(parts[0]));
        	edge.add(Integer.parseInt(parts[1]));
        	edges.add(edge);
        	
        	gs.get(Integer.parseInt(parts[0])).set(i-N-1+N,1);
        	gs.get(Integer.parseInt(parts[1])).set(i-N-1+N,1);
        	grading.vec.add(0);//for slacks
        }
        //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
        //System.out.println(grading);
		System.out.println("Trying to solve the problem: V="+N+" E="+E);
        try {
        	long startTime = System.currentTimeMillis();
        	/*
        	for(ArrayList<Integer> edge: edges){
        		
        		if(grading.get(edge.get(0))>grading.get(edge.get(1))){
        			VectorBinomial gg = gs.get(edge.get(0)).copyVectorBinomial();
        			gg.Add(gs.get(edge.get(1)),-1);
        			gs.add(gg);
        		}else{
        			if(grading.get(edge.get(0))<grading.get(edge.get(1))){
        				VectorBinomial gg = gs.get(edge.get(1)).copyVectorBinomial();
            			gg.Add(gs.get(edge.get(0)),-1);
            			gs.add(gg);
        			}else{
        				if(edge.get(0)<edge.get(1)){
        					VectorBinomial gg = gs.get(edge.get(0)).copyVectorBinomial();
                			gg.Add(gs.get(edge.get(1)),-1);
                			gs.add(gg);
        				}else{
        					VectorBinomial gg = gs.get(edge.get(1)).copyVectorBinomial();
                			gg.Add(gs.get(edge.get(0)),-1);
                			gs.add(gg);
        				}
        			}
        		}
        		
        		
        	}
        	
        	System.out.println("gs");
			for(int i=0;i<gs.size();i++){
				System.out.println(gs.get(i));
			}
			*/
			ArrayList<VectorBinomial> gB = VectorBinomial.BuchbergerAlgorithm1(outp,gs, grading,N);
			out+=""+gB.size();
			/*System.out.println("gB");
			for(int i=0;i<gB.size();i++){
				System.out.println(gB.get(i));
			}*/

			gB=VectorBinomial.MinimizeBasis(gB);
			out+=" & "+gB.size();
			
			System.out.println("Minimized GB: "+gB.size());
			
			
			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			//i.e. take ALL
			for(int i=0; i< N+E;i++){
				feasSolution.vec.add(1);
			}
			
			
			System.out.println("FeasibleSolution: "+feasSolution);
			feasSolution.FindNormalForm(gB);
			System.out.println("OptimalSolution: "+feasSolution);
			long endTime   = System.currentTimeMillis();
        	long totalTime = endTime - startTime;
        	System.out.println("Execution Time:"+(double)(totalTime)/1000);//seconds
			out+=" & "+(double)(totalTime)/1000;
        	
        	
			return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
		
	}
	
private static ArrayList<Integer> SolveBoundedVertexCover(ArrayList<String> outp,String fileName) throws IOException {
		
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
        //System.out.println(lines.size());
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int N = Integer.parseInt(firstLine[0]);
        int E = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
        ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
        
        
		for(int i=1; i <= N; i++){
        	grading.vec.add(Integer.parseInt(lines.get(i))+1);//+1 for binary slacks
        	VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
        	for(int j=0; j<2*N+E; j++){
        		g1.vec.add(0);
        	}
        	g1.set(i-1,1);
        	g1.set(N+E+i-1,-1);
        	gs.add(g1);
        	
        }
		
		for(int i=0;i<N;i++){
			grading.vec.add(1);
		}
		//ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
        for(int i=N+1; i <=N+E; i++){
        	String line = lines.get(i);
        	String[] parts = line.split("\\s+");
        	
        	gs.get(Integer.parseInt(parts[0])).set(i-N-1+N,1);
        	gs.get(Integer.parseInt(parts[1])).set(i-N-1+N,1);
        	grading.vec.add(0);//for slacks
        }
        //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
        //System.out.println(grading);
		System.out.println("Trying to solve the problem: V="+N+" E="+E);
        try {
        	long startTime = System.currentTimeMillis();
        	
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.BuchbergerAlgorithm(outp, gs, grading);
			out+=""+gB.size();
			/*System.out.println("gB");
			for(int i=0;i<gB.size();i++){
				System.out.println(gB.get(i));
			}
*/
			gB=VectorBinomial.MinimizeBasis(gB);
			out+=" & "+gB.size();
			
			System.out.println("Minimized GB: "+gB.size());
			
			
			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			//i.e. take ALL
			for(int i=0; i< N+E;i++){
				feasSolution.vec.add(1);
			}
			for(int i=0; i< N;i++){
				feasSolution.vec.add(0);
			}
			
			
			System.out.println("FeasibleSolution: "+feasSolution);
			feasSolution.FindNormalForm(gB);
			System.out.println("OptimalSolution: "+feasSolution);
			
			long endTime   = System.currentTimeMillis();
        	long totalTime = endTime - startTime;
        	
        	int cost=0;
			for(int i=0;i<N;i++){
				cost+=(grading.get(i)-1)*feasSolution.get(i);
			}
			System.out.println("COST:"+cost);
			
        	System.out.println("Execution Time:"+(double)(totalTime)/1000);//seconds
			out+=" & "+(double)(totalTime)/1000;
        	return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
		
	}

	/*private static ArrayList<Integer> BuildFeasibleTTP(ArrayList<Integer> ds, ArrayList<Integer> ss, ArrayList<Integer> cs, int D, int S, int G){
		//ds demands matrix
		//ss supplies matrix
		//cs road capacities matrix
		int i=0;
		int j=0;
		int k=0;
		ArrayList<Integer> feasSol = new ArrayList<Integer>();
		for(int t=0;t<D*S*G;i++){
			feasSol.add(0);
		}
		
		while((i<ss.size())&&(j<ds.size())&&(k<cs.size())){
			int min=0;
			if(cs.get(D*i+j)<ss.get(G*i+k)){
				if(ds.get(G*j+k)<cs.get(D*i+j)){
					min=ds.get(G*j+k);
					ds.set(G*j+k,ds.get(G*j+k)-min);
					cs.set(D*i+j,cs.get(D*i+j)-min);
					ss.set(G*i+k,ss.get(G*i+k)-min);
				}else{
					min=cs.get(D*i+j);
					ds.set(G*j+k,ds.get(G*j+k)-min);
					cs.set(D*i+j,cs.get(D*i+j)-min);
					ss.set(G*i+k,ss.get(G*i+k)-min);
				}
			}else{
				if(ss.get(G*i+k)<ds.get(G*j+k)){
					min=ss.get(G*i+k);
					ds.set(G*j+k,ds.get(G*j+k)-min);
					cs.set(D*i+j,cs.get(D*i+j)-min);
					ss.set(G*i+k,ss.get(G*i+k)-min);
				}else{
					min=ds.get(G*j+k);
					ds.set(G*j+k,ds.get(G*j+k)-min);
					cs.set(D*i+j,cs.get(D*i+j)-min);
					ss.set(G*i+k,ss.get(G*i+k)-min);
				}
			}
			if(ds.get(G*j+k)==0){
				
			}
		}
		
		return null;
	}
*////////////VERY BETA STAFF





private static void TestVCoverBoundedGB(String fileName) throws IOException {
	// TODO Auto-generated method stub
	
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
    //System.out.println(lines.size());
    
    // parse the data in the file
    String[] firstLine = lines.get(0).split("\\s+");
    int N = Integer.parseInt(firstLine[0]);
    int E = Integer.parseInt(firstLine[1]);
	
    Vector grading = new Vector(new ArrayList<Integer>());
    
    
    
	for(int i=1; i <= N; i++){
    	grading.vec.add(Integer.parseInt(lines.get(i)));
    }
	
	ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
	
    for(int i=N+1; i <N+E+1; i++){
    	String line = lines.get(i);
    	String[] parts = line.split("\\s+");
    	
    	edges.add(new ArrayList<Integer>());
    	edges.get(edges.size()-1).add(Integer.parseInt(parts[0]));
    	edges.get(edges.size()-1).add(Integer.parseInt(parts[1]));
    	//System.out.println(edges.get(edges.size()-1));
    }
    
    try {
    	long startTime = System.currentTimeMillis();
		ArrayList<VectorBinomial> bgB = VectorBinomial.FindVCoverGB(edges, grading);
		System.out.println("Execution Time:"+(double)(System.currentTimeMillis()-startTime)/1000);//seconds
		
		System.out.println("BASIS");
		System.out.println("size: "+ bgB.size());
		//bgB=VectorBinomial.MinimizeBasis(bgB);
		System.out.println("size: "+ bgB.size());
		
		Vector feasSolution = new Vector(new ArrayList<Integer>());
		
		//i.e. take ALL
		for(int i=0; i< N+E;i++){
			feasSolution.vec.add(1);
		}
		/*for(int i=0; i< N;i++){
			feasSolution.vec.add(0);
		}*/
		
		
		System.out.println("FeasibleSolution: "+feasSolution);
		feasSolution.FindNormalForm(bgB);
		System.out.println("OptimalSolution: "+feasSolution);
		int cost=0;
		for(int i=0;i<grading.Size();i++){
			cost+=grading.get(i)*feasSolution.get(i);
		}
		System.out.println("COST:"+cost);
		/*for(VectorBinomial el: bgB){
			System.out.println(el);
		}*/
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	/*public static void SolveVCovWithGurobi(String fileName) throws IOException{
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
        System.out.println(lines.size());
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int N = Integer.parseInt(firstLine[0]);
        int E = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
        
        
        
		for(int i=1; i <= N; i++){
        	grading.vec.add(Integer.parseInt(lines.get(i)));
        }
		
		ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
        for(int i=N+1; i <N+E+1; i++){
        	String line = lines.get(i);
        	String[] parts = line.split("\\s+");
        	
        	edges.add(new ArrayList<Integer>());
        	edges.get(edges.size()-1).add(Integer.parseInt(parts[0]));
        	edges.get(edges.size()-1).add(Integer.parseInt(parts[1]));
        }
        //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
        //System.out.println(grading);
		System.out.println("Trying to solve the problem: V="+N+" E="+E);
		
		try {
			 
			long startTime   = System.currentTimeMillis();
        	
		      GRBEnv    env   = new GRBEnv("mip1.log");
		      GRBModel  model = new GRBModel(env);

		      // VARIABLES
			// a warehouse is either open or closed
			
			// which warehouse supplies a store
			ArrayList<GRBVar> X = new ArrayList<GRBVar>();//client-warehouse connections
		
			for(int i=0; i< N; i++){
				X.add(model.addVar(0.0, 1.0, grading.get(i), GRB.INTEGER, "x_"+i));
			}
			
		      // OBJECTIVE is set
			 model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);

			 //EDGE CONSTRAINTS
			 for (int j = 0; j < E; j++) {
			        GRBLinExpr ptot = new GRBLinExpr();
			        
			        ptot.addTerm(1, X.get(edges.get(j).get(0)));
			        ptot.addTerm(1, X.get(edges.get(j).get(1)));

			        model.addConstr(ptot, GRB.GREATER_EQUAL, 1, "edge" + j);
			 }

		
		      // Optimize model
			 
			 model.getEnv().set(GRB.DoubleParam.TimeLimit, 3600); 
			 model.getEnv().set(GRB.IntParam.Cuts, 0);
			 model.getEnv().set(GRB.IntParam.CoverCuts, 1);
			 model.getEnv().set(GRB.IntParam.Presolve, 0);
			 model.getEnv().set(GRB.IntParam.MIPFocus, 2);
			 
			model.optimize();
		
			
			System.out.println("SOLUTION");
			for(int i=0;i<X.size();i++){
				System.out.print(X.get(i).get(GRB.DoubleAttr.X)+" ");
			}
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("");
			System.out.println("EXEC TIME: "+totalTime);
		      // Dispose of model and environment
		
		      model.dispose();
		      env.dispose();
		
		    } catch (GRBException e) {
		      System.out.println("Error code: " + e.getErrorCode() + ". " +
		                         e.getMessage());
		    }
	}
	*/
	private static String out="";
	
	/**********TRANSPORTATION*******************/
	private static Vector NWCorner(ArrayList<Integer> demands, ArrayList<Integer> supplies ){
		Vector feasSolution = new Vector(new ArrayList<Integer>());
		
		//NW corner
		for(int i=0; i< demands.size()*supplies.size();i++){
			feasSolution.vec.add(0);
		}
		
		int j=0;
		for(int i=0;i<demands.size();i++){
			if(demands.get(i)<=supplies.get(j)){
				feasSolution.set(j*demands.size()+i,demands.get(i));
				supplies.set(j,supplies.get(j)-demands.get(i));
				if(supplies.get(j)==0){
					j++;
				}
			}else{
				feasSolution.set(j*demands.size()+i,supplies.get(j));
				demands.set(i,demands.get(i)-supplies.get(j));
				j++;
				
				while(demands.get(i)>supplies.get(j)){
					feasSolution.set(j*demands.size()+i,feasSolution.get(j*demands.size()+i)+supplies.get(j));
					demands.set(i,demands.get(i)-supplies.get(j));
					j++;
				}
				feasSolution.set(j*demands.size()+i,feasSolution.get(j*demands.size()+i)+demands.get(i));
				supplies.set(j,supplies.get(j)-demands.get(i));
				if(supplies.get(j)==0){
					j++;
				}
				
			}
		}
		
		
		/*System.out.println("FeasibleSolution: ");
		for(int i1=0;i1<supplies.size();i1++){
			for(int j1=0;j1<demands.size();j1++){
				System.out.print(feasSolution.get(i1*demands.size()+j1)+" ");
			}
			System.out.println();
		}*/
		return feasSolution;
		
	}
	
	public static void SampleTransportation(int m,int n,int samples){
		ArrayList<Integer> costs= new ArrayList<Integer>();
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				costs.add((int)Math.round(Math.random()*20+10));
			}
		}
		ArrayList<String> testList = new ArrayList<String>();
		
		for(int sam=0;sam<samples;sam++){
			testList.add("transp_"+m+"x"+n+"_"+sam+".txt");
			
			
			int Sup = (int) Math.round(Math.random()*1000+100);
			
			int Dem = Sup;
						
			ArrayList<Integer> supplies= new ArrayList<Integer>();
			ArrayList<Integer> demands= new ArrayList<Integer>();
			
			for(int i=0;i<m;i++){
				if(i<m-1){
					int sup=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
					while(sup==0 || sup==Sup){
						sup=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
					}
					supplies.add(sup);
					Sup-=sup;
				}else{
					supplies.add(Sup);
					Sup=0;
				}
				
				for(int j=0;j<n;j++){
					if(i==0){
						if(j<n-1){
							int dem=(int)Math.round(Math.random()*Dem/4+0.1*Dem);
							while(dem==0 || dem==Sup){
								dem=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
							}
							demands.add(dem);
							Dem-=dem;
						}else{
							demands.add(Dem);
							Dem=0;
						}
					}
					
				}
			}
			
			try{
    			PrintWriter writer = new PrintWriter(".\\data\\transp_samples\\samples_"+m+"x"+n+"\\transp_"+m+"x"+n+"_"+sam+".txt", "UTF-8");
    			
    			writer.println(m+" "+n);
    			
    			for(int i=0;i<m;i++){
    				for(int j=0;j<n;j++){
    					writer.println(costs.get(i*n+j));
    				}
    			}
    			
    		
    			for(int j=0;j<n;j++){
    				writer.println(demands.get(j));
    			}
    			
    			for(int i=0;i<m;i++){
    				writer.println(supplies.get(i));
    			}
    			
				
				writer.close();
				
				
				
			} catch (IOException e) {
	   			// do something
			}
		}
		try{
			PrintWriter writer = new PrintWriter(".\\data\\transp_samples\\samples_"+m+"x"+n+"\\testList.txt", "UTF-8");
			
			for(String test: testList){
				writer.println(test);		
			}

			writer.close();		
		} catch (IOException e) {
   			// do something
		}
		
		
	}
	
	/*
	private static void SolveTransportationWithGurobi(String fileName) throws IOException {
		
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
	    System.out.println(lines.size());
	    
	    // parse the data in the file
	    String[] firstLine = lines.get(0).split("\\s+");
	    int M = Integer.parseInt(firstLine[0]);
	    int N = Integer.parseInt(firstLine[1]);
		
	    Vector grading = new Vector(new ArrayList<Integer>());
	       
		for(int i=1; i <= M*N; i++){
	    	grading.vec.add(Integer.parseInt(lines.get(i)));
	    }
		
		ArrayList<Integer> demands = new ArrayList<Integer>();
		ArrayList<Integer> supplies = new ArrayList<Integer>();
		
		for(int i=0; i<N; i++){
			demands.add(Integer.parseInt(lines.get(i+M*N+1)));
		}
		
		for(int i=0; i<M; i++){
			supplies.add(Integer.parseInt(lines.get(i+M*N+N+1)));
		}
		
		//ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
	    //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
	    //System.out.println(grading);
		System.out.println("Trying to solve the problem: M="+M+" N="+N);
	    try {
	    	
	    	try {
				 
				long startTime   = System.currentTimeMillis();
	        	
			      GRBEnv    env   = new GRBEnv("mip1.log");
			      GRBModel  model = new GRBModel(env);

			      // VARIABLES
				// a warehouse is either open or closed
				
				// which warehouse supplies a store
				ArrayList<GRBVar> X = new ArrayList<GRBVar>();//client-warehouse connections
			
				for(int i=0; i< M; i++){
					for(int j=0; j< N; j++){
						X.add(model.addVar(0.0, GRB.INFINITY, grading.get(i*N+j), GRB.INTEGER, "x_"+i+"_"+j));
					}
				}
				
			      // OBJECTIVE is set
				 model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);

				 //CONSTRAINTS
				 for (int i = 0; i < M; i++) {
					 GRBLinExpr ptot = new GRBLinExpr();
					 for(int j=0;j<N;j++){
				        ptot.addTerm(1, X.get(i*N+j));
					 }
					 model.addConstr(ptot, GRB.EQUAL, supplies.get(i), "row" + i);
				 }
				 for(int j=0;j<N;j++){
				 
					 GRBLinExpr ptot = new GRBLinExpr();
					 for (int i = 0; i < M; i++) {
				        ptot.addTerm(1, X.get(i*N+j));
					 }
					 model.addConstr(ptot, GRB.EQUAL, demands.get(j), "col" + j);
				 }

			
			      // Optimize model
				 
				 model.getEnv().set(GRB.DoubleParam.TimeLimit, 3600); 
				 
				 
				model.optimize();
			
				
				System.out.println("SOLUTION");
				for(int i=0;i<M;i++){
					for(int j=0;j<N;j++){
						System.out.print(Math.round(X.get(i*N+j).get(GRB.DoubleAttr.X))+" ");
					}
					System.out.println("");
				}
				long totalTime = System.currentTimeMillis() - startTime;
				System.out.println("");
				System.out.println("EXEC TIME: "+totalTime);
			      // Dispose of model and environment
			
			      model.dispose();
			      env.dispose();
			
			    } catch (GRBException e) {
			      System.out.println("Error code: " + e.getErrorCode() + ". " +
			                         e.getMessage());
			    }
	    	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		
	}*/
	
	public static void SampleSeveralTransportation(int m,int n,int samples){
		File dir = new File(".\\data\\transp_samples\\samples_"+m+"x"+n+"\\");
		dir.mkdir();
		
		ArrayList<String> testList = new ArrayList<String>();
		
		for(int sam=0;sam<samples;sam++){
			testList.add("transp_"+m+"x"+n+"_"+sam+".txt");
			
			ArrayList<Integer> costs= new ArrayList<Integer>();
			for(int i=0;i<m;i++){
				for(int j=0;j<n;j++){
					costs.add((int)Math.round(Math.random()*20+10));
				}
			}
			
			int Sup = (int) Math.round(Math.random()*1000+100);
			
			int Dem = Sup;
						
			ArrayList<Integer> supplies= new ArrayList<Integer>();
			ArrayList<Integer> demands= new ArrayList<Integer>();
			
			for(int i=0;i<m;i++){
				if(i<m-1){
					int sup=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
					while(sup==0 || sup==Sup){
						sup=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
					}
					supplies.add(sup);
					Sup-=sup;
				}else{
					supplies.add(Sup);
					Sup=0;
				}
				
				for(int j=0;j<n;j++){
					if(i==0){
						if(j<n-1){
							int dem=(int)Math.round(Math.random()*Dem/4+0.1*Dem);
							while(dem==0 || dem==Sup){
								dem=(int)Math.round(Math.random()*Sup/4+0.1*Sup);
							}
							demands.add(dem);
							Dem-=dem;
						}else{
							demands.add(Dem);
							Dem=0;
						}
					}
					
				}
			}
			
			try{
    			PrintWriter writer = new PrintWriter(".\\data\\transp_samples\\samples_"+m+"x"+n+"\\transp_"+m+"x"+n+"_"+sam+".txt", "UTF-8");
    			
    			writer.println(m+" "+n);
    			
    			for(int i=0;i<m;i++){
    				for(int j=0;j<n;j++){
    					writer.println(costs.get(i*n+j));
    				}
    			}
    			
    		
    			for(int j=0;j<n;j++){
    				writer.println(demands.get(j));
    			}
    			
    			for(int i=0;i<m;i++){
    				writer.println(supplies.get(i));
    			}
    			
				
				writer.close();
				
				
				
			} catch (IOException e) {
	   			// do something
			}
		}
		try{
			PrintWriter writer = new PrintWriter(".\\data\\transp_samples\\samples_"+m+"x"+n+"\\testList.txt", "UTF-8");
			
			for(String test: testList){
				writer.println(test);		
			}

			writer.close();		
		} catch (IOException e) {
   			// do something
		}
		
		
	}

	private static Vector BuildTranspFeasSol(String fileName) throws IOException {
		// TODO Auto-generated method stub
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
	    int M = Integer.parseInt(firstLine[0]);
	    int N = Integer.parseInt(firstLine[1]);

		ArrayList<Integer> demands = new ArrayList<Integer>();
		ArrayList<Integer> supplies = new ArrayList<Integer>();
		
		for(int i=0; i<N; i++){
			demands.add(Integer.parseInt(lines.get(i+M*N+1)));
		}
		
		for(int i=0; i<M; i++){
			supplies.add(Integer.parseInt(lines.get(i+M*N+N+1)));
		}
		
		return NWCorner(demands, supplies);
	}

	private static ArrayList<VectorBinomial> FindGBTransportation(ArrayList<String> outp,String fileName) throws IOException {
		
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
	    System.out.println(lines.size());
	    
	    // parse the data in the file
	    String[] firstLine = lines.get(0).split("\\s+");
	    int M = Integer.parseInt(firstLine[0]);
	    int N = Integer.parseInt(firstLine[1]);
		
	    Vector grading = new Vector(new ArrayList<Integer>());
	       
		for(int i=1; i <= M*N; i++){
	    	grading.vec.add(Integer.parseInt(lines.get(i)));
	    }
		
		
		//ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
	    //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
	    //System.out.println(grading);
		System.out.println("Trying to solve the problem: M="+M+" N="+N);
	    try {
	    	
	    	//basis construction
	    	ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
	        
	        
	        for(int j=1;j<=M-1;j++){
	        	for(int i=0;i<j;i++){
	        		for(int l=1;l<=N-1;l++){
	                	for(int k=0;k<l;k++){
	                		VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
	                    	for(int x=0;x<M*N;x++){
	                    		g1.vec.add(0);
	                    	}
	                		g1.set(i*N+l, 1);
	                		g1.set(j*N+k, 1);
	                		g1.set(i*N+k, -1);
	                		g1.set(j*N+l, -1);
	                		gs.add(g1);
	                	}
	                }
	        	}
	        }
	        long start = System.currentTimeMillis();
	        
			ArrayList<VectorBinomial> gB = VectorBinomial.MinimizeBasis(VectorBinomial.BuchbergerAlgorithm(outp, gs, grading));
			outp.add(""+(double)(System.currentTimeMillis()-start)/1000);
			int maxdeg=0;
			for(VectorBinomial v: gB){
				int deg=0;
				for(int i=0;i<v.Size();i++){
					if(v.get(i)>0){
						deg+=v.get(i);
					}
				}
				if(deg>maxdeg){
					maxdeg=deg;
				}
			}
			outp.add(""+maxdeg);
			System.out.println("Minimized GB: "+gB.size());
			return gB;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return null;
		
	}
	
private static ArrayList<Integer> SolveTransportation(ArrayList<String> outp, String fileName) throws IOException {
		
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
        System.out.println(lines.size());
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int M = Integer.parseInt(firstLine[0]);
        int N = Integer.parseInt(firstLine[1]);
		
        Vector grading = new Vector(new ArrayList<Integer>());
           
		for(int i=1; i <= M*N; i++){
        	grading.vec.add(Integer.parseInt(lines.get(i)));
        }
		
		ArrayList<Integer> demands = new ArrayList<Integer>();
		ArrayList<Integer> supplies = new ArrayList<Integer>();
		
		for(int i=0; i<N; i++){
			demands.add(Integer.parseInt(lines.get(i+M*N+1)));
		}
		
		for(int i=0; i<M; i++){
			supplies.add(Integer.parseInt(lines.get(i+M*N+N+1)));
		}
		
		//ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		
        //for(int i=0;i<gs.size();i++){
		//	System.out.println(gs.get(i));
		//}
        //System.out.println(grading);
		System.out.println("Trying to solve the problem: M="+M+" N="+N);
        try {
        	long startTime = System.currentTimeMillis();
        	
        	//basis construction
        	ArrayList<VectorBinomial> gs = new ArrayList<VectorBinomial>();
            
            
            for(int j=1;j<=M-1;j++){
            	for(int i=0;i<j;i++){
            		for(int l=1;l<=N-1;l++){
                    	for(int k=0;k<l;k++){
                    		VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),0);
                        	for(int x=0;x<M*N;x++){
                        		g1.vec.add(0);
                        	}
                    		g1.set(i*N+l, 1);
                    		g1.set(j*N+k, 1);
                    		g1.set(i*N+k, -1);
                    		g1.set(j*N+l, -1);
                    		gs.add(g1);
                    	}
                    }
            	}
            }
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.MinimizeBasis(VectorBinomial.BuchbergerAlgorithm(outp,gs, grading));
			System.out.println("Minimized GB: "+gB.size());
			//System.out.println("gB");
			//for(int i=0;i<gB.size();i++){
			//	System.out.println(gB.get(i));
			//}

			Vector feasSolution = new Vector(new ArrayList<Integer>());
			
			//NW corner
			for(int i=0; i< M*N;i++){
				feasSolution.vec.add(0);
			}
			
			int j=0;
			for(int i=0;i<N;i++){
				if(demands.get(i)<=supplies.get(j)){
					feasSolution.set(j*N+i,demands.get(i));
					supplies.set(j,supplies.get(j)-demands.get(i));
					if(supplies.get(j)==0){
						j++;
					}
				}else{
					feasSolution.set(j*N+i,supplies.get(j));
					demands.set(i,demands.get(i)-supplies.get(j));
					j++;
					
					while(demands.get(i)>supplies.get(j)){
						feasSolution.set(j*N+i,feasSolution.get(j*N+i)+supplies.get(j));
						demands.set(i,demands.get(i)-supplies.get(j));
						j++;
					}
					feasSolution.set(j*N+i,feasSolution.get(j*N+i)+demands.get(i));
					supplies.set(j,supplies.get(j)-demands.get(i));
					if(supplies.get(j)==0){
						j++;
					}
				}
			}
			
			
			System.out.println("FeasibleSolution: ");
			for(int i=0;i<M;i++){
				for(j=0;j<N;j++){
					System.out.print(feasSolution.get(i*N+j)+" ");
				}
				System.out.println();
			}
			
			feasSolution.FindNormalForm(gB);
			
			System.out.println("OptimalSolution: "+feasSolution);
			for(int i=0;i<M;i++){
				for(j=0;j<N;j++){
					System.out.print(feasSolution.get(i*N+j)+" ");
				}
				System.out.println();
			}
			
			long endTime   = System.currentTimeMillis();
        	long totalTime = endTime - startTime;
        	System.out.println("Execution Time:"+(double)(totalTime)/1000);//seconds
        	
        	if(fileName=="./data/transp/really_easy"){
        		System.out.println("gB");
    			for(int i=0;i<gB.size();i++){
    				System.out.println(gB.get(i).asMatrix(3, 3));
    			}
        		System.out.println("ENUMERATION");
        		
        		ArrayList<Vector> feasSet = VectorBinomial.Enumerate(feasSolution, gB,3,3);
        		System.out.println("Size of feasible set:"+feasSet.size());
        	}
        	
			return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
		
	}


	
	/**********************MAIN*********************************/
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		/**************TESTING FAUGERE*******************/
		//Solve vcover
		ArrayList<String> outp = new ArrayList<String>();
		SolveVertexCover(outp, "./data/vcov_10_14");
		
		
		
		/*************KNAPSACKTESTS*****************/
		
		//FileWriter fw = new FileWriter(".\\data\\knapsack_samples\\LOG.csv",false);
		//BufferedWriter bw = new BufferedWriter(fw);
		//bw.write("Problem,FullTime,GBSize,minGBSize,critPairs,zeroRed,maxDeg,maxCoord\n");
		//bw.close();
		
		/*int maxWeight=5;
		int Nsamples=3;
		
		for(int N=20;N<=40;N++){
			SampleKnapsack(maxWeight, N, Nsamples);
			
	        List<String> lines = new ArrayList<String>();
	        BufferedReader input =  new BufferedReader(new FileReader("./data/knapsack_samples/samples_"+N+"/testlist.txt"));
	        try {
	            String line = null;
	            while (( line = input.readLine()) != null){
	                lines.add(line);
	            }
	        }
	        finally {
	            input.close();
	        }
			
	        
	        for(String line: lines){
	        	ArrayList<String> outp = new ArrayList<String>();
	        	
	        	try {
					SolveKnapsack(outp, line);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//outp format
				// * outp.add(""+grobBasis.size());
				//	outp.add(""+critPairsConsidered);
				//	outp.add(""+zeroRed);
				//	outp.add(""+minGrobBasis.size());
				//	outp.add(time)
				//	outp.add(maxdeg)	
				 
	        	FileWriter fw = new FileWriter(".\\data\\knapsack_samples\\LOG.csv",true);
	        	BufferedWriter bw = new BufferedWriter(fw);
	    		//bw.write("Problem,FullTime,GBSize,minGBSize,critPairs,zeroRed\n");
	    		bw.write(N+","+outp.get(4)+","+outp.get(0)+","+outp.get(3)+",");
	    		bw.write(outp.get(1)+","+outp.get(2)+","+outp.get(5)+","+outp.get(6)+"\n");
	    		bw.close();
	        	
	        }
		}*/
		

		
		
		
		
		
		//TestVCoverBoundedGB("./data/vcov_20_95");
		//SolveBoundedVertexCover("./data/vcov_20_95");
		//SolveVCovWithGurobi("./data/vcov_20_95");
		//TESTING SYSTEM DO NOT TOUCH IT
		//ArrayList<String> tests = new ArrayList<String>();
		/*try {
			
			tests.add("./data/vcov_6_7");
			tests.add("./data/vcov_10_14");
			tests.add("./data/vcov_20_23");
			
			tests.add("./data/vcov_20_33");
			
			//not solved
			//tests.add("./data/vcov_20_43");
			//tests.add("./data/vcov_50_63");
			//tests.add("./data/vcov_50_73");
			
			ArrayList<String> outs = new ArrayList<String>();
			for(String test : tests){
				out="";
				ArrayList<Integer> vCovGB = SolveVertexCover(test);
				outs.add(out);
				String[] parts = test.split("_");
				int V = Integer.parseInt(parts[1]);
				//ArrayList<Integer> vCovGB = SolveMoney();
				System.out.println("Comparison");
				for (int i=0; i< V; i++){
					System.out.print(vCovGB.get(i)+" ");
				}
			}
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*try {//the same vcover with gurobi
			tests = new ArrayList<String>();
			tests.add("./data/vcov_6_7");
			tests.add("./data/vcov_10_14");
			tests.add("./data/vcov_20_23");
			
			tests.add("./data/vcov_20_33");
			
			//not solved
			tests.add("./data/vcov_20_43");
			tests.add("./data/vcov_50_63");
			tests.add("./data/vcov_50_73");
			
			for(String test : tests){
				SolveVCovWithGurobi(test);
			}
			
			
						
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//AutoTransportationTest
		
		/*int SAMPLES=1;
		String latexTableOut = "\\hline\nProblem & FullTime & GBTime & GBSize & MinGBSize & critPairs & ZeroReductions\\\\\n \\hline\n";
		
		
		for(int M=4;M<=10;M++){
			for(int N=4;N<=10;N++){
				
				FileWriter fw = new FileWriter(".\\data\\transp_samples\\LOG.csv",true);
				BufferedWriter bw = new BufferedWriter(fw);
				SampleSeveralTransportation(M,N, SAMPLES);
				
				/*try {
					
				    // read the lines out of the file
				    List<String> lines = new ArrayList<String>();
		
				    BufferedReader input =  new BufferedReader(new FileReader("./data/transp_samples/samples_"+M+"x"+N+"/"+"testList.txt"));
				    try {
				        String line = null;
				        while (( line = input.readLine()) != null){
				            lines.add(line);
				        }
				    }
				    finally {
				        input.close();
				    }
					
					long start = System.currentTimeMillis();
					
					for(String test: lines){
						SolveTransportationWithGurobi("./data/transp_samples/samples_"+M+"x"+N+"/"+test);
					}
					System.out.println("***********GUROBI TOTAL EXEC TIME: "+(System.currentTimeMillis()-start));
					latexTableOut+=""+M+"x"+N+" & ";
					latexTableOut+=""+(System.currentTimeMillis()-start)+" & ";
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				//and now, use GB
				/*try {
					
				    // read the lines out of the file
				    List<String> lines = new ArrayList<String>();
		
				    BufferedReader input =  new BufferedReader(new FileReader("./data/transp_samples/samples_"+M+"x"+N+"/testList.txt"));
				    try {
				        String line = null;
				        while (( line = input.readLine()) != null){
				            lines.add(line);
				        }
				    }
				    finally {
				        input.close();
				    }
		    	
					//bw.write("Supplies,Demands,FullTime,GBTime,GBSize,MinGBSize,critPairs,ZeroRed\n");
					
					for(String test: lines){
						/*outp format
						 * outp.add(""+grobBasis.size());
							outp.add(""+critPairsConsidered);
							outp.add(""+zeroRed);
							outp.add(""+gBTime);
						 * */
					/*	ArrayList<String> outp=new ArrayList<String>();
						long start = System.currentTimeMillis();
						ArrayList<VectorBinomial> gB = FindGBTransportation(outp,"./data/transp_samples/samples_"+M+"x"+N+"/"+"transp_"+M+"x"+N+"_0.txt");
						Vector feasSolution = BuildTranspFeasSol("./data/transp_samples/samples_"+M+"x"+N+"/"+test);
						feasSolution.FindNormalForm(gB);	
						long fin = (System.currentTimeMillis()-start);
						bw.write(M+","+N+","+(double)(fin)/1000+","+outp.get(3));
						bw.write(","+outp.get(0)+","+gB.size()+","+outp.get(1)+","+outp.get(2));
						bw.write("\n");
						//SolveTransportationWithGurobi("./data/transp_samples/"+test);
					}
					*/
					
					/*System.out.println("GB TOTAL EXEC TIME: "+(System.currentTimeMillis()-start));
					
					latexTableOut+=""+(System.currentTimeMillis()-start)+" & ";
					latexTableOut+=""+(-1)+" & ";
					latexTableOut+=""+gB.size()+" & ";
					latexTableOut+=""+"critPairs"+" & ";
					latexTableOut+=""+"zeroRED"+" & ";
					latexTableOut+="\\\\\n";*/
			/*	} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bw.close();
			}
		}
		
		latexTableOut+="\\hline";
		System.out.println(latexTableOut);
		*/
		
		//REALLYEASY example
		/*try {
			ArrayList<Integer> vCovGB = SolveTransportation("./data/transp/really_easy");
			System.out.println("Comparison");
			for (int i=0; i< vCovGB.size(); i++){
				System.out.print(vCovGB.get(i)+" ");
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
	}

	
	

}
