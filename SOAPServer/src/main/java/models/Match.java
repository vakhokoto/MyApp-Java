package models;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "game_id")
    private long gameId;

    @Column(name = "score")
    private long score;

    @Column(name = "player_id")
    private long playerId;

    @OneToOne
    @JoinColumn(name = "game_id", insertable=false, updatable=false)
    private Game game;

    @OneToOne
    @JoinColumn(name = "player_id", insertable=false, updatable=false)
    private Player player;

    public Match(long id, long gameId, long score, long playerId) {
        this.id = id;
        this.gameId = gameId;
        this.score = score;
        this.playerId = playerId;
    }

    public Match(long id, long gameId, long score, long playerId, Game game, Player player) {
        this.id = id;
        this.gameId = gameId;
        this.score = score;
        this.playerId = playerId;
        this.game = game;
        this.player = player;
    }

    public Match() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public int compareTo(Object o) {
        Match m = (Match) o;
        if (getScore() < m.getScore()){
            return -1;
        } else if (getScore() > m.getScore()){
            return 1;
        }
        return 0;
    }
}