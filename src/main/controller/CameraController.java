package main.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.vecmath.Vector3f;

import main.World;
import main.opengl.Camera;

public class CameraController implements KeyListener, MouseListener, MouseMotionListener, Updatable {

	private static final float ROTATE_SENSITIVITY = 0.25f;
	private static final float MOVE_SENSIBILITY = 50;
	private static final float MAX_ROTATE_UP = 60f;
	private static final float MAX_ROTATE_DOWN = -60f;
	
	private final Camera camera;
	private final World world;
	
	private boolean forward = false;
	private boolean backward = false;
	private boolean right = false;
	private boolean left = false;
	private boolean moveCamera = false;
	private Point oldMousePos = null;
	private float yRotation = 0;
	private float xRotation = 0;
	
	public CameraController(Camera camera, World world) {
		super();
		this.camera = camera;
		this.world = world;
		
		// Quando iniciamos a camera, como o olho está no centro e olhando para fora, fazemos uma translação da distancia do centro para poder ver o cenário de fora
		Vector3f viewTranslate = new Vector3f(camera.getCenter());
		viewTranslate.negate();
		viewTranslate.add(camera.getEye());
		this.camera.translate(viewTranslate);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (moveCamera) {
			Point currentPos = arg0.getPoint();
			
			if (oldMousePos == null) {
				oldMousePos = currentPos;
			}
		
			float yDiff = (oldMousePos.x - currentPos.x) * ROTATE_SENSITIVITY;
			yRotation += yDiff;
			
			float xDiff = (currentPos.y - oldMousePos.y) * 0.5f;
			xRotation += xDiff;
			if (xRotation > MAX_ROTATE_UP) {
				xRotation = MAX_ROTATE_UP;
			} else if (xRotation < MAX_ROTATE_DOWN) {
				xRotation = MAX_ROTATE_DOWN;
			}
			
			camera.rotate(xRotation, yRotation);
			world.requestRender();
			
			oldMousePos = currentPos;
		}
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		
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
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			moveCamera = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			moveCamera = false;
			oldMousePos = null;
		}
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
			case KeyEvent.VK_D:
				right = true;
				break;
			case KeyEvent.VK_A:
				left = true;
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
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_A:
				left = false;
				break;
		}	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void update(float deltaTime) {
		if (forward) {
			Vector3f direction = getDirection(camera.getEye(), camera.getCenter());
			direction.normalize();
			direction = multiply(direction, deltaTime * MOVE_SENSIBILITY);
			moveCameraToDirection(direction);
		}
		if (backward) {
			Vector3f direction = getDirection(camera.getCenter(), camera.getEye());
			direction.normalize();
			direction = multiply(direction, deltaTime * MOVE_SENSIBILITY);
			moveCameraToDirection(direction);
		}
		if (right) {
			Vector3f direction = getDirection(camera.getEye(), camera.getCenter());
			direction.normalize();
			direction = multiply(direction, deltaTime * MOVE_SENSIBILITY);
			direction = new Vector3f(-direction.z, direction.y, direction.x);
			moveCameraToDirection(direction);
		}
		if (left) {
			Vector3f direction = getDirection(camera.getEye(), camera.getCenter());
			direction.normalize();
			direction = multiply(direction, deltaTime * MOVE_SENSIBILITY);
			direction = new Vector3f(direction.z, direction.y, -direction.x);
			moveCameraToDirection(direction);
		}

	}
	
	private void moveCameraToDirection(Vector3f direction) {
		camera.translate(direction);
		world.requestRender();
	}
	
	private Vector3f multiply(Vector3f a, float b) {
		return new Vector3f(a.x * b, a.y * b, a.z * b);
	}
	
	private Vector3f getDirection(Vector3f from, Vector3f to) {
		return new Vector3f(to.x - from.x, to.y - from.y, to.z - from.z);
	}

}
