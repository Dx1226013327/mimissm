package edu.cque.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.cque.mapper.ProductInfoMapper;
import edu.cque.pojo.ProductInfo;
import edu.cque.pojo.ProductInfoExample;
import edu.cque.pojo.ProductInfoVo;
import edu.cque.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService{
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum, pageSize);
        //进入PageInfo的数据封装
        //进行有条件的查询操作，必须要创建
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序排序
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合，切记切记，一定在取集合之前，设置PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询到的集合封装进pageinfo
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，先要设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }
}
