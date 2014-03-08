package org.black_mesa.webots_remote_control.remote_object;

import java.io.Serializable;

public class RemoteCameraState implements Serializable {
	private static final long serialVersionUID = 8377512273594945634L;
	private final int id;
	private double[] t;
	private double[] r;

	public RemoteCameraState(int id, double[] translation, double[] rotation) {
		this.id = id;
		t = translation.clone();
		r = rotation.clone();
	}

	public int getId() {
		return id;
	}

	public RemoteCameraState clone() {
		return new RemoteCameraState(id, t, r);
	}

	public double[] getTranslation() {
		return t;
	}

	public double[] getRotation() {
		return r;
	}

	@Override
	public String toString() {
		return "(" + t[0] + "," + t[1] + "," + t[2] + ") ; (" + r[0] + "," + r[1] + "," + r[2] + "," + r[3] + ")";
	}

}
