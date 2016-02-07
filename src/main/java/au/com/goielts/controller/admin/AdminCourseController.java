package au.com.goielts.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.JsonResponse;
import au.com.goielts.core.Select2JsonResult;
import au.com.goielts.model.Course;
import au.com.goielts.model.Student;
import au.com.goielts.model.Teacher;
import au.com.goielts.services.CourseService;
import au.com.goielts.services.StudentService;
import au.com.goielts.services.TeacherService;

@Controller
public class AdminCourseController {
	
	@Autowired private CourseService courseService;
	@Autowired private StudentService studentService;
	@Autowired private TeacherService teacherService;
	
	@RequestMapping(value = "/admin/course_list", method = RequestMethod.GET)
	public String courseList(ModelMap model){
		List<Course> courseList = courseService.findAll();
		System.out.println(courseList);
		model.addAttribute("courseList", courseList);
		return "/admin/course_list";
	}
	
	@RequestMapping(value = "/admin/course_edit/{id}", method = RequestMethod.GET)
	public String editCourse(@PathVariable int id, ModelMap model){
		Course course = courseService.findById(id);
		model.addAttribute("course", course);
		return "/admin/course_edit";
	}
	
	@RequestMapping(value = { "/admin/course_edit/{id}" }, method = RequestMethod.POST)
    public String editCourse(@Valid Course course,  Errors errors,
            ModelMap model, @PathVariable String id, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/admin/course_edit";
        }
		else{
			courseService.update(course);
			redirectAttrs.addFlashAttribute("success", "Course " + course.getName()  + " updated successfully");
			return "redirect:/admin/course_list";
		}
		
    }
	
	@RequestMapping(value = "/admin/course_create", method = RequestMethod.GET)
	public String createCourse(ModelMap model){
		Course course = new Course();
		model.addAttribute("course", course);
		return "/admin/course_create";
	}
	
	@RequestMapping(value = { "/admin/course_create" }, method = RequestMethod.POST)
    public String createCourse(@Valid Course course,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/admin/course_create";
        }
		else{
			courseService.save(course);
			redirectAttrs.addFlashAttribute("success", "Course " + course.getName()  + " updated successfully");
			return "redirect:/admin/course_list";
		}
		
    }
	
	@RequestMapping(value = "/admin/get_students", method = RequestMethod.GET)
	public @ResponseBody Select2JsonResult getStudents(@RequestParam String q){
		Select2JsonResult result = new Select2JsonResult();
		List<HashMap<String, Object>> list = new ArrayList<>();
		for(Student student : studentService.findAllByTerm(q)){
			HashMap<String, Object> sMap = new HashMap<>();
			sMap.put("id", student.getId());
			sMap.put("studentId", student.getStudentId());
			sMap.put("fullName", student.getFullName());
			sMap.put("email", student.getEmail());
			list.add(sMap);
		}
		result.items = list;
		return result;
	}
	
	@RequestMapping(value = "/admin/get_teachers", method = RequestMethod.GET)
	public @ResponseBody Select2JsonResult getTeachers(@RequestParam String q){
		Select2JsonResult result = new Select2JsonResult();
		List<HashMap<String, Object>> list = new ArrayList<>();
		for(Teacher teacher : teacherService.findAllByTerm(q)){
			HashMap<String, Object> sMap = new HashMap<>();
			sMap.put("id", teacher.getId());
			sMap.put("fullName", teacher.getFullName());
			sMap.put("email", teacher.getEmail());
			list.add(sMap);
		}
		result.items = list;
		return result;
	}
	
	@RequestMapping(value = "/admin/course/{id}/assign/students", method = RequestMethod.GET)
	public String assignStudentsToCourse(@PathVariable int id, ModelMap model){
		Course course = courseService.with("students").findById(id);
		model.addAttribute("course", course);
		return "/admin/course/assign_students";
	}
	
	@RequestMapping(value = "/admin/course/{id}/assign/students", method = RequestMethod.POST)
	public String assignStudentsToCoursePost(@PathVariable int id, ModelMap model, @RequestParam int studentId){
		Course course = courseService.with("students").findById(id);
		model.addAttribute("course", course);
		
		Student student = studentService.findById(studentId);
		if(student!=null){
			Set<Student> students = course.getStudents();
			students.add(student);
			course.setStudents(students);
			courseService.merge(course);
		}
		return "redirect:/admin/course/{id}/assign/students";
	}
	
	@RequestMapping(value = { "/admin/course/{courseId}/remove_student/{studentId}" }, method = RequestMethod.POST)
    public @ResponseBody JsonResponse removeStudentFromCourse(@PathVariable int courseId, @PathVariable int studentId) {
		
		Course course = courseService.findById(courseId);
		Student student = studentService.findById(studentId);
		Set<Student> students = course.getStudents();
		students.remove(student);
		course.setStudents(students);
		courseService.merge(course);
		return JsonResponse.factory(true);
	}
	
	@RequestMapping(value = "/admin/course/{id}/assign/teachers", method = RequestMethod.GET)
	public String assignTeachersToCourse(@PathVariable int id, ModelMap model){
		Course course = courseService.with("teachers").findById(id);
		model.addAttribute("course", course);
		return "/admin/course/assign_teachers";
	}
	
	@RequestMapping(value = "/admin/course/{id}/assign/teachers", method = RequestMethod.POST)
	public String assignTeachersToCoursePost(@PathVariable int id, ModelMap model, @RequestParam int teacherId){
		Course course = courseService.with("students").findById(id);
		model.addAttribute("course", course);
		
		Teacher teacher = teacherService.findById(teacherId);
		if(teacher!=null){
			Set<Teacher> teachers = course.getTeachers();
			teachers.add(teacher);
			course.setTeachers(teachers);
			courseService.merge(course);
		}
		return "redirect:/admin/course/{id}/assign/teachers";
	}
	
	@RequestMapping(value = { "/admin/course/{courseId}/remove_teacher/{teacherId}" }, method = RequestMethod.POST)
    public @ResponseBody JsonResponse removeTeacherFromCourse(@PathVariable int courseId, @PathVariable int teacherId) {
		
		Course course = courseService.findById(courseId);
		Teacher teacher = teacherService.findById(teacherId);
		Set<Teacher> teachers = course.getTeachers();
		teachers.remove(teacher);
		course.setTeachers(teachers);
		courseService.merge(course);
		return JsonResponse.factory(true);
	}
	
	@RequestMapping(value = { "/admin/course_delete/{id}" }, method = RequestMethod.POST)
    public @ResponseBody JsonResponse deleteCourse(@PathVariable int id) {
		if(courseService.deleteById(id)>0){
			return JsonResponse.factory(true);
		}else{
			return JsonResponse.factory(false);
		}
	}
	
}
