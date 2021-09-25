package org.ccunix.eshop.service.impl;

import org.ccunix.eshop.dao.MemberDao;
import org.ccunix.eshop.domain.VO.MemberVO;
import org.ccunix.eshop.service.IMemberService;

public class MemberServiceimpl implements IMemberService {
    MemberDao memberDao = new MemberDao();

    @Override
    public MemberVO login(String loginName, String loginPwd) {
        MemberVO memberVO = new MemberVO();
        memberVO.setLoginName(loginName);
        memberVO.setLoginPwd(loginPwd);

        return memberDao.selectMemberByLoginNameAndLoginPwd(memberVO);
    }
}
