package models;

public class Game {
    private long id;

    private String name;

    public Game(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
