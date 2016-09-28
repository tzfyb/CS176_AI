package maze;

import java.util.ArrayList;
import java.util.List;

public class SingleMazeProblem extends SearchProblem{
	private int sx, sy, gx, gy;
	private static int moves[][] = {MazeWorld.NORTH, MazeWorld.EAST, MazeWorld.SOUTH, MazeWorld.WEST}; 
	private MazeWorld maze_grid;
	
	public SingleMazeProblem(MazeWorld m, int startX, int startY, int goalX, int goalY){
		startNode = new SingleMazeNode(startX, startY, 0, null);
		sx = startX;
		sy = startY;
		gx = goalX;
		gy = goalY;
		maze_grid = m;
	}
	
	public ArrayList<String> singleMazeResult(List<SearchNode> path){
		ArrayList<ArrayList<Character>> grid = maze_grid.getGrid();
		for(SearchNode p : path){
			int x = ((SingleMazeNode) p).getX();
			int y = ((SingleMazeNode) p).getY();
			if(grid.get(x).get(y) != 'S' && grid.get(x).get(y) != 'G')
				grid.get(x).set(y, '-');
		}
		grid.get(sx).set(sy, 'S');
		grid.get(gx).set(gy, 'G');
		
		ArrayList<String> res = new ArrayList<String>();
		for(int i = 0; i < maze_grid.height; i++){
			String line = "";
			for(int j = 0; j < maze_grid.width; j++)
				line += grid.get(i).get(j);
			res.add(line);
		}
		return res;
	}
	
	public ArrayList<ArrayList<String>> singleMazeAnimation(List<SearchNode> path){
		ArrayList<ArrayList<Character>> grid = maze_grid.getGrid();
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		int x = ((SingleMazeNode) path.get(0)).getX();
		int y = ((SingleMazeNode) path.get(0)).getY();
		grid.get(x).set(y, 'S');
		x = ((SingleMazeNode) path.get(path.size() - 1)).getX();
		y = ((SingleMazeNode) path.get(path.size() - 1)).getY();
		grid.get(x).set(y, 'G');
		res.add(ac2as(grid));
		for(int i = 1; i < path.size(); i++){
			x = ((SingleMazeNode) path.get(i)).getX();
			y = ((SingleMazeNode) path.get(i)).getY();
			grid.get(x).set(y, 'S');
			res.add(new ArrayList<String>(ac2as(grid)));
		}
		return res;
	}
	
	private ArrayList<String> ac2as(ArrayList<ArrayList<Character>> a){
		ArrayList<String> res = new ArrayList<String>();
		for(ArrayList<Character> b : a){
			StringBuilder tmp = new StringBuilder(b.size());
			for(Character c : b)
				tmp.append(c);
			res.add(tmp.toString());
		}
		return res;
	}
	
	public class SingleMazeNode implements SearchNode{
		protected int[] state;
		
		private double cost;
		
		private SingleMazeNode parent;
		
		public SingleMazeNode(int x, int y, double c, SingleMazeNode p){
			state = new int[2];
			state[0] = x;
			state[1] = y;
			parent = p;
			cost = c;
		}
		
		public int getX(){return state[0];}
		public int getY(){return state[1];}
		public SearchNode getParent(){return parent;}
		
		public ArrayList<SearchNode> getSuccessors(){
			ArrayList<SearchNode> suc = new ArrayList<SearchNode>();
			for (int[] move: moves) {
				int xNew = state[0] + move[0];
				int yNew = state[1] + move[1]; 
				if(maze_grid.isSafe(xNew, yNew)) {
					SearchNode next = new SingleMazeNode(xNew, yNew, getCost() + 1.0, this);
					suc.add(next);
				}
			}
			return suc;
		}

		//Check if the two nodes are the same node
		@Override
		public boolean equals(Object other) {
			int[] otherState = ((SingleMazeNode) other).state;
			return state[0] == otherState[0] && state[1] == otherState[1];
		}
		
		//For the consistency as we override "equals" method
		//Declare a hash policy
		@Override
		public int hashCode() {
			return state[0] * 37 + state[1]; 
		}

		
		@Override
		public int compareTo(SearchNode arg0) {
			// TODO Auto-generated method stub
			return (int) Math.signum(getPriority() - arg0.getPriority());
		}

		@Override
		public boolean goalTest() {
			// TODO Auto-generated method stub
			return state[0] == gx && state[1] == gy;
		}

		@Override
		public double getCost() {
			// TODO Auto-generated method stub
			return cost;
		}

		@Override
		public double getHeuristic() {
			// TODO Auto-generated method stub
			double dx = Math.abs(gx - state[0]);
			double dy = Math.abs(gy - state[1]);
			return Math.abs(dx) + Math.abs(dy);
		}

		@Override
		public double getPriority() {
			// TODO Auto-generated method stub
			return getHeuristic() + getCost();
		}
	}

}
