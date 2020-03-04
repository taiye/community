package com.example.song.controller;

import com.example.song.DTO.PaginationDTO;
import com.example.song.mapper.UserMapper;
import com.example.song.model.User;
import com.example.song.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    QuestionService questionService;

    @RequestMapping("/")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                        HttpServletRequest httpServletRequest,
                        Model model) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {//Cookie里面有很多项，getName拿到名叫token的那一项
                    String token = cookie.getValue();//保存那个名为token的值
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.getAllList(pageNum, pageSize);
        model.addAttribute("pagination", pagination);
        return "index";
    }


}
