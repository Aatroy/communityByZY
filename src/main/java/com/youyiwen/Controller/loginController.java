package com.youyiwen.Controller;

import com.youyiwen.Bean.User;
import com.youyiwen.Service.UserService;
import com.youyiwen.dto.AccessTokenDTO;
import com.youyiwen.dto.GithubUser;
import com.youyiwen.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.*;
import java.io.IOException;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/08
 */
@Controller
public class loginController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String loginPage(HttpSession session){
        //防止重复进入登录界面
        if (session.getAttribute("loginUser") == null) {
            return "login";
        }else {
            return "index";
        }
    }

    /**
     * 重定向防止表单重复提交
     *
     * @return
     */
    @PostMapping("/login")
    public String login(User user, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())) {
            String psw = userService.searchPasswordByName(user.getUserName());
            if(user.getPassword().equals(psw)) {
                String name = user.getUserName();
                // 存Cookie  重新打开浏览器直接获取账户信息
                Cookie cname = new Cookie("name",name);
                Cookie cpsw = new Cookie("password",psw);
                //不记住密码，30分钟有效
                if (request.getParameter("remember") == null) {
                    cname.setMaxAge(60 * 30);
                    cpsw.setMaxAge(60 * 30);
                }
                //记住密码，14天有效
                else{
                    cname.setMaxAge(60 * 60 * 24 * 14);
                    cpsw.setMaxAge(60 * 60 * 24 * 14);
                }
                response.addCookie(cname);
                response.addCookie(cpsw);

                request.getSession().setAttribute("loginUser", user);

                //客户跳转到前台
                if ("customer".equals(userService.searchRole(user))) {
                    return "redirect:/index";
                }
                //管理跳转到后台
                return "redirect:/main.html";
            }
        }
        model.addAttribute("msg", "账号或密码错误");
        return "login";
    }


    @GetMapping("/register")
    public String registerPage(){
        return "registration";
    }

    @PostMapping("/register")
    public String register(User user,Model model) {

        if (!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())) {
            if (userService.searchUserByName(user.getUserName()) == null) {
                userService.insertUser(user);
                return "login";
            }
        }
        model.addAttribute("msg", "用户名已存在，请重新输入");
        return "registration";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        request.getSession().removeAttribute("loginUser");
        return "index";
    }

//    使用Github登陆

    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state)  {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.abd885095074893f");
        accessTokenDTO.setClient_secret("24025f59df674b244bd7da623e2c8a955acb007b");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
