package forer.currency;

import javax.swing.JLabel;
import javax.swing.JTextField;
import retrofit2.*;

public class CurrencyController {
	private CurrencyLayerService service;

	public CurrencyController(CurrencyLayerService service) {
		this.service = service;
	}

	public void requestCurrencyFeed(JTextField currency, JTextField amount, JLabel result, JLabel rate) {
		service.getLiveCurrency().enqueue(new Callback<CurrencyFeed>() {

			@Override
			public void onFailure(Call<CurrencyFeed> call, Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onResponse(Call<CurrencyFeed> call, Response<CurrencyFeed> response) {
				CurrencyFeed feed = response.body();
				showAmountInCurrency(feed, currency, amount, result);
				showRateToUSD(feed, currency, rate);
			}

		});
	}

	public void showAmountInCurrency(CurrencyFeed feed, JTextField currency, JTextField amount, JLabel result) {
		double value;
		String trimmedAmount = amount.getText().trim();
		if (trimmedAmount.charAt(0) == '$') {
			value = Double.valueOf(trimmedAmount.substring(1));
		} else {
			value = Double.valueOf(trimmedAmount);
		}
		result.setText(String.valueOf(feed.amountInCurrency(currency.getText().trim(), value)));
	}

	public void showRateToUSD(CurrencyFeed feed, JTextField currency, JLabel rate) {
		rate.setText((String.valueOf(feed.rateToUSD(currency.getText().trim()))));
	}
}
