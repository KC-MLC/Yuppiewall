package gabriel.yuppiewall.gwt.client.widget;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ValueBoxBase;
import java.math.BigDecimal;


public class CurrencyBoxBase extends ValueBoxBase<BigDecimal> {

    protected CurrencyBoxBase(Element elem) {
        super(elem, CurrencyBoxRenderer.instance(), 
                CurrencyBoxParser.instance());
    }

    @Override
    public BigDecimal getValue() {
        BigDecimal raw = super.getValue();
        return raw ;
    }
}