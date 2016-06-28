package br.com.jorgepgjr.question3;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is the Main class of the exercis
 * @author jorge
 *
 */
public class Answer {

	public static Character main(String arg) {
		if(StringUtils.isEmpty(arg)){
			System.out.println("Parametro não é valido");
			return null;
		}
		
		StreamImpl question = new StreamImpl(arg);
		LinkedHashSet<Character> unicSet = new LinkedHashSet<Character>();
		HashSet<Character> duplicatedSet = new HashSet<Character>();
		
		while (question.hasNext()) {
			char i = question.getNext();
			
			if (!duplicatedSet.contains(i)) {
				//returns true if its a new insert
				boolean newInsert = unicSet.add(i);
				if (!newInsert) {
					//Only unics on this set
					unicSet.remove(i);
					duplicatedSet.add(i);
				}
				
			}
		}
		if (unicSet.size() >= 1) {
			char firstChar = unicSet.iterator().next();
			System.out.println("Primeira letra que não se repete é: "+ firstChar);
			return firstChar;
		}else {
			System.out.println("Todas letras se repetem");
			return null;
		}
	}

}
