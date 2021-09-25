package org.ccunix.eshop.controller.api;

import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.MerchandiseVO;
import org.ccunix.eshop.service.IMerchandiseService;
import org.ccunix.eshop.service.impl.MerchandiseServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/merchandise")
public class MerchandiseController extends HttpServlet {
    IMerchandiseService merchandiseService = new MerchandiseServiceimpl();
    String method = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("m");
        if ("getMerchListSpecial".equals(method)){
            getMerchListSpecial(req,resp);
        }else if("getMerchantById".equals(method)){
            getMerchantById(req,resp);
        }
    }
    //根据id查询商品详情
    private void getMerchantById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("id");
        MerchandiseVO merchandiseVO = merchandiseService.getMerchantById(id);
        String data = JSON.toJSONString(merchandiseVO);
        resp.getWriter().write(data);
        return;
    }

    //根据是否特价  查询商品集合
    public void getMerchListSpecial(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String special = req.getParameter("special");
        String query  = req.getParameter("query");
        List<MerchandiseVO> list = merchandiseService.getMerchListSpecial(special,query);;

        String listJson = JSON.toJSONString(list);
        resp.getWriter().write(listJson);

    }
}
