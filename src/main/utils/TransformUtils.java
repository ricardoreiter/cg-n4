package main.utils;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.Transform;

public class TransformUtils {

	public static Transform getTranslationTransform(Vector3f translation) {
		Matrix4f matrix = new Matrix4f(new Quat4f(0, 0, 0, 1), translation, 1);
		return new Transform(matrix);
	}
	
}
