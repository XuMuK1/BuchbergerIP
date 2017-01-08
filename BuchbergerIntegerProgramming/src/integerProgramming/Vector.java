package integerProgramming;

import java.util.ArrayList;

public class Vector {
	
	public ArrayList<Integer> vec;
	
	
	public Vector(ArrayList<Integer> Vec){
		this.vec=Vec;
	}
	public Vector(){//to satisfy Java inheritance rules
		
	}
	
	
	public int Size(){
		return vec.size();
	}
	public String toString(){
		return "Vector of length "+ Size() + ": "+vec.toString();
	}
	public int get(int i) throws IndexOutOfBoundsException{
		return vec.get(i);
	}
	public int set(int i, int val) throws IndexOutOfBoundsException{
		return vec.set(i, val);
	}
	
	//****************************<Arithmetics>**************************//
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
				if(g.Plus().CompareTotally(this)<=0){
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
