package com.example.Coffee.entities.order.order;

import com.example.Coffee.entities.PaymentForm_;
import com.example.Coffee.entities.city.City_;
import com.example.Coffee.entities.user.User_;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static Specification<Order> likeDate(String dateStartString, String dateFinString) throws ParseException {
        if (dateStartString.equals("") && dateFinString.equals("")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = new Date(1L);
        String localDateFin = "8023-07-12";
        Date dateFin = format.parse(localDateFin);

        if (!dateStartString.equals("")){
            dateStart = format.parse(dateStartString);
        }
        if (!dateFinString.equals("")){
            dateFin = format.parse(dateFinString);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFin);
        calendar.add(Calendar.DATE, 1);

        Date finalDateStart = dateStart;
        Date finalDateFin = calendar.getTime();
        return (root, query, cb) -> {
            return cb.between(root.get(Order_.DATE), finalDateStart, finalDateFin);
        };
    }

    public static Specification<Order> likeTime(String timeStartString, String timeFinString) throws ParseException {
        if (timeStartString.equals("") && timeFinString.equals("")) {
            return null;
        }
        System.out.println(timeStartString);
        System.out.println(timeFinString);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Date timeStart = new Date(1L);
        String localDateFin = "23:59";
        Date timeFin = format.parse(localDateFin);

        if (!timeStartString.equals("")){
            timeStart = format.parse(timeStartString);
        }
        if (!timeFinString.equals("")){
            timeFin = format.parse(timeFinString);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeFin);
        calendar.add(Calendar.MINUTE, 1);

        Date finalTimeStart = timeStart;
        Date finalTimeFin = calendar.getTime();

        System.out.println(finalTimeStart);
        System.out.println(finalTimeFin);
        return (root, query, cb) -> {
            return cb.between(root.get(Order_.TIME), finalTimeStart, finalTimeFin);
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
