package com.example.eventpass;


public final class TicketUtils {
    private TicketUtils() {}


    public static int makeCode(String name, String event) {
        String seed = name + event;
        return Math.abs(seed.hashCode()) % 100000;
    }


    public static int discountFor(String promo) {
        String p = promo.trim();
        if (p.equalsIgnoreCase("STUDENT10")) return 10;
        if (p.equalsIgnoreCase("VIP20")) return 20;
        return 0;
    }
}
