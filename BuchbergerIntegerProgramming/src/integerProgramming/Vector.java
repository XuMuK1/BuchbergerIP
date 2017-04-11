package integerProgramming;

import java.util.ArrayList;

public class Vector {
	
	public ArrayList<Integer> vec;
	
	
	public Vector(ArrayList<Integer> Vec){
		this.vec=Vec;
	}
	public Vector(){//to satisfy Java inheritance rules
		
	}
	
	public static Vector copyVector(Vector vecc){
		Vector vec1=new Vector(new ArrayList<>(vecc.vec));
		return vec1;
	}
	
	public int Size(){
		return vec.size();
	}
	public String toString(){
		return "Vector of length "+ Size() + ": "+vec.toString();
	}
	public String asMatrix(int m,int n) throws Exception{
		if(m*n != this.vec.size()){
			throw new Exception("Vector dimensionality mismatch: "+m*n+" versus vecSize "+this.vec.size());
		}
		String res="";
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				res=res+" "+vec.get(i*n+j);
			}
			res=res+'\n';
		}
		return res;
	}
	public int get(int i) throws IndexOutOfBoundsException{
		return vec.get(i);
	}
	public int set(int i, int val) throws IndexOutOfBoundsException{
		return vec.set(i, val);
	}
	
	//****************************<Arithmetics>**************************//
	public static Vector Mul(Vector v, int numb) throws Exception{
		Vector res = new Vector(new ArrayList<Integer>());
		for(int i = 0;i<v.vec.size(); i++){
			res.vec.add(numb*v.vec.get(i));
		}
		return res;
	}
	
	public void Add(Vector oth) throws Exception{
		if(oth.Size() != vec.size()){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<vec.size(); i++){
			vec.set(i, vec.get(i)+oth.vec.get(i));
		}
	}
	
	//just set mul=-1 to subtract
	public void Add(Vector oth, int mul) throws Exception{
		if(oth.vec.size() != vec.size()){
			throw new Exception("Vector dimensionality mismatch");
		}
		
		for(int i = 0;i<vec.size(); i++){
			vec.set(i, vec.get(i)+mul*oth.vec.get(i));
		}
	}
	
	public boolean eq(Vector oth){
		for(int i=0;i<oth.vec.size();i++){
			if(oth.vec.get(i)!=this.vec.get(i)){
				return false;
			}
		}
		return true;
	}
	
	//****************************</Arithmetics>**************************//
	
	//****************************<BinomialReduction>**************************//
	
	public void FindNormalForm(ArrayList<VectorBinomial> lst) throws Exception{
		if(vec.size() <= lst.get(0).Size()){
			for(int i=0; i<lst.get(0).firstValuableVariable; i++){
				vec.add(0, 0);
			}
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
		
		for(int i=0; i<lst.get(0).firstValuableVariable; i++){
			vec.remove(0);
		}
		
	}
}
