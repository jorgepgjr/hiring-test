package br.com.jorgepgjr.question3;

public class Question3 implements Stream{
	private String input;
	private int count;
	
	
	public Question3(String input) {
		super();
		this.input = input;
		this.count = 0;
	}

	@Override
	public char getNext() {
		if (this.hasNext()) {
			input.charAt(count);
		}
		return 0;
	}

	@Override
	public boolean hasNext() {
		return !(input.length() == count+1); 
	}
}
