package DB;

import models.Match;
import models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerInfoDAO {

    private Connection con;

    public PlayerInfoDAO(Connection con){
        this.con = con;
    }

    public int addGame(Match game) throws SQLException {
        String sql = "insert into games \n" +
                "(score, player_id) \n" +
                "values \n" +
                "(?, (select p.id \n" +
                "       from players p \n" +
                "       where p.first_name = ? and p.last_name = ?));";
//        PreparedStatement preparedStatement = con.prepareStatement(sql);
//
//        preparedStatement.setInt(1, game.getScore());
//        preparedStatement.setString(2, game.getPlayer().getFirstName());
//        preparedStatement.setString(3, game.getPlayer().getLastName());
//        int count = preparedStatement.executeUpdate();

        return 1;
    }

    public int addPlayer(Player player) throws SQLException {
        String sql = "insert into players (first_name, last_name) values (?, ?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1, player.getFirstName());
        preparedStatement.setString(2, player.getLastName());
        int count = preparedStatement.executeUpdate();

        return count;
    }
}
