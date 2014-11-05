package logic.pathfinder;

public class Cell {
	
	private CellType type = CellType.UNDEFINED;
	
	public Cell() {
		
	}
	
	public Cell(CellType type) {
		this.type = type;
	}
	
	public void setType(CellType type) {
		this.type = type;
	}
	
	public CellType getCellType() {
		return type;
	}

}
