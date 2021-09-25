package org.ccunix.eshop.controller.api;


import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.CartSelectedmerVO;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.MemberVO;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.service.IOrdersService;
import org.ccunix.eshop.service.IShopCarService;
import org.ccunix.eshop.service.impl.OrdersServiceimpl;
import org.ccunix.eshop.service.impl.ShopCarServiceimpl;
import org.ccunix.eshop.utils.AjaxResult;
import org.ccunix.eshop.utils.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/auth/orders")
public class OrdersController extends HttpServlet {
    IOrdersService ordersService = new OrdersServiceimpl();
    IShopCarService shopCarService = new ShopCarServiceimpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("m");
        if ("list".equals(method)){
            list(req,resp);
        }else if ("delete".equals(method)){
            deleteOrdersById(req,resp);
        }else if ("detail".equals(method)){
            orderDetail(req,resp);
        }else if ("merchantList".equals(method)){
            merchantList(req,resp);
        }
    }
    //订单详情页面
    private void orderDetail(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String id = req.getParameter("id");
        AjaxResult result = AjaxResult.success("查询单个订单成功");

        OrdersVO ordersVO = ordersService.getOrdersById(id);

        result.put("data",ordersVO);
        resp.getWriter().write(JSON.toJSONString(result));

    }
    //订单中的商品详情
    private void merchantList(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        //获得订单id
        String id = req.getParameter("id");
        //获得登陆信息
        OrdersVO ordersVO = ordersService.getOrdersById(id);
        AjaxResult result = AjaxResult.success("订单商品详情列表获取成功");
        List<CartSelectedmerVO> cartSelectedmerVOList = shopCarService.getCarSelectedMerListByCar(ordersVO.getCart());
        result.put("data",cartSelectedmerVOList);
        resp.getWriter().write(JSON.toJSONString(result));
    }
    //删除订单信息
    private void deleteOrdersById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        AjaxResult result = AjaxResult.success("订单删除成功");

        Boolean b = ordersService.deleteOrdersById(id);

        result.put("data",b);
        resp.getWriter().write(JSON.toJSONString(result));
    }
    //获取订单列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        AjaxResult result = AjaxResult.success("订单列表获取成功");

        MemberVO memberVO = SessionUtil.getLoginMemberInfo(req);
        List<OrdersVO> ordersList =ordersService.getOrdersByMid(memberVO.getId());

        result.put("data",ordersList);
        resp.getWriter().write(JSON.toJSONString(result));
    }
}
