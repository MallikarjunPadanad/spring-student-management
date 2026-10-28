package in.strikes.service;

import in.strikes.exception.DuplicateEmailException;
import in.strikes.exception.StudentNotFoundException;
import in.strikes.model.Student;
import in.strikes.model.StudentStats;
import in.strikes.repository.StudentRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> search(String keyword) {
        return studentRepository.findAll(keyword);
    }

    public StudentStats getStats() {
        return studentRepository.getStats();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional
    public Student create(Student student) {
        normalize(student);
        try {
            student.setId(studentRepository.save(student));
            return student;
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional
    public void update(Long id, Student student) {
        normalize(student);
        try {
            if (studentRepository.update(id, student) == 0) {
                throw new StudentNotFoundException(id);
            }
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional
    public void delete(Long id) {
        if (studentRepository.deleteById(id) == 0) {
            throw new StudentNotFoundException(id);
        }
    }

    private void normalize(Student student) {
        student.setName(student.getName().trim());
        student.setEmail(student.getEmail().trim().toLowerCase());
    }
}
