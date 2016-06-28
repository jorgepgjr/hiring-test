package br.com.jorgepgjr.question3;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit to valid the algorism
 * @author jorge
 *
 */
public class Question3Test {

	/**
	 * All characters repeat
	 */
	@Test
	public void allRepeatTest(){
		Character retorno = Answer.main("abAbBABa");
		Assert.assertEquals(null, retorno);
	}
	
	/**
	 * The first is the first that do not repeat
	 */
	@Test
	public void firstIsFirstTest(){
		Character retorno = Answer.main("abAbBAB");
		Assert.assertEquals(Character.valueOf('a'), retorno);
	}
	
	/**
	 * Checking if in the middle works too
	 */
	@Test
	public void middleTest(){
		Character retorno = Answer.main("abABABa");
		Assert.assertEquals(Character.valueOf('b'), retorno);
	}
	
	/**
	 * More then one that does not repeat, check if it returns the first one
	 */
	@Test
	public void moreThenOneTest(){
		Character retorno = Answer.main("abAB");
		Assert.assertEquals(Character.valueOf('a'), retorno);
	}
	
	/**
	 * Passing no Strings
	 */
	@Test
	public void noStringTest(){
		Character retorno = Answer.main(null);
		Assert.assertEquals(null, retorno);
	}
}
