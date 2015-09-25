package Neurons;

public class InputConnection extends Connection{
	
	private double weight;
	public InputConnection (Neuron neuron, double weight){
		super (neuron);
		this.setWeight(weight);
	}
	public void setWeight (double weight){this.weight  = weight;}
	public double getWeight (){return weight;}
}
