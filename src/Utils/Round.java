package Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Round {

	public static double round(double number, int places) {
	    BigDecimal decimal = new BigDecimal(number);
	    decimal = decimal.setScale(places, RoundingMode.HALF_UP);
	    return decimal.doubleValue();
	}
}
