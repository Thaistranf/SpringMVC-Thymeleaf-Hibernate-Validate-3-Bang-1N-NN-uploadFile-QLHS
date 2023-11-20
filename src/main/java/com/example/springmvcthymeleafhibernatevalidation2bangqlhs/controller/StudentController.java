package com.example.springmvcthymeleafhibernatevalidation2bangqlhs.controller;

import com.example.springmvcthymeleafhibernatevalidation2bangqlhs.model.Classroom;
import com.example.springmvcthymeleafhibernatevalidation2bangqlhs.model.Student;
import com.example.springmvcthymeleafhibernatevalidation2bangqlhs.service.classroom.IClassroomService;
import com.example.springmvcthymeleafhibernatevalidation2bangqlhs.service.student.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private IClassroomService classroomService;

    @ModelAttribute("classList")    //Khai bao "classList" de lay ra ds classroom dung duoc o nhieu .html khac nhau trong views, ma ko can khoi tao nhieu lan o moi method create / update / delete ...
    public Iterable<Classroom> classrooms(){
        return classroomService.findAll();
    }

    @GetMapping
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("studentList", studentService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("newStudent", new Student());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(Student student){
        studentService.save(student);
        return ("redirect:/students");
    }

    @GetMapping("/update/{id}")
    public ModelAndView showFormUpdate(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/student/update");
        Student student = studentService.findById(id).get();
        modelAndView.addObject("studentUp", student);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, Student student){
        studentService.save(student);
        return ("redirect:/students");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        studentService.remove(id);
        return ("redirect:/students");
    }

    @GetMapping("/view/{id}")
    public String View(@PathVariable Long id, Model model){
        model.addAttribute("studentView", studentService.findById(id).get());
        return "/student/view";
    }

    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        List<Student> searchStudent = studentService.search(keyword);
        model.addAttribute("searchStudent", searchStudent);
        return "/student/list";
    }
}
