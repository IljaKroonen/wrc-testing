package org.black_mesa.webots_remote_control.communication_structures;

import java.io.Serializable;

/**
 * Represents a CommunicationStructure. This can for example be a camera or a
 * robot. The implementation can, for example, hold the state of the object, or
 * be a simple instruction queue.
 * 
 * @author Ilja Kroonen
 * 
 */
public abstract class CommunicationStructure implements Serializable {
	private static final long serialVersionUID = -6066263437899074202L;
	private final int mId;

	/**
	 * Instanciates the CommunicationStructure with a given identifier.
	 * 
	 * @param id
	 *            The identifier of this CommunicationStructure. This identifier
	 *            has to be unique for a server.
	 */
	protected CommunicationStructure(final int id) {
		mId = id;
	}

	/**
	 * Identifies the CommunicationStructure in a unique way. Normally, this id
	 * is given by the server at connection, and never changes.
	 * 
	 * @return Id of this CommunicationStructure.
	 */
	public final int getId() {
		return mId;
	}

	/**
	 * Tests if the structure contains valid data.
	 * 
	 * @return True if the structure is valid, false if not.
	 */
	public abstract boolean checkIntegrity();
}