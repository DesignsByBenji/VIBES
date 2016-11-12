package application;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MPModule extends Application
{
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	Media media;
	private Label time;
	private Slider progressBar;
	private Slider volumeSlider;
	private Button filesButton;
	Duration duration;
	String path;
	Scene scene;
	double width;
	double height;

	public void start(Stage primaryStage)
	{
		startModule(primaryStage);
	}

	public void startModule(Stage stage)
	{
		scene = setScene(this.width, this.height);

		filesButton.setOnAction((ActionEvent event) ->
		{
			restartModule(stage);
		});

		stage.setTitle("VIBES");
		stage.setScene(scene);
		stage.show();
	}

	public void restartModule(Stage stage)
	{
		mediaPlayer.dispose();
		startModule(stage);
	}

	private String getFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp3", "*.mp4", "*.mpeg", "*.wav", "*.aiff"));
		File file = fileChooser.showOpenDialog(null);

		if (file == null)
		{
			return this.path;
		}
		else
		{
			path = file.getAbsolutePath();
			path = this.path.replace("\\", "/");

			return path;
		}
	}

	public Scene setScene(double width, double height)
	{
		this.height = height;
		this.width = width;

		String fileLocation = getFile();

		if (fileLocation == null)
		{
			System.out.println("File Not Found");
			System.exit(0);
		}

		path = new File(fileLocation).getAbsolutePath();

		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(false);
		mediaView = new MediaView(mediaPlayer);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(mediaView);
		borderPane.setBottom(addMediaBar());

		if (path.contains(".mp3") || path.contains(".wav") || path.contains(".aiff"))
		{
			scene = new Scene(borderPane, 900, 50);
		}
		else
		{
			scene = new Scene(borderPane, 1280, 800);
		}

		return scene;
	}

	private HBox addMediaBar()
	{
		HBox mediaBar = new HBox();
		mediaBar.setPadding(new Insets(20));
		mediaBar.setAlignment(Pos.CENTER);
		mediaBar.alignmentProperty().isBound();
		mediaBar.setSpacing(5);

		Label volumeLabel = new Label("Vol:");

		volumeSlider = new Slider();
		volumeSlider.setValue(65);
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(30);

		filesButton = new Button("Files");
		Button rewindButton = new Button("Rewind");
		Button playButton = new Button("Play");
		Button pauseButton = new Button("Pause");
		Button reloadButton = new Button("Reload");
		Button forwardButton = new Button("Forward");

		progressBar = new Slider();
		HBox.setHgrow(progressBar, Priority.ALWAYS);
		progressBar.setMinWidth(50);
		progressBar.setMaxWidth(Region.USE_COMPUTED_SIZE);

		volumeSlider.valueProperty().addListener((Observable object) ->
		{
			if (volumeSlider.isValueChanging())
			{
				mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
			}
		});

		rewindButton.setOnAction((ActionEvent event) ->
		{
			Duration timeGap = new Duration(15000);

			mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(timeGap));
		});

		playButton.setOnAction((ActionEvent event) ->
		{
			mediaPlayer.play();
		});

		pauseButton.setOnAction((ActionEvent event) ->
		{
			mediaPlayer.pause();
		});

		reloadButton.setOnAction((ActionEvent event) ->
		{
			mediaPlayer.seek(mediaPlayer.getStartTime());
		});

		forwardButton.setOnAction((ActionEvent event) ->
		{
			Duration timeGap = new Duration(15000);

			mediaPlayer.seek(mediaPlayer.getCurrentTime().add(timeGap));
		});

		progressBar.valueProperty().addListener((Observable object) ->
		{
			if (progressBar.isValueChanging())
			{
				mediaPlayer.seek(duration.multiply(progressBar.getValue() / 100.0));
			}
		});

		time = new Label();
		time.setTextFill(Color.CADETBLUE);
		time.setPrefWidth(80);

		mediaPlayer.currentTimeProperty().addListener((Observable object) ->
		{
			updateValues();
		});


		mediaPlayer.setOnReady(() ->
		{
			duration = mediaPlayer.getMedia().getDuration();
			updateValues();
		});

		mediaBar.getChildren().addAll(filesButton, volumeLabel ,volumeSlider, rewindButton, playButton, pauseButton, reloadButton, forwardButton, progressBar, time);

		return mediaBar;
	}

	@SuppressWarnings("deprecation")
	protected void updateValues()
	{
		if (time != null && volumeSlider != null && progressBar != null)
		{
			Platform.runLater(() ->
			{
				Duration currentTime = mediaPlayer.getCurrentTime();
				time.setText(formatTime(currentTime, duration));

				progressBar.setDisable(duration.isUnknown());

				if (!progressBar.isDisabled() && duration.greaterThan(Duration.ZERO) && !progressBar.isValueChanging())
				{
					progressBar.setValue(currentTime.divide(duration).toMillis() * 100.0);
				}
			});
		}
	}

	private static String formatTime(Duration elapsed, Duration duration)
	{
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);

		if (elapsedHours > 0)
		{
			intElapsed -= elapsedHours * 60 * 60;
		}

		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO))
		{
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);

			if (durationHours > 0)
			{
				intDuration -= durationHours * 60 * 60;
			}

			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

			if (durationHours > 0)
			{
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);
			}
			else
			{
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
			}
		}
		else
		{
			if (elapsedHours > 0)
			{
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			}
			else
			{
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
