package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validateEntity(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password,
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0) {
            removeRoles(user.id());
        } else return null;
        addRolesInBatch(user.getRoles(), user.id());
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, STRING_AGG(role, ', ') AS roles FROM users 
                LEFT JOIN user_roles ur ON id = ur.user_id WHERE id=? GROUP BY id""", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, STRING_AGG(role, ', ') AS roles FROM users 
                LEFT JOIN user_roles ur ON id = ur.user_id WHERE email=? GROUP BY id""", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("""
                SELECT id, name, email, password, registered, enabled, calories_per_day, STRING_AGG(role, ', ') AS roles FROM users 
                LEFT JOIN user_roles ur ON id = ur.user_id GROUP BY id ORDER BY MAX(name), MAX(email)""", ROW_MAPPER);
    }

    private void removeRoles(final int userId) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", userId);
    }

    private void addRolesInBatch(final Set<Role> roles, final int userId) {
        List<Role> rolesById = new ArrayList<>(roles);
        if (rolesById.size() > 0) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles(role, user_id) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, rolesById.get(i).name());
                            ps.setInt(2, userId);
                        }

                        @Override
                        public int getBatchSize() {
                            return rolesById.size();
                        }
                    });
        }
    }
}
