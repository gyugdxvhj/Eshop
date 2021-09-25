package org.ccunix.eshop.domain.VO;

import org.ccunix.eshop.domain.MemberPOJO;

public class MemberVO extends MemberPOJO {
    public MemberVO() {
    }

    public MemberVO(Integer id, Integer memberLevel, String loginName, String loginPwd, String memberName, String phone, String address, String zip, String regDate, String lastDate, Integer loginTimes, String email, String levelName, int favourable) {
        super(id, memberLevel, loginName, loginPwd, memberName, phone, address, zip, regDate, lastDate, loginTimes, email);
        this.levelName = levelName;
        this.favourable = favourable;
    }

    //扩展字段
    private String levelName;//登记名称
    private int favourable;//打折

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getFavourable() {
        return favourable;
    }

    public void setFavourable(Integer favourable) {
        this.favourable = favourable;
    }
}
