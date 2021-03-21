package global.util.tarefa;

public abstract class TaskRunnable implements Runnable{
	private int seconds;
	private boolean running = true;
	
	public abstract void shutdown();
	public abstract boolean isRun();

}
