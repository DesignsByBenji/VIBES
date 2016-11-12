package application;

public class Movie {

	String film;
	String actor;
	String director;
	String producer;
	String genre;
	String production;

	public Movie(String film, String actor, String director, String producer, String genre, String production) {
		this.film = film;
		this.actor = actor;
		this.director = director;
		this.producer = producer;
		this.genre = genre;
		this.production = production;
	}

	public String getFilm() {
		return film;
	}

	public void setFilm(String film) {
		this.film = film;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}
}
