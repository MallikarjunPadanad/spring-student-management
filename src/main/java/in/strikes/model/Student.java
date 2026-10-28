package in.strikes.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Student {

    private Long id;

    @NotBlank(message = "Enter the student's name.")
    @Size(max = 100, message = "Name can be at most 100 characters.")
    private String name;

    @NotBlank(message = "Enter an email address.")
    @Email(message = "Enter a valid email address, like name@example.com.")
    @Size(max = 150, message = "Email can be at most 150 characters.")
    private String email;

    @NotNull(message = "Enter the student's age.")
    @Min(value = 1, message = "Age must be at least 1.")
    @Max(value = 120, message = "Age must be 120 or less.")
    private Integer age;

    public Student() {
    }

    public Student(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
