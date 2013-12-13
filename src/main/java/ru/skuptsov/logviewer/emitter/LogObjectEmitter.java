package ru.skuptsov.logviewer.emitter;

public interface LogObjectEmitter<T> {
	
	void sendObject(T object);

}
