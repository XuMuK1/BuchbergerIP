package integerProgramming;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gurobi.*;

public class TestingInterface {
	
	
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
	
	private static void GrobnerTest(){
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
				grobBasis = VectorBinomial.BuchbergerAlgorithm(gs, grading);
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
	
	
	private static ArrayList<Integer> SolveKnapsack(String fileName) throws IOException {
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
        	VectorBinomial g1= new VectorBinomial(new ArrayList<Integer>(),1);
        	
        	g1.vec.add(0);
        	g1.vec.add(Integer.parseInt(parts[1]));
        	
        	for (int j=2; j<i+1; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(-1);
    		for (int j=i+2; j<=numberOfItems+i; j++){
        		g1.vec.add(0);
        	}
    		g1.vec.add(1);
    		for (int j=1; j<=numberOfItems-i; j++){
        		g1.vec.add(0);
        	}
    		gs.add(g1);
    		
    		grading.vec.set(i,1+g1.vec.get(1));
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
			ArrayList<VectorBinomial> gB = VectorBinomial.FilterBasis(VectorBinomial.BuchbergerAlgorithm(gs, grading));
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
			
			return feasSolution.vec;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;     
		
	}
	//MAIN*******************************
	
	private static ArrayList<Integer> SolveMoney(){
		
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
        	
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.MinimizeBasis(VectorBinomial.BuchbergerAlgorithm(gs, grading));
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
		
	}
	
	private static ArrayList<Integer> SolveVertexCover(String fileName) throws IOException {
		
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
        	
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.BuchbergerAlgorithm(gs, grading, N);
			out+=""+gB.size();
			gB=VectorBinomial.MinimizeBasis(gB);
			out+=" & "+gB.size();
			System.out.println("Minimized GB: "+gB.size());
			/*System.out.println("gB");
			for(int i=0;i<gB.size();i++){
				System.out.println(gB.get(i));
			}*/

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
	
private static ArrayList<Integer> SolveTransportation(String fileName) throws IOException {
		
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
        	
			ArrayList<VectorBinomial> gB = VectorBinomial.MinimizeBasis(VectorBinomial.BuchbergerAlgorithm(gs, grading));
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


	public static void SolveVCovWithGurobi(String fileName) throws IOException{
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
			ArrayList<GRBVar> open = new ArrayList<GRBVar>();
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
	private static String out="";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//VectorsAndOrders();//test orders and vectors TESTED
		//GrobnerTest(); //test Buchberger TESTED
		/*try {
			int[] takenBaB = BaBKnapsack.solveProblem("./data/ks_4_0");
			ArrayList<Integer> takeGB = SolveKnapsack("./data/ks_4_0");
			System.out.println("Comparison");
			for (int i=0; i< takenBaB.length; i++){
				System.out.println(takenBaB[i]-takeGB.get(i+1));
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			ArrayList<String> tests = new ArrayList<String>();
			/*tests.add("./data/vcov_6_7");
			tests.add("./data/vcov_10_14");
			tests.add("./data/vcov_20_23");
			tests.add("./data/vcov_20_33");
			tests.add("./data/vcov_20_43");*/
			tests.add("./data/vcov_50_63");
			tests.add("./data/vcov_50_73");
			
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
		
		/*try {//the same vcover with gurobi
			SolveVCovWithGurobi("./data/vcov_50_133");
						
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
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
