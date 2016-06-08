package main.physic;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

public class BulletMain {

	private final static float WORLD_GRAVITY = -9.81f;
	
	private final BroadphaseInterface broadphase = new DbvtBroadphase();
	private final DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
	private final CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
	
	private final SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
	private final DiscreteDynamicsWorld dynamicsWorld;
	
	public BulletMain() {
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3f(0, WORLD_GRAVITY, 0));
	}

	public DiscreteDynamicsWorld getWorld() {
		return dynamicsWorld;
	}
	
}
