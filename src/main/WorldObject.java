package main;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.sun.opengl.util.GLUT;

import main.opengl.Drawable;
import main.opengl.GraphicObject;

public abstract class WorldObject implements Drawable {

	protected final GraphicObject graphicObject;
	protected final CollisionShape collisionShape;
	private final RigidBody rigidBody;
	private final MotionState motionState;
	
	public WorldObject(GraphicObject graphicObject, CollisionShape collisionShape) {
		super();
		this.graphicObject = graphicObject;
		this.collisionShape = collisionShape;
		this.motionState = new DefaultMotionState();
		
		RigidBodyConstructionInfo rigidBodyInfo = new RigidBodyConstructionInfo(0, this.motionState, this.collisionShape, new Vector3f());
		this.rigidBody = new RigidBody(rigidBodyInfo);
	}

	public RigidBody getRigidBody() {
		return this.rigidBody;
	}
	
	@Override
	public void draw(GL gl, GLUT glut) {
		graphicObject.draw(gl, glut);
	}
	
	
}
