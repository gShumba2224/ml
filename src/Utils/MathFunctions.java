package Utils;

public final class MathFunctions {

	private MathFunctions (){
	}
	
	public long sigmoid(double base, double exponent){
		return (long) (1.0/(1.0+ Math.pow(base, exponent * -1)));
	}
}
