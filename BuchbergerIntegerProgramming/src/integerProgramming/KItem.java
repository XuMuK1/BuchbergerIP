package integerProgramming;

public class KItem implements Comparable<KItem> {
	
	public int weight;
	public int val;
	public int id;
	
	public KItem(int Weight,int Val, int Id){
		this.weight = Weight;
		this.val=Val;
		this.id=Id;
	}

	@Override
	public int compareTo(KItem arg0) {
		// TODO Auto-generated method stub
		if(arg0.val*this.weight > this.val*arg0.weight){
			return 1;
		}else{
			if(arg0.val*this.weight < this.val*arg0.weight){
				return -1;
			}else{
				return 0;
			}
		}
		
	}

	
	
}