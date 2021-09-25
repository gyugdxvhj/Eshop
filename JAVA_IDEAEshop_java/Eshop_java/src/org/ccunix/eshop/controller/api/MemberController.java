package org.ccunix.eshop.controller.api;

import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.MemberVO;
import org.ccunix.eshop.service.IMemberService;
import org.ccunix.eshop.service.IShopCarService;
import org.ccunix.eshop.service.impl.MemberServiceimpl;
import org.ccunix.eshop.service.impl.ShopCarServiceimpl;
import org.ccunix.eshop.utils.AjaxResult;
import org.ccunix.eshop.utils.LogOutputUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/member")
public class MemberController extends HttpServlet {
    IMemberService memberService = new MemberServiceimpl();
    IShopCarService shopCarService = new ShopCarServiceimpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("m");
        if ("login".equals(method)){
            login(req,resp);
        }else if ("getLoginMember".equals(method)){
            getLoginMember(req,resp);
        }else if ("exit".equals(method)){
            exit(req,resp);
        }
    }

    private void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //清空 Session 作用域
        HttpSession session = req.getSession();
        if (session != null){
            //清空 Session中的信息
            session.invalidate();//删除所有session信息
            //删除 特定 session 信息
//            session.removeAttribute("loginMember");
        }
        AjaxResult result = AjaxResult.success("会员安全退出成功");
        resp.getWriter().write(JSON.toJSONString(result));
    }

    //获得登录用户信息
    private void getLoginMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object obj =  session.getAttribute("loginMember");
        if (obj != null){
            MemberVO memberVO = (MemberVO)obj;
            AjaxResult result = AjaxResult.success("登录成功",memberVO);
            resp.getWriter().write(JSON.toJSONString(result));
        }else {
            AjaxResult result = AjaxResult.error(508,"Session中不存在该用户信息");
            resp.getWriter().write(JSON.toJSONString(result));
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String loginName = request.getParameter("loginName");
        String loginPwd = request.getParameter("loginPwd");
        //调用控制器 进行传值  返回一个对象
        MemberVO memberVO =  memberService.login(loginName,loginPwd);
        AjaxResult result = null;
        //将用户信息存入道Session中

        if (memberVO != null){
            //创建或者获得购物车信息t
            CartVO cartVO = shopCarService.getShopCart(memberVO.getId());
            LogOutputUtil.logger.info(loginName+"登录成功");
            result = AjaxResult.success("登录成功",memberVO);
//
            HttpSession session = request.getSession(true);//true的时候如果没有session则会创建一个新的session
            session.setAttribute("loginMember",memberVO);
            session.setAttribute("shopCart",cartVO);

        }else {
//            LogOutputUtil.logger.error(loginName+"登录失败");
            result = AjaxResult.error(505,"用户名或者密码错误");
        }
        response.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
