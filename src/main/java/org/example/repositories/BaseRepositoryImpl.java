package org.example.repositories;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepositoryImpl<TEntity> implements BaseRepository<TEntity>{


    private final String tableName;

    private final String columnIdName;

    public BaseRepositoryImpl(String tableName, String columnIdName) {
        this.tableName = tableName;
        this.columnIdName = columnIdName;
    }

    protected abstract TEntity buildEntity(ResultSet rs);

    @Override
    public TEntity getOne(Integer id) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/DemoJdbc", "postgres", "postgres");
            PreparedStatement psmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnIdName + " = ?");

            psmt.setInt(1,id);

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){
                throw new EntityNotFoundException();
            }
            conn.close();
            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TEntity> getMany() {
        try {

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/DemoJdbc", "postgres", "postgres");
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM " + tableName);

            List<TEntity> entities = new ArrayList<>();

            while (rs.next()) {

                entities.add(buildEntity(rs));
            }

            conn.close();
            return entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public abstract TEntity add(TEntity entity);

    @Override
    public abstract boolean update(Integer id, TEntity entity);

    @Override
    public boolean delete(Integer id) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/DemoJdbc", "postgres", "postgres");
            PreparedStatement psmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + columnIdName + " = ?");
            psmt.setInt(1,id);

            int nbRow = psmt.executeUpdate();
            conn.close();
            return  nbRow == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}