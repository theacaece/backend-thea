package edu.caece.app.service;

import edu.caece.app.domain.EventType;
import edu.caece.app.domain.Person;

public interface SecurityService {
	public void logAccess(Person person);
	public void logAccessThresholdNotMet(Person person);
	
	public void log(String message, EventType eventType);
	public void log(String module, String message, EventType eventType);
	void log(String module, String message, Person person, EventType eventType);
}
