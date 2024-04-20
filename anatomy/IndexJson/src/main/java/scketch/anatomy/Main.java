package scketch.anatomy;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        MemJson mj_checkin = new MemJson("/home/data/yelp_academic_dataset_checkin.json",1,300000000);
        MemJson mj_review = new MemJson("/home/data/yelp_academic_dataset_review.json",5,1073741824);
        MemJson mj_tip = new MemJson("/home/data/yelp_academic_dataset_tip.json",1,200000000);
        MemJson mj_user = new MemJson("/home/data/yelp_academic_dataset_user.json",4,1073741824);
        MemJson mj_business = new MemJson("/home/data/yelp_academic_dataset_business.json",1,120000000);
        System.out.println("Hello world!");
    }
}