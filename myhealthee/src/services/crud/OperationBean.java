package services.crud;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Operation bean to check if an operation is one of the crud operations.
 * 
 * @author adlo
 */
@Named("operation")
@RequestScoped
public class OperationBean implements Serializable {
	private static final long serialVersionUID = -2882047661591442611L;

	/* Fields */
	private Operation create = Operation.CREATE;
	private Operation read = Operation.READ;
	private Operation update = Operation.UPDATE;
	private Operation delete = Operation.DELETE;

	// ACTIONS
	/**
	 * @return true if the operation is a CRUD operation.
	 */
	public boolean hasOperation(Operation op) {
		return op != Operation.NO_OPERATION;
	}

	/**
	 * @return true if the operation is a create operation, false otherwise.
	 */
	public boolean isCreate(Operation op) {
		return op == Operation.CREATE;
	}

	/**
	 * @return true if the operation is a read operation, false otherwise.
	 */
	public boolean isRead(Operation op) {
		return op == Operation.READ;
	}

	/**
	 * @return true if the operation is a update operation, false otherwise.
	 */
	public boolean isUpdate(Operation op) {
		return op == Operation.UPDATE;
	}

	/**
	 * @return true if the operation is a delete operation, false otherwise.
	 */
	public boolean isDelete(Operation op) {
		return op == Operation.DELETE;
	}

	/**
	 * Method used to disable input text, for example in a read or update view
	 * disable the id field.
	 * 
	 * @return true if the operation should be disabled, false otherwise.
	 */
	public boolean isDisabled(Operation op) {
		return op == Operation.READ || op == Operation.UPDATE;
	}

	// Getters
	public Operation getCreate() {
		return create;
	}

	public Operation getRead() {
		return read;
	}

	public Operation getUpdate() {
		return update;
	}

	public Operation getDelete() {
		return delete;
	}
}
