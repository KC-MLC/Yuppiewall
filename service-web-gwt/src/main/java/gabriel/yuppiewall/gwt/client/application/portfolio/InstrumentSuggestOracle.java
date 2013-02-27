package gabriel.yuppiewall.gwt.client.application.portfolio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

public class InstrumentSuggestOracle extends MultiWordSuggestOracle {

	private List<InstrumentMultiWordSuggestion> instrumentSuggestions = null;

	@Override
	public void requestSuggestions(Request suggestRequest, Callback callback) {
		// SystemDataServiceAsync.Util.getInstance().getAllInstrument(query,
		// "INR", callback);
		Response resp = new Response(matchingInstrument(
				suggestRequest.getQuery(), suggestRequest.getLimit()));

		callback.onSuggestionsReady(suggestRequest, resp);
	}

	private Collection<InstrumentMultiWordSuggestion> matchingInstrument(
			String query, int limit) {
		List<InstrumentMultiWordSuggestion> matchingInstrument = new ArrayList<InstrumentMultiWordSuggestion>(
				limit);

		// only begin to search after the user has type two characters
		if (query.length() >= 2) {
			String prefixToMatch = query.toLowerCase();

			int i = 0;
			int s = instrumentSuggestions.size();

			// Skip forward over all the names that don't match at the beginning
			// of the array.
			while (i < s
					&& !instrumentSuggestions.get(i).getDisplayString()
							.toLowerCase().startsWith(prefixToMatch)) {
				i++;
			}

			// Now we are at the start of the block of matching names. Add
			// matching names till we
			// run out of names, stop finding matches, or have enough matches.
			int count = 0;

			while (i < s
					&& instrumentSuggestions.get(i).getDisplayString()
							.toLowerCase().startsWith(prefixToMatch)
					&& count < limit) {
				matchingInstrument.add(instrumentSuggestions.get(i));
				i++;
				count++;
			}

		}

		return matchingInstrument;
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(InstrumentMultiWordSuggestion o) {
		if (instrumentSuggestions == null) {
			instrumentSuggestions = new ArrayList<InstrumentMultiWordSuggestion>();
		}

		return instrumentSuggestions.add(o);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (instrumentSuggestions != null) {
			return instrumentSuggestions.remove(o);
		}

		return false;
	}
}