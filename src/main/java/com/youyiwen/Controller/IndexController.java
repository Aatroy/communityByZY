package com.youyiwen.Controller;

import com.youyiwen.Bean.User;
import com.youyiwen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/02
 */
@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/main.html")
    public String mainPage()
    {
        return "main";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @GetMapping(value = {"/index", "/"})
    public String indexPage(HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        if (cookies!=null){
            String name = null;
            String password = null;
            for (Cookie cookie : cookies){
                if ("name".equals(cookie.getName())) {
                    name = cookie.getValue();
                }
                if ("password".equals(cookie.getName())){
                    password = cookie.getValue();
                }
            }
            if (password != null && name !=null) {
                if (password.equals(userService.searchPasswordByName(name))){
                    User user = userService.searchUserByName(name);
                    request.getSession().setAttribute("loginUser",user);
                }
            }
        }
        return "index";
    }
    @GetMapping("/doctors")
    public String doctors(){
        return "doctors";
    }

    @GetMapping("/userCenter")
    public String userCenter(){
        return "userCenter";
    }

    @GetMapping("/communityCenter")
    public String community(){
        return "communityCenter";
    }

    @GetMapping("/first")
    public String firstGithubLogin(){
        return "firstGithubLogin";
    }
}
