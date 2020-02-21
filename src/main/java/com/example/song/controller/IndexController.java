package com.example.song.controller;

import com.example.song.mapper.UserMapper;
import com.example.song.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired(required = false)
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        Cookie[] cokkies=httpServletRequest.getCookies();
        for (Cookie cookie : cokkies) {
            if(cookie.getName().equals("token")){//Cookie里面有很多项，getName拿到名叫token的那一项
                String token=cookie.getValue();//保存那个名为token的值
                User user=userMapper.findByToken(token);
                if (user!=null){
                    httpServletRequest.getSession().setAttribute("user",user);
                }
                break;
            }

        }

        return "index";
    }
}
