package org.black_mesa.webots_remote_control.remote_object;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class InstructionQueue implements Serializable {
	private static final long serialVersionUID = 228351533118850327L;
	private int id;
	private Queue<Instruction> queue = new LinkedList<>();
	
	public InstructionQueue(int id) {
		this.id = id;
	}

	public Object execute(Object initialState) {
		while(!queue.isEmpty()) {
			initialState = queue.poll().execute(initialState);
		}
		return initialState;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		String ret = "Queue of size " + queue.size();
		while(!queue.isEmpty()) {
			ret += "\n" + queue.poll().toString();
		}
		return ret;
	}
}