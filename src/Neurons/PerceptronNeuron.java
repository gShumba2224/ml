package Neurons;


public class PerceptronNeuron extends Neuron{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2038845571928393675L;

	public PerceptronNeuron() {}
	public PerceptronNeuron(int ID) {this.setID(ID);}
	public PerceptronNeuron (int ID, double bias){
		this.setID(ID);
	}

	@Override
	protected double thresholdFunction() {
		if (this.getSumValue()  > 0 ){return 1.0;}
		else {return 0;}
	}
	
}
