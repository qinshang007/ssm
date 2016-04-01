package com.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller  
@RequestMapping("/test") 
public class TestController {
	
    @RequestMapping(method=RequestMethod.GET)  
    public String getUserById(Model model){  
       String name = "林炀平";
       model.addAttribute("name", name);  
       return "test";  
    }

	
}
