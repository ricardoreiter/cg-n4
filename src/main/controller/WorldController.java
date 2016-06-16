package main.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.glu.GLU;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import main.Box;
import main.Line;
import main.World;
import main.WorldObject;
import main.physics.Ray;
import main.view.Render;

import com.bulletphysics.collision.dispatch.CollisionWorld.ClosestRayResultCallback;
import com.bulletphysics.linearmath.Transform;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener, Updatable {

	private static final float OBJECT_TO_ADD_HEIGHT = 10f;
	private final World world;
	private final Render render;
	private Point currentMousePosOnScreen;
	private WorldObject objectToBeAdded;
	private Line lineAux;

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
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Box box = new Box(4, new float[]{1, 0, 0, 1}, 10, new Vector3f(0, 25, 0));
		world.add(box);
		world.requestRender();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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

	@Override
	public void keyPressed(KeyEvent e) {
		world.requestRender();
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	private Ray mousePosToRay(Point mousePos) {
		IntBuffer viewport = render.getViewport();
		DoubleBuffer modelMatrix = render.getModelMatrix();
		DoubleBuffer projectionMatrix = render.getProjectionMatrix();
		
		float winY = (float) (viewport.get(3) - mousePos.getY());
		
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
		if (currentMousePosOnScreen != null) {
			Ray ray = mousePosToRay(currentMousePosOnScreen);
			
			ClosestRayResultCallback rayResult = null;
			synchronized (world.lockList) {
				rayResult = new ClosestRayResultCallback(ray.getOrigin(), ray.getDirection());
				world.getPhysicWorld().rayTest(ray.getOrigin(), ray.getDirection(), rayResult);
			}
			
			if (rayResult.hasHit()) {
				if (objectToBeAdded == null) {
					objectToBeAdded = new Box(3, new float[]{0.0f, 0.0f, 1.0f}, new Vector3f());
					lineAux = new Line(OBJECT_TO_ADD_HEIGHT, new float[] {1f, 0.0f, 1f}, new Vector3f());
					world.add(objectToBeAdded);
					world.add(lineAux);
				}
				// Movimenta a linha
				Matrix4f matrix = new Matrix4f(new Quat4f(0, 0, 0, 1), rayResult.hitPointWorld, 1);
				Transform newTrans = new Transform(matrix);
				lineAux.getMotionState().setWorldTransform(newTrans);
				
				// Movimenta a caixa
				rayResult.hitPointWorld.y += OBJECT_TO_ADD_HEIGHT;
				matrix = new Matrix4f(new Quat4f(0, 0, 0, 1), rayResult.hitPointWorld, 1);
				newTrans = new Transform(matrix);
				objectToBeAdded.getMotionState().setWorldTransform(newTrans);
			} else if (objectToBeAdded != null) {
				world.remove(objectToBeAdded);
				world.remove(lineAux);
				objectToBeAdded = null;
				lineAux = null;
			}
		}
	}
	
}
