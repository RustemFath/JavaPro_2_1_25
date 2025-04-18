package ru.mystudy;

public class Task implements Runnable {
    private final int num;

    public Task(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " run task " + num);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}