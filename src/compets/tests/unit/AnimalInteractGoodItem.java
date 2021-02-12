package compets.tests.unit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import compets.engine.data.animal.Animal;
import compets.engine.data.map.Map;
import compets.engine.data.map.Position;
import compets.engine.data.map.item.GoodItem;
import compets.engine.process.AnimalManager;

/**
 * @author Maxence
 */
public class AnimalInteractGoodItem {
	
	private static Map map;
	private static Position position;
	private static Animal animal;
	private static AnimalManager manager;

	@BeforeClass
	public static void init() {
		map = new Map(1, 1);
		
		position = new Position(0, 0);
		map.getMap()[position.getX()][position.getY()] = new GoodItem(position);
		
		animal = new Animal(position);
		manager = new AnimalManager(animal, map);
	}

	@Test
	public void getReward() {
		assertTrue(manager.reward());
	}
	
	@Test
	public void dontGetPunish() {
		assertFalse(manager.punish());
	}

}
