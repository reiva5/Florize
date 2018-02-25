package com.findachan.florize;

/**
 * Created by User on 22/02/2018.
 */

public class Util {

    public Util(){

    }

    public String toPrettyPrice(Long A){
        StringBuilder res = new StringBuilder();
        int cnt = 1;
        res.append("00,");
        while (A > 0){
            res.append((char) (A % 10 + '0'));
            A /= 10;
            if (cnt % 3 == 0 && A > 0){
                res.append('.');
            }
            ++cnt;
        }
        res.append(" pR");
        return res.reverse().toString();
    }
}
