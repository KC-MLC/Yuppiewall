package gabriel.yuppiewall.gwt.client.widget;
import com.google.gwt.text.shared.Parser;
import java.math.BigDecimal;
import java.text.ParseException;

/**
 *
 * @author Ujjwal Halder
 */
public class CurrencyBoxParser implements Parser<BigDecimal> {

    private static CurrencyBoxParser INSTANCE;

    /**
     * Returns the instance of the no-op renderer.
     */
    public static Parser<BigDecimal> instance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyBoxParser();
        }
        return INSTANCE;
    }

    @Override
    public BigDecimal parse(CharSequence text) throws ParseException {
        try {
            return text == null ? null : 
                    new BigDecimal(parseFormattedInput(text));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 
     * @param text The text obtained from underlying text 
     * input. Such as $1,234.56
     * @return The String for numeric conversion 
     * such as $1,234.56 --> 1234.56
     */
    private String parseFormattedInput(CharSequence text) {
        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < text.length(); pos++) {
            Character c = text.charAt(pos);
            if (c == '-' || Character.isDigit(c) || c == '.') {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}