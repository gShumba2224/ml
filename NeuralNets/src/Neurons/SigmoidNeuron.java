package Neurons;

import Utils.DoubleDimension;

public class SigmoidNeuron extends Neuron {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3331273803078816650L;

	public SigmoidNeuron() {}
	public SigmoidNeuron(int ID) {this.setID(ID);}
	public SigmoidNeuron (int ID, double bias){
		this.setID(ID);
		//this.setBias(bias);
	}
	
	@Override
	public double thresholdFunction() {
		
		double exponent = this.getSumValue()  * -1;
		return  1.0/ (1.0 + Math.pow(Math.E, exponent));
	}

}
