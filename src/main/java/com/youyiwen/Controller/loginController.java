package com.youyiwen.Controller;

import com.youyiwen.Bean.AccessToken;
import com.youyiwen.Bean.GithubUser;
import com.youyiwen.Bean.User;
import com.youyiwen.Service.UserService;
import com.youyiwen.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/08
 */
@Controller
public class loginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        //防止重复进入登录界面
        if (session.getAttribute("loginUser") == null) {
            return "login";
        } else {
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
            if (user.getPassword().equals(psw)) {
                String name = user.getUserName();
                // 存Cookie  重新打开浏览器直接获取账户信息
                Cookie cname = new Cookie("name", name);
                Cookie cpsw = new Cookie("password", psw);
                //不记住密码，30分钟有效
                if (request.getParameter("remember") == null) {
                    cname.setMaxAge(60 * 30);
                    cpsw.setMaxAge(60 * 30);
                }
                //记住密码，14天有效
                else {
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
    public String registerPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {

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
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
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
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpSession session, HttpServletResponse response) {

        AccessToken accessTokenDTO = new AccessToken();

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (githubUser != null) {
            //登录成功添加cookie和session
            response.addCookie(new Cookie("token", accessToken));

            session.setAttribute("loginUser", githubUser);
            return "index";
        }
            return "redirect:/";
    }
}