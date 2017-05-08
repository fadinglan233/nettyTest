//package com.celient;
//
///**
// * Created by fadinglan on 2016/12/13.
// */
//public class TestData extends Thread{
//
//    private int tempnum;
//
//    public TestData(int tempnum) {
//        super();
//        this.tempnum = tempnum;
//    }
//
//    @Override
//    public void run() {
//        // TODO Auto-generated method stub
//        super.run();
//        try {
//            new Client().connect("192.168.1.139",8000 , tempnum);
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
