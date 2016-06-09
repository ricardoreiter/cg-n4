package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import main.controller.Updatable;
import main.opengl.Camera;
import main.opengl.Drawable;
import main.opengl.GraphicObject;
import main.physics.BulletMain;
import main.view.Render;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.sun.opengl.util.GLUT;

/**
 * Mundo que agrupa objetos gráficos.
 */
public class World implements Drawable, Updatable {

	private final Camera camera = new Camera();
	private final List<WorldObject> objects = new LinkedList<>();
	private final BulletMain mainPhysics = new BulletMain();
	private final Render render;
	private boolean needRender = false;
	private Object lockList = new Object();

	public World(Render render) {
		this.render = render;
		Box ground = new Box(10, new float[] {0.5f, 0.5f, 0.0f, 1}, 0, new Vector3f(0, -10, 0));
		add(ground);
		requestRender();
	}
	
	/**
	 * Obtém a camera associada ao mundo.
	 * 
	 * @return {@link Camera} do mundo.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Adiciona um novo objeto gráfico ao mundo.
	 * 
	 * @param worldObject
	 *            Objeto a ser adicionado.
	 */
	public void add(WorldObject worldObject) {
		synchronized (lockList) {
			worldObject.setWorld(this);
			objects.add(worldObject);
			mainPhysics.getWorld().addRigidBody(worldObject.getRigidBody());
		}
	}

	/**
	 * Remove um objeto gráfico do mundo.
	 * 
	 * @param graphicObject
	 *            objeto a ser removido.
	 */
	public void remove(GraphicObject graphicObject) {
		synchronized (lockList) {
			objects.remove(graphicObject);
		}
	}

	public DynamicsWorld getPhysicWorld() {
		return mainPhysics.getWorld();
	}
	
	public void requestRender() {
		this.needRender = true;
	}
	
	@Override
	public void draw(GL gl, GLUT glut) {
		synchronized (lockList) {
			objects.forEach(o -> o.draw(gl, glut));
		}
	}

	@Override
	public void update(float deltaTime) {
		if (needRender) {
			needRender = false;
			render();
		}
		mainPhysics.getWorld().stepSimulation(deltaTime);
		synchronized (lockList) {
			objects.forEach(o -> o.update(deltaTime));
		}
	}
	
	private void render() {
		final Camera camera = getCamera();
		final float[] axis = camera.axisSizes();
		render.addDrawable(this);
		render.setAxisSizes(axis);
		render.render();
	}

}
