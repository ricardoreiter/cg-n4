package main.controller;


public class LoopController implements Runnable {

	private final Updatable updatable;
	private boolean active = true;
	private double lastTime;
	
	public LoopController(Updatable updatable) {
		this.updatable = updatable;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void run() {
		lastTime = System.currentTimeMillis();
		while (isActive()) {
			double lastTimeBkp = lastTime;
			lastTime = System.currentTimeMillis();
			float deltaTime = (float) ((lastTime - lastTimeBkp) / 1000);
			updatable.update(deltaTime);
		}
	}

}
