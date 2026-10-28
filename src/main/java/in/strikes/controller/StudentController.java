package in.strikes.controller;

import in.strikes.exception.DuplicateEmailException;
import in.strikes.model.Student;
import in.strikes.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String FORM_VIEW = "students/form";

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        model.addAttribute("students", studentService.search(q));
        model.addAttribute("stats", studentService.getStats());
        model.addAttribute("q", q);
        return "students/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        return FORM_VIEW;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("student") Student student,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            studentService.create(student);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("success", "Student added.");
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return FORM_VIEW;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("student") Student student,
                         BindingResult result,
                         RedirectAttributes redirect) {
        student.setId(id);
        if (result.hasErrors()) {
            return FORM_VIEW;
        }
        try {
            studentService.update(id, student);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            return FORM_VIEW;
        }
        redirect.addFlashAttribute("success", "Changes saved.");
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        studentService.delete(id);
        redirect.addFlashAttribute("success", "Student deleted.");
        return "redirect:/students";
    }
}
