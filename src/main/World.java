package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import main.controller.Updatable;
import main.opengl.Drawable;
import main.physics.BulletMain;
import main.utils.GLDebugDraw;
import main.view.Render;

import com.bulletphysics.ContactAddedCallback;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.TypedConstraint;
import com.sun.opengl.util.GLUT;

/**
 * Mundo que agrupa objetos gráficos.
 */
public class World implements Drawable, Updatable {

	private final List<WorldObject> objects = new LinkedList<>();
	private final BulletMain mainPhysics = new BulletMain();
	private final Render render;
	private boolean needRender = false;
	public Object lockList = new Object(); // Pequena gambizinha botar isso em public pro worldcontroller locar ao fazer raytest, mas depois tem q ser arrumado

	public World(Render render) {
		this.render = render;
		this.render.addDrawable(this);
		requestRender();
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

			if (worldObject.getRigidBody() != null) {
				mainPhysics.getWorld().addRigidBody(worldObject.getRigidBody());
			}
		}
	}

	/**
	 * Remove um objeto gráfico do mundo.
	 * 
	 * @param worldObject
	 *            objeto a ser removido.
	 */
	public void remove(WorldObject worldObject) {
		synchronized (lockList) {
			objects.remove(worldObject);
		}
	}

	public DynamicsWorld getPhysicWorld() {
		return mainPhysics.getWorld();
	}
	
	public void requestRender() {
		this.needRender = true;
	}
	
	@Override
	public void initDraw(GLAutoDrawable drawable, GL gl) {
		mainPhysics.getWorld().setDebugDrawer(new GLDebugDraw(gl));
	}
	
	@Override
	public void draw(GL gl, final GLU glu, GLUT glut) {
		synchronized (lockList) {
			objects.forEach(o -> o.draw(gl, glu, glut));
		}
		mainPhysics.getWorld().debugDrawWorld();
	}

	@Override
	public void update(float deltaTime) {
		if (needRender) {
			needRender = false;
			render();
		}
		synchronized (lockList) {
			mainPhysics.getWorld().stepSimulation(deltaTime);
			objects.forEach(o -> o.update(deltaTime));
		}
	}
	
	private void render() {
		render.render();
	}

	public void removeConstraint(TypedConstraint constraint) {
		synchronized (lockList) {
			mainPhysics.getWorld().removeConstraint(constraint);
			constraint.getRigidBodyA().activate();
			constraint.getRigidBodyB().activate();
		}
	}

	public void addConstraint(TypedConstraint constraint) {
		synchronized (lockList) {
			mainPhysics.getWorld().addConstraint(constraint);
			constraint.getRigidBodyA().activate();
			constraint.getRigidBodyB().activate();
		}
	}

}
