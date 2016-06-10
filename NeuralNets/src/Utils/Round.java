package Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Round {

	public static double round(double number, int places) {
		//System.out.println("Number Error= " + number);
	    BigDecimal decimal = null;
	    try{
	    	decimal = new BigDecimal(number);
	    	decimal = decimal.setScale(places, RoundingMode.HALF_UP);
	    }catch (NumberFormatException e){
	    	decimal = new BigDecimal(0); }
	    return decimal.doubleValue();
	}
	
	public static double remapValues (double value, double oldMin, double oldMax, double newMin, double newMax){
		double result = (((value - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
		return result;
	}
}
