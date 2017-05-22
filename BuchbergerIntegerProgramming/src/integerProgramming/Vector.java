package integerProgramming;

import java.util.ArrayList;
import java.util.Arrays;

public class Vector {
	
	public int[] vec=new int[1];
	public int size=0;
	
	public Vector(int[] Vec){
		this.vec=Vec.clone();
		size=Vec.length;
	}
	public Vector(){//to satisfy Java inheritance rules
		
	}
	
	public static Vector copyVector(Vector vecc,int siz){
		Vector vec1=new Vector(new int[siz]);
		for(int i=0;i<siz;i++){
			vec1.vec[i]=vecc.vec[i];
		}
		return vec1;
	}
	
	public int Size(){
		return size;
	}
	public String toString(){
		return "Vector of length "+ Size() + ": "+Arrays.toString(vec);
	}
	public String asMatrix(int m,int n) throws Exception{
		if(m*n != size){
			throw new Exception("Vector dimensionality mismatch: "+m*n+" versus vecSize "+size);
		}
		String res="";
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				res=res+" "+vec[i*n+j];
			}
			res=res+'\n';
		}
		return res;
	}
	public int get(int i) throws IndexOutOfBoundsException{
		return vec[i];
	}
	public void set(int i, int val) throws IndexOutOfBoundsException{
		vec[i]= val;
	}
	
	//****************************<Arithmetics>**************************//
	public static Vector Mul(Vector v, int numb) throws Exception{
		Vector res = new Vector(new int[v.size]);
		for(int i = 0;i<v.size; i++){
			res.set(i,numb*v.get(i));
		}
		return res;
	}
	
	public void Add(Vector oth) throws Exception{
		if(oth.Size() != size){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<size; i++){
			this.set(i, vec[i]+oth.get(i));
		}
	}
	
	//just set mul=-1 to subtract
	public void Add(Vector oth, int mul) throws Exception{
		if(oth.size != size){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<size; i++){
			this.set(i, vec[i]+mul*oth.get(i));
		}
	}
	
	public boolean eq(Vector oth){
		for(int i=0;i<oth.size;i++){
			if(oth.get(i)!=vec[i]){
				return false;
			}
		}
		return true;
	}
	
	//****************************</Arithmetics>**************************//
	
	//****************************<BinomialReduction>**************************//
	
	public void FindNormalForm(ArrayList<VectorBinomial> lst) throws Exception{
		if(size <= lst.get(0).Size()){
			/*for(int i=0; i<lst.get(0).firstValuableVariable; i++){
				vec.add(0, 0);
			}*///DEPRECATED
		}
		boolean reduced=true;
		while(reduced){
			reduced=false;
			for(VectorBinomial g: lst){
				//System.out.println(g.Plus());
				//System.out.println(g.Plus().CompareTotally(this));
				if(g.Plus().CompareTotally(this)<=0){
					//System.out.println("AAAAA");
					this.Add((Vector)g,-1);
					reduced=true;
				}
			}
		}
		
		/*for(int i=0; i<lst.get(0).firstValuableVariable; i++){
			vec.remove(0);
		}*///DEPRECATED you do not need it
		
	}
}
