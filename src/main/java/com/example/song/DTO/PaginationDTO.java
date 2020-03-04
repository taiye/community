package com.example.song.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;//当前页面号
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<Integer>();//导航栏所有页码号


    public void setPagination(Integer totalPage, Integer pageNum) {
        this.totalPage=totalPage;//让每一页都知道总页数是多少
        this.page = pageNum;//将当前页码设置为需求的页码
        pages.add(page);//将本页码设置到导航页码合集中
        for (int i = 1; i <= 3; i++) {//求出导航页码集合
            if (pageNum - i > 0) {
                pages.add(0, pageNum - i);
            }
            if (pageNum + i <= totalPage) {
                pages.add(pageNum + i);
            }
        }
        if (pageNum == 1) {//是否展示上一页
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        if (pageNum == totalPage) {//是否
            showNext = false;
        } else {
            showNext = true;
        }
        if (page==1) {//是否展示第一页
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        if (page==totalPage) {//是否展示最后一页
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
