package com.ne.www.snaker;

/**
 * Created by OOXX on 18/1/15.
 */

public class SnakerBody {
    public int x = 0;
    public int y = 0;
    public boolean isHeader;  //头
    public boolean isLast; //尾


    @Override
    public String toString() {
        return "SnakerBody{" +
                "x=" + x +
                ", y=" + y +
                ", isHeader=" + isHeader +
                ", isLast=" + isLast +
                '}';
    }
}
