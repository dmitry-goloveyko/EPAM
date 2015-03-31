package com.epam.wt.command;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	
	public CommandHelper() {
		commands.put(CommandName.ADD, new AddCommand());
		commands.put(CommandName.FIND, new FindCommand());
		commands.put(CommandName.DELETE, new DeleteCommand());
		commands.put(CommandName.CLEAR, new ClearCommand());
	}
	
	public Command getCommand(CommandName commandName) {
		Command command = commands.get(commandName);
		
		return command;
	}
}
