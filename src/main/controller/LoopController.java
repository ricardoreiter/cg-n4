package main.controller;

import java.util.LinkedList;
import java.util.List;

public class LoopController implements Runnable {

	private final List<Updatable> updatables = new LinkedList<>();
	private boolean active = true;
	private double lastTime;
	private double frames;
	
	public double getFrames() {
		return frames;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void addUpdatable(Updatable updatable) {
		updatables.add(updatable);
	}

	@Override
	public void run() {
		lastTime = System.currentTimeMillis();
		while (isActive()) {
			double lastTimeBkp = lastTime;
			lastTime = System.currentTimeMillis();
			double deltaTime = (lastTime - lastTimeBkp) / 1000;
			
			if (deltaTime > 0) {
				float smoothing = 0.99f;
				double current = 1 / deltaTime; 
				frames = (frames * smoothing) + (current * (1.0-smoothing));
			}
			
			float deltaTimeFloat = (float) deltaTime;
			for (Updatable updatable : updatables) {
				updatable.update(deltaTimeFloat);
			}
		}
	}

}
