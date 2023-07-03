package org.frank.spring.security.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateUsersTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void saveUser(){
        String sql = "INSERT INTO `sys_user` (`id`, `username`, `password`, `mobile`) VALUES (?, ?,?,?);";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "2");
                preparedStatement.setString(2, "rose");
                preparedStatement.setString(3, passwordEncoder.encode("123456"));
                preparedStatement.setString(4, "13268050688");
                return preparedStatement;
            }
        });
    }

}
