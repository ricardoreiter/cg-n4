package main.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.vecmath.Vector3f;

import main.Box;
import main.World;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener {

	private final World world;

	public WorldController(final World world) {
		this.world = world;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
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

}
