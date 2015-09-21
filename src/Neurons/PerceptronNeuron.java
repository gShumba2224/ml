package Neurons;

import Utils.DoubleDimension;

public class PerceptronNeuron extends Neuron{

	public PerceptronNeuron() {}
	public PerceptronNeuron(int ID) {this.setID(ID);}
	public PerceptronNeuron (int ID, double bias){
		this.setID(ID);
		this.setBias(bias);
	}
	

	@Override
	protected double thresholdFunction() {
		if (this.getSumValue() + this.getBias() > 0 ){return 1.0;}
		else {return 0;}
	}
	
}
