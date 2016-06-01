package main.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Camera;
import main.World;
import main.view.Render;

public class WorldController implements KeyListener, MouseListener, MouseMotionListener {

	private final World world;
	private final Render render;

	public WorldController(final World world, final Render render) {
		this.world = world;
		this.render = render;
	}

	private void render() {
		final Camera camera = world.getCamera();
		final float[] axis = camera.axisSizes();
		render.addDrawable(world);
		render.setAxisSizes(axis);
		render.render();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		updateCamera(e);
		render();
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	/**
	 * Altera zoom/panorama da camera.
	 * 
	 * @param e
	 *            Evento do teclado.
	 */
	private void updateCamera(final KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_I:
			zoom(50);
			break;
		case KeyEvent.VK_O:
			zoom(-50);
			break;
		case KeyEvent.VK_E:
			adjustPan(0, 50);
			break;
		case KeyEvent.VK_D:
			adjustPan(0, -50);
			break;
		case KeyEvent.VK_C:
			adjustPan(1, 50);
			break;
		case KeyEvent.VK_B:
			adjustPan(1, -50);
			break;
		}
	}

	/**
	 * Realiza zoom-in ou zoom-out na câmera.
	 * 
	 * @param zoom
	 *            quantidade de zoom, quanto negativo é feito zoom-out, quando é
	 *            positivo é feito zoom-in.
	 */
	private void zoom(final int offset) {
		world.getCamera().zoom(offset);
		render();
	}

	/**
	 * Desloca a camera entre os eixos.
	 * 
	 * @param axis
	 *            Eixo a ser deslocado, sendo X = 0 e Y = 1.
	 * @param offset
	 *            Valor a ser deslocado.
	 */
	private void adjustPan(final int axis, final int offset) {
		world.getCamera().pan(axis, offset);
		render();
	}
}
