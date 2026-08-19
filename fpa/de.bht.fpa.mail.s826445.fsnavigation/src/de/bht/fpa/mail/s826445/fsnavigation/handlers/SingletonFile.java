package de.bht.fpa.mail.s826445.fsnavigation.handlers;

import java.util.Observable;

public final class SingletonFile extends Observable {

	private String path;
	private static SingletonFile instance = new SingletonFile();

	public static SingletonFile getInstance() {
		return instance;
	}

	public void setpath(String path) {
		this.path = path;
		setChanged();

		notifyObservers(path);
	}

	public String getpath() {
		return path;
	}
}