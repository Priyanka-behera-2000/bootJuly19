package com.techmentro.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.techmentro.entity.Emp;
import com.techmentro.repository.EmpRepository;

@RestController
@RequestMapping("/emps")
public class EmpController {

	@Autowired
	EmpRepository empRepository;
	
	@GetMapping("/{id}")
	public  Emp getEmp(@PathVariable int id) throws Exception
	{
		Optional<Emp> emp = empRepository.findById(id);

		if (!emp.isPresent())
			throw new Exception("Emp with id:" + id+" is not found.");

		return emp.get();
		
	}
	
	@GetMapping("/")
	public  Iterable<Emp> allEmp() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName()+" is requesting emp resource.");
		return empRepository.findAll();
	}
	@GetMapping("/job")
	public  Iterable<Emp> allEmpByJob(@PathVariable String job) {
		return empRepository.findByJobOrderByNameAsc(job);
	}
	@GetMapping("/namestartswith/{searchString}")
	public  Iterable<Emp> allEmpHavingName(@PathVariable String searchString) {
		return empRepository.findByNameStartingWith(searchString);
	}
	
	@PostMapping(value= {"/"},produces= {"text/plain"},consumes= {"application/json"})
	public  String create(@RequestBody Emp emp) {
		 empRepository.save(emp);
		 return "successfully saved.";
	}

	@PutMapping(value= {"/"},produces= {"text/plain"},consumes= {"application/json"})
	public  String updateS(@RequestBody Emp emp) {
		 empRepository.save(emp);
		 return "successfully updated.";
	}
	@DeleteMapping(value= {"/{id}"},produces= {"text/plain"})
	public  String delete(@PathVariable int id) {
		empRepository.deleteById(id);
		return "successfully saved.";
	}

}
