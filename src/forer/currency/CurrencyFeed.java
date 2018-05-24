package forer.currency;

import java.util.*;

public class CurrencyFeed {

	private Map<String, Double> quotes;

	public final Map<String, Double> getQuotes() {
		return quotes;
	}

	public double rateToUSD(String currency) {
		return quotes.get("USD" + currency.trim());
	}

	public double amountInCurrency(String currency, double amount) {
		return amount * rateToUSD(currency);
	}

	public String[] getCurrencyList() {
		List<String> currencyList = new ArrayList<String>(quotes.keySet());
		String[] currencyArray = currencyList.stream().sorted().toArray(String[]::new);
		for (int i = 0; i < currencyArray.length; i++) {
			String sub = currencyArray[i].substring(3);
			currencyArray[i] = sub;
		}
		return currencyArray;
	}

}
