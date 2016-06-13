package main.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.vecmath.Vector3d;

import main.World;
import main.opengl.Camera;

public class CameraController implements KeyListener, MouseListener, MouseMotionListener, Updatable {

	private final Camera camera;
	private final World world;
	
	private boolean forward = false;
	private boolean backward = false;
	
	public CameraController(Camera camera, World world) {
		super();
		this.camera = camera;
		this.world = world;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_W:
				forward = true;
				break;
			case KeyEvent.VK_S:
				backward = true;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_W:
				forward = false;
				break;
			case KeyEvent.VK_S:
				backward = false;
				break;
		}	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void update(float deltaTime) {
		if (forward) {
			Vector3d direction = getDirection(camera.getEye(), camera.getCenter());
			direction.normalize();
			direction = multiply(direction, deltaTime * 100);
			moveCameraToDirection(direction);
		}
		if (backward) {
			Vector3d direction = getDirection(camera.getCenter(), camera.getEye());
			direction.normalize();
			direction = multiply(direction, deltaTime * 100);
			moveCameraToDirection(direction);
		}
	}
	
	private void moveCameraToDirection(Vector3d direction) {
		Vector3d newEyePos = sum(camera.getEye(), direction);
		Vector3d newCenterPos = sum(camera.getCenter(), direction);
		camera.setEye(newEyePos);
		camera.setCenter(newCenterPos);
		world.requestRender();
	}
	
	private Vector3d multiply(Vector3d a, float b) {
		return new Vector3d(a.x * b, a.y * b, a.z * b);
	}
	
	private Vector3d sum(Vector3d a, Vector3d b) {
		return new Vector3d(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	private Vector3d getDirection(Vector3d from, Vector3d to) {
		return new Vector3d(to.x - from.x, to.y - from.y, to.z - from.z);
	}

}
