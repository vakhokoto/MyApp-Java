package models;

public class Player {

    private long id;

    private String nick;

    private String firstName;

    private String lastName;

    public Player(long id, String nick, String firstName, String lastName) {
        this.id = id;
        this.nick = nick;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Player() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}