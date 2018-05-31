package forer.currency;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyView extends JFrame {

	Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apilayer.net")
			.addConverterFactory(GsonConverterFactory.create()).build();
	CurrencyLayerService service = retrofit.create(CurrencyLayerService.class);

	CurrencyController controller = new CurrencyController(service, this);

	private JComboBox<String> currency = new JComboBox<String>();
	private JTextField amount = new JTextField("$");
	private JLabel rate = new JLabel();
	private JLabel result = new JLabel();
	private JLabel resultLabel = new JLabel();

	public final JComboBox<String> getCurrency() {
		return currency;
	}

	public CurrencyView() {
		setTitle("Dollar to Currency");
		setSize(500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		controller.requestCurrencyFeed();

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);

		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.fill = GridBagConstraints.BOTH;
		constraint.insets = new Insets(3, 2, 3, 2);

		mainPanel.add(new JLabel("Please select a currency:"), constraint);
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

		DocumentListener listen = new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				change(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				change(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				change(e);
			}

			public void change(DocumentEvent e) {
				if (!amount.getText().isEmpty()) {
					controller.fillInAmount(currency.getSelectedItem().toString(), amount.getText().trim(), result,
							rate);
					resultLabel.setText(currency.getSelectedItem().toString().trim());
				}

			}
		};

		currency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!amount.getText().isEmpty()) {
					controller.fillInAmount(currency.getSelectedItem().toString(), amount.getText().trim(), result,
							rate);
					resultLabel.setText(currency.getSelectedItem().toString().trim());
				}
			}
		});

		amount.getDocument().addDocumentListener(listen);
		add(mainPanel);

	}

	public static void main(String[] args) {
		new CurrencyView().setVisible(true);
	}

}
