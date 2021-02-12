package com.nemnous.bank.interfaces;

/**
 * This is the reader Interface
 * which needs to be implemented by 
 * the reader classes like ConsoleReader,
 * or FileInputReader
 * @author nemnous
 *
 */
public interface InputReader {
	
	/**
	 * This function should read input from the user
	 */
	public void read();

}
