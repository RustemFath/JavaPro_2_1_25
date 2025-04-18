package ru.mystudy;

public class MainApp {
    private final static int TASK_COUNT = 10;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");

        ThreadPool threadPool = new ThreadPool(3);

        for (int i = 0; i < TASK_COUNT; i++) {
            threadPool.execute(new Task(i));
        }

        Thread.sleep(2000);
        System.out.println("add new task");

        for (int i = TASK_COUNT; i < 2 * TASK_COUNT; i++) {
            threadPool.execute(new Task(i));
        }

        Thread.sleep(3000);

        threadPool.shutdown();

        try {
            System.out.println("add new task after shutdown");
            threadPool.execute(new Task(-1));
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");
        }

        threadPool.awaitTermination();

        System.out.println("main finish");
    }
}