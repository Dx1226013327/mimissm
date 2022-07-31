package edu.cque;

import edu.cque.mapper.ProductInfoMapper;
import edu.cque.pojo.ProductInfo;
import edu.cque.pojo.ProductInfoVo;
import edu.cque.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    ProductInfoMapper productInfoMapper;
    @Test
    public void testSelectCondiction(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(4000);
        List<ProductInfo> productInfos = productInfoMapper.selectCondition(vo);
        productInfos.forEach(productInfo -> System.out.println(productInfo));
    }



    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    public static void main(String[] args) {


    }


}
