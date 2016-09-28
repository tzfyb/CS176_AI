package maze;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MazeWorld {
	public static int[] NORTH = {0, 1};
	public static int[] SOUTH = {0, -1};
	public static int[] EAST = {1, 0};
	public static int[] WEST = {-1, 0};
	
	public int width, height;
	
	private char[][] maze_grid;
	
	//MazeWorld generator for a single robot, read from a file in current folder
	public MazeWorld(String filename){
		Charset charset = Charset.forName("UTF-8");
		File file =  new File(filename);
		String pathStr = file.getAbsolutePath();
		Path path = Paths.get(pathStr);
		
		try{
			List<String> lines = Files.readAllLines(path, charset);
			height = lines.size();
			width = lines.get(0).length();
			maze_grid = new char[height][width];
			for(int i = 0; i < height; i++)
				for(int j = 0; j < width; j++)
					maze_grid[i][j] = lines.get(i).charAt(j);
		} catch (IOException e){
			System.out.println(e);
		}
	}
	
	//Check if (x, y) is in the map and is a floor
	public boolean isSafe(int x, int y){
		if(x >= 0 && x < height && y >= 0 && y < width)
			return maze_grid[x][y] == '.';
		return false;
	}
	
	public ArrayList<ArrayList<Character>> getGrid(){
		ArrayList<ArrayList<Character>> grid_copy = new ArrayList<ArrayList<Character>>();
		for(int i = 0; i < height; i++){
			ArrayList<Character> tmp = new ArrayList<Character>();
			for(int j = 0; j < width; j++)
				tmp.add(maze_grid[i][j]);
			grid_copy.add(tmp);
		}
		return grid_copy;
	}
	
	public String getGridString(){
		String res = "";
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++)
				res += maze_grid[y][x];
			res += "\n";
		}
		return res;
	}
	
	//Test function for this class
	public static void main(String args[]){
		MazeWorld test = new MazeWorld("test");
		System.out.println(test.getGridString());
		if(test.isSafe(3, 2))
			System.out.println("Safe");
		else
			System.out.println("Not Safe");
	}
}
