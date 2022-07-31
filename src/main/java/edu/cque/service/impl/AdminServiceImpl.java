package edu.cque.service.impl;

import edu.cque.mapper.AdminMapper;
import edu.cque.pojo.Admin;
import edu.cque.pojo.AdminExample;
import edu.cque.service.AdminService;
import edu.cque.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;



    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户名到DB中响应用户对象


        /*有条件查询就要新建
        *   AdminExample
        * */
        AdminExample example = new AdminExample();
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size() > 0){
            Admin admin = list.get(0);
            //如果查询到用户对象，再进行密码的比对，注意密码是密文
            //String miPwd = MD5Util.getMD5(pwd);
            if(pwd.equals(admin.getaPass())){
                return admin;
            }
        }

        //如果查询到用户对象，再进行密码的比对
        return null;
    }
}
