package br.com.jorgepgjr.question3;


/**
 * Implementation of {@linkplain Stream}
 * @author jorge
 *
 */
public class StreamImpl implements Stream{
	
	private String input;
	private int count;
	
	public StreamImpl(String input) {
		super();
		this.input = input;
		this.count = 0;
	}

	@Override
	public char getNext() {
		return input.charAt(count++);
		
	}

	@Override
	public boolean hasNext() {
		return input.length() > count; 
	}
	
}
