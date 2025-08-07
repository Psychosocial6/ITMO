package server.dataStorage;

import server.comands.Command;

public class CommandWrapper {
	 private final Command<?> command;
	 private final boolean requiresInput;
	 
	 public CommandWrapper(Command<?> command, boolean requiresInput) {
	    this.command = command;
	    this.requiresInput = requiresInput;
	 }

	 public Command<?> getCommand() {
		 return command;
	 }

	 public boolean requiresInput() {
	     return requiresInput;
	 }
}
