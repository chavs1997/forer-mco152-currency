package forer.currency;

import java.awt.*;
import javax.swing.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyView extends JFrame {
	private JTextField currency = new JTextField();
	private JTextField amount = new JTextField("$");
	private JLabel rate = new JLabel();
	private JLabel result = new JLabel();
	private JLabel resultLabel = new JLabel();

	public CurrencyView() {
		setTitle("Dollar to Currency");
		setSize(500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);

		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.fill = GridBagConstraints.BOTH;
		constraint.insets = new Insets(3, 2, 3, 2);

		mainPanel.add(new JLabel("Please enter a 3-letter currency abbreviation:"), constraint);
		constraint.gridy = 1;
		mainPanel.add(new JLabel("Please enter a dollar amount to exchange:"), constraint);
		constraint.gridy = 2;
		mainPanel.add(new JLabel("Current rate to USD:"), constraint);
		constraint.gridy = 3;
		mainPanel.add(new JLabel("Currency amount exchanged:"), constraint);
		constraint.gridy = 0;
		constraint.gridx = 1;

		mainPanel.add(currency, constraint);
		constraint.gridy = 1;
		mainPanel.add(amount, constraint);
		constraint.gridy = 2;
		mainPanel.add(rate, constraint);
		constraint.gridy = 3;
		mainPanel.add(result, constraint);
		constraint.gridx = 2;
		mainPanel.add(resultLabel, constraint);

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apilayer.net")
				.addConverterFactory(GsonConverterFactory.create()).build();
		CurrencyLayerService service = retrofit.create(CurrencyLayerService.class);

		CurrencyController controller = new CurrencyController(service);

		JButton button = new JButton("Search");
		button.addActionListener(e -> {
			controller.requestCurrencyFeed(currency, amount, result, rate);
			resultLabel.setText(currency.getText());
		});

		constraint.gridx = 1;
		constraint.gridy = 4;
		mainPanel.add(button, constraint);

		add(mainPanel);

	}

	public static void main(String[] args) {
		new CurrencyView().setVisible(true);
	}

}
