package org.example.repositories;

import org.example.exception.EntityException;
import org.example.models.entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author> implements AuthorRepository {

    public AuthorRepositoryImpl() {
        super("AUTHOR", "AUTHOR_ID");
    }

    @Override
    protected Author buildEntity(ResultSet rs) {
        try {
            return Author.builder()
                    .id(rs.getInt("AUTHOR_ID"))
                    .firstName(rs.getString("FIRSTNAME"))
                    .lastName(rs.getString("LASTNAME"))
                    .pseudo(rs.getString("PSEUDO"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author add(Author author,Connection conn){

        try{

            PreparedStatement psmt = conn.prepareStatement("INSERT INTO AUTHOR (FIRSTNAME,LASTNAME,PSEUDO) VALUES (?,?,?) RETURNING *");
            psmt.setString(1, author.getFirstName());
            psmt.setString(2, author.getLastName());
            psmt.setString(3, author.getPseudo());
            ResultSet rs = psmt.executeQuery();
            if(!rs.next())
                throw new EntityException("Failed");

            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author add(Author author) {

        return add(author,openConnection());
    }

    @Override
    public boolean update(Integer id, Author author) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE AUTHOR SET FIRSTNAME = ?, LASTNAME = ?, PSEUDO = ? WHERE AUTHOR_ID = ?");
            psmt.setString(1, author.getFirstName());
            psmt.setString(2, author.getLastName());
            psmt.setString(3, author.getPseudo());
            psmt.setInt(4,id);

            int nbRows = psmt.executeUpdate();

            conn.close();

            return nbRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> getByKeyword(String keyword) {


        try{
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("SELECT * FROM AUTHOR " +
                                                               "WHERE FIRSTNAME LIKE %?% OR " +
                                                                     "LASTNAME LIKE %?% OR " +
                                                                     "PSEUDO LIKE %?%");
            psmt.setString(1,keyword);
            psmt.setString(2,keyword);
            psmt.setString(3,keyword);

            ResultSet rs = psmt.executeQuery();

            List<Author> authors = new ArrayList<>();

            while(rs.next()){
                authors.add(buildEntity(rs));
            }

            conn.close();
            return authors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
















