package main.utils;

import javax.vecmath.Vector3f;

import com.bulletphysics.dynamics.constraintsolver.Point2PointConstraint;

import main.Box;
import main.Plane;
import main.Sphere;
import main.World;
import main.WorldObject;

public abstract class Scenario {

	public static WorldObject boxCover;
	
	public static void mountGameScenario(World world) {
		
		//Paredes
		world.add(new Box(new Vector3f(200, 10, 1), new float[]{1, 1, 0}, 0, new Vector3f(0, 4, 100)));
		world.add(new Box(new Vector3f(200, 10, 1), new float[]{1, 1, 0}, 0, new Vector3f(0, 4, -100)));
		world.add(new Box(new Vector3f(1, 10, 200), new float[]{1, 1, 0}, 0, new Vector3f(100, 4, 0)));
		world.add(new Box(new Vector3f(1, 10, 200), new float[]{1, 1, 0}, 0, new Vector3f(-100, 4, 0)));
		
		//Alvo
		world.add(new Box(new Vector3f(10, 5, 1), new float[]{1, 0.5f, 0}, 0, new Vector3f(0, 0, 5)));
		world.add(new Box(new Vector3f(10, 5, 1), new float[]{1, 0.5f, 0}, 0, new Vector3f(0, 0, -5)));
		world.add(new Box(new Vector3f(1, 5, 10), new float[]{1, 0.5f, 0}, 0, new Vector3f(5, 0, 0)));
		world.add(new Box(new Vector3f(1, 5, 10), new float[]{1, 0.5f, 0}, 0, new Vector3f(-5, 0, 0)));
		
		//Caixa de Bolas
		world.add(new Box(new Vector3f(5, 25, 1), new float[]{1, 0.5f, 0.2f}, 0, new Vector3f(-22.5f, 50, 2.5f)));
		world.add(new Box(new Vector3f(5, 25, 1), new float[]{1, 0.5f, 0.2f}, 0, new Vector3f(-22.5f, 50, -2.5f)));
		
		Box boxWallB = new Box(new Vector3f(1, 25, 5), new float[]{1, 0.5f, 0.2f}, 0, new Vector3f(-25f, 50, 0));
		Box boxWallA = new Box(new Vector3f(1, 25, 5), new float[]{1, 0.5f, 0.2f}, 0, new Vector3f(-20f, 50, 0));
		world.add(boxWallB);
		world.add(boxWallA);
		
		//Tampa caixa de bolas
		boxCover = new Box(new Vector3f(5, 1, 5), new float[]{1, 0.2f, 1f}, 1, new Vector3f(-22.5f, 36.9f, 0));
		world.add(boxCover);
		
		Point2PointConstraint constaintA = new Point2PointConstraint(boxCover.getRigidBody(), boxWallA.getRigidBody(), new Vector3f(-2.5f, -0.5f, 2.5f), new Vector3f(0, -13f, 2.5f));
		Point2PointConstraint constaintB = new Point2PointConstraint(boxCover.getRigidBody(), boxWallA.getRigidBody(), new Vector3f(-2.5f, -0.5f, -2.5f), new Vector3f(0, -13f, -2.5f));
		Point2PointConstraint constaintC = new Point2PointConstraint(boxCover.getRigidBody(), boxWallB.getRigidBody(), new Vector3f(2.5f, -0.5f, 2.5f), new Vector3f(0, -13f, 2.5f));
		Point2PointConstraint constaintD = new Point2PointConstraint(boxCover.getRigidBody(), boxWallB.getRigidBody(), new Vector3f(2.5f, -0.5f, -2.5f), new Vector3f(0, -13f, -2.5f));
		
		world.getPhysicWorld().addConstraint(constaintA);
//		world.getPhysicWorld().addConstraint(constaintB);
//		world.getPhysicWorld().addConstraint(constaintC);
//		world.getPhysicWorld().addConstraint(constaintD);
		
		//Bolas
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 50, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 54, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 58, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 62, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 66, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 70, 0)));
		world.add(new Sphere(2, new float[]{1, 0, 0.8f}, 50, new Vector3f(-22.5f, 74, 0)));
		
		//chão
		Plane plane = new Plane(100, new float[] {0, 0.8f, 1, 1}, new Vector3f(0, -1, 0));
		world.add(plane);
	}
	
}
