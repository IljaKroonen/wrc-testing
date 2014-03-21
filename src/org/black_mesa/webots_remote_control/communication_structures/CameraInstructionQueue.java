package org.black_mesa.webots_remote_control.communication_structures;

import java.util.LinkedList;
import java.util.Queue;

public class CameraInstructionQueue extends CommunicationStructure {
	private static final long serialVersionUID = 228351533118850327L;
	private Queue<CameraInstruction> mQueue = new LinkedList<>();
	
	public CameraInstructionQueue(int id) {
		super(id);
	}

	public ViewPointState execute(ViewPointState initialState) {
		while(!mQueue.isEmpty()) {
			initialState = mQueue.poll().execute(initialState);
		}
		return initialState;
	}
	
	private void move(CameraInstructionQueue queue) {
		while (!queue.mQueue.isEmpty()) {
			this.mQueue.add(queue.mQueue.poll());
		}
	}
	
	public CameraInstructionQueue board(CameraInstructionQueue previous) {
		if (previous == null) {
			previous = new CameraInstructionQueue(getId());
		}
		previous.move(this);
		return previous;
	}

	@Override
	public boolean checkIntegrity() {
		return true;
	}
	
	@Override
	public String toString() {
		Queue<CameraInstruction> queueCopy = new LinkedList<CameraInstruction>(mQueue);
		String ret = "Queue of size " + mQueue.size();
		while(!queueCopy.isEmpty()) {
			ret += "\n" + queueCopy.poll().toString();
		}
		return ret;
	}
}