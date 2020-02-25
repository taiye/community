package com.example.song.controller;

import com.example.song.mapper.QuestionMapper;
import com.example.song.mapper.UserMapper;
import com.example.song.model.Question;
import com.example.song.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest httpServletRequest
    ){
        /*model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null||title==""){
            model.addAttribute("error","Not Null");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","Not Null");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","Not Null");
            return "publish";
        }*/
        User user= (User) httpServletRequest.getSession().getAttribute("user");
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
