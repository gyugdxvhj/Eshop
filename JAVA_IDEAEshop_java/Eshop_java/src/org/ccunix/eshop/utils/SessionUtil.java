package org.ccunix.eshop.utils;

import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    /**
     * 获得会员登陆信息
     * @param request
     * @return
     */
    public static MemberVO getLoginMemberInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("loginMember");
        MemberVO memberVO = (MemberVO) obj;
        return memberVO;
    }

    //获得购物车信息
    public static CartVO getShopCarInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("shopCart");
        CartVO cartVO = (CartVO) obj;
        return cartVO;
    }
}
