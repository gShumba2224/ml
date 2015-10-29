package Neurons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GeneticAlgorithm.Gene;
import GeneticAlgorithm.Genome;

public class NeuralNetwork{
	
	private NeuralLayer inputLayer = new NeuralLayer(1);
	private NeuralLayer outputLayer =  new NeuralLayer(2);
	private List <NeuralLayer> hiddenLayers = new ArrayList <NeuralLayer> ();
	private Map<Integer,Neuron> allNeurons = new HashMap <Integer,Neuron> ();
	private NeuralNetworkReader inputReader = null;
	public String name = null;
	
	public NeuralNetwork (){
	}
	public NeuralNetwork (NeuralLayer in, List<NeuralLayer> hidden ,NeuralLayer out) throws DuplicateNeuronID_Exception{
		inputLayer = in;
		outputLayer = out;
		hiddenLayers = hidden;
		
		for (Neuron neuron : inputLayer.getNeurons()){mapNeuronToID(neuron);}
		for (NeuralLayer layer : hiddenLayers){
			for (Neuron neuron : layer.getNeurons()){mapNeuronToID(neuron);}
		}
		for (Neuron neuron : outputLayer.getNeurons()){mapNeuronToID(neuron);}
		connectAllLayers (null);
		update();
	}
	
	public NeuralNetwork (int inputLayerSize, int outputLayerSize, int numberOfhidden, int hiddenLayerSizes) {
		try{
			int count = 0;
			inputLayer = new NeuralLayer (0);
			for (int i = 0 ;  i < inputLayerSize ; i++){
				InputNeuron neuron = new InputNeuron(count);
				inputLayer.getNeurons().add(neuron);
				mapNeuronToID(neuron);
				count++;
			}
			
			
			hiddenLayers = new ArrayList<NeuralLayer> ();
			for (int i = 0 ;  i < numberOfhidden ; i++){
				NeuralLayer layer = new NeuralLayer (1+i);
				for (int j = 0 ; j < hiddenLayerSizes ; j++){
					SigmoidNeuron neuron = new SigmoidNeuron(count);
					layer.getNeurons().add(neuron);
					mapNeuronToID(neuron);
					count ++;
				}
				hiddenLayers.add(layer);
			}
			outputLayer = new NeuralLayer (numberOfhidden);
			for (int i = 0 ;  i < outputLayerSize ; i++){
				SigmoidNeuron neuron = new SigmoidNeuron(count);
				outputLayer.getNeurons().add(neuron);
				mapNeuronToID(neuron);
				count ++;
			}
			
			setBiasNeurons(count);
			if (hiddenLayers.size() == 0){connectLayers(inputLayer, outputLayer);}
			else{connectAllLayers (null);}
			updateAllNeuronsMap();
		}catch (DuplicateNeuronID_Exception e){ e.printStackTrace();}
	}
	
	public void connectAllLayers (Genome genome) throws DuplicateNeuronID_Exception{
		connectLayers(inputLayer, hiddenLayers.get(0) );
		connectLayers( hiddenLayers.get(hiddenLayers.size()-1),outputLayer );
		for (int i = 0; i < hiddenLayers.size()-1; i++){
			connectLayers(hiddenLayers.get(i), hiddenLayers.get(i+1));
		}
		if (genome != null){setWeights(genome);}
	}
	
	public void connectLayers (NeuralLayer connectFrom, NeuralLayer connectTo ){
		for (Neuron layer1Neuron : connectFrom.getNeurons()){
			for (Neuron layer2Neuron : connectTo.getNeurons()){
				layer2Neuron.addInputConnection(layer1Neuron,0.0);
			}
		}
	}
	
	public void update () throws DuplicateNeuronID_Exception {
		updateAllNeuronsMap ();
		for (Neuron neuron : inputLayer.getNeurons()){neuron.update();}
		for (NeuralLayer layer : hiddenLayers){
			for (Neuron neuron : layer.getNeurons()){neuron.update();}
		}
		for (Neuron neuron : outputLayer.getNeurons()){neuron.update();}
	}
	
	private void updateAllNeuronsMap () throws DuplicateNeuronID_Exception{
		allNeurons.clear();
		for (Neuron neuron : inputLayer.getNeurons()){
			mapNeuronToID(neuron);
		}
		for (NeuralLayer layer : hiddenLayers){
			for (Neuron neuron : layer.getNeurons()){
				mapNeuronToID(neuron);
			}
		}
		for (Neuron neuron : outputLayer.getNeurons()){
			mapNeuronToID(neuron);
		}
	}
	public void setWeights (Genome genome) throws DuplicateNeuronID_Exception{
		updateAllNeuronsMap();
		for (Gene gene: genome.getGenes()){
			Neuron neuron = allNeurons.get(gene.getNeuronID());
			neuron.getInputConnections().get(gene.getInputNumber()).setWeight(gene.getWeight());
		}
	}
	
	public List<Double> getOutputs (){
		
		 List<Double>  output = new  ArrayList<Double> ();
		for ( Neuron neuron :outputLayer.getNeurons()){
			output.add(neuron.getOutputValue());
		}
		return output;
	}
	
	private void mapNeuronToID (Neuron neuron) throws DuplicateNeuronID_Exception{
		
		if (allNeurons.containsKey(neuron.getID()) == true){
			throw new DuplicateNeuronID_Exception (neuron.getID());
		}else{
			allNeurons.put(neuron.getID(), neuron);
		}
	}
	
	private void setBiasNeurons (int startIndex) throws DuplicateNeuronID_Exception{
		InputNeuron biasNeuron;
		for (NeuralLayer layer : hiddenLayers){
			biasNeuron = new InputNeuron(startIndex);
			layer.setBiasNeuron(biasNeuron);
			mapNeuronToID(biasNeuron);
			startIndex++;
		}
		biasNeuron = new InputNeuron(startIndex);
		outputLayer.setBiasNeuron(biasNeuron);
		mapNeuronToID(biasNeuron);
	}
	
	public void setBiaInputs (double value){
		for (NeuralLayer layer : hiddenLayers){layer.getBiasNeuron().setOutputValue(value);}
		outputLayer.getBiasNeuron().setOutputValue(value);
	}
	public NeuralLayer getInputLayer() {return inputLayer;}
	
	public void setInputLayer(NeuralLayer inputLayer) {this.inputLayer = inputLayer;}
	
	public NeuralLayer getOutputLayer() {return outputLayer;}
	
	public void setOutputLayer(NeuralLayer outputLayer) {this.outputLayer = outputLayer;}
	
	public List<NeuralLayer> getHiddenLayers() {return hiddenLayers;}
	
	public void setHiddenLayers(List<NeuralLayer> hiddenLayers) {this.hiddenLayers = hiddenLayers;}
	
	public Map<Integer, Neuron> getAllNeurons() {return allNeurons;}
	
	public NeuralNetworkReader getInputReader() {return inputReader;}
	
	public void setInputReader(NeuralNetworkReader inputReader) {this.inputReader = inputReader;}
	
}
