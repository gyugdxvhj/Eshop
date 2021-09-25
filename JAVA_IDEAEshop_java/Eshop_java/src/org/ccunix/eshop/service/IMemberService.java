package org.ccunix.eshop.service;

import org.ccunix.eshop.dao.MemberDao;
import org.ccunix.eshop.domain.VO.MemberVO;

public interface IMemberService {

    //登陆
    MemberVO login(String loginName, String loginPwd);

}
