package forer.currency;

import javax.swing.JLabel;
import retrofit2.*;

public class CurrencyController {
	private CurrencyLayerService service;
	private CurrencyView view;

	public CurrencyController(CurrencyLayerService service, CurrencyView view) {
		this.service = service;
		this.view = view;
	}

	public void requestCurrencyFeed() {
		service.getLiveCurrency().enqueue(new Callback<CurrencyFeed>() {

			@Override
			public void onFailure(Call<CurrencyFeed> call, Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onResponse(Call<CurrencyFeed> call, Response<CurrencyFeed> response) {
				CurrencyFeed feed = response.body();
				String[] list = feed.getCurrencyList();
				for (String cur : list) {
					view.getCurrency().addItem(cur);
				}

			}

		});
	}

	public void fillInAmount(String currency, String amount, JLabel result, JLabel rate) {
		service.getLiveCurrency().enqueue(new Callback<CurrencyFeed>() {

			@Override
			public void onFailure(Call<CurrencyFeed> call, Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onResponse(Call<CurrencyFeed> call, Response<CurrencyFeed> response) {
				CurrencyFeed feed = response.body();
				String[] list = feed.getCurrencyList();
				for (String cur : list) {
					view.getCurrency().addItem(cur);
				}
				if (!amount.isEmpty()) {
					showAmountInCurrency(feed, currency, amount, result);
					showRateToUSD(feed, currency, rate);
				}
			}

		});
	}

	public void showAmountInCurrency(CurrencyFeed feed, String currency, String amount, JLabel result) {
		double value;

		if (amount.charAt(0) == '$' && amount.length() > 1) {
			value = Double.parseDouble(amount.substring(1));
		} else if (amount.charAt(0) != '$' && amount.length() >= 1) {
			value = Double.parseDouble(amount);
		} else {
			value = 0;
		}
		result.setText(String.format("%.2f", feed.amountInCurrency(currency, value)));
	}

	public void showRateToUSD(CurrencyFeed feed, String currency, JLabel rate) {
		rate.setText(String.format("%.2f", feed.rateToUSD(currency)));
	}
}
