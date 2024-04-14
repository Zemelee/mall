package com.example.mall.controller;


import com.example.mall.entity.Coupon;
import com.example.mall.entity.CouponUsed;
import com.example.mall.mapper.CouponMapper;
import com.example.mall.response.CouponUsedResponse;
import com.example.mall.service.CouponService;
import com.example.mall.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    private final CouponMapper couponMapper;
    @Autowired
    private UserService userService;

    public CouponController(CouponService couponService, CouponMapper couponMapper) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    @Data
    static class DiscountAndDuration {
        int discount;
        int duration;
    }

    @PostMapping("/gen")
    public Coupon genCoupon(@RequestBody DiscountAndDuration discountAndDuration) {
        return couponService.genCoupon(discountAndDuration.getDiscount(), discountAndDuration.getDuration());
    }

    @GetMapping("/get")
    public List<Coupon> getAllCoupon() {
        return couponService.getAllCoupon();
    }


    @Data
    static class UidCode {
        int uid;
        String code;
        Date time;
        float discountNum;
    }

    @PostMapping("/use")
    public boolean useCoupon(@RequestBody UidCode uidCode) {
        return couponService.useCoupon(uidCode.getUid(), uidCode.getCode(), uidCode.getTime(), uidCode.getDiscountNum());
    }

    @GetMapping("/validate/{code}")
    public Coupon validate(@PathVariable String code) {
        return couponMapper.getCouponByCode(code);

    }


    @GetMapping("/record/{code}")
    public CouponUsedResponse getCouponUsed(@PathVariable String code) {
        CouponUsedResponse response = new CouponUsedResponse();
        Coupon coupon = couponMapper.getCouponByCode(code);
        CouponUsed couponUsed =  couponService.getCouponUsedByID(code);
        String uname = userService.getUserById(couponUsed.getUid()).getUsername();
        response.setUseId(couponUsed.getId());
        response.setCid(couponUsed.getCid());
        response.setCode(code);
        response.setUid(couponUsed.getUid());
        response.setUname(uname);
        response.setDiscount(coupon.getDiscount());
        response.setDiscountNum(couponUsed.getDiscountNum());
        response.setOrderTime(couponUsed.getOrderTime());
        return response;
    }


}
