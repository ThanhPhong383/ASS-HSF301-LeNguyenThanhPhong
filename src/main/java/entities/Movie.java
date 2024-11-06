package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @Column(name = "movieID")
    private int movieID;

    @Column(name = "movieName", nullable = false)
    private String movieName;

    @Column(name = "duration")
    private int duration;

    @Column(name = "actor")
    private String actor;

    @Column(name = "status")
    private String status;

    @Column(name = "directorID")
    private int directorID;

    // Default constructor
    public Movie() {}

    // Constructor with parameters
    public Movie(int movieID, String movieName, int duration, String actor, String status, int directorID) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.duration = duration;
        this.actor = actor;
        this.status = status;
        this.directorID = directorID;
    }

    // Getters and setters
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDirectorID() {
        return directorID;
    }

    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }
}
