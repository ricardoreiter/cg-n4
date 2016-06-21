package main.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionWorld.ClosestRayResultCallback;

import main.Box;
import main.Line;
import main.Ramp;
import main.Sphere;
import main.World;
import main.WorldObject;
import main.physics.Ray;
import main.utils.TransformUtils;
import main.view.Render;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Updatable {

	private static final float GHOST_OBJECT_HEIGHT = 10f;
	private static final float GHOST_OBJECT_MAX_HEIGHT = 30f;
	
	private static final float GHOST_OBJECT_DEFAULT_SIZE = 3f;
	private static final float GHOST_OBJECT_MAX_SIZE = 30f;
	private static final float GHOST_OBJECT_MIN_SIZE = 1f;
	
	private final World world;
	private final Render render;
	private Point currentMousePosOnScreen;
	
	private WorldObject ghostObject;
	private Line ghostObjectLine;
	private Vector3f ghostObjectPos = new Vector3f();
	private float ghostObjectHeight = GHOST_OBJECT_HEIGHT;
	private float ghostObjectSize = GHOST_OBJECT_DEFAULT_SIZE;
	private boolean needUpdateGhostObject = false;
	private GhostObjectWheelAction currentMouseWheelAction = GhostObjectWheelAction.CHANGE_HEIGHT;
	private GhostObjectType currentObjectType = GhostObjectType.BOX;

	public WorldController(final World world, final Render render) {
		this.world = world;
		this.render = render;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentMousePosOnScreen = e.getPoint();
		needUpdateGhostObject = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			WorldObject object = factoryObject(currentObjectType, new float[]{1, 0, 0, 1}, true, 10, ghostObjectPos);
			world.add(object);
			world.requestRender();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	private void setCurrentObjectType(GhostObjectType type) {
		System.out.println("lol");
		currentObjectType = type;
		
		if (ghostObject != null) {
			world.remove(ghostObject);
		}
		
		ghostObject = factoryObject(type, new float[]{0.0f, 0.0f, 1.0f}, false, 0, new Vector3f());
		world.add(ghostObject);
	}
	
	private WorldObject factoryObject(GhostObjectType type, float[] color, boolean withPhysics, float mass, Vector3f pos) {
		switch (type) {
			case BOX:
				if (withPhysics) {
					return new Box(ghostObjectSize, color, mass, pos);
				} else {
					return new Box(ghostObjectSize, color, pos);
				}
			case SPHERE:
				if (withPhysics) {
					return new Sphere(ghostObjectSize, color, mass, pos);
				} else {
					return new Sphere(ghostObjectSize, color, pos);
				}
			case RAMP:
				if (withPhysics) {
					return new Ramp(ghostObjectSize, color, mass, pos);
				} else {
					return new Ramp(ghostObjectSize, color, pos);
				}
		default:
			return null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				currentMouseWheelAction = GhostObjectWheelAction.CHANGE_SIZE;
				break;
			case KeyEvent.VK_CONTROL:
				currentMouseWheelAction = GhostObjectWheelAction.CHANGE_ROTATION;
				break;
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
			case KeyEvent.VK_CONTROL:
				currentMouseWheelAction = GhostObjectWheelAction.CHANGE_HEIGHT;
				break;
			case KeyEvent.VK_1:
				setCurrentObjectType(GhostObjectType.BOX);
				break;
			case KeyEvent.VK_2:
				setCurrentObjectType(GhostObjectType.SPHERE);
				break;
			case KeyEvent.VK_3:
				setCurrentObjectType(GhostObjectType.RAMP);
				break;
		}
	}

	private Ray mousePosToRay(Point mousePos) {
		IntBuffer viewport = render.getViewport();
		DoubleBuffer modelMatrix = render.getModelMatrix();
		DoubleBuffer projectionMatrix = render.getProjectionMatrix();
		
		double winY = viewport.get(3) - mousePos.getY();
		
		GLU glu = render.getGlu();
		
		DoubleBuffer coordBuffer = DoubleBuffer.allocate(3);
		glu.gluUnProject(mousePos.getX(), winY, 0.0f, //
						 modelMatrix, projectionMatrix, viewport, //
						 coordBuffer);
		Vector3f origin = new Vector3f((float) coordBuffer.get(0), (float) coordBuffer.get(1), (float) coordBuffer.get(2));
		
		glu.gluUnProject(mousePos.getX(), winY, 1.0f, //
						 modelMatrix, projectionMatrix, viewport, //
						 coordBuffer);
		Vector3f direction = new Vector3f((float) (coordBuffer.get(0)), (float) (coordBuffer.get(1)), (float) (coordBuffer.get(2)));
		
		return new Ray(origin, direction);
	}

	@Override
	public void update(float deltaTime) {
		updateGhostObject();
	}
	
	private void updateGhostObject() {
		if (needUpdateGhostObject && currentMousePosOnScreen != null && render.getGlu() != null) {
			Ray ray = mousePosToRay(currentMousePosOnScreen);
			
			ClosestRayResultCallback rayResult = null;
			synchronized (world.lockList) {
				rayResult = new ClosestRayResultCallback(ray.getOrigin(), ray.getDirection());
				world.getPhysicWorld().rayTest(ray.getOrigin(), ray.getDirection(), rayResult);
			}
			
			if (rayResult.hasHit()) {
				if (ghostObject == null) {
					setCurrentObjectType(GhostObjectType.BOX);
					ghostObjectLine = new Line(ghostObjectHeight, new float[] {1f, 0.0f, 1f}, new Vector3f());
					world.add(ghostObjectLine);
				}
				// Movimenta a linha
				ghostObjectLine.setSize(ghostObjectHeight);
				ghostObjectLine.getMotionState().setWorldTransform(TransformUtils.getTranslationTransform(rayResult.hitPointWorld));
				
				// Movimenta a caixa
				ghostObject.setSize(ghostObjectSize);
				rayResult.hitPointWorld.y += ghostObjectHeight;
				ghostObject.getMotionState().setWorldTransform(TransformUtils.getTranslationTransform(rayResult.hitPointWorld));
				ghostObjectPos = new Vector3f(rayResult.hitPointWorld);
			} else if (ghostObject != null) {
				world.remove(ghostObject);
				world.remove(ghostObjectLine);
				ghostObject = null;
				ghostObjectLine = null;
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		switch (currentMouseWheelAction) {
			case CHANGE_HEIGHT:
				changeGhostHeight(e);
				break;
			case CHANGE_ROTATION:
				// TODO: Fazer rotação em eixo Y
				break;
			case CHANGE_SIZE:
				changeGhostSize(e);
				break;
		}
		needUpdateGhostObject = true;
	}

	private void changeGhostSize(MouseWheelEvent e) {
		ghostObjectSize += e.getWheelRotation();
		ghostObjectSize = clampRange(ghostObjectSize, GHOST_OBJECT_MIN_SIZE, GHOST_OBJECT_MAX_SIZE);
	}

	private void changeGhostHeight(MouseWheelEvent e) {
		ghostObjectHeight += e.getWheelRotation();
		ghostObjectHeight = clampRange(ghostObjectHeight, 0, GHOST_OBJECT_MAX_HEIGHT);
	}
	
	private float clampRange(float currentValue, float minValue, float maxValue) {
		if (currentValue > maxValue) {
			return maxValue;
		} else if (currentValue < minValue) {
			return minValue;
		}
		return currentValue;
	}
	
}
