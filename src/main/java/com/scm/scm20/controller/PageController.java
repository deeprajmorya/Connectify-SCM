package com.scm.scm20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home Page Handler");
        model.addAttribute("name", "Deepraj Morya");
        model.addAttribute("occupation", "Software Engineer");
        model.addAttribute("githubLink", "https://github.com/deeprajmorya");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About handler");
        return "about";
    }

    @RequestMapping("/service")
    public String servicePage() {
        System.out.println("service handler");
        return "service";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        System.out.println("contact handler");
        return "contact";
    }
    @RequestMapping("/login")
    public String loginPage() {
        System.out.println("login handler");
        return "login";
    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        System.out.println("login handler");

        UserForm userForm = new UserForm();
        //userForm.setName("Deepraj");
        //userForm.setEmail("deep@gmail.com");
        //userForm.setAbout("hello mote");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        // fetch form data 
        // userForm
        System.out.println("Processing registration");
        System.out.println(userForm);

        // validate form data
        if(rBindingResult.hasErrors()){
            return "register";
        }

        // save data in the database

        // userService 

        // userForm --> user
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");
        user.setAbout(userForm.getAbout());

        User savedUser = userService.saveUser(user);
        System.out.println("user saved");

        // message = "successfull registration"
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message);
        // redirect to login page
        return "redirect:register";
    }
}
