package com.example.Coffee.entities.order.order;

import com.example.Coffee.entities.PaymentForm_;
import com.example.Coffee.entities.city.City_;
import com.example.Coffee.entities.user.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class OrderSpecifications {

    public static Specification<Order> likeUserName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.USER).get(User_.NAME), "%" + name.toLowerCase(Locale.ROOT) + "%");
        };
    }

    public static Specification<Order> likeCityName(String name) {
        if (name == null) {
            return null;
        }
        if(name.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.CITY).get(City_.NAME), "%" + name.toLowerCase(Locale.ROOT) + "%");
        };
    }

    public static Specification<Order> likeId(String id) {
        if (id == null) {
            return null;
        }
        try{
            Long.parseLong(id);
        } catch (Exception e){
            return null;
        }
        return (root, query, cb) -> {
            return cb.equal(root.get(Order_.ID), id);
        };
    }

    public static Specification<Order> likeDate(String date) {
        if (date == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.DATE), "%" + date + "%");
        };
    }

    public static Specification<Order> likeTime(String time) {
        if (time == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.TIME), "%" + time + "%");
        };
    }

    public static Specification<Order> likeHome(String home) {
        if (home == null) {
            return null;
        }
        if(home.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.HOME), "%" + home + "%");
        };
    }

    public static Specification<Order> likeEntrance(String entrance) {
        if (entrance == null) {
            return null;
        }
        if(entrance.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.ENTRANCE), "%" + entrance + "%");
        };
    }

    public static Specification<Order> likeFlat(String flat) {
        if (flat == null) {
            return null;
        }
        if(flat.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.FLAT), "%" + flat + "%");
        };
    }

    public static Specification<Order> likePaymentForm(String paymentForm) {
        if (paymentForm == null) {
            return null;
        }
        if(paymentForm.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.PAYMENT_FORM).get(PaymentForm_.NAME), "%" + paymentForm + "%");
        };
    }

    public static Specification<Order> likePrepareCash(String prepareCash) {
        if (prepareCash == null) {
            return null;
        }
        if(prepareCash.equals("")){
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(Order_.PREPARE_CASH), "%" + prepareCash + "%");
        };
    }
}
