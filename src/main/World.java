package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.sun.opengl.util.GLUT;

import main.opengl.Camera;
import main.opengl.Drawable;
import main.opengl.GraphicObject;
import main.physic.BulletMain;

/**
 * Mundo que agrupa objetos gráficos.
 */
public class World implements Drawable {

	private final Camera camera = new Camera();
	private final List<WorldObject> objects = new LinkedList<>();
	private final BulletMain mainPhysics = new BulletMain();

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
		objects.add(worldObject);
		mainPhysics.getWorld().addRigidBody(worldObject.getRigidBody());
	}

	/**
	 * Remove um objeto gráfico do mundo.
	 * 
	 * @param graphicObject
	 *            objeto a ser removido.
	 */
	public void remove(GraphicObject graphicObject) {
		objects.remove(graphicObject);
	}

	public DynamicsWorld getPhysicWorld() {
		return mainPhysics.getWorld();
	}
	
	@Override
	public void draw(GL gl, GLUT glut) {
		objects.forEach(o -> o.draw(gl, glut));
	}

}
