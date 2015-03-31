package com.epam.wt.view;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.epam.wt.command.CommandName;
import com.epam.wt.controller.Controller;
import com.epam.wt.datatempstorage.Responce;

public class View {
	private Controller controller = Controller.getInstance();

	public void Run() {
		int choice = -1;
		int index = 0;
		String text;
		DateTime alertDate = null;
		String color;
		Map<String, Object> params = new HashMap<String, Object>();
		Responce responce = null;
		
		while(choice != 0) {
			choice = ViewNotifier.showMenu();
			switch (choice) {
			case 1:
				text = ViewNotifier.enterNoteText(); 
				
				params.put("text", text);
				
				responce = controller.doRequest(CommandName.ADD, params);
				
				ViewNotifier.noteAddedNotification(responce);
				
				break;
				
			case 2:
				text = ViewNotifier.enterNoteText(); 
				
				alertDate = ViewNotifier.enterNoteAlertDate();
				
				params.put("text", text);
				params.put("alertDate", alertDate);
				
				responce = controller.doRequest(CommandName.ADD, params);
				
				ViewNotifier.noteAddedNotification(responce);
				
				break;
				
			case 3:
				text = ViewNotifier.enterNoteText(); 
				color = ViewNotifier.enterNoteColor();
				
				params.put("text", text);
				params.put("color", color);
				
				responce = controller.doRequest(CommandName.ADD, params);
				
				ViewNotifier.noteAddedNotification(responce);
				
				break;
				
			case 4:
				index = ViewNotifier.enterIndexOfNote();
				
				responce = controller.doRequest(CommandName.FIND, index);
				
				ViewNotifier.noteInfoNotification(responce);
				
				break;
				
			case 5:
				index = ViewNotifier.enterIndexOfNote();
				
				responce = controller.doRequest(CommandName.DELETE, index);
				
				ViewNotifier.noteDeletedNotification(responce);
					
				break;
				
			case 6:
				responce = controller.doRequest(CommandName.CLEAR, null);
				
				ViewNotifier.noteBookClearedNotification(responce);
				
				break;
			
			case 0:
				choice = 0;
				
				break;

			default:
				break;
			}
		}
	}
}
