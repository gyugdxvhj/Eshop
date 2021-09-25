package org.ccunix.eshop.dao;

import org.ccunix.eshop.domain.VO.MemberVO;
import org.ccunix.eshop.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDao {
    //登陆  查找是否存在该用户
    public MemberVO selectMemberByLoginNameAndLoginPwd(MemberVO memberVO) {
        MemberVO vo = null;

        Connection connection = DataSource.getConnection();
        try {
            String sql = "select member.id,memberLevel,loginName,loginPwd,memberName,phone,address,zip,DATE_FORMAT(regDate,'%Y-%m-%d') regDate, " +
                    "DATE_FORMAT(lastDate,'%Y-%m-%d') lastDate,loginTimes,email,memberlevel.levelName,memberlevel.favourable  from member,memberlevel " +
                    "where member.memberLevel = memberlevel.id and loginName=? and loginPwd=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            //对问号传值
            ps.setString(1,memberVO.getLoginName());
            ps.setString(2,memberVO.getLoginPwd());
            ResultSet set = ps.executeQuery();
            if (set.next()){
                vo = new MemberVO(set.getInt("id"),set.getInt("memberLevel"),
                        set.getString("loginName"),set.getString("loginPwd"),
                        set.getString("memberName"),set.getString("phone"),
                        set.getString("address"),set.getString("zip"),
                        set.getString("regDate"),set.getString("lastDate"),
                        set.getInt("loginTimes"),set.getString("email"),
                        set.getString("levelName"),set.getInt("favourable")
                        );
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return vo;
    }
}
