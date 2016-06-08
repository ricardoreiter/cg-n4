package main;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import main.controller.Updatable;
import main.opengl.Drawable;
import main.opengl.GraphicObject;
import main.physics.MotionProvider;
import main.physics.PhysicsUpdatable;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.sun.opengl.util.GLUT;

public abstract class WorldObject implements Drawable, Updatable, PhysicsUpdatable {

	protected final GraphicObject graphicObject;
	protected final CollisionShape collisionShape;
	private final RigidBody rigidBody;
	private final MotionState motionState;
	private World world;
	
	public WorldObject(GraphicObject graphicObject, CollisionShape collisionShape) {
		super();
		this.graphicObject = graphicObject;
		this.collisionShape = collisionShape;
		this.motionState = new MotionProvider(this);
		
		RigidBodyConstructionInfo rigidBodyInfo = new RigidBodyConstructionInfo(0, this.motionState, this.collisionShape, new Vector3f());
		this.rigidBody = new RigidBody(rigidBodyInfo);
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	public RigidBody getRigidBody() {
		return this.rigidBody;
	}
	
	@Override
	public void draw(GL gl, GLUT glut) {
		graphicObject.draw(gl, glut);
	}
	
	@Override
	public void updatePhysics(Transform updated) {
		graphicObject.translate(updated.origin.x, updated.origin.y, updated.origin.z);
		world.requestRender();
	}
	
}
