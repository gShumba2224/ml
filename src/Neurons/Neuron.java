package Neurons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Utils.DoubleDimension;

abstract class Neuron implements Serializable {
	
	private int ID;
	private List <InputConnection> inputConnections = new ArrayList <InputConnection>();
	private List <Connection> outputConnections =  new ArrayList <Connection>();
	private double sumValue = 0.0;
	private double outputValue = 0.0;
	private double bias;
	
	public void addInputConnection (Neuron neuron, double weight){
		inputConnections.add( new InputConnection (neuron,weight));}
	
	public void addInputConnection (Neuron neuron){addInputConnection (neuron,0.0);}
	
	public void addOutputConnection (Neuron neuron){outputConnections.add( new Connection (neuron) );}
	
	public void removeInputConnection (int index){inputConnections.remove(index);}
	
	public void removeOutputConnection (int index){outputConnections.remove(index);}
	
	private void sumInputs (){
		sumValue = 0;
		for (InputConnection connection : inputConnections){ sumInputs(connection);}}
	
	private void sumInputs (InputConnection connection){
		sumValue = sumValue + (connection.getNeuron().getOutputValue() * connection.getWeight());}
	
	public void update (){
		sumInputs ();
		outputValue = thresholdFunction ();
	}
	
	public void setOutputValue(double outputValue) {this.outputValue = outputValue;}
	
	public double  getOutputValue(){return this.outputValue;}
	
	public void setSumValue(double outputValue) {this.sumValue = sumValue;}
	
	public double getSumValue() {return sumValue;}
	
	public int getID() {return ID;}
	
	public void setID(int iD) {ID = iD;}
	
	public List<InputConnection> getInputConnections() {return inputConnections;}
	
	public List<Connection> getOutputConnections() {return outputConnections;}
	
	public double getBias() {return bias;}
	
	public void setBias(double bias) {this.bias = bias;}
	
	abstract double thresholdFunction ();

	
	
}
class Connection{
	private Neuron neuron;
	public Connection (Neuron neuron){
		this.setNeuron(neuron);
	}
	public Neuron getNeuron() {return neuron;}
	public void setNeuron(Neuron neuron) {this.neuron = neuron;}

}
class InputConnection extends Connection{
	
	private double weight;
	public InputConnection (Neuron neuron, double weight){
		super (neuron);
		this.setWeight(weight);
	}
	public void setWeight (double weight){this.weight  = weight;}
	public double getWeight (){return weight;}
	
}