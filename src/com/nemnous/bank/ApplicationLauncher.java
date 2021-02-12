package com.nemnous.bank;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.services.ConsoleReader;
//import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.services.DatabaseManager;
import com.nemnous.bank.services.FileReader;
import com.nemnous.bank.services.PropertyFileReader;

public class ApplicationLauncher {
	private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Scanner scan = new Scanner(System.in);
	/**
	 * Start of the program.
	 */
	public static void main(String[] args) {
		logger.log(Level.FINE, "----------------------------------------------------");
		logger.log(Level.INFO, "Press 1 to read from Console\n"
				+ "Press 2 to read from File\n"
				+ "Press 3 to read from propeties file\n"
				+ "press 4 to start Database Manager");

		switch (scan.nextInt()) {
			case 1:
				InputReader consoleReader = new ConsoleReader();
				consoleReader.read();
				break;
			case 2:
				InputReader fileReader = new FileReader();
				fileReader.read();
				break;
			case 3:
				InputReader propertiesReader = new PropertyFileReader();
				propertiesReader.read();
				break;
			case 4:
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.start();
				break;
			default:
				break;
		}
	}
}
