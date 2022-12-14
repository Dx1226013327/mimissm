package edu.cque.controller;

import com.github.pagehelper.PageInfo;
import edu.cque.pojo.ProductInfo;
import edu.cque.pojo.ProductInfoVo;
import edu.cque.service.ProductInfoService;
import edu.cque.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;
    //异步上传的图片的名称
    String saveFileName = "";
    //当前页
    int page = 1;
    @Autowired
    ProductInfoService productInfoService;
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //显示第1页的五条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else{
            System.out.println("no data");
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "product";
    }
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxsplit(ProductInfoVo vo, HttpSession session){
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生成文件名UUID+上传图片的后缀.jpg .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path+ File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //范湖客户端JSON对象，封装图片的路径，为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpDate(new Date());
        info.setpImage(saveFileName);
        int num = -1;
        try {
            num = productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(num > 0){
            request.setAttribute("msg","增加成功！");
        }else {
            request.setAttribute("msg","增加失败！");
        }
        //增加成功后应该重新访问数据库,所以跳转到分页显示的action
        //清空saveFileName变量的内容，为了下次增加或修改
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo info = productInfoService.getByID(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为ajax的异步图片上传，如果有上传过，则saveFileName里有上唇上来的图片的名称，
        //如果没有则为空,实体类info使用隐藏表单提交的原始的图片名称
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0){
            //此时说明更新成功
            request.setAttribute("msg","更新成功");
        }else {
            //更新失败
            request.setAttribute("msg","更新失败");
        }
        //处理完更新后，saveFileName里可能还有数据，所以为了下次更新或新增要清空
        saveFileName = "";
        return "forward:/prod/split.action";
    }
    //单个数据的删除
    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0){
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
            System.out.println(vo.getTypeid());
        }else {
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){

        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if(vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("deleteProdVo");
        }else{
            System.out.println("no data");
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deletebatch(String pids,ProductInfoVo vo,HttpServletRequest request){
        String[] ps = pids.split(",");
        this.page = page;
        int num = -1;
        try {
            num = productInfoService.deleteBatch(ps);
            if(num > 0){
                request.setAttribute("msg","批量删除成功!");
                request.getSession().setAttribute("deleteProdVo",vo);
            }else {
                request.setAttribute("msg","批量删除失败!");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除!");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }

    //多条件查询功能实现
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }

}
