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
	public boolean hasOperation(Operation op) {
		return op != Operation.NO_OPERATION;
	}
	
	public boolean isCreate(Operation op) {
		return op == Operation.CREATE;
	}

	public boolean isRead(Operation op) {
		return op == Operation.READ;
	}

	public boolean isUpdate(Operation op) {
		return op == Operation.UPDATE;
	}

	public boolean isDelete(Operation op) {
		return op == Operation.DELETE;
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
