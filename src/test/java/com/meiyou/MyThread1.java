package com.meiyou;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-09-20 13:53
 **/
public class MyThread1 implements Runnable {
    @Override
    public void run() {
        System.out.println("myThread");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread1());
        thread.start();
    }
}