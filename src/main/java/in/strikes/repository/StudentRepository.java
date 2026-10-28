package in.strikes.repository;

import in.strikes.model.Student;
import in.strikes.model.StudentStats;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private static final String SELECT_ALL =
            "SELECT id, name, email, age FROM students ORDER BY id";

    private static final String SEARCH =
            "SELECT id, name, email, age FROM students "
                    + "WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ? ORDER BY id";

    private static final String SELECT_BY_ID =
            "SELECT id, name, email, age FROM students WHERE id = ?";

    private static final String INSERT =
            "INSERT INTO students (name, email, age) VALUES (?, ?, ?) RETURNING id";

    private static final String UPDATE =
            "UPDATE students SET name = ?, email = ?, age = ? WHERE id = ?";

    private static final String DELETE =
            "DELETE FROM students WHERE id = ?";

    private static final String STATS =
            "SELECT COUNT(*) AS total, COALESCE(AVG(age), 0) AS average_age FROM students";

    private static final RowMapper<Student> STUDENT_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt("age"));

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query(SELECT_ALL, STUDENT_MAPPER);
        }
        String pattern = "%" + keyword.trim().toLowerCase() + "%";
        return jdbcTemplate.query(SEARCH, STUDENT_MAPPER, pattern, pattern);
    }

    public Optional<Student> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, STUDENT_MAPPER, id).stream().findFirst();
    }

    /** Inserts a student and returns the generated id. */
    public Long save(Student student) {
        return jdbcTemplate.queryForObject(
                INSERT, Long.class, student.getName(), student.getEmail(), student.getAge());
    }

    /** @return number of rows updated (0 when the id does not exist) */
    public int update(Long id, Student student) {
        return jdbcTemplate.update(
                UPDATE, student.getName(), student.getEmail(), student.getAge(), id);
    }

    /** @return number of rows deleted (0 when the id does not exist) */
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE, id);
    }

    public StudentStats getStats() {
        return jdbcTemplate.queryForObject(STATS, (rs, rowNum) ->
                new StudentStats(rs.getLong("total"), rs.getDouble("average_age")));
    }
}
