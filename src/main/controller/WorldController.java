package main.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import main.Box;
import main.World;
import main.physics.Ray;
import main.view.Render;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener, Updatable {

	private final World world;
	private final Render render;
	private Point currentMousePosOnScreen;

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
		
		for (int i = 0; i < 16; i++) {
			System.out.println(modelMatrix.get(i));
		}
		
		DoubleBuffer coordBuffer = DoubleBuffer.allocate(3);
		glu.gluUnProject(mousePos.getX(), winY, 0.0f, //
						 modelMatrix, projectionMatrix, viewport, //
						 coordBuffer);
		Vector3d origin = new Vector3d(coordBuffer.get(0), coordBuffer.get(1), coordBuffer.get(2));
		
		glu.gluUnProject(mousePos.getX(), winY, 1.0f, //
						 modelMatrix, projectionMatrix, viewport, //
						 coordBuffer);
		Vector3d direction = new Vector3d(coordBuffer.get(0), coordBuffer.get(1), coordBuffer.get(2));
		
		return new Ray(origin, direction);
	}

	@Override
	public void update(float deltaTime) {
		if (currentMousePosOnScreen != null) {
			System.out.println(mousePosToRay(currentMousePosOnScreen));
		}
	}
	
}
