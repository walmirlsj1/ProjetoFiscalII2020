package global.util.tarefa;

import java.util.concurrent.ThreadPoolExecutor;

public class MonitorThread implements Runnable
{
    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run=true;

    public MonitorThread(ThreadPoolExecutor executor, int delay)
    {
        this.executor = executor;
        this.seconds=delay;
    }
    public void shutdown(){
        this.run=false;
    }
    @Override
    public void run()
    {
        while(run){
                try {
                    Thread.sleep(seconds*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
            
    }
}