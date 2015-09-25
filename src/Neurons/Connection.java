package Neurons;

public class Connection{
	private Neuron neuron;
	public Connection (Neuron neuron){
		this.setNeuron(neuron);
	}
	public Neuron getNeuron() {return neuron;}
	public void setNeuron(Neuron neuron) {this.neuron = neuron;}

}
