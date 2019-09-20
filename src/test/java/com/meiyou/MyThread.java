package com.meiyou;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-09-20 13:52
 **/
public class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("myThread");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}