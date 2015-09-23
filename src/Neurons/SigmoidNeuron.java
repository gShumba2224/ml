package Neurons;

import Utils.DoubleDimension;

public class SigmoidNeuron extends Neuron {
	
	public static final double BASE =  2.7183;
	
	public SigmoidNeuron() {}
	public SigmoidNeuron(int ID) {this.setID(ID);}
	public SigmoidNeuron (int ID, double bias){
		this.setID(ID);
		this.setBias(bias);
	}
	
	@Override
	public double thresholdFunction() {
		double exponent = (this.getSumValue() - this.getBias())  * -1;
		return  1.0/ (1.0 + Math.pow(BASE, exponent));
	}

}