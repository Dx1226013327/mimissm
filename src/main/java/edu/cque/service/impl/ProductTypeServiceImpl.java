package edu.cque.service.impl;

import edu.cque.mapper.ProductTypeMapper;
import edu.cque.pojo.ProductType;
import edu.cque.pojo.ProductTypeExample;
import edu.cque.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
