package com.example.song.controller;

import com.example.song.DTO.PaginationDTO;
import com.example.song.mapper.UserMapper;
import com.example.song.model.User;
import com.example.song.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        User user=null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {//Cookie里面有很多项，getName拿到名叫token的那一项
                    String token = cookie.getValue();//保存那个名为token的值
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user==null)
            return  "redirect:/";

        if ("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), pageNum, pageSize);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
