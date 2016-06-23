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
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionWorld.ClosestRayResultCallback;

import main.Box;
import main.Line;
import main.Sphere;
import main.World;
import main.WorldObject;
import main.opengl.utils.ColorUtils;
import main.physics.Ray;
import main.utils.Scenario;
import main.utils.TransformUtils;
import main.view.Render;
import main.view.WorldControllerUI;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Updatable {

	private static final float GHOST_OBJECT_HEIGHT = 10f;
	private static final float GHOST_OBJECT_MAX_HEIGHT = 30f;

	private static final float GHOST_OBJECT_DEFAULT_SIZE = 3f;
	private static final float GHOST_OBJECT_MAX_SIZE = 30f;
	private static final float GHOST_OBJECT_MIN_SIZE = 1f;

	private static final float GHOST_OBJECT_DEFAULT_MASS = 10;
	private static final float GHOST_OBJECT_MAX_MASS = 100000;
	private static final float GHOST_OBJECT_MIN_MASS = 0;
	private static final float GHOST_OBJECT_MASS_STEP = 10;
	
	private static final float TIME_KEPPING_MOUSE_TO_ADD = 0.5f;
	private static final float TIME_INTERVAL_TO_ADD = 0.01f;
	
	private static final float TIME_TO_ADD_MASS_SENSITIVITY = 10f;

	private final World world;
	private final Render render;
	private final WorldControllerUI controllerUI;
	private Point currentMousePosOnScreen;

	private int colorIndex = 0;
	private WorldObject ghostObject;
	private Line ghostObjectLine;
	private Vector3f ghostObjectPos = new Vector3f();
	private float ghostObjectHeight = GHOST_OBJECT_HEIGHT;
	private Vector3f ghostObjectSize = new Vector3f(GHOST_OBJECT_DEFAULT_SIZE, GHOST_OBJECT_DEFAULT_SIZE,
			GHOST_OBJECT_DEFAULT_SIZE);
	private float newObjectMass = GHOST_OBJECT_DEFAULT_MASS;
	private boolean needUpdateGhostObject = false;
	private GhostObjectWheelAction currentMouseWheelAction = GhostObjectWheelAction.CHANGE_HEIGHT;
	private GhostObjectType currentObjectType = GhostObjectType.BOX;
	
	// Variáveis relacionadas ao clique de adicionar vários objetos
	private boolean mouseDown = false;
	private boolean addMultipleObjects = false;
	private double timeCounter = 0;
	
	// Variáveis relacionadas ao adicionar massa
	private byte addMass = 0;
	private double timeAddingMass = 0;

	public WorldController(final World world, final Render render) {
		this.world = world;
		this.render = render;
		this.controllerUI = new WorldControllerUI();
		this.render.addDrawable(this.controllerUI);

		Scenario.mountGameScenario(world);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currentMousePosOnScreen = e.getPoint();
		needUpdateGhostObject = true;
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
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseDown = true;
			timeCounter = TIME_KEPPING_MOUSE_TO_ADD;
			addMultipleObjects = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			addMultipleObjects = false;
			mouseDown = false;
			addNewObject();
		}
	}

	private void addNewObject() {
		WorldObject object = factoryObject(currentObjectType, ColorUtils.colors[colorIndex], true, newObjectMass,
				ghostObjectPos);
		world.add(object);
		world.requestRender();
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
		currentObjectType = type;

		if (ghostObject != null) {
			world.remove(ghostObject);
		}

		ghostObject = factoryObject(type, ColorUtils.colors[colorIndex], false, 0, new Vector3f());
		world.add(ghostObject);
	}

	private WorldObject factoryObject(GhostObjectType type, float[] color, boolean withPhysics, float mass,
			Vector3f pos) {
		switch (type) {
		case BOX:
			if (withPhysics) {
				return new Box(ghostObjectSize, color, mass, pos);
			} else {
				return new Box(ghostObjectSize, color, pos);
			}
		case SPHERE:
			if (withPhysics) {
				return new Sphere(ghostObjectSize.x, color, mass, pos);
			} else {
				return new Sphere(ghostObjectSize.x, color, pos);
			}
		case RAMP:
			if (withPhysics) {
				return new Box(ghostObjectSize, color, mass, pos);
			} else {
				return new Box(ghostObjectSize, color, pos);
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
			case KeyEvent.VK_F1:
				if (addMass == 0) {
					addMass = 1;
					timeCounter = TIME_INTERVAL_TO_ADD;
					timeAddingMass = 0;
				}
				break;
			case KeyEvent.VK_F2:
				if (addMass == 0) {
					addMass = -1;
					timeCounter = TIME_INTERVAL_TO_ADD;
					timeAddingMass = 0;
				}
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
		case KeyEvent.VK_P:
			world.getPhysicWorld().stepSimulation(1);
			break;
		case KeyEvent.VK_O:
			world.removeConstraint(Scenario.boxCoverConstraintA);
			world.removeConstraint(Scenario.boxCoverConstraintB);
			break;
		case KeyEvent.VK_C:
			world.addConstraint(Scenario.boxCoverConstraintA);
			world.addConstraint(Scenario.boxCoverConstraintB);
			break;
		case KeyEvent.VK_F1:
		case KeyEvent.VK_F2:
			addMass = 0;
			break;
		case KeyEvent.VK_UP:
			if (colorIndex == ColorUtils.colors.length - 1) {
				colorIndex = 0;
			} else {
				colorIndex++;
			}
			ghostObject.changeColor(ColorUtils.colors[colorIndex]);
			break;
		case KeyEvent.VK_DOWN:
			if (colorIndex == 0) {
				colorIndex = ColorUtils.colors.length - 1;
			} else {
				colorIndex--;
			}
			ghostObject.changeColor(ColorUtils.colors[colorIndex]);
			break;
		}
	}

	private void addObjectMass(float mass) {
		newObjectMass = clampRange(newObjectMass + mass, GHOST_OBJECT_MIN_MASS, GHOST_OBJECT_MAX_MASS);
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
		Vector3f origin = new Vector3f((float) coordBuffer.get(0), (float) coordBuffer.get(1),
				(float) coordBuffer.get(2));

		glu.gluUnProject(mousePos.getX(), winY, 1.0f, //
				modelMatrix, projectionMatrix, viewport, //
				coordBuffer);
		Vector3f direction = new Vector3f((float) (coordBuffer.get(0)), (float) (coordBuffer.get(1)),
				(float) (coordBuffer.get(2)));

		return new Ray(origin, direction);
	}

	@Override
	public void update(float deltaTime) {
		updateGhostObject();
		updateMouseActions(deltaTime);
		updateMassButtonAction(deltaTime);
	}

	private void updateMassButtonAction(float deltaTime) {
		if (addMass != 0) {
			timeAddingMass -= (double) deltaTime;
			timeCounter -= deltaTime;
			if (timeCounter <= 0) {
				timeCounter = TIME_INTERVAL_TO_ADD;
				addObjectMass((float) ((GHOST_OBJECT_MASS_STEP * addMass) * (timeAddingMass * TIME_TO_ADD_MASS_SENSITIVITY)));
			}
		}
	}

	/**
	 * Faz o tratamento do clique e segura do mouse para adicionar vários objetos
	 * @param deltaTime
	 */
	private void updateMouseActions(float deltaTime) {
		if (mouseDown && !addMultipleObjects) {
			timeCounter -= deltaTime;
			if (timeCounter <= 0) {
				addMultipleObjects = true;
				timeCounter = TIME_INTERVAL_TO_ADD;
			}
		} else if (addMultipleObjects) {
			timeCounter -= deltaTime;
			if (timeCounter <= 0) {
				addNewObject();
				timeCounter = TIME_INTERVAL_TO_ADD;
			}
		}
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
					ghostObjectLine = new Line(ghostObjectHeight, new float[] { 1f, 0.0f, 1f }, new Vector3f());
					world.add(ghostObjectLine);
				}
				// Movimenta a linha
				if (ghostObjectLine != null) {
					ghostObjectLine.setSize(new Vector3f(ghostObjectHeight, ghostObjectHeight, ghostObjectHeight));
					ghostObjectLine.getMotionState()
							.setWorldTransform(TransformUtils.getTranslationTransform(rayResult.hitPointWorld));
				}

				// Movimenta a caixa
				ghostObject.setSize(ghostObjectSize);
				rayResult.hitPointWorld.y += ghostObjectHeight;
				ghostObject.getMotionState()
						.setWorldTransform(TransformUtils.getTranslationTransform(rayResult.hitPointWorld));
				ghostObjectPos = new Vector3f(rayResult.hitPointWorld);

				// Atualiza a UI com as informações do objeto
				controllerUI.setCurrentObjectMass(newObjectMass);
				controllerUI.setCurrentInfoPos(new Vector3d(ghostObjectPos.x + (ghostObjectSize.x / 2) + 2,
						ghostObjectPos.y, ghostObjectPos.z));
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
		float size = clampRange(ghostObjectSize.x + e.getWheelRotation(), GHOST_OBJECT_MIN_SIZE, GHOST_OBJECT_MAX_SIZE);
		ghostObjectSize = new Vector3f(size, size, size);
		if (currentObjectType == GhostObjectType.RAMP) {
			ghostObjectSize.setY(0.5f);
		}
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
