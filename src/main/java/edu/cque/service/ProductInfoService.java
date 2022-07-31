package edu.cque.service;

import com.github.pagehelper.PageInfo;
import edu.cque.pojo.ProductInfo;
import edu.cque.pojo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品（不分页）
    List<ProductInfo> getAll();

    //分页功能实现
    PageInfo splitPage(int pageNum,int pageSize);

    //商品添加
    int save(ProductInfo info);

    //按主键id查询商品
    ProductInfo getByID(int pid);

    //修改数据
    int update(ProductInfo info);

    //单个数据的删除
    int delete(int pid);

    //批量删除商品的功能
    int deleteBatch(String[] ids);

    //多条件商品查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页
    PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
