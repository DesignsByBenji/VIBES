package application;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import java.*;

public class SuggestionEngine {

	private JFrame frame;
	private JTextField txtFindASuggestion;
	static List<Music> mDB = new ArrayList<Music>();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SuggestionEngine window = new SuggestionEngine();
					window.frame.setVisible(true);

					createMusicArray(mDB);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SuggestionEngine() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Suggestion");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		TextArea textArea = new TextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBounds(10, 10, 414, 211);
		frame.getContentPane().add(textArea);
		JButton btnSearch = new JButton("Find");
		btnSearch.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				String userSuggestion = txtFindASuggestion.getText();
				textArea.append("User Input: " + userSuggestion);
				textArea.append("\n");
				if (checkMusicDatabase(userSuggestion)) {
					Random randomGenerator = new Random();
					int index = randomGenerator.nextInt(mDB.size());
					textArea.append("\n");
					textArea.append("Since you enjoy " + userSuggestion + ", you might want to try "
							+ mDB.get(index).getSong() + " by " + mDB.get(index).getArtist() + " from the album "
							+ mDB.get(index).getAlbum());
				} else {
					textArea.append("Cannot Find Suggestions for: " + userSuggestion);
					textArea.append("\n");
				}
			}
		});
		btnSearch.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(btnSearch);

		txtFindASuggestion = new JTextField();
		txtFindASuggestion.setText("Find a Suggestion");
		txtFindASuggestion.setBounds(10, 227, 315, 20);
		frame.getContentPane().add(txtFindASuggestion);
		txtFindASuggestion.setColumns(10);
	}

	public static void createMusicArray(List<Music> musicDB) {

		musicDB.add(new Music("Avenged Sevenfold", "Avenged Sevenfold(2007)", "Afterlife", "Metal"));
		musicDB.add(new Music("Avenged Sevenfold", "Nightmare(2010)", "Nightmare", "Metal"));
		musicDB.add(new Music("Avenged Sevenfold", "Hail to the King(2013)", "Shephard of Fire", "Metal"));
		musicDB.add(new Music("Avenged Sevenfold", "City of Evil(2005)", "Bat Country", "Metal"));
		musicDB.add(new Music("Slipknot", "Slipknot(1999)", "Wait and Bleed", "Metal"));
		musicDB.add(new Music("Slipknot", "All Hope Is Gone(2008)", "Psychosocial", "Metal"));
		musicDB.add(new Music("Slipknot", ".5: The Gray Chapter(2014)", "The Devil in I", "Metal"));

	}

	public static boolean checkMusicDatabase(String input) {
		for (Music mC : mDB) {
			if (mC.getArtist().contains(input) || mC.getAlbum().contains(input) || mC.getSong().contains(input)
					|| mC.getGenre().contains(input)) {
				return true;
			}
		}
		return false;
	}

	public JSONObject suggestJSON(JSONObject obj, List<String> UAK) throws JSONException {
		JSONArray jsonArray = obj.getJSONArray("articles");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String title = jsonObject.getString("title");
			for (String x : UAK) {
				if (!title.contains(x)) {
					jsonArray.remove(i);
				}
			}
		}
		return obj;
	}
}
