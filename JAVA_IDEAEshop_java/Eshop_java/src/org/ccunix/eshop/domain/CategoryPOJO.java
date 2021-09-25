package org.ccunix.eshop.domain;

/**
 * 和商品列表对应的Java类型    表中有啥 他就有啥
 */
public class CategoryPOJO {
    //主键
    private Integer id;
    //类别名称
    private String cateName;
    //类别描述
    private String cateDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

}
