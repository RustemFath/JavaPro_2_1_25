package ru.mystudy;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final LinkedList<WorkerThread> workerThreads = new LinkedList<>();
    private final AtomicBoolean canAddTask = new AtomicBoolean(true);

    public ThreadPool(int workThreadCount) {
        if (workThreadCount < 1) {
            throw new IllegalArgumentException("Параметр workThreadCount задан некорректно");
        }
        for (int i = 0; i < workThreadCount; i++) {
            final var worker = new WorkerThread();
            workerThreads.add(worker);
            worker.start();
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
        for (final var worker : workerThreads) {
            if (worker.isLocked()) {
                worker.unlock();
                return;
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
                for (final var worker : workerThreads) {
                    if (worker.isLocked()) {
                        worker.unlock();
                        isTerminated = false;
                    } else if (!worker.isTerminate()) {
                        isTerminated = false;
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

    private void runWorker(WorkerThread workerThread) {
        System.out.println(Thread.currentThread().getName() + " start");
        Runnable task;
        while (canAddTask.get() || hasTasks()) {
            synchronized (tasks) {
                task = tasks.pollFirst();
            }
            if (task == null) {
                workerThread.lock();
            } else {
                task.run();
            }
        }
        System.out.println(Thread.currentThread().getName() + " finish");
    }

    private class WorkerThread implements Runnable {
        private final Thread thread;

        public WorkerThread() {
            thread = new Thread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        void start() {
            thread.start();
        }

        void lock() {
            try {
                synchronized (thread) {
                    thread.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void unlock() {
            synchronized (thread) {
                thread.notify();
            }
        }

        boolean isLocked() {
            synchronized (thread) {
                return (thread.getState() == Thread.State.WAITING) ||
                       (thread.getState() == Thread.State.TIMED_WAITING);
            }
        }

        boolean isTerminate() {
            synchronized (thread) {
                return thread.getState() == Thread.State.TERMINATED;
            }
        }
    }
}