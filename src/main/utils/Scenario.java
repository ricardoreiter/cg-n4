package main.utils;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Vector3f;

import main.Box;
import main.Plane;
import main.WorldObject;

public abstract class Scenario {

	public static List<WorldObject> getGameScenario() {
		List<WorldObject> objects = new LinkedList<>();
		
		//Paredes
		objects.add(new Box(new Vector3f(200, 10, 1), new float[]{1, 1, 0}, 0, new Vector3f(0, 4, 100)));
		objects.add(new Box(new Vector3f(200, 10, 1), new float[]{1, 1, 0}, 0, new Vector3f(0, 4, -100)));
		objects.add(new Box(new Vector3f(1, 10, 200), new float[]{1, 1, 0}, 0, new Vector3f(100, 4, 0)));
		objects.add(new Box(new Vector3f(1, 10, 200), new float[]{1, 1, 0}, 0, new Vector3f(-100, 4, 0)));
		
		//Alvo
		objects.add(new Box(new Vector3f(10, 5, 1), new float[]{1, 0.5f, 0}, 0, new Vector3f(0, 0, 5)));
		objects.add(new Box(new Vector3f(10, 5, 1), new float[]{1, 0.5f, 0}, 0, new Vector3f(0, 0, -5)));
		objects.add(new Box(new Vector3f(1, 5, 10), new float[]{1, 0.5f, 0}, 0, new Vector3f(5, 0, 0)));
		objects.add(new Box(new Vector3f(1, 5, 10), new float[]{1, 0.5f, 0}, 0, new Vector3f(-5, 0, 0)));
		
		//chão
		Plane plane = new Plane(100, new float[] {0, 0.8f, 1, 1}, new Vector3f(0, -1, 0));
		objects.add(plane);
		
		return objects;
	}
	
}
