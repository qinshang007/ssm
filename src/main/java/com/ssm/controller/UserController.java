package com.ssm.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssm.model.User;
import com.ssm.service.UserService;

@Controller  
@RequestMapping("/user") 
public class UserController {

	 @Resource  
	 private UserService userService;  
	      
     @RequestMapping(value="/{id}",method=RequestMethod.GET)  
     public String toIndex(@PathVariable("id") int id,Model model){  
        User user = this.userService.getUserById(id);  
        model.addAttribute("user", user);  
        return "showUser";  
     }
     
}
