package gabriel.yuppiewall.gwt.client.widget;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.i18n.client.NumberFormat;
import java.math.BigDecimal;


public class CurrencyBoxRenderer extends AbstractRenderer<BigDecimal> {

    private static CurrencyBoxRenderer INSTANCE;

    /**
     * Returns the instance of the no-op renderer.
     */
    public static Renderer<BigDecimal> instance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyBoxRenderer();
        }
        return INSTANCE;
    }

    @Override
    public String render(BigDecimal object) {
        return object != null ? formatCurrencyStr(object) : "";
    }

    /**
     * Formats the number such as 1234.56 to $1,234.56
     * and 1234.56 to -$1,234.56 instead of ($1,234.56)
     * @param object
     * @return 
     */
    private String formatCurrencyStr(BigDecimal object) {
        String currencyString = NumberFormat.getCurrencyFormat()
                .format(object);
        if (currencyString != null && currencyString.startsWith("(") 
                && currencyString.endsWith(")")) {
            currencyString = currencyString.replace("(", "-");
            currencyString = currencyString.replace(")", "");
        }
        return currencyString;
    }
}