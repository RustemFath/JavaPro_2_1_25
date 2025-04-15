package ru.mystudy;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final Thread[] workers;
    private final AtomicBoolean canAddTask = new AtomicBoolean(true);

    public ThreadPool(int workThreadCount) {
        if (workThreadCount < 1) {
            throw new IllegalArgumentException("Параметр workThreadCount задан некорректно");
        }
        workers = new Thread[workThreadCount];
        for (int i = 0; i < workThreadCount; i++) {
            workers[i] = new Thread(() -> {
                Thread worker = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + " start");
                Runnable task;
                while (canAddTask.get() || hasTasks()) {
                    synchronized (tasks) {
                        task = tasks.pollFirst();
                    }
                    if (task == null) {
                        try {
                            synchronized (worker) {
                                worker.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        task.run();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " finish");
            });
            workers[i].start();
        }
    }

    /**
     * При выполнении у пула потоков метода execute(Runnable), указанная задача должна попасть в очередь исполнения,
     * и как только появится свободный поток – должна быть выполнена.
     */
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("Параметр task is null");
        }
        if (!canAddTask.get()) {
            throw new IllegalStateException();
        }
        synchronized (tasks) {
            tasks.add(task);
        }
        for (final var worker : workers) {
            synchronized (worker) {
                if ((worker.getState() == Thread.State.WAITING) ||
                        (worker.getState() == Thread.State.TIMED_WAITING)) {
                    worker.notify();
                    return;
                }
            }
        }
    }

    /**
     * после выполнения которого новые задачи больше не принимаются пулом
     * (при попытке добавить задачу можно бросать IllegalStateException),
     * и все потоки для которых больше нет задач завершают свою работу.
     */
    public void shutdown() {
        if (canAddTask.get()) {
            canAddTask.set(false);
        }
        for (var worker : workers) {
            synchronized (worker) {
                if ((worker.getState() == Thread.State.WAITING) ||
                        (worker.getState() == Thread.State.TIMED_WAITING)) {
                    worker.notify();
                }
            }
        }
        System.out.println("shutdown");
    }

    /**
     * Дополнительно можно добавить метод awaitTermination() без таймаута,
     * работающий аналогично стандартным пулам потоков
     */
    public void awaitTermination() {
        try {
            boolean isTerminated;
            while (true) {
                isTerminated = true;
                for (var worker : workers) {
                    synchronized (worker) {
                        if ((worker.getState() == Thread.State.WAITING) ||
                                (worker.getState() == Thread.State.TIMED_WAITING)) {
                            worker.notify();
                            isTerminated = false;
                        } else if (worker.getState() != Thread.State.TERMINATED) {
                            isTerminated = false;
                        }
                    }
                }
                if (isTerminated) {
                    System.out.println("awaitTermination");
                    return;
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasTasks() {
        synchronized (tasks) {
            return !tasks.isEmpty();
        }
    }
}