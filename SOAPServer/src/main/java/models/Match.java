package models;

public class Match implements Comparable{

    private long id;

    private long gameId;

    private long score;

    private long playerId;

    private Game game;

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