package org.example.repositories;

import org.example.exception.EntityException;
import org.example.models.entities.Movie;

import java.sql.*;

public class MovieRepositoryImpl extends BaseRepositoryImpl<Movie> implements MovieRepository{

    public MovieRepositoryImpl() {
        super("MOVIE", "MOVIE_ID");
    }

    @Override
    protected Movie buildEntity(ResultSet rs) {

        try {
            return Movie.builder()
                    .id(rs.getInt("MOVIE_ID"))
                    .title(rs.getString("TITLE"))
                    .description(rs.getString("DESCRIPTION"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie add(Movie movie) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO MOVIE (TITLE,DESCRIPTION) VALUES (?,?) RETURNING *");
            psmt.setString(1, movie.getTitle());
            psmt.setString(2, movie.getDescription());
            ResultSet rs = psmt.executeQuery();
            if(!rs.next())
                throw new EntityException("Failed");

            conn.close();
            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Integer id, Movie movie) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE MOVIE SET TITLE = ?, DESCRIPTION = ? WHERE MOVIE_ID = ?");
            psmt.setString(1,movie.getTitle());
            psmt.setString(2, movie.getDescription());
            psmt.setInt(3,id);

            int nbRows = psmt.executeUpdate();

            conn.close();

            return nbRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
