package com.minakov.utils;

public class Statistics {

    public static final String STAT_QUERY = "SELECT CITY.NAME AS City,\n" +
            "\tCOUNT(CAR.ID) AS Taxed_Cars\n" +
            "\tFROM CAR\n" +
            "\tJOIN CAR_SHOWROOM ON CAR.SHOWROOM_ID = CAR_SHOWROOM.ID\n" +
            "\tJOIN CITY ON CAR_SHOWROOM.CITY_ID = CITY.ID\n" +
            "\tWHERE CAR.PRICE >= 3000000\n" +
            "\tGROUP BY CITY.NAME\n" +
            "\tORDER BY CITY.NAME;";

    private String city;
    private int count;

    public Statistics(String street, int count) {
        this.city = street;
        this.count = count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
