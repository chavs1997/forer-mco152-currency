package forer.currency;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyLayerService {

	@GET("/api/live?access_key=2de0bf823f79bd714fea65f23cd995e4&format=1")
	Call<CurrencyFeed> getLiveCurrency();
}
