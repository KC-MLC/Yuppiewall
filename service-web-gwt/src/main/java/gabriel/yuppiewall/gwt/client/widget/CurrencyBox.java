package gabriel.yuppiewall.gwt.client.widget;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import java.math.BigDecimal;


public class CurrencyBox extends CurrencyBoxBase {

    public static CurrencyBox wrap(Element element) {
        // Assert that the element is attached.
        assert Document.get().getBody().isOrHasChild(element);

        CurrencyBox textBox = new CurrencyBox(element);

        // Mark it attached and remember it for cleanup.
        textBox.onAttach();
        RootPanel.detachOnWindowClose(textBox);

        return textBox;
    }

    /**
     * Creates an empty text box.
     */
    public CurrencyBox() {
        this(Document.get().createTextInputElement(), "gwt-TextBox");
        setAlignment(TextAlignment.RIGHT);
        addHaldler();
    }

    /**
     * This constructor may be used by subclasses to explicitly use an existing
     * element. This element must be an &lt;input&gt; element whose type is
     * 'text'.
     *
     * @param element the element to be used
     */
    protected CurrencyBox(Element element) {
        super(element);
        assert InputElement.as(element).getType().equalsIgnoreCase("text");
    }

    CurrencyBox(Element element, String styleName) {
        super(element);
        if (styleName != null) {
            setStyleName(styleName);
        }
    }

    /**
     * Gets the maximum allowable length of the text box.
     *
     * @return the maximum length, in characters
     */
    public int getMaxLength() {
        return getInputElement().getMaxLength();
    }

    /**
     * Gets the number of visible characters in the text box.
     *
     * @return the number of visible characters
     */
    public int getVisibleLength() {
        return getInputElement().getSize();
    }

    /**
     * Sets the maximum allowable length of the text box.
     *
     * @param length the maximum length, in characters
     */
    public void setMaxLength(int length) {
        getInputElement().setMaxLength(length);
    }

    /**
     * Sets the number of visible characters in the text box.
     *
     * @param length the number of visible characters
     */
    public void setVisibleLength(int length) {
        getInputElement().setSize(length);
    }

    private InputElement getInputElement() {
        return getElement().cast();
    }

    private void addHaldler() {
        sinkEvents(Event.ONPASTE);
        addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() != '$' && event.getCharCode() != '.' 
                        && !Character.isDigit(event.getCharCode())) {
                    cancelKey();
                }
                if (event.getCharCode() == '$') {
                    if (getValue() != null && getText().trim().length() > 0) {
                        cancelKey();
                    }
                }
                if (event.getCharCode() == '.') {
                    if (getValue() != null && getText().indexOf('.') != -1) {
                        cancelKey();
                    }
                }
                if (getValue() != null) {
                    if (getText().trim().length() == getMaxLength() - 3) {
                        if (!getText().contains(".") && event.getCharCode() != '.') {
                            setValue(new BigDecimal(getValue() + "."));
                        }
                    }

                }
                //getValue().trim().length() -3
            }
        });
        addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                setValue(getValue());
            }
        });

    }

    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONPASTE: {
                String clipBoardData = getClipBoardData();

                if (null != clipBoardData && !"".equalsIgnoreCase(clipBoardData)) {

                    Integer totalLength = getText().length() + clipBoardData.length();
                    if (totalLength > getMaxLength()) {
                        event.preventDefault();
                        return;
                    }
                    String totalString = getText().trim() + clipBoardData;
                    if (!totalString.matches(("^[\\$]?([\\d])*[\\.]?([\\d])*$"))) {
                        event.preventDefault();
                        return;
                    }
                }
                break;
            }
        }
    }

    private static native String getClipBoardData() /*-{ 
            var content = clipboardData.getData("Text"); return content;
    }-*/;
}