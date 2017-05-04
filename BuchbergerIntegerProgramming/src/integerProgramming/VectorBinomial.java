package integerProgramming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class VectorBinomial extends Vector {
	public int firstValuableVariable=0;	
	
	public VectorBinomial(ArrayList<Integer> Vec, int Last){
		firstValuableVariable=Last;
		vec=Vec;	
	}
	
	public VectorBinomial(Vector vecc, int Last){
		firstValuableVariable=Last;
		vec=vecc.vec;	
	}
	
	public VectorBinomial copyVectorBinomial() {
		// TODO Auto-generated method stub
		VectorBinomial vB = new VectorBinomial(new ArrayList<>(this.vec),this.firstValuableVariable);
		return vB;
	}

	public int isZero=-1;//not set
	/******<Arithmetics>**************/
	public static ArrayList<Integer> Add(VectorBinomial v1, VectorBinomial v2, int mul) throws Exception{
		ArrayList<Integer> res = new ArrayList<Integer>();
		if(v2.Size() != v1.Size()){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<v1.Size(); i++){
			res.add(v1.get(i) + mul*v2.get(i));
		}
		return res;
	}
	
	public static ArrayList<Integer> Add(VectorBinomial v1, VectorBinomial v2) throws Exception{
		ArrayList<Integer> res = new ArrayList<Integer>();
		if(v2.Size() != v1.Size()){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<v1.Size(); i++){
			res.add(v1.get(i) + v2.get(i));
		}
		return res;
	}
	/******</Arithmetics>**************/

	/*******<Comparisons>************/
	public int CompareTotally(VectorBinomial other) throws Exception{
		
		if(other.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		boolean isSet = false;
		int result=0;//0: equal, 2: uncomparable,
				   //1: this > other, -1: this < other
		
		for(int i=0; i < Size();i++){
			
			if (vec.get(i) < other.vec.get(i)){
				
				if(isSet){
					if(result == 1){
						return 2;//uncomparable then
					}
				}else{
					isSet=true;
					result=-1;
				}
				
			}else{
				
				if (vec.get(i) > other.vec.get(i)){
					
					if(isSet){
						if(result == -1){
							return 2;//uncomparable then
						}
					}else{
						isSet=true;
						result=1;
					}
				}
				
			}
			
		}
		
		return result;
	}
	
	public int CompareTotally(Vector other) throws Exception{
		
		if(other.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch: othSize="+other.Size()+" vs thisSize="+Size());
		}
		
		boolean isSet = false;
		int result=0;//0: equal, 2: uncomparable,
				   //1: this > other, -1: this < other
		
		for(int i=0; i < Size();i++){
			
			if (vec.get(i) < other.vec.get(i)){
				
				if(isSet){
					if(result == 1){
						return 2;//uncomparable then
					}
				}else{
					isSet=true;
					result=-1;
				}
				
			}else{
				
				if (vec.get(i) > other.vec.get(i)){
					
					if(isSet){
						if(result == -1){
							return 2;//uncomparable then
						}
					}else{
						isSet=true;
						result=1;
					}
				}
				
			}
			
		}
		
		return result;
	}

	public int CompareTotally(int cons){
		
		
		
		boolean isSet = false;
		int result=0;//0: equal, 2: uncomparable,
				   //1: this > other, -1: this < other
		
		for(int i=0; i < Size();i++){
			
			if (vec.get(i) < cons){
				
				if(isSet){
					if(result == 1){
						return 2;//uncomparable then
					}
				}else{
					isSet=true;
					result=-1;
				}
				
			}else{
				
				if (vec.get(i) > cons){
					
					if(isSet){
						if(result == -1){
							return 2;//uncomparable then
						}
					}else{
						isSet=true;
						result=1;
					}
				}
				
			}
			
		}
		
		return result;
	}
	
	
	public int CompareLex(VectorBinomial other) throws Exception{
		
		if(other.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		for(int i=0; i < Size();i++){
			
			if (vec.get(i) < other.vec.get(i)){
				return -1;
			}else{
				if (vec.get(i) > other.vec.get(i)){
					return 1;
				}
				
			}
			
		}
		
		return 0;
	}

	public int CompareLex(int cons){
		
		
		
		for(int i=0; i < Size();i++){
			
			if (vec.get(i) < cons){
				return -1;
			}else{
				if (vec.get(i) > cons){
					return 1;
				}
				
			}
			
		}
		
		return 0;
	}
	
	
	public int CompareRevLex(VectorBinomial other) throws Exception{
		
		if(other.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		for(int i= Size()-1; i >=0;i--){
			
			if (vec.get(i) < other.vec.get(i)){
				return 1;
			}else{
				if (vec.get(i) > other.vec.get(i)){
					return -1;
				}
				
			}
			
		}
		
		return 0;
	}
	
	public int CompareRevLex(int cons){
		
		for(int i= Size()-1; i >=0;i--){
			
			if (vec.get(i) < cons){
				return 1;
			}else{
				if (vec.get(i) > cons){
					return -1;
				}
				
			}
			
		}
		
		return 0;
	}

	
	public int CompareGLex(VectorBinomial other, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
	
		if(other.Size() != Size() || grading.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		double deg1=0;
		double deg2=0;
		
		for(int i= Size()-1; i >=0;i--){
			deg1+=vec.get(i)*grading.get(i);
			deg2+=other.get(i)*grading.get(i);
		}
		
		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareLex(other);
			}
		}
		
	}

	public int CompareGLex(int cons, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
	
		if( grading.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		double deg1=0;
		double deg2=0;
		
		for(int i= Size()-1; i >=0;i--){
			deg1+=vec.get(i)*grading.get(i);
			deg2+=cons*grading.get(i);
		}
		
		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareLex(cons);
			}
		}
		
	}

	
	public int CompareGRevLex(VectorBinomial other, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other

		if(other.Size() != Size() || grading.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}

		double deg1=0;
		double deg2=0;

		for(int i= Size()-1; i >=0;i--){
			deg1+=vec.get(i)*grading.get(i);
			deg2+=other.get(i)*grading.get(i);
		}

		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareRevLex(other);
			}
		}

	}
	
	public int CompareGRevLex(int cons, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other

		if( grading.Size() != Size()){
			throw new Exception("Vector Dimension Mismatch");
		}

		double deg1=0;
		double deg2=0;

		for(int i= Size()-1; i >=0;i--){
			deg1+=vec.get(i)*grading.get(i);
			deg2+=cons*grading.get(i);
		}

		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareRevLex(cons);
			}
		}

	}
	
	
	public int CompareBlockGRevLex(VectorBinomial other, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
		
		
		if(other.Size() != Size() || grading.Size() != Size()-firstValuableVariable+1){
			throw new Exception("Vector Dimension Mismatch");
		}

		//Block1 Lex for t's
		for(int i = 0; i < firstValuableVariable;i++){
	
			if (vec.get(i) < other.vec.get(i)){
				return 1;
			}else{
				if (vec.get(i) > other.vec.get(i)){
					return -1;
				}
				
			}
			
		}
		//here the situation is tied, so continue with grlex
		
		//Block2
		double deg1=0;
		double deg2=0;

		for(int i= Size()-1; i >=firstValuableVariable;i--){
			deg1+=vec.get(i)*grading.get(i-firstValuableVariable);
			deg2+=other.get(i)*grading.get(i-firstValuableVariable);
		}

		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareRevLex(other);
			}
		}

	}

	public int CompareBlockGRevLex(int cons, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
		
		
		if( grading.Size() != Size()-firstValuableVariable){
			throw new Exception("Vector Dimension Mismatch");
		}

		//Block1 Lex for t's
		for(int i = 0; i < firstValuableVariable;i++){
	
			if (vec.get(i) < cons){
				return 1;
			}else{
				if (vec.get(i) > cons){
					return -1;
				}
				
			}
			
		}
		//here the situation is tied, so continue with grlex
		
		//Block2
		double deg1=0;
		double deg2=0;

		for(int i= Size()-1; i >=firstValuableVariable;i--){
			deg1+=vec.get(i)*grading.get(i-firstValuableVariable);
			deg2+=cons*grading.get(i-firstValuableVariable);
		}

		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareRevLex(cons);
			}
		}

	}
	
	
	public int CompareBlockGLex(VectorBinomial other, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
	
		
		if(other.Size() != Size() || grading.Size() != Size()-firstValuableVariable){
			throw new Exception("Vector Dimension Mismatch");
		}
		
		//Block1 Lex for t's
		for(int i = 0; i < firstValuableVariable;i++){

			if (vec.get(i) < other.vec.get(i)){
				return 1;
			}else{
				if (vec.get(i) > other.vec.get(i)){
					return -1;
				}

			}

		}
		//here the situation is tied, so continue with glex

		//Block2
		double deg1=0;
		double deg2=0;
		
		for(int i= Size()-1; i >=firstValuableVariable;i--){
			deg1+=vec.get(i)*grading.get(i-firstValuableVariable);
			deg2+=other.get(i)*grading.get(i-firstValuableVariable);
		}
		
		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareLex(other);
			}
		}
		
	}
		
	public int CompareBlockGLex(int cons, Vector grading) throws Exception{
		//1: this > other,
		//-1: this < other,
		//0: this = other
	
		if( grading.Size() != Size()-firstValuableVariable){
			throw new Exception("Vector Dimension Mismatch "+grading.Size()+" vs "+ (Size()-firstValuableVariable));
		}
		
		//Block1 Lex for t's
		for(int i = 0; i < firstValuableVariable;i++){

			if (vec.get(i) < cons){
				return -1;
			}else{
				if (vec.get(i) > cons){
					return 1;
				}

			}

		}
		//here the situation is tied, so continue with glex

		//Block2
		double deg1=0;
		double deg2=0;
		
		for(int i= firstValuableVariable; i <Size();i++){
			deg1+=vec.get(i)*grading.get(i-firstValuableVariable);
			deg2+=cons*grading.get(i-firstValuableVariable);
		}
		
		if(deg1>deg2){
			return 1;
		}else{
			if(deg1<deg2){
				return -1;
			}else{
				return CompareLex(cons);
			}
		}
		
	}	
	/*******</Comparisons>************/
	
	/*******<ReductionThings>**********/
	/////ATTENTION, IT USES GRLEX
	public void TransformToPlus(Vector grading) throws Exception{
			if (CompareBlockGLex(0, grading)<0){
				for (int i=0; i< vec.size(); i++){
					vec.set(i, -vec.get(i));
				}
			}
	}
	
	public void TransformToMinus(Vector grading) throws Exception{
		if (CompareBlockGLex(0, grading)>0){
			for (int i=0; i< vec.size(); i++){
				vec.set(i, -vec.get(i));
			}
		}
}
	
	public VectorBinomial Plus(){
		
		VectorBinomial result = new VectorBinomial(new ArrayList<Integer>(), firstValuableVariable);
		for (int i=0; i < vec.size() ; i++){
			
			if(vec.get(i)>0){
				result.vec.add(vec.get(i));
			}else{
				result.vec.add(0);
			}
			
		}
		
		return result;
		
	}
	
	public VectorBinomial Minus(){
		
		VectorBinomial result = new VectorBinomial(new ArrayList<Integer>(), firstValuableVariable);
		for (int i=0; i < vec.size() ; i++){
			
			if(vec.get(i)<0){
				result.vec.add(vec.get(i));
			}else{
				result.vec.add(0);
			}
			
		}
		
		return result;
		
	}
	
	public void ReduceByList(ArrayList<VectorBinomial> lst,Vector grading) throws Exception{
		boolean wasReduction=true;
		while(wasReduction){
			
			wasReduction = false;
			
			//2.1
			for(int i=0; i<lst.size(); i++){
			
				if(lst.get(i).Plus().CompareTotally(Plus())<=0){
					this.Add(lst.get(i), -1);
					this.TransformToPlus(grading);
					//System.out.println("2.1reduced: "+this);
					wasReduction=true;
					
					//zero test
					int k=0;
					for(k=0;k<this.Size();k++){
						if(this.get(k)!=0){
							break;
						}
					}
					if(k==this.Size()){
						this.isZero=1;//yes it is zero
						return;//if suddenly we get 0
					}
					break;
				}
			}
			/*if(!wasReduction){
				for(int i=0; i<lst.size(); i++){
					
					if(lst.get(i).Minus().CompareTotally(Plus())<=0){
						this.Add(lst.get(i));
						this.TransformToPlus(grading);
						System.out.println("2.2reduced: "+this);
						wasReduction=true;
						if(this.CompareTotally(0)==0){
							return;//if suddenly we get 0
						}
						break;
					}
				}
				
			}	*/	
			
		}
		
	}
	
	public void ReduceByList(ArrayList<VectorBinomial> lst,Vector grading,int V, int iter) throws Exception{
		//with iteration criterion
		boolean wasReduction=true;
		while(wasReduction){
			
			wasReduction = false;
			
			if(IterationCriterion(this, V, iter)){//DROP IT
				/*for(int i=0;i<this.vec.size();i++){
					this.vec.set(i,0);
					
				}*/
				this.isZero=1;//yes it is zero
				return;
			}
			//2.1
			for(int i=0; i<lst.size(); i++){
			
				if(lst.get(i).Plus().CompareTotally(Plus())<=0){
					this.Add(lst.get(i), -1);
					this.TransformToPlus(grading);
					//System.out.println("2.1reduced: "+this);
					wasReduction=true;
					
					//zero test
					int j;
					for(j=0;j<V;j++){
						if(vec.get(j)!=0){
							break;
						}
					}
					if(j==V){
						this.isZero=1;//yes it is zero
						return;//if suddenly we get 0
					}
					break;
				}
			}
			/*if(!wasReduction){
				for(int i=0; i<lst.size(); i++){
					
					if(lst.get(i).Minus().CompareTotally(Plus())<=0){
						this.Add(lst.get(i));
						this.TransformToPlus(grading);
						System.out.println("2.2reduced: "+this);
						wasReduction=true;
						if(this.CompareTotally(0)==0){
							return;//if suddenly we get 0
						}
						break;
					}
				}
				
			}	*/	
			
		}
		
	}
	
	/*****************Grobner Bases
	 * @throws Exception *******************/
	public static boolean BuchbCriterion(VectorBinomial v1, VectorBinomial v2) throws Exception{
		if(v1.Size() != v2.Size()){
			throw new Exception("Dimension Mismatch");
		}
		
		for(int i=0; i<v1.Size(); i++){
			if(v1.get(i)>0){
				if(v2.get(i)>0){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	private static boolean IterationCriterion(VectorBinomial spair,int V,int iter){
		
		if(iter>1){
			int num=0;
			//System.out.println(spair.vec);
			for(int i=0;i<V;i++){
				
				if(spair.vec.get(i)!=0){
					num++;
					
				}
				if(num>iter){
					return false; //ok...
				}
				//System.out.println(num);
				
			}
			if(num<=iter){
				return true;
			}
			
			
		}
		return false; // ok.....
	}
	
	
	private static boolean IterationCriterion(VectorBinomial g1, VectorBinomial g2,int V, int iter) {
		// TODO Auto-generated method stub
		// one more variant
		int num=0;
		//System.out.println(spair.vec);
		for(int i=0;i<V;i++){
			
			if(g1.get(i)!=g2.get(i)){
				num++;
			}
			if(num>iter){
				return false; //ok...
			}
			//System.out.println(num);
			
		}
		if(num<=iter){
			return true;
		}
		
		return false;
	}
	
	public static ArrayList<VectorBinomial> BuchbergerAlgorithm(ArrayList<String> outp, ArrayList<VectorBinomial> basis, Vector grading, int V) throws Exception{
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>(basis);
		//with iteration criterion
		int j0=0;
		boolean added = true;
		
		for(int i=0; i< basis.size(); i++){
			basis.get(i).TransformToPlus(grading);
		}
		
		long critPairsConsidered =0;
		long zeroRed =0;
		int iter=0;
		
		while(added){
			iter++;
			added=false;
			int N=grobBasis.size();
			
			for (int i=0; i< N-1;i++){
				for (int j=Integer.max(j0, i+1); j< N; j++){
					//System.out.println("i="+i+"  j="+j);
					
					if(!BuchbCriterion(grobBasis.get(i), grobBasis.get(j))){
						if(!ChainCriterion(i,j,grobBasis)){
							VectorBinomial spair = new VectorBinomial(new ArrayList<Integer>(grobBasis.get(i).vec),grobBasis.get(i).firstValuableVariable);
							spair.Add(grobBasis.get(j),-1);
							spair.TransformToPlus(grading);
							if(!IterationCriterion(spair,V,iter)){
								
									//String spairstr="Spair:"+spair;
									//System.out.println("SpairSign:"+ spair.CompareBlockGLex(0, grading));
									//String basisstr="Basis"+grobBasis;
									spair.ReduceByList(grobBasis, grading, V, iter);
									if(spair.isZero != 1){
										grobBasis.add(spair);
										//System.out.println("Added: "+spair.toString());
										added=true;
									}else{
										/*System.out.println(spairstr+" REDUCED TO 0");
										System.out.println(basisstr);*/
										zeroRed++;
									}
									critPairsConsidered++;
								
							}
						}
					}
						
					
				}
			}
			j0=N;
			System.out.println("GBSIze:"+grobBasis.size());
			
		}
		outp.add(""+grobBasis.size());
		outp.add(""+critPairsConsidered);
		outp.add(""+zeroRed);
		
		System.out.println("SOLVED!");
		System.out.println("Critical pairs considered: "+critPairsConsidered);
		System.out.println("zero Reductions: "+zeroRed);
		System.out.println("GBSize: "+grobBasis.size());
		
		return grobBasis;
	}
	
	
	private static boolean inTermsDivisible(int k, int i, int j, ArrayList<VectorBinomial> grobBasis) {
		// TODO Auto-generated method stub
		for(int w=0;w<grobBasis.get(k).Size();w++){
			if(grobBasis.get(k).get(w)>0){
				int lcm=0;
				if(grobBasis.get(i).get(w)>0){
					if(grobBasis.get(j).get(w)>0){
						if(grobBasis.get(i).get(w)>grobBasis.get(j).get(w)){
							lcm=grobBasis.get(i).get(w);
						}else{
							lcm=grobBasis.get(j).get(w);
						}
					}else{
						lcm=grobBasis.get(i).get(w);
					}
				}else{
					if(grobBasis.get(j).get(w)>0){
						lcm=grobBasis.get(j).get(w);
					}else{
						lcm=0;
					}
				}
				if(grobBasis.get(k).get(w)>lcm){
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean ChainCriterion(int i, int j, ArrayList<VectorBinomial> grobBasis) {
		// TODO Auto-generated method stub
		for(int k=0;k<i;k++){
			if(inTermsDivisible(k,i,j,grobBasis)){
				return true;
			}
		}
		return false;
	}
	
	

	public static ArrayList<VectorBinomial> BuchbergerAlgorithm1(ArrayList<String> outp, ArrayList<VectorBinomial> basis, Vector grading, int V) throws Exception{
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>(basis);
		//with iteration criterion
		//for vertex cover specially: no need for transform+startj0 from first pair
		int j0=0;//startj0;
		boolean added = true;
		System.out.println(grading);
		/*for(int i=0; i< basis.size(); i++){
			basis.get(i).TransformToPlus(grading);
		}*/
		
		long critPairsConsidered =0;
		long zeroRed =0;
		int iter=0;
		
		while(added){
			iter++;
			added=false;
			int N=grobBasis.size();
			
			for (int i=0; i< N-1;i++){
				for (int j=Integer.max(j0, i+1); j< N; j++){
					//System.out.println("i="+i+"  j="+j);
					
					if(!BuchbCriterion(grobBasis.get(i), grobBasis.get(j))){
						if(!ChainCriterion(i,j,grobBasis)){
							if(!IterationCriterion(grobBasis.get(i), grobBasis.get(j),V,iter)){
								VectorBinomial spair = new VectorBinomial(new ArrayList<Integer>(grobBasis.get(i).vec),grobBasis.get(i).firstValuableVariable);
								spair.Add(grobBasis.get(j),-1);
								spair.TransformToPlus(grading);
								String spairstr=spair.toString();
								
								//System.out.println("Spair:"+spair);
								//System.out.println("SpairSign:"+ spair.CompareBlockGLex(0, grading));
								//System.out.println("Basis"+grobBasis);
								spair.ReduceByList(grobBasis, grading, V, iter);
								if(spair.isZero != 1){
									grobBasis.add(spair);
									//System.out.println("Added: "+spair.toString());
									added=true;
								}else{
									zeroRed++;
									System.out.println("REDUCED TO ZERO!! "+spairstr);
									System.out.println("BASIS:\n "+grobBasis);
								}
								critPairsConsidered++;
							}
						}
					}
						
					
				}
			}
			j0=N;
			System.out.println("GBSIze:"+grobBasis.size());
			
		}
		outp.add(""+grobBasis.size());
		outp.add(""+critPairsConsidered);
		outp.add(""+zeroRed);
		
		System.out.println("SOLVED!");
		System.out.println("Critical pairs considered: "+critPairsConsidered);
		System.out.println("zero Reductions: "+zeroRed);
		System.out.println("GBSize: "+grobBasis.size());
		
		return grobBasis;
	}
	
	
	

	

	public static ArrayList<VectorBinomial> BuchbergerAlgorithm(ArrayList<String> outp,ArrayList<VectorBinomial> basis, Vector grading) throws Exception{
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>(basis);
		
		int j0=0;
		boolean added = true;
		
		for(int i=0; i< basis.size(); i++){
			basis.get(i).TransformToPlus(grading);
		}
		
		long critPairsConsidered =0;
		long zeroRed =0;
		
		while(added){
			
			added=false;
			int N=grobBasis.size();
			
			for (int i=0; i< N-1;i++){
				for (int j=Integer.max(j0, i+1); j< N; j++){
					//System.out.println("i="+i+"  j="+j);
					
					if(!BuchbCriterion(grobBasis.get(i), grobBasis.get(j))){
						if(!ChainCriterion(i,j,grobBasis)){
							VectorBinomial spair = new VectorBinomial(new ArrayList<Integer>(grobBasis.get(i).vec),grobBasis.get(i).firstValuableVariable);
							spair.Add(grobBasis.get(j),-1);
							spair.TransformToPlus(grading);
							//System.out.println("Spair:"+spair);
							//System.out.println("SpairSign:"+ spair.CompareBlockGLex(0, grading));
							//System.out.println("Basis"+grobBasis);
							spair.ReduceByList(grobBasis, grading);
							if(spair.isZero != 1){
								grobBasis.add(spair);
								//System.out.println("Added: "+spair.toString());
								added=true;
							}else{
								zeroRed++;
							}
							critPairsConsidered++;
						}
					}
						
					
				}
			}
			j0=N;
			System.out.println("GBSIze:"+grobBasis.size());
			
		}
		outp.add(""+grobBasis.size());
		outp.add(""+critPairsConsidered);
		outp.add(""+zeroRed);
		
		System.out.println("SOLVED!");
		System.out.println("Critical pairs considered: "+critPairsConsidered);
		System.out.println("zero Reductions: "+zeroRed);
		System.out.println("GBSize: "+grobBasis.size());
		
		
		return grobBasis;
	}
	
	public static ArrayList<VectorBinomial> MinimizeBasis(ArrayList<VectorBinomial> basis) throws Exception{
		ArrayList<VectorBinomial> newBasis = new ArrayList<VectorBinomial>();
		for (int i=0;i<basis.size();i++){
			int j=0;
			for (j=0;j<basis.size();j++){
				if(i!=j){
					VectorBinomial aa = basis.get(j).Plus();
					aa.Add(basis.get(i).Plus(),-1);
					
					if(basis.get(j).Plus().CompareTotally(basis.get(i).Plus())<0){
						break;
					}
				}
			}
			if(j==basis.size()){
				newBasis.add(basis.get(i));
			}
		}
		
		return newBasis;
	}
	
	public static ArrayList<VectorBinomial> FilterBasis(ArrayList<VectorBinomial> basis){
		
		ArrayList<VectorBinomial> toRemain = new ArrayList<VectorBinomial>();
		for (int i=0; i< basis.size(); i++){
			int j;
			for (j=0;j<basis.get(i).firstValuableVariable; j++){
				if (basis.get(i).vec.get(j) !=0){
					break;
				}
			}
			if(j>=basis.get(i).firstValuableVariable){
				toRemain.add(basis.get(i));
			}
		}
		
		return toRemain;
	}
	
	public static ArrayList<Vector> Enumerate(Vector optimum, ArrayList<VectorBinomial> basis) throws Exception{
		ArrayList<Vector> feasSet=new ArrayList<Vector>();
		ArrayList<ArrayList<Integer>> adjacency = new ArrayList<ArrayList<Integer>>();
		ArrayList<Vector> edges= new ArrayList<Vector>();
		enumer(adjacency,edges, feasSet,optimum, basis,0);
		for(int i=0; i<adjacency.size();i++){
			System.out.println("TRAVEL");
			System.out.println("from");
			System.out.println(feasSet.get(adjacency.get(i).get(0)));
			System.out.println("through");
			System.out.println(edges.get(i));
			System.out.println("to");
			System.out.println(feasSet.get(adjacency.get(i).get(1)));
		}
		return feasSet;
		
	}
	
	public static ArrayList<Vector> Enumerate(Vector optimum, ArrayList<VectorBinomial> basis, int m, int n) throws Exception{
		//WRITE IN MATRIX FORM M x N
		ArrayList<Vector> feasSet=new ArrayList<Vector>();
		ArrayList<ArrayList<Integer>> adjacency = new ArrayList<ArrayList<Integer>>();
		ArrayList<Vector> edges= new ArrayList<Vector>();
		enumer(adjacency,edges, feasSet,optimum, basis,0);
		for(int i=0; i<adjacency.size();i++){
			System.out.println("TRAVEL");
			System.out.println("from");
			System.out.println(feasSet.get(adjacency.get(i).get(0)).asMatrix(m, n));
			System.out.println("through");
			System.out.println(edges.get(i).asMatrix(m, n));
			System.out.println("to");
			System.out.println(feasSet.get(adjacency.get(i).get(1)).asMatrix(m, n));
		}
		return feasSet;
		
	}
	
	private static void enumer(ArrayList<ArrayList<Integer>> adjacency, ArrayList<Vector> edges, ArrayList<Vector> res, Vector current, ArrayList<VectorBinomial> basis, int prevIndex ) throws Exception{
		if(res.size()==0){
			System.out.println("START");
		}else{
			//System.out.println("to");
			//System.out.println(current);
		}
		
		res.add(Vector.copyVector(current));
		int curIndex=res.size()-1;
		for(int i=0;i< basis.size(); i++){
			if(basis.get(i).CompareTotally(Vector.Mul(current, -1))==1){
				current.Add(basis.get(i));
				int j=0;
				for(j=0;j<res.size();j++){
					if(current.eq(res.get(j))){
						break;
					}
				}
				if(j<res.size()){
					current.Add(basis.get(i),-1);
					
					adjacency.add(new ArrayList<>());
					adjacency.get(adjacency.size()-1).add(curIndex);
					adjacency.get(adjacency.size()-1).add(j);
					
					edges.add(basis.get(i));
					
					continue;
				}
				/*System.out.println("TRAVELING");
				System.out.println("from");
				System.out.println(current);*/
				
				adjacency.add(new ArrayList<>());
				adjacency.get(adjacency.size()-1).add(curIndex);
				adjacency.get(adjacency.size()-1).add(res.size());
				
				edges.add(basis.get(i));
				
				enumer(adjacency,edges,res,current,basis,curIndex);
				current.Add(basis.get(i),-1);
			}
		}
		return;
		
	}
	
	private static int IncrementAsVCovSeq(ArrayList<Integer> seq){
		//ternary system 0 1 -1
		for(int i=seq.size()-1;i>=0;i--){	
			if(seq.get(i)==1){
				seq.set(i, -1);
				return 0;//ok
			}else{
				if(i>0){
					seq.set(i, 1);
				}
			}
			
		}
		return -1; //not incrementable
	}
	
	
	
	private static int UpdateKeys(ArrayList<Integer> keys) {
		for(int i=0;i<keys.size();i++){
			if(keys.get(i)>i){
				keys.set(i,keys.get(i)-1);
				return 0;
			}else{
				if(i<keys.size()-1){
					if(keys.get(i+1)-1>=i+1){
						keys.set(i,keys.get(i+1)-2);
						keys.set(i+1,keys.get(i+1)-1);
						return 0;
					}
				}
			}
		}

		return -1;// oh no
	}
	
	private static int UpdateKeysKnapsack(ArrayList<Integer> keys) {
		for(int i=0;i<keys.size();i++){
			if(keys.get(i)>i+1){
				keys.set(i,keys.get(i)-1);
				return 0;
			}else{
				if(i<keys.size()-1){
					if(keys.get(i+1)-1>i+1){
						keys.set(i,keys.get(i+1)-2);
						keys.set(i+1,keys.get(i+1)-1);
						return 0;
					}
				}
			}
		}

		return -1;// oh no
	}
	
	public static ArrayList<VectorBinomial> FindVCoverGB(ArrayList<ArrayList<Integer>> edges, Vector grading) throws Exception{
		VectorBinomial g = new VectorBinomial(new ArrayList<Integer>(), 0);
		ArrayList<VectorBinomial> basis = new ArrayList<VectorBinomial>();
		
		//init
		Vector extgrading = Vector.copyVector(grading);
		for(int i=0; i< grading.Size(); i++){
			g.vec.add(0);
			extgrading.set(i, grading.get(i)+1);
		}
		int k=1; //use k vertices for operations
		ArrayList<Integer> keys = new ArrayList<Integer>();//included vertices
		ArrayList<Integer> seq = new ArrayList<Integer>();//assigned numbers
		
		
		for(int i=0; i<edges.size();i++){
			extgrading.vec.add(0);
		}
		/*for(int i=0; i<grading.Size();i++){
			extgrading.vec.add(1);
		}*/
		
		while(k<=grading.Size()){
			System.out.println("Considering k="+k);
			int i=0;
			
			seq.add(1);
			keys.add(grading.Size()-1);
			for(int j=keys.size()-2;j>=0;j--){
				keys.set(j,keys.get(j+1)-1);
				seq.set(j, 1);
			}
			
			
			
			int incStatus = 0;
			
			while(incStatus==0){
				
				
				//System.out.println("INC status: "+incStatus);
				//System.out.println();
				while(true){
					//output seq
					
					for(int j=0;j<keys.size();j++){
						g.set(keys.get(j), seq.get(j));
					}
					//System.out.println("KEYS:" + keys);
					//System.out.println("seq:" + seq);
					//System.out.print("g:" + g);
					if(g.CompareBlockGLex(0, grading)>0){
						/*System.out.println("+");*/
						VectorBinomial g1 = AddSlacks(g,basis,grading.Size(),edges,extgrading,k-1);
						if(g1!=null){
							basis.add(g1);
							System.out.println(k+"BASIS SIZE:"+basis.size());
						}
					}/*else{
						System.out.println("-");
					}*/
					for(int j=0;j<keys.size();j++){
						g.set(keys.get(j), 0);
					}
					int upd=UpdateKeys(keys);
					//System.out.println("UPD status: "+upd);
					
					if(upd!=0){
						break;
					}
				}
				
				incStatus = IncrementAsVCovSeq(seq);
				if(incStatus==0){
					keys.set(keys.size()-1, grading.Size()-1);
					for(int j=keys.size()-2;j>=0;j--){
						keys.set(j,keys.get(j+1)-1);
					}
				}
				
			}
			k++;
		}
		
		return basis;
	}

	public static ArrayList<VectorBinomial> FindKnapsackGB(ArrayList<Integer> weights,Vector grading) throws Exception{
		VectorBinomial g = new VectorBinomial(new ArrayList<Integer>(), 0);
		ArrayList<VectorBinomial> basis = new ArrayList<VectorBinomial>();
		
		//init
		//Vector extgrading = Vector.copyVector(grading);
		for(int i=0; i< grading.Size(); i++){
			g.vec.add(0);
			//extgrading.set(i, grading.get(i)+1);
		}
		int k=1; //use k items for operations
		ArrayList<Integer> keys = new ArrayList<Integer>();//included vertices
		ArrayList<Integer> seq = new ArrayList<Integer>();//assigned numbers
		
		/*for(int i=0; i<grading.Size();i++){
			extgrading.vec.add(1);
		}*/
		
		while(k<=(grading.Size()-1)/2){
			System.out.println("Considering k="+k);
			int i=0;
			
			seq.add(1);
			keys.add((grading.Size()-1)/2);
			for(int j=keys.size()-2;j>=1;j--){
				keys.set(j,keys.get(j+1)-1);
				seq.set(j, 1);
			}
			
			
			
			int incStatus = 0;
			
			while(incStatus==0){
				
				
				//System.out.println("INC status: "+incStatus);
				//System.out.println();
				while(true){
					//output seq
					
					for(int j=0;j<keys.size();j++){
						g.set(keys.get(j), seq.get(j));
					}
					//System.out.println("KEYS:" + keys);
					//System.out.println("seq:" + seq);
					//System.out.print("g:" + g);
					if(g.CompareBlockGLex(0, grading)>0){
						/*System.out.println("+");*/
						VectorBinomial g1 = AddSlacksKnapsack(g,basis,(grading.Size()-1)/2,weights,grading);
						if(g1!=null){
							basis.add(g1);
							System.out.println(k+"BASIS SIZE:"+basis.size());
						}
					}/*else{
						System.out.println("-");
					}*/
					for(int j=0;j<keys.size();j++){
						g.set(keys.get(j), 0);
					}
					int upd=UpdateKeysKnapsack(keys);
					//System.out.println("UPD status: "+upd);
					
					if(upd!=0){
						break;
					}
				}
				
				incStatus = IncrementAsVCovSeq(seq);
				if(incStatus==0){
					keys.set(keys.size()-1, grading.Size()-1);
					for(int j=keys.size()-2;j>=0;j--){
						keys.set(j,keys.get(j+1)-1);
					}
				}
				
			}
			k++;
		}
		
		return basis;
	}
	
	private static VectorBinomial AddSlacks(VectorBinomial g,ArrayList<VectorBinomial> basis, int size,
			ArrayList<ArrayList<Integer>> edges, Vector grading, int iter) throws Exception {
		
		VectorBinomial g1 = g.copyVectorBinomial();
		
		for(int i=0;i<edges.size();i++){
			int res=0;
			if(g.get(edges.get(i).get(0))!=0){
				res+=g.get(edges.get(i).get(0));
			}
			if(g.get(edges.get(i).get(1))!=0){
				res+=g.get(edges.get(i).get(1));
			}
			if(res==1){
				
			}
			g1.vec.add(res);
		}
		/*for(int i=0;i<size;i++){
			g1.vec.add(-g1.get(i));
		}*/
		
		/*VectorBinomial g2=g1.Plus();
		for(int i=0;i<basis.size();i++){
			int buf=g2.CompareTotally(basis.get(i).Plus());
			if(buf*(1-buf)==0){
				return null;
			}
		}*/
		g1.ReduceByList(basis, grading,size,iter);
		if(g1.CompareTotally(0)==0){
			return null;
		}
		
		return g1;
	}
	
	private static VectorBinomial AddSlacksKnapsack(VectorBinomial g,ArrayList<VectorBinomial> basis, int items,
			ArrayList<Integer> weights, Vector grading) throws Exception {
		
		VectorBinomial g1 = g.copyVectorBinomial();
		int s0=0;
		
		for(int i=1;i<=items;i++){
			if(g.get(i)!=0){
				s0+=g.get(i)*weights.get(i);
			}
			g.vec.add(-g.get(i));
		}
		g1.set(0,s0);
		/*for(int i=0;i<size;i++){
			g1.vec.add(-g1.get(i));
		}*/
		
		/*VectorBinomial g2=g1.Plus();
		for(int i=0;i<basis.size();i++){
			int buf=g2.CompareTotally(basis.get(i).Plus());
			if(buf*(1-buf)==0){
				return null;
			}
		}*/
		g1.ReduceByList(basis, grading);
		if(g1.CompareTotally(0)==0){
			return null;
		}
		
		return g1;
	}

	public static ArrayList<VectorBinomial> MinimizeBinaryBasis(ArrayList<VectorBinomial> basis) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<VectorBinomial> newBasis = new ArrayList<VectorBinomial>();
		for (int i=0;i<basis.size();i++){
			int j=0;
			for (j=0;j<basis.size();j++){
				if(i!=j){
					
					if(basis.get(j).Plus().CompareTotally(basis.get(i).Plus())<0){
						break;
					}
				}
			}
			if(j==basis.size()){
				newBasis.add(basis.get(i));
			}
		}
		
		return newBasis;
		
	}
	

	/***********FAUGERE algorithms****************/
	
	public Vector mult = new Vector();
	public Vector signature = new Vector();
	ArrayList<Integer> signSupp = new ArrayList<Integer>();
	public int signatureId;
	
	public static ArrayList<VectorBinomial> BuchbergerIncrementalSubAlgorithm(ArrayList<VectorBinomial> basis, Vector grading) throws Exception{
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>(basis);
		
		int j0=basis.size()-1;
		boolean added = true;
		//System.out.println("strating with basis="+basis.size());
		while(added){
			
			added=false;
			int N=grobBasis.size();
			//System.out.println(N);
			//System.out.println("j0="+j0);
			for (int i=0; i< N-1;i++){
				for (int j=Integer.max(j0, i+1); j< N; j++){
					//System.out.println("i="+i+"  j="+j);
					
					if(!BuchbCriterion(grobBasis.get(i), grobBasis.get(j))){
						VectorBinomial spair = new VectorBinomial(new ArrayList<Integer>(grobBasis.get(i).vec),grobBasis.get(i).firstValuableVariable);
						spair.Add(grobBasis.get(j),-1);
						spair.TransformToPlus(grading);
						//System.out.println("Spair:"+spair);
						//System.out.println("SpairSign:"+ spair.CompareBlockGLex(0, grading));
						//System.out.println("Basis"+grobBasis);
						spair.ReduceByList(grobBasis, grading);
						if(spair.isZero != 1){
							grobBasis.add(spair);
							//System.out.println("Added: "+spair.toString());
							//System.out.println("wow"+grobBasis.size());
							added=true;
						}
						
					}
						
					
				}
			}
			j0=N;
			//System.out.println("Inner GBSize:"+grobBasis.size());
			
		}
		//outp.add(""+grobBasis.size());
		//outp.add(""+critPairsConsidered);
		//outp.add(""+zeroRed);
		
		
		
		
		return grobBasis;
	}
	
	public static ArrayList<VectorBinomial> BuchbergerIncrementalAlgorithm(ArrayList<String> outp,ArrayList<VectorBinomial> basis, Vector grading) throws Exception{
		int iteration=2;
		System.out.println("GBSize: "+basis.size());
		for(int i=0; i< basis.size(); i++){
			basis.get(i).TransformToPlus(grading);
		}
		
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>();
		grobBasis.add(basis.get(0));
		
		while(iteration<=basis.size()){//basis.size()
			//on each iteration add new element from initial basis
			//System.out.println("Current GBSize: "+grobBasis.size());
			grobBasis.add(basis.get(iteration-1));
			grobBasis=BuchbergerIncrementalSubAlgorithm(grobBasis, grading);
			iteration++;
		}
		
		System.out.println("SOLVED!");
		System.out.println("GBSize: "+grobBasis.size());
		//System.out.println(basis);
		
		
		return grobBasis;
	}
	
	
}
