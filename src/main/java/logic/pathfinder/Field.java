package logic.pathfinder;

import logic.pathfinder.exceptions.*;
import logic.struct.*;
import logic.struct.exceptions.*;
import static logic.pathfinder.PreviousCellType.*;
import static logic.pathfinder.CellType.*;
import static logic.pathfinder.PathExistanceType.*;

public class Field {	
	private Cell[][] field;
	private PreviousCellType[][] prev; 
	private int height;
	private int width;
	private PathExistanceType pathExists = UNKNOWN;	
	
	private final double DEGREE_OF_WALL_FULLNESS = 0.3;
	
	public Field(int height, int width) {
		field = new Cell[height][width];
		prev = new PreviousCellType[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j) {
				field[i][j] = new Cell(EMPTY);
				prev[i][j] = NOWAY;
			}
		this.height = height;
		this.width = width;
	}

    public void cleanField() {
        field = new Cell[height][width];
        prev = new PreviousCellType[height][width];
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j) {
                field[i][j] = new Cell(EMPTY);
                prev[i][j] = NOWAY;
            }
    }
	
	public void setCell(CellType type, int i, int j) {
		field[i][j].setType(type);
	}
	
	public void setCell(Cell cell, int i, int j) {
		field[i][j] = cell;
	}
	
	public CellType getCellType(int i, int j) {
		return field[i][j].getCellType();
	}
	
	public Cell getCell(int i, int j) {
		return field[i][j];
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public PathExistanceType pathExists() {
		return pathExists;
	}
	
	public Point getHeroCoords() throws WrongMapException {
		return findHero();
	}
	
	public Point getExitCoords() throws WrongMapException {
		return findExit();
	}
	
	public CellType[][] getCellTypesField() {
		CellType[][] field = new CellType[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				field[i][j] = this.field[i][j].getCellType();
		return field;
	}
	
	public void generateField() {
		generateField(height, width);
	}
	
	public void generateField(int height, int width) {
		field = new Cell[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				field[i][j] = getRandomWallOrEmptyCell();
		Point hero = getRandomPoint(height, width);
		field[hero.getX()][hero.getY()] = new Cell(HERO);
		Point exit = getRandomPoint(height, width);
		while (exit.equals(hero))
			exit = getRandomPoint(height, width);
		field[exit.getX()][exit.getY()] = new Cell(EXIT);
		pathExists = UNKNOWN;
	}
	
	private Cell getRandomWallOrEmptyCell() {
		double random = Math.random();
		if (random < DEGREE_OF_WALL_FULLNESS)
			return new Cell(WALL);
		else
			return new Cell(EMPTY);
	}
	
	private Point getRandomPoint(int maxX, int maxY) {
		return new Point(getRandomNumber(maxX), getRandomNumber(maxY));
	}
	
	private int getRandomNumber(int max) {
		int random = (int)(Math.random() * max);
		if (random == max)
			return random - 1;
		else
			return random;
	}

    private static boolean isWay(CellType type) {
        if (type == EMPTY || type == UNDEFINED || type == WALL) return false;
        else return true;
    }

    public void findWay() throws WrongMapException {
        findPath();
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j) {
                if (field[i][j].getCellType() == PATH) {
                    CellType up, down, left, right;
                    if (i == 0) {up = CellType.EMPTY; down = field[i+1][j].getCellType(); }
                    else  if (i == height - 1)  { up = field[i-1][j].getCellType(); down = CellType.EMPTY; }
                    else { up = field[i-1][j].getCellType(); down = field[i+1][j].getCellType(); }
                    if (j == 0) {left = CellType.EMPTY; right = field[i][j+1].getCellType(); }
                    else  if (j == width - 1)  { left = field[i][j-1].getCellType(); right = CellType.EMPTY; }
                    else { left = field[i][j-1].getCellType(); right = field[i][j+1].getCellType(); }

                    if ( isWay(up) && isWay(left) ) setCell(LEFTUP, i, j);
                    else if (isWay(up) && isWay(right)) setCell(LEFTDOWN, i, j);
                    else if (isWay(up) && isWay(down)) setCell(HORIZONTAL, i, j);
                    else if (isWay(down) && isWay(right)) setCell(RIGHTDOWN, i, j);
                    else if (isWay(down) && isWay(left)) setCell(RIGHTUP, i, j);
                    else if (isWay(left) && isWay(right)) setCell(VERTICAL, i, j);

                }
            }

    }

	public void findPath() throws WrongMapException {
		Point heroCoords = findHero();
		Point exitCoords = findExit();	
		PreviousCellType[][] prev = findPathMap(heroCoords); 
					
		if (prev[exitCoords.getX()][exitCoords.getY()] == NOWAY)
			pathExists = NOPATH;
		else { 
			pathExists = EXISTS;
			markPath(heroCoords, exitCoords, prev);
		}
	}
	
	public Point findHero() {
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				if (field[i][j].getCellType() == HERO)
					return new Point(i, j);
		//throw new WrongMapException();
        return new Point(-1, -1);
	}
	
	public Point findExit() {
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				if (field[i][j].getCellType() == EXIT)
					return new Point(i, j);
		//throw new WrongMapException();
        return new Point(-1, -1);
	}
	
	private PreviousCellType[][] findPathMap(Point heroCoords) {
		Queue queue = new Queue();
		queue.add(heroCoords);
		prev = new PreviousCellType[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				prev[i][j] = NOWAY;
		int[][] visited = new int[height][width];		
		visited[heroCoords.getX()][heroCoords.getY()] = 1;
		
		while (!queue.isEmpty()) {
			Point point = getNextCell(queue);
			int x = point.getX(), y = point.getY();
			if (x > 0 && canBeVisited(x - 1, y, visited))
				markNeighbour(x - 1, y, queue, visited, prev, RIGHT);
			if (x < height - 1 && canBeVisited(x + 1, y, visited))
				markNeighbour(x + 1, y, queue, visited, prev, LEFT);
			if (y > 0 && canBeVisited(x, y - 1, visited))
				markNeighbour(x, y - 1, queue, visited, prev, UP);
			if (y < width - 1 && canBeVisited(x, y + 1, visited))
				markNeighbour(x, y + 1, queue, visited, prev, DOWN);
		}
		return prev;
	}
	
	// Just to block Exception
	private Point getNextCell(Queue queue) {
		try {
			return queue.pop();
		} catch (Exception e) {
			// Never happens
			return null;
		}
	}
	
	private boolean canBeVisited(int x, int y, int[][] visited) {
		return visited[x][y] == 0 && field[x][y].getCellType() != WALL;
	}
	
	private void markNeighbour(int x, int y, Queue queue, int[][] visited, 
			PreviousCellType[][] prev, PreviousCellType type) {
		
		Point prevPoint = getPrevPoint(x, y, type);
		visited[x][y] = visited[prevPoint.getX()][prevPoint.getY()] + 1;
		queue.add(new Point(x, y));
		prev[x][y] = type;		
	}
	
	private Point getPrevPoint(int x, int y, PreviousCellType type) {
		switch (type) {
			case UP:
				return new Point(x, y + 1);
			case DOWN:
				return new Point(x, y - 1);
			case LEFT:
				return new Point(x - 1, y);
			default: 
				return new Point(x + 1, y);
		}
	}
	
	private void markPath(Point heroCoords, Point exitCoords, PreviousCellType[][] prev) {
		Point currentPoint = exitCoords.clone();
		while (!currentPoint.equals(heroCoords)) {
			int x = currentPoint.getX(), y = currentPoint.getY();
			field[x][y] = new Cell(PATH);
			currentPoint = getPrevPoint(x, y, prev[x][y]);			
		}
		field[exitCoords.getX()][exitCoords.getY()] = new Cell(EXIT);
	}
	
}
