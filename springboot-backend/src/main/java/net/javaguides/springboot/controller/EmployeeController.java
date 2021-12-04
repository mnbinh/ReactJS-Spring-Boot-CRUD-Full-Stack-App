package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Comments;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.News;
import net.javaguides.springboot.model.UserInfo;
import net.javaguides.springboot.repository.CommentRepository;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.NewsRepository;
import net.javaguides.springboot.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
//		commentRepository.findAll();
//		commentRepository.save(new Comments());
		return employeeRepository.findAll();
	}		
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/user/{id}")
	public ResponseEntity<Map> getEmployeeById(@PathVariable Long id) {
		Map<String, Object> val = new HashMap<String,Object>();
		UserInfo user = userRepository.findById(id).get();
		val.put("user", user);
		List<News> news = newsRepository.findByUser_Id(user.getId());
		val.put("blogs", news);
		return ResponseEntity.ok(val);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<UserInfo> updateUser(@PathVariable Long id,
			@RequestParam(value = "firstName", required=false,  defaultValue = "") String firstName, 
            @RequestParam(value = "lastName", required=false, defaultValue = "") String lastName,
            @RequestParam(value = "mobile", required=false,  defaultValue = "") String mobile){
		UserInfo user = userRepository.findById(id).get();
		if(!StringUtils.isEmpty(firstName)) {
			user.setFirstName(firstName);
		}
		if(!StringUtils.isEmpty(lastName)) {
			user.setLastName(lastName);
		}
		if(!StringUtils.isEmpty(mobile)) {
			user.setMobile(mobile);
		}
		UserInfo userUpdate = userRepository.save(user);
		return ResponseEntity.ok(userUpdate);
	}
	
	// update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
