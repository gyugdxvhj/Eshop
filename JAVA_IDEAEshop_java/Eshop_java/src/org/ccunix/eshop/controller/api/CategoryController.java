package org.ccunix.eshop.controller.api;

import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.CategoryVO;
import org.ccunix.eshop.service.ICategoryService;
import org.ccunix.eshop.service.impl.CategoryServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/api/category")
public class CategoryController extends HttpServlet {
    ICategoryService categoryService = new CategoryServiceimpl();

    String method = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        method = req.getParameter("m");
        if ("list".equals(method)){
            list(req,resp);
        }else if ("delete".equals(method)){
            delete(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("add".equals(method)){
            add(req,resp);
        }else if ("update".equals(method)){
            update(req,resp);
        }
    }

    //add
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {

    }

    //delete
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
    }

    //list
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{

        List<CategoryVO> categoryVOList = categoryService.getCategoryList();

        //把数据转换成json字符串
        String data = JSON.toJSONString(categoryVOList);
        //把数据写回到前端  调用回复数据
        resp.getWriter().write(data);
    }

    //update
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
    }
}
