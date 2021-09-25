package org.ccunix.eshop.service.impl;

import org.ccunix.eshop.dao.MerchandiseDao;
import org.ccunix.eshop.domain.VO.MerchandiseVO;
import org.ccunix.eshop.service.IMerchandiseService;

import java.util.List;

public class MerchandiseServiceimpl implements IMerchandiseService {
    MerchandiseDao merchandiseDao = new MerchandiseDao();

    @Override
    public List<MerchandiseVO> getMerchListSpecial(String special, String query) {
        int number = Integer.parseInt(special);
        return merchandiseDao.selectMerchListSpecial(number,query);
    }

    @Override
    public MerchandiseVO getMerchantById(String id) {
        int new_id = Integer.parseInt(id);
        return merchandiseDao.getMerchantById(new_id);
    }
}
