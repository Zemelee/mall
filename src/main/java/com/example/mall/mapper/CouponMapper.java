package com.example.mall.mapper;

import com.example.mall.entity.Coupon;
import com.example.mall.entity.CouponUsed;
import com.example.mall.entity.History;
import com.example.mall.entity.HistoryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CouponMapper {


    Coupon getNewCoupon(Date created);
    Coupon getCouponByCode(String code);
    List<Coupon> getAllCoupon();
    int genCoupon(String code, int discount, Date created, Date expire, int status);

    int modifyCouponStatus(String code);
    int addCouponUseRecord(int cid,int uid,Date orderTime,float discountNum);

    CouponUsed getCouponUsedByID(int cid);

}
