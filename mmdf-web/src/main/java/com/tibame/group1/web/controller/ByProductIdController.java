package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.web.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

//@RestController
@Controller
@Validated
//@RequestMapping("api/")
@RequestMapping("/product")
public class ByProductIdController {

    @Autowired
    private ProductService productService;
        @PostMapping("getOne_For_Display")
        public String getOne_For_Display(
                /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
                @NotEmpty(message = "商品編號: 請勿空白")
                @Digits(integer = 4, fraction = 0, message = "商品編號: 請填數字-請勿超過{integer}位數")
                @Min(value = 1, message = "商品編號: 不能小於{value}")
                @Max(value = 9999, message = "商品編號: 不能超過{value}")
                @RequestParam("productId")
                String productId,
                ModelMap model) {

            /***************************2.開始查詢資料*********************************************/

            ProductEntity productEntity = productService.getOneProduct(Integer.valueOf(productId));

            List<ProductEntity> list = productService.getAll();
            model.addAttribute("productListData", list); // for select_page.html 第97 109行用
            model.addAttribute("productCategoryEntity", new ProductCategoryEntity()); // for select_page.html 第133行用
            List<ProductCategoryEntity> list2 = productService.getAllCategory();
            model.addAttribute("productCategoryListData", list2); // for select_page.html 第135行用
            List<ProductImgEntity> list3 = productService.getAllProductImg();
            model.addAttribute("productImgListData", list3); // for select_page.html 第135行用

            if (productEntity == null) {
                model.addAttribute("errorMessage", "查無資料");
                return "/product/select";
            }

            /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
            model.addAttribute("productEntity", productEntity);
            model.addAttribute(
                    "getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->

            return "/product/select";
        }

        @ExceptionHandler(value = {ConstraintViolationException.class})
        // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
        public ModelAndView handleError(
                HttpServletRequest req, ConstraintViolationException e, Model model) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            StringBuilder strBuilder = new StringBuilder();
            for (ConstraintViolation<?> violation : violations) {
                strBuilder.append(violation.getMessage() + "<br>");
            }

            List<ProductEntity> list = productService.getAll();
            model.addAttribute("productListData", list); // for select_page.html 第97 109行用
            model.addAttribute("productCategoryEntity", new ProductCategoryEntity()); // for select_page.html 第133行用
            model.addAttribute("productImgEntity", new ProductImgEntity()); // for select_page.html 第133行用
            List<ProductCategoryEntity> list2 = productService.getAllCategory();
            model.addAttribute("productCategoryListData", list2); // for select_page.html 第135行用
            List<ProductImgEntity> list3 = productService.getAllProductImg();
            model.addAttribute("productImgListData", list3); // for select_page.html 第135行用
            String message = strBuilder.toString();
            return new ModelAndView(
                    "/product/select", "errorMessage", "請修正以下錯誤:<br>" + message);
        }
}

//    @PostMapping("productCategory/create")
//    @CheckLogin
//    public @ResponseBody ResDTO<ProductCategoryCreateResDTO> productCategoryCreate(
//            @Valid @RequestBody ProductCategoryCreateReqDTO req) throws IOException {
//        ResDTO<ProductCategoryCreateResDTO> res = new ResDTO<>();
//        res.setData(productService.productCategoryCreate(req));
//        return res;
//    }