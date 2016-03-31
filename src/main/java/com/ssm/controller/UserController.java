package com.ssm.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssm.model.User;
import com.ssm.service.UserService;

@Controller  
@RequestMapping("/user") 
public class UserController {

	 @Resource  
	 private UserService userService;  
	 
	 /**
	  * 创建用户
	  * @param user
	  */
	 @RequestMapping(method=RequestMethod.POST)  
	 @ResponseStatus(HttpStatus.CREATED)
	 public void createUser(@Valid User user){
		 userService.createUser(user);
	 }
	 
	 /**
	  * 更新用户
	  * @param id
	  * @param user
	  */
	 @RequestMapping(method=RequestMethod.PUT) 
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public void updateUser(@PathVariable("id") int id,@Valid User user){
		 userService.updateUser(user);
	 }
	 
	 /**
	  * 查找用户
	  * @param id
	  * @param model
	  * @return
	  */
     @RequestMapping(value="/{id}",method=RequestMethod.GET)  
     public String getUserById(@PathVariable("id") int id,Model model){  
        User user = this.userService.getUserById(id);  
        model.addAttribute("user", user);  
        return "showUser";  
     }
     
     /**
      * 删除用户
      * @param id
      */
     @RequestMapping(method=RequestMethod.DELETE) 
	 @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteUser(@PathVariable("id") int id){
    	 userService.deleteUser(id);
     }
     
}
