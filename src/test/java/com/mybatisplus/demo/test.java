package com.mybatisplus.demo;


import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone {
    public  synchronized void sendEmail()throws Exception{
        TimeUnit.SECONDS.sleep(3);
        System.out.println("******sendEmail");
    }
    public       synchronized void sendSMS()throws Exception{
        System.out.println("******sendSMS");
    }
    public void sayHello()throws Exception{
        System.out.println("******sayHello");
    }
}

public class test {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(() ->{
            try {
                phone1.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();
    }

}
