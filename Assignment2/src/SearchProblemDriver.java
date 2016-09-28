package maze;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import maze.SearchProblem.SearchNode;

public class SearchProblemDriver {
	public static void main(String args[]) throws IOException {
		/******************************************************
		 * The following are the test codes. Uncomment the part 
		 * of codes you want to test. Have fun!
		 *******************************************************/
		
		/*******************************************************
		 * Warm up BFS Test
		 *******************************************************/
	
		int sx = 6, sy = 0, gx = 6, gy = 6;
		singleRobotTest(sx, sy, gx, gy, "simpleMaze.txt", "single_bfs.gif", "bfs");
		
		/*******************************************************
		 * A* Search Single Robot Test
		 *******************************************************/
		int asx = 6, asy = 0, agx = 6, agy = 6;
		singleRobotTest(asx, asy, agx, agy, "simpleMaze.txt", "single_astar.gif", "astar");
		
		/*******************************************************
		 * MultiRobots Test
		 *******************************************************/
		
		//0. The basic test for the simple maze provide by
		//   instruction

		/*Integer[] msx = { 0, 1, 2};
		Integer[] msy = { 0, 0, 0};
		Integer[] mgx = { 6, 5, 4};
		Integer[] mgy = { 6, 6, 6};
		
		multiRobotTest(msx, msy, mgx, mgy, "simpleMaze.txt", "multiRobots.gif", true);*/
		
		//Five Interesting Test Cases
		
		//1. Yield Test
		
		/*Integer[] y_msx = {2, 2};
		Integer[] y_msy = {2, 6};
		Integer[] y_mgx = {2, 2};
		Integer[] y_mgy = {6, 2};
		multiRobotTest(y_msx, y_msy, y_mgx, y_mgy, "Yield.txt", "yield_multiRobots.gif", true);*/
				
		//2. MultiRobot Narrow Maze Reorder Test
		
		/*Integer[] n_msx = {1, 1, 1};
		Integer[] n_msy = {0, 1, 2};
		Integer[] n_mgx = {1, 1, 1};
		Integer[] n_mgy = {6, 5, 4};
		multiRobotTest(n_msx, n_msy, n_mgx, n_mgy, "narrowMaze.txt", "n_multiRobots.gif", true);*/
		
		//3. Cross Road Test
		/*Integer[] c_msx = {3, 6};
		Integer[] c_msy = {0, 3};
		Integer[] c_mgx = {6, 3};
		Integer[] c_mgy = {3, 0};
		multiRobotTest(c_msx, c_msy, c_mgx, c_mgy, "Cross.txt", "cross_multiRobots.gif", false);*/
		
		//4. 8-puzzle test. Note that this may take hours to run,
		//   if you want to have a quick glance, try the 3-puzzle
		//   one.
		
		//8-Puzzles Test 
		/*Integer[] e_msx = {2, 2, 0, 0, 1, 1, 1, 0};
		Integer[] e_msy = {1, 0, 2, 1, 2, 1, 0, 0};
		Integer[] e_mgx = {0, 0, 0, 1, 1, 1, 2, 2};
		Integer[] e_mgy = {0, 1, 2, 0, 1, 2, 0, 1};
		multiRobotTest(e_msx, e_msy, e_mgx, e_mgy, "8-Puzzles.txt", "eight_puzzle_multiRobots.gif", true);*/
		
		//3-Puzzles Test
		/*Integer[] t_msx = {0, 1, 0};
		Integer[] t_msy = {1, 0, 0};
		Integer[] t_mgx = {0, 0, 1};
		Integer[] t_mgy = {0, 1, 0};
		multiRobotTest(t_msx, t_msy, t_mgx, t_mgy, "3-puzzle.txt", "3_puzzle_multiRobots.gif", false);*/
		
		
		//5. Huge Maze World
		
		//The animation part may take an hour! If you really want to try,
		//change the augment "gif_name" to a non-empty string
		/*MazeWorld hugeMaze = new MazeWorld("hugeMaze.txt");
		Integer[] h_msx = {39, 0};
		Integer[] h_msy = {38, 1};
		Integer[] h_mgx = {0, 0};
		Integer[] h_mgy = {38, 39};
		multiRobotTest(h_msx, h_msy, h_mgx, h_mgy, "hugeMaze.txt", "", true);*/
		
		
		/***********************************************************************
		 * Blind Robot Test
		 ***********************************************************************/
		
		//Empty 3 by 3 maze test
		//int gx = 0, gy = 0;
		//blindRobotTest(gx, gy, "ThreeByThreeEmptyMaze.txt", "3_3_Empty_Blind.gif");
		
		//5*5 maze test
		//int gx_1 = 2, gy_1 = 2;
		//blindRobotTest(gx_1, gy_1, "5_5_maze.txt", "5_5_blind.gif");
		
		//7*7 empty test
		//int gx_2 = 4, gy_2 = 2;
		//blindRobotTest(gx_2, gy_2, "7_7_empty.txt", "7_7_empty_blind.gif");
		
		//7 * 7 maze test
		//int gx_3 = 4, gy_3 = 2;
		//blindRobotTest(gx_3, gy_3, "7_7_maze.txt", "7_7_maze_blind.gif");
	}
	
	public static void singleRobotTest(int sx, int sy, int gx, int gy, String maze_name, String gif_name, String method) throws IOException{
		MazeWorld maze_grid = new MazeWorld(maze_name);
		SingleMazeProblem smp = new SingleMazeProblem(maze_grid,
				sx, sy, gx, gy);
		List<SearchNode> singlePath = null;
		if(method.equals("bfs"))
			singlePath = smp.breadthFirstSearch();
		else if(method.equals("astar"))
			singlePath = smp.astarSearch();
		else {
			System.out.println("No Such Method Provided!");
			return;
		}
		
		if(singlePath == null)
			System.out.println("path not found");
		else{
			
			System.out.println("Multi-" + method + ":  ");
			System.out.println("  path length: " + singlePath.size());
			
			if(!gif_name.isEmpty()){
				ArrayList<ArrayList<String>> smpAnimation = smp.singleMazeAnimation(singlePath);
				writeMazeAnimation(gif_name, smpAnimation);
			}
		}
		smp.printStats();
	}
	
	public static void multiRobotTest(Integer[] sx, Integer[] sy, Integer[] gx, Integer[] gy, String maze_name, String gif_name, boolean showGoal) throws IOException{
		MazeWorld maze_grid = new MazeWorld(maze_name);
		MultiMazeProblem mmp = new MultiMazeProblem(maze_grid, sx.length,
				sx, sy, gx, gy);
		List<SearchNode> multiPath = mmp.astarSearch();
		if(multiPath == null)
			System.out.println("path not found");
		else{
			System.out.println("Multi-A*:  ");
			System.out.println("  path length: " + multiPath.size());
			
			if(!gif_name.isEmpty()){
				ArrayList<ArrayList<String>> mmpAnimation = mmp.multiMazeAnimation(multiPath, showGoal);
				writeMazeAnimation(gif_name, mmpAnimation);
			}
		}
		mmp.printStats();
	}
	
	public static void blindRobotTest(int gx, int gy, String maze_name, String gif_name) throws IOException{
		MazeWorld maze_grid = new MazeWorld(maze_name);
		BlindMazeProblem bmp = new BlindMazeProblem(maze_grid, gx, gy);
		List<SearchNode> blindPath = bmp.astarSearch();
		if(blindPath == null)
			System.out.println("path not found");
		else{
			System.out.println("Blind-A*:  ");
			System.out.println("  path length: " + blindPath.size());
			bmp.printStats();
			ArrayList<ArrayList<String>> blindAnimation = bmp.blindMazeAnimation(blindPath);
			writeMazeAnimation(gif_name, blindAnimation);
		}
	}
	
	public static void writeMazeToFile(String filename, ArrayList<String> grid) throws IOException{

		FileWriter writer = new FileWriter(filename); 
		for(String str: grid) {
		  writer.write(str);
		  writer.write(System.getProperty( "line.separator" ));
		}
		writer.close();
	}
	
	public static BufferedImage writeMazeToImage(String filename, ArrayList<String> grid, boolean flag) throws IOException{
	       //create a File Object
	       File newFile = new File("./" + filename + ".jpeg");
	         
	       //create the font you wish to use
	       Font font = new Font("Monospaced", Font.PLAIN, 100);
	       //Determine the width and height of the image
	       //FontMetrics fontMetrics = new FontMetrics(font);
	       int width = grid.get(0).length() * font.getSize();
	       int height = (grid.size() + 2) * font.getSize();
	       
	       //create a BufferedImage object
	       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        
	       //calling createGraphics() to get the Graphics2D
	       Graphics2D g = image.createGraphics();
	       FontRenderContext frc = g.getFontRenderContext();
	       double lineWidth = font.getStringBounds(grid.get(0), frc).getWidth();
	       double lineHeight = font.getStringBounds(grid.get(0), frc).getHeight();
	       //set color and other parameters
	       
	       g.setColor(Color.WHITE);
	       g.fillRect(0, 0, width, height);
	       g.setColor(Color.darkGray);
	       g.setFont(font);
	       
	       double x = (lineWidth - font.getSize()) / 2;
	       double y = (lineHeight + font.getSize());
	       for(String line : grid){
	    	    g.drawString(line,(int)x, (int)y);
	    	    y += font.getSize();
	       }
	       
	      //releasing resources
	      g.dispose();
	       
	       //creating the file
	      if(flag) ImageIO.write(image, "jpeg", newFile);
	      
	      return image;
	}
	
	public static void writeMazeAnimation(String filename, ArrayList<ArrayList<String>> grid_sequence) throws IOException{
		ImageOutputStream output = new FileImageOutputStream(new File(filename));
		BufferedImage cur = writeMazeToImage(filename, grid_sequence.get(0), false);
		GifSequenceWriter writer = 
			      new GifSequenceWriter(output, cur.getType(), 500, false);
		writer.writeToSequence(cur);
		for(int i = 1; i < grid_sequence.size(); i++){
			cur = writeMazeToImage(filename, grid_sequence.get(i), false);
			writer.writeToSequence(cur);
		}
		writer.close();
		output.close();
	}
}
