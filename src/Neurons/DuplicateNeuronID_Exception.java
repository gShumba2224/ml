package Neurons;

public class DuplicateNeuronID_Exception extends Exception{
	
	private int duplicatedIDs;
	
	public DuplicateNeuronID_Exception(int id){
		duplicatedIDs = id;
	}

	public int getDuplicatedIDs() {
		return duplicatedIDs;
	}
	
}
