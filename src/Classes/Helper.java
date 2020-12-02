package Classes;

import java.math.BigDecimal;

public class Helper {

    public static Float twoDecimalsFloat(Float number) {
        BigDecimal bigDecimal = new BigDecimal(number.toString());
        BigDecimal roundOff = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return roundOff.floatValue();
    }
}
