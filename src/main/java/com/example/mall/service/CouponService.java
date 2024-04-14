package com.example.mall.service;

import com.example.mall.entity.Coupon;
import com.example.mall.entity.CouponUsed;
import com.example.mall.mapper.CouponMapper;
import com.example.mall.response.CouponUsedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponMapper couponMapper;

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(50, random).toString(8);
    }


    public Coupon genCoupon(int discount, int duration) {
        Coupon coupon = new Coupon();
        coupon.setDiscount(discount);
        // 设置优惠券的有效期，假设duration表示有效期的天数
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, duration);
        Date expireDate = calendar.getTime();
        coupon.setExpire(expireDate);
        String randomCode = generateRandomCode(); // 生成8位长度的随机码
        coupon.setCode(randomCode);
        Date created = new Date();
        couponMapper.genCoupon(coupon.getCode(), discount, created, coupon.getExpire(), 0);
        return couponMapper.getNewCoupon(created);
    }


    public List<Coupon> getAllCoupon(){
        return couponMapper.getAllCoupon();
    }

    public boolean isCouponValid(String code) {
        Coupon coupon = couponMapper.getCouponByCode(code);
        Date currentDate = new Date(); // 获取当前日期
        if (currentDate.after(coupon.getExpire())) {
            return false;
        }
        return coupon.getStatus() == 0;
    }

    public boolean useCoupon(int uid,String code,Date time,float discountNum){
        if(!isCouponValid(code)){
            return false;
        }else{
            Coupon coupon = couponMapper.getCouponByCode(code);
            couponMapper.addCouponUseRecord(coupon.getCid(),uid,time,discountNum);
            couponMapper.modifyCouponStatus(code);
            return true;
        }
    }


    public CouponUsed getCouponUsedByID(String code){
        //  根据code查询coupon
        Coupon coupon = couponMapper.getCouponByCode(code);

        return couponMapper.getCouponUsedByID(coupon.getCid());
    }
}
