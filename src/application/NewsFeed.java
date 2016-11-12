package application;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class NewsFeed extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// ----------SETUP
			BorderPane root = new BorderPane();
			VBox container = new VBox(10);
			container.getStyleClass().add("container");
			container.setMaxWidth(10);
			Scene scene = new Scene(container, 750, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			// ----------GET JSON OBJECT
			JSONObject objFull = getJSON(); // entire json
			// ----------PARSE JSON AND ADD ARTICLE TO VIEW
			JSONObject firstObj = firstDisplay(objFull);
			displayModule(firstObj, container);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	// ----------HELPER FUNCTION
	public JSONObject getJSON() {
		String s = "{" + "'status': 'ok'," + "'source': 'Entertainment-Weekly'," + "'sortBy': 'top'," + "'articles': ["
				+ "{" + "'author': 'Will Robinson',"
				+ "'title': 'Kate Bosworth To Play Manson Murder Victim Sharon Tate in Biopic',"
				+ "'description': 'Kate Bosworth is taking center stage to bring one of Hollywood’s more horrid tragedies to life. The Superman Returns actress is set to play late...',"
				+ "'url': 'http://www.ew.com/article/2016/10/29/kate-bosworth-sharon-tate-biopic',"
				+ "'urlToImage': 'http://www.ew.com/sites/default/files/styles/tout_image_612x380/public/i/2016/10/28/kate-bosworth-sharon-tate.jpg?itok=SixggyIF',"
				+ "'publishedAt': '2016-10-29T16:13:00Z'" + "}," + "{" + "'author': 'Nick Romano',"
				+ "'title': 'Listen to Anna Kendricks Sexy Haunted House Story on The Tonight Show',"
				+ "'description': 'Anna Kendricks haunted house experience was pretty untraditional. As she explained to Jimmy Fallon on The Tonight Show Friday night, it was filled...',"
				+ "'url': 'http://www.ew.com/article/2016/10/29/tonight-show-anna-kendrick-sexy-haunted-house-story',"
				+ "'urlToImage': 'http://www.ew.com/sites/default/files/styles/tout_image_612x380/public/i/2016/10/29/fallon-2.jpg?itok=gpYkb8GI',"
				+ "'publishedAt': '2016-10-29T16:06:00Z'" + "}," + "{" + "'author': 'Nick Romano',"
				+ "'title': 'Anna Kendrick and John Lithgow Share Kids Campfire Stories on The Tonight Show',"
				+ "'description': 'John Lithgow reading campfire horror stories written by elementary school kids is the kind of thing you do not know you need until you watch it. The...',"
				+ "'url': 'http://www.ew.com/article/2016/10/29/tonight-show-anna-kendrick-john-lithgow-kids-campfire-stories',"
				+ "'urlToImage': 'http://www.ew.com/sites/default/files/styles/tout_image_612x380/public/i/2016/10/29/fallon.jpg?itok=zodv0Olq',"
				+ "'publishedAt': '2016-10-29T15:46:00Z'" + "}," + "{" + "'author': 'Nick Romano',"
				+ "'title': 'See Jacob Tremblays Adorable Back to the Future Halloween Costume',"
				+ "'description': 'Jacob Tremblay is still too cool for school. The 10-year-old Room actor posted a photo of his Halloween costume that, apparently, only the adults...',"
				+ "'url': 'http://www.ew.com/article/2016/10/29/jacob-tremblay-back-future-costume-steals-halloween',"
				+ "'urlToImage': 'http://www.ew.com/sites/default/files/styles/tout_image_612x380/public/i/2016/10/29/jacob-tremblay.jpg?itok=CCLOL8zx',"
				+ "'publishedAt': '2016-10-29T14:54:00Z'" + "}," + "{" + "'author': 'Jodi Guglielmi',"
				+ "'title': 'Anna Kendrick says she wants to play Squirrel Girl',"
				+ "'description': 'The actress, 30, recently revealed that shed be interested in joining a superhero franchise, and in true Kendrick fashion, the heroine she wants to play is just as quircky...',"
				+ "'url': 'http://www.ew.com/article/2016/07/04/anna-kendrick-marvel-character-squirrel-girl#',"
				+ "'urlToImage': 'http://www.ew.com/sites/default/files/styles/tout_image_612x380/public/i/2016/07/04/anna-kendrick-612x380.jpg?itok=JD8oKnxM',"
				+ "'publishedAt': '2016-07-04T14:16:00Z'" + "}," + "{" + "'author': 'Will Robinson',"
				+ "'title': 'Anna Kendrick really wants to be Robin to Ben Affleck�s Batman',"
				+ "'description': 'The actress and Ben Affleck, her costar in the upcoming thriller The Accountant, were �interviewed� by MTV News ahead of the movie. While these pre-movie release junket interviews...',"
				+ "'url': 'http://www.ew.com/article/2016/10/12/anna-kendrick-robin-ben-affleck-batman?iid=sr-link5',"
				+ "'urlToImage': 'https://i.ytimg.com/vi/nwDHlkZV9iE/maxresdefault.jpg',"
				+ "'publishedAt': '2016-10-12T23:06:00Z'" + "}," + "]" + "}";
		JSONObject objFull = new JSONObject();
		try {
			objFull = new JSONObject(s);
			return objFull;
		} catch (JSONException e) {
			e.printStackTrace();
			return objFull;
		}
	}

	public JSONObject suggestJSON(JSONObject obj) throws JSONException {
		JSONArray jsonArray = obj.getJSONArray("articles");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String title = jsonObject.getString("title");
			if (!title.contains("Anna Kendrick")) {
				jsonArray.remove(i);
			}
		}
		return obj;
	}

	public void displayModule(JSONObject objPartial, VBox container) throws JSONException, MalformedURLException {

		JSONArray jsonArray = objPartial.getJSONArray("articles"); // articles
		Label NewsOutlet = new Label();
		NewsOutlet.setText(objPartial.getString("source"));
		NewsOutlet.getStyleClass().add("NewsOutlet");
		container.getChildren().add(NewsOutlet);
		for (int i = 0; i < jsonArray.length(); i++) {
			FlowPane flow = new FlowPane();
			VBox textBlock = new VBox(10);
			HBox articleContainer = new HBox(5);
			articleContainer.setPrefWidth(730);
			articleContainer.getStyleClass().add("articleBlock");
			flow.setVgap(10);
			flow.setMaxWidth(300);
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String title = jsonObject.getString("title");
			String desc = jsonObject.getString("description");
			String url = jsonObject.getString("url");
			Image img = new Image(jsonObject.getString("urlToImage"));
			URL urlLink = new URL(url);
			Hyperlink titleText = new Hyperlink();
			titleText.getStyleClass().add("Title");
			Label descText = new Label();
			ImageView imgView = new ImageView(img);
			imgView.setFitWidth(112);
			imgView.setFitHeight(75);
			Hyperlink urlText = new Hyperlink();
			final WebView browser = new WebView();
			WebEngine web = browser.getEngine();
			titleText.setOnAction(e -> {
				try {
					openWebpage(urlLink.toURI());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			titleText.setText(title);
			descText.setText(desc);
			urlText.setText("Click Here To Find Suggestions");

			urlText.setOnAction(e -> {
				web.load(url);
				try {
					JSONObject full = getJSON();
					JSONObject suggest = suggestJSON(full);
					container.getChildren().clear();
					displayModule(suggest, container);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			descText.setWrapText(true);
			descText.setMaxWidth(500);
			textBlock.getChildren().add(titleText);
			textBlock.getChildren().add(descText);
			textBlock.getChildren().add(urlText);
			articleContainer.getChildren().add(imgView);
			articleContainer.getChildren().add(textBlock);
			flow.getChildren().add(articleContainer);
			System.out.println(title);
			System.out.println(desc);
			System.out.println(url + "\n");
			container.getChildren().add(flow);
		}
	}

	private void openWebpage(URI url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public JSONObject firstDisplay(JSONObject obj) throws JSONException{
		JSONArray jsonArray = obj.getJSONArray("articles");
		int l = jsonArray.length();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String title = jsonObject.getString("title");
			if (title.contains("Batman") || title.contains("Squirrel Girl")) {
				jsonArray.remove(i);
				i--;
			}
		}
		return obj;
	}
}
