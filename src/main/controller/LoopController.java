package main.controller;

import java.util.LinkedList;
import java.util.List;

public class LoopController implements Runnable {

	private final List<Updatable> updatables = new LinkedList<>();
	private boolean active = true;
	private double lastTime;
	
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
			float deltaTime = (float) ((lastTime - lastTimeBkp) / 1000);
			for (Updatable updatable : updatables) {
				updatable.update(deltaTime);
			}
		}
	}

}
