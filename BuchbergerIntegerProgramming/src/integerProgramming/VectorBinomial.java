package integerProgramming;

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
					if(this.CompareTotally(0)==0){
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
	
	public static ArrayList<VectorBinomial> BuchbergerAlgorithm(ArrayList<VectorBinomial> basis, Vector grading) throws Exception{
		ArrayList<VectorBinomial> grobBasis = new ArrayList<VectorBinomial>(basis);
		
		int j0=0;
		boolean added = true;
		
		for(int i=0; i< basis.size(); i++){
			basis.get(i).TransformToPlus(grading);
		}
		
		while(added){
			
			added=false;
			int N=grobBasis.size();
			
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
						if(spair.CompareTotally(0) != 0){
							grobBasis.add(spair);
							//System.out.println("Added: "+spair.toString());
							added=true;
						}
					}
						
					
				}
			}
			j0=N+1;
			System.out.println("GBSIze:"+grobBasis.size());
			
		}
		
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
	
	
}
