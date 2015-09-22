package Neurons;

import java.util.ArrayList;
import java.util.List;

public class NeuralLayer  {
	
	private List <Neuron> neurons = new ArrayList <Neuron>();
	private int ID;
	
	public NeuralLayer (){}
	
	public NeuralLayer (int ID){}
	
	public NeuralLayer (int ID, List <Neuron> neurons){}
	
	public List<Neuron> getNeurons() {return neurons;}
	
	public void setNeurons(List<Neuron> neurons) {this.neurons = neurons;}
	
	public int getID() {return ID;}
	
	public void setID(int iD) {ID = iD;}
}
