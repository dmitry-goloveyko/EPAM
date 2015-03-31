package com.epam.wt.command;

import com.epam.wt.command.CommandSourceRecordNotFoundException;
import com.epam.wt.command.CommandSourceNotFoundException;
import com.epam.wt.command.CommandIOException;
import com.epam.wt.datatempstorage.Request;
import com.epam.wt.datatempstorage.Responce;

public interface Command {
	Responce execute(Request request) throws CommandIOException,
			CommandSourceNotFoundException,
			CommandSourceRecordNotFoundException,
			CommandDOMParserConfigurationException, CommandSQLException,
			CommandClassNotFoundException;
}
