package com.example.song.service;

import com.example.song.DTO.PaginationDTO;
import com.example.song.DTO.QuestionDTO;
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

    public PaginationDTO getAllList(Integer pageNum, Integer pageSize) {
        Integer totalCount = questionMapper.count();//查询总记录数
        Integer totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1); //计算总页数
        pageNum = (pageNum > 0) ? pageNum : 1; //合理化页码数，小于1的设置为1
        pageNum = (pageNum <= totalPage) ? pageNum : totalPage;//合理化页码数，大于总页数的设置为最后一页
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalPage, pageNum);
        Integer offset = pageSize * (pageNum - 1);//计算偏移值
        List<Question> questions = questionMapper.list(offset, pageSize);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//快速将前面对象的属性赋值到后面对象，按属性名对应
            questionDTO.setUser(user);//开启mybatis驼峰映射，不然mybatis无法将user.countView与count_view对应
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }
}
