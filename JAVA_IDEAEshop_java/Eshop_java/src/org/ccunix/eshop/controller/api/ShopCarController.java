package org.ccunix.eshop.controller.api;

import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.CartSelectedmerVO;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.MemberVO;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.service.IShopCarService;
import org.ccunix.eshop.service.impl.ShopCarServiceimpl;
import org.ccunix.eshop.utils.AjaxResult;
import org.ccunix.eshop.utils.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/auth/shopCar")
public class ShopCarController extends HttpServlet {
    IShopCarService shopCarService = new ShopCarServiceimpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("m");
        if ("list".equals(method)){
            list(req,resp);
        }else if ("add".equals(method)){
            add(req,resp);
        }else if ("delete".equals(method)){
            delete(req,resp);
        }else if ("clear".equals(method)){
            clear(req,resp);
        }else if ("updateNumber".equals(method)){
            updateNumber(req,resp);
        }else if ("submit".equals(method)){
            submitShopCart(req,resp);
        }
    }
    //提交购物车
    private void submitShopCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        MemberVO memberVO = SessionUtil.getLoginMemberInfo(req);
        OrdersVO ordersVO = shopCarService.submitShopCart(cartVO.getId(),memberVO.getId());
        if (ordersVO != null){
            AjaxResult result = AjaxResult.success("购物车订单提交成功");
            result.put("data",ordersVO);
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            resp.getWriter().write(JSON.toJSONString(AjaxResult.error("购物车订单提交失败")));
        }
    }

    //手动修改购物车商品数量
    private void updateNumber(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String num = req.getParameter("num");
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        Boolean b = shopCarService.updateCartSelectedMerNumber(id,cartVO.getId(),Integer.parseInt(num));
        if (b){
            AjaxResult result = AjaxResult.success("购物车商品数量修改成功");
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            resp.getWriter().write(JSON.toJSONString(AjaxResult.error("购物车商品数量修改失败")));
        }

    }

    //清空购物车
    private void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        Boolean b = shopCarService.clearCartSelectedMerByCart(cartVO.getId());
        if (b){
            AjaxResult result = AjaxResult.success("购物车清空成功");
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            resp.getWriter().write(JSON.toJSONString(AjaxResult.error("清空购物车商品失败")));
        }
    }

    //删除购物车的商品
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id= req.getParameter("id");
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        Boolean b = shopCarService.deleteCartSelectedMerById(id,cartVO.getId());
        if (b){
            AjaxResult result = AjaxResult.success("购物车删除商品成功,id = "+id);
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            resp.getWriter().write(JSON.toJSONString(AjaxResult.error("删除购物车商品失败")));
        }
    }

    //商品添加购物车
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String merId = req.getParameter("merId");
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        MemberVO memberVO = SessionUtil.getLoginMemberInfo(req);
        Boolean b = shopCarService.addShopCar(cartVO.getId(),merId,memberVO.getFavourable());
        if (b){
            AjaxResult result = AjaxResult.success("购物车添加商品成功,id = "+merId);
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            resp.getWriter().write(JSON.toJSONString(AjaxResult.error("添加购物车失败")));
        }

    }

    //购物车列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        AjaxResult result = AjaxResult.success("购物车列表获取成功");
//        resp.getWriter().write(JSON.toJSONString(result));
        //获得登录信息
//        MemberVO memberVO = SessionUtil.getLoginMemberInfo(req);
        //获得购物车信息
        CartVO cartVO = SessionUtil.getShopCarInfo(req);
        //根据购物车去查询购物车详情
        List<CartSelectedmerVO> cartSelectedmerList = shopCarService.getCarSelectedMerListByCar(cartVO.getId());
        result.put("data",cartSelectedmerList);
        resp.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
