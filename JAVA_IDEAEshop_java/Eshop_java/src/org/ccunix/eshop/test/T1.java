package org.ccunix.eshop.test;

import com.alibaba.fastjson.JSON;
import org.ccunix.eshop.domain.VO.CategoryVO;

import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        CategoryVO vo1 =new CategoryVO();
        vo1.setId(1);
        vo1.setCateName("计算机类型");
        vo1.setCateDesc("计算机类型的书籍");
        CategoryVO vo2 =new CategoryVO();
        vo2.setId(2);
        vo2.setCateName("物理类型");
        vo2.setCateDesc("物理类型的书籍");
        CategoryVO vo3 =new CategoryVO();
        vo3.setId(3);
        vo3.setCateName("化学类型");
        vo3.setCateDesc("化学类型的书籍");

        categoryVOList.add(vo1);
        categoryVOList.add(vo2);
        categoryVOList.add(vo3);

        //fastjson
        String data = JSON.toJSONString(categoryVOList);
        System.out.println(data);
    }
}
