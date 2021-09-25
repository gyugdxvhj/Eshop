package org.ccunix.eshop.service;

import org.ccunix.eshop.domain.VO.MerchandiseVO;

import java.util.List;

public interface IMerchandiseService {
    //是否特价查询商品列表
    List<MerchandiseVO> getMerchListSpecial(String special,String query);
    //根据id查询商品详情
    MerchandiseVO getMerchantById(String id);
}
