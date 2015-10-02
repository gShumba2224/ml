package Neurons;

public class InputNeuron extends Neuron {
	
	public InputNeuron() {}
	public InputNeuron(int ID) {this.setID(ID);}
	public InputNeuron (int ID, double inputValue){
		this.setID(ID);
		this.setOutputValue(inputValue);
	}

	@Override
	public double thresholdFunction() {
		return this.getOutputValue();
	}
	
	public void setInputValue (double value){
		this.setOutputValue(value);
	}

}
