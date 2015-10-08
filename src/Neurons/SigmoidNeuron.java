package Neurons;

import Utils.DoubleDimension;

public class SigmoidNeuron extends Neuron {
	
	public SigmoidNeuron() {}
	public SigmoidNeuron(int ID) {this.setID(ID);}
	public SigmoidNeuron (int ID, double bias){
		this.setID(ID);
		//this.setBias(bias);
	}
	
	@Override
	public double thresholdFunction() {
		
		double exponent = this.getSumValue()  * -1;
		
		//double out = 1.0/ (1.0 + Math.pow(Math.E, exponent));
		//System.out.println("out =" + out + " sum " + this.getSumValue() + " bias" + this.getBias() + " ID = " + this.getID());
		return  1.0/ (1.0 + Math.pow(Math.E, exponent));
	}

}
