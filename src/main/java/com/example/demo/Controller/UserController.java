package com.example.demo.Controller;

import com.example.demo.Entities.User;
import com.example.demo.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

        @Autowired
        private UserRepository userRepository;

        @ModelAttribute
        public void add_common_data(Model model,Principal principal){
            String username = principal.getName();
            User user = userRepository.getUserByUsername(username);
            model.addAttribute("user",user);
        }

        @RequestMapping("/index")
        public String dashboard(Model model, Principal principal){
            return "normal/user_dashboard";
        }

        @GetMapping("/add-contact")
        public String open_add_contact(Model model){
            model.addAttribute("title","add-contact");
            return "normal/add-contact-form";
        }
}
