package org.jeecg.modules.demo.pagebean;

import lombok.Data;

import java.util.List;
@Data
public class PageBean <T>{
    private  int totalCount ;
    private  int currentPage;
    private  int pageSize ;
    private List<T> pageData ;
    private  int totalPage;
    private  int start ;

}