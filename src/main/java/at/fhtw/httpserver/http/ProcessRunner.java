package at.fhtw.httpserver.http;

public class ProcessRunner implements Runnable{
    private SlowProcess slowProcess;
    private long counter;
    public ProcessRunner(SlowProcess slowProcess) {
        this.slowProcess = slowProcess;
        this.counter = 0;
    }
    @Override
    public void run() {
        counter = slowProcess.slowCounter(1000000);
        System.out.printf("[%s] Counter: %d\n", Thread.currentThread().getName(), counter);
    }

    public long getCounter() {
        return counter;
    }
}
