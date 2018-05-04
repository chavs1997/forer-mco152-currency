package forer.currency;

import java.util.Map;

public class CurrencyFeed {

	private boolean success;
	private String terms;
	private String privacy;
	private long timestamp;
	private String source;
	private Map<String, Double> quotes;
	
	public final boolean isSuccess() {
		return success;
	}
	public final String getTerms() {
		return terms;
	}
	public final String getPrivacy() {
		return privacy;
	}
	public final long getTimestamp() {
		return timestamp;
	}
	public final String getSource() {
		return source;
	}
	public final Map<String, Double> getQuotes() {
		return quotes;
	}
	
	public double rateToUSD(String currency) {
		return quotes.get(currency);
	}
	
	
}
