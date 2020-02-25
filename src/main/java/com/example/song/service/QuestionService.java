package com.example.song.service;

import com.example.song.dataTransferO.QuestionDTO;
import com.example.song.mapper.QuestionMapper;
import com.example.song.mapper.UserMapper;
import com.example.song.model.Question;
import com.example.song.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    UserMapper userMapper;
    public List<QuestionDTO> list() {
        List<Question> questions=questionMapper.list();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//快速将前面对象的属性赋值到后面对象，按属性名对应
            questionDTO.setUser(user);//开启mybatis驼峰映射，不然mybatis无法将user.countView与count_view对应
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
