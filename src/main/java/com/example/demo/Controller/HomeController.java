package com.example.demo.Controller;

import com.example.demo.Entities.User;
import com.example.demo.Message;
import com.example.demo.Repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder ;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title","Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup/")
    public String SignUp(Model model){
        model.addAttribute("title","Register Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String do_register(@ModelAttribute(value = "user") User user, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){

        try{
            if(!agreement){
                System.out.println("User has not accepted the terms and conditions");
                throw new Exception("User has not accepted the terms and conditions");
            }else if(user.getName().length()<3 || user.getName().length()>20){
                throw new Exception("Username should have 3 to 20 characters");
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImage_url("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User result = this.userRepository.save(user);
            session.setAttribute("message",new Message("You have successfully registered","alert-success"));
            model.addAttribute("user",new User());

            System.out.println(agreement);
            System.out.println(user);

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message",new Message("Something Went Wrong" + e.getMessage(),"alert-danger"));
            return "signup";
        }
        return "signup";
    }

    // handler for custom login
    @GetMapping("/login")
    public String custom_login(Model model){
        model.addAttribute("title","login page");
        return "login";
    }
}
