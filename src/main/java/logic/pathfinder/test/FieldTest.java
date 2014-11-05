package logic.pathfinder.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.junit.Test;

import logic.pathfinder.*;
import logic.pathfinder.exceptions.*;
import logic.struct.exceptions.EmptyQueueException;

public class FieldTest {

	@Test
	public void testConstructor() {
		int height = 4, width = 3;
		Field field = new Field(height, width);
		Assert.assertEquals(height, field.getHeight());
		Assert.assertEquals(width, field.getWidth());
	}
	
	@Test
	public void testSetGet() {		
		int height = 2, width = 2;
		Field field = new Field(height, width);
		
		field.setCell(new Cell(CellType.HERO), 0, 0);
		field.setCell(new Cell(CellType.EXIT), 1, 1);
		field.setCell(new Cell(CellType.WALL), 0, 1);
		field.setCell(new Cell(CellType.EMPTY), 1, 0);
		
		Assert.assertEquals(CellType.HERO, field.getCell(0, 0).getCellType());
		Assert.assertEquals(CellType.EXIT, field.getCell(1, 1).getCellType());
		Assert.assertEquals(CellType.WALL, field.getCell(0, 1).getCellType());
		Assert.assertEquals(CellType.EMPTY, field.getCell(1, 0).getCellType());
		
		Assert.assertEquals(CellType.HERO, field.getCellType(0, 0));
		Assert.assertEquals(CellType.EXIT, field.getCellType(1, 1));
		Assert.assertEquals(CellType.WALL, field.getCellType(0, 1));
		Assert.assertEquals(CellType.EMPTY, field.getCellType(1, 0));
	}
	
	@Test
	public void testPathExist() {
		int height = 4, width = 3;
		Field field = new Field(height, width);
		Assert.assertEquals(PathExistanceType.UNKNOWN, field.pathExists());
	}

	@Test
	public void testGenerator() throws FileNotFoundException {
		int height = 4, width = 3;
		Field field = new Field(height, width);
		field.generateField();

		PrintWriter file = new PrintWriter(new File("testGenerator.txt"));
		for (int i = 0; i < field.getHeight(); ++i) {
			for (int j = 0; j < field.getWidth(); ++j) {
				CellType type = field.getCellType(i, j);
				char typeSymbol = getCharForCellType(type);
				file.write(typeSymbol);
			}
			file.write('\n');
		}			
		file.close();
	}
	
	private char getCharForCellType(CellType type) {
		switch (type) {
			case EMPTY:
				return '0';
			case HERO:
				return 'H';
			case EXIT:
				return '#';
			case WALL:
				return 'W';
			case PATH:
				return 'P';
			default:
				return '?';			
		}
	}
	
	@Test
	public void testFindPathManual() throws FileNotFoundException, WrongMapException {
		int height = 2, width = 2;
		Field field = new Field(height, width);
		
		field.setCell(CellType.HERO, 0, 0);
		field.setCell(CellType.WALL, 0, 1);
		field.setCell(CellType.EMPTY, 1, 0);
		field.setCell(CellType.EXIT, 1, 1);
		
		field.findPath();
		
		Assert.assertEquals(PathExistanceType.EXISTS, field.pathExists());

		PrintWriter file = new PrintWriter(new File("testFindPathManual.txt"));
		for (int i = 0; i < field.getHeight(); ++i) {
			for (int j = 0; j < field.getWidth(); ++j) {
				CellType type = field.getCellType(i, j);
				char typeSymbol = getCharForCellType(type);
				file.write(typeSymbol);
			}
			file.write('\n');
		}			
		file.close();
	}
	

	@Test
	public void testFindPathGenerated() throws FileNotFoundException, WrongMapException {
		int height = 20, width = 20;
		Field field = new Field(height, width);
		field.generateField();
		field.findPath();

		PathExistanceType pathExistsActual = PathExistanceType.NOPATH;
		
		PrintWriter file = new PrintWriter(new File("testFindPathGenerated.txt"));
		for (int i = 0; i < field.getHeight(); ++i) {
			for (int j = 0; j < field.getWidth(); ++j) {
				CellType type = field.getCellType(i, j);
				char typeSymbol = getCharForCellType(type);
				if (typeSymbol == 'P')
					pathExistsActual = PathExistanceType.EXISTS;
				file.write(typeSymbol);
			}
			file.write('\n');
		}			
		file.close();
		
		Assert.assertEquals(field.pathExists(), pathExistsActual);
	}
	
}
