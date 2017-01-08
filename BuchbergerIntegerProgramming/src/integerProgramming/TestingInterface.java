package integerProgramming;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        
        grading.vec.add(0);//for s0
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
    		
    		grading.vec.set(i-1,1);
    		grading.vec.set(i-1+numberOfItems,Integer.parseInt(parts[0])+1);
        }
		
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
			
			return (ArrayList<Integer>) feasSolution.vec.subList(1, numberOfItems);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;     
		
	}
	//MAIN*******************************
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//VectorsAndOrders();//test orders and vectors TESTED
		//GrobnerTest(); //test Buchberger TESTED
		try {
			int[] takenBaB = BaBKnapsack.solveProblem("./data/ks_50_0");
			ArrayList<Integer> takeGB = SolveKnapsack("./data/ks_50_0");
			System.out.println("Comparison");
			for (int i=0; i< takeGB.size(); i++){
				System.out.println(takenBaB[i]-takeGB.get(i));
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			SolveKnapsack("./data/ks_50_0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
