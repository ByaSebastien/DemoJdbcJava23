package org.example.repositories;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.User;

import java.sql.*;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("SECURITY_USER", "USER_ID");
    }

    @Override
    protected User buildEntity(ResultSet rs) {

        try {
            return User.builder()
                    .id(rs.getInt("USER_ID"))
                    .username(rs.getString("USERNAME"))
                    .email(rs.getString("EMAIL"))
                    .password(rs.getString("PWD"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User add(User user) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO SECURITY_USER(USERNAME,EMAIL,PWD) " +
                                                               "VALUES(?,?,?) RETURNING *");
            psmt.setString(1, user.getUsername());
            psmt.setString(2, user.getEmail());
            psmt.setString(3, user.getPassword());

            ResultSet rs = psmt.executeQuery();

            if(!rs.next())
                throw new EntityNotFoundException();

            User result = buildEntity(rs);
            conn.close();

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Integer id, User user) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE SECURITY_USER " +
                                                               "SET USERNAME = ?," +
                                                                   "EMAIL = ?, " +
                                                                   "PASSWORD = ? " +
                                                               "WHERE USER_ID = ?");
            psmt.setString(1, user.getUsername());
            psmt.setString(2, user.getEmail());
            psmt.setString(3, user.getPassword());
            psmt.setInt(4,id);

            int nbRows = psmt.executeUpdate();

            conn.close();

            return nbRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("SELECT * FROM SECURITY_USER WHERE USERNAME = ? OR EMAIL = ?");
            psmt.setString(1,login);
            psmt.setString(2,login);

            ResultSet rs = psmt.executeQuery();

            if(!rs.next())
                throw new EntityNotFoundException();

            User user = buildEntity(rs);
            conn.close();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}














