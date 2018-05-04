package forer.currency;

import java.io.IOException;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyRetrofitClient {

	public static void main(String[] args) throws IOException {

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apilayer.net")
				.addConverterFactory(GsonConverterFactory.create()).build();
		CurrencyLayerService service = retrofit.create(CurrencyLayerService.class);

		Call<CurrencyFeed> call = service.getLiveCurrency();
		call.enqueue(new Callback<CurrencyFeed>() {

			@Override
			public void onFailure(Call<CurrencyFeed> call, Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onResponse(Call<CurrencyFeed> call, Response<CurrencyFeed> response) {
				CurrencyFeed feed = response.body();

				System.out.println(feed.getQuotes().toString());
				System.exit(0);
			}

		});

	}

}
