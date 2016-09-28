package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MultiMazeProblem extends SearchProblem{
	private static int moves[][] = {MazeWorld.NORTH, MazeWorld.EAST, MazeWorld.SOUTH, MazeWorld.WEST, {0, 0}}; 
	private Integer[] sx, sy, gx, gy;
	private MazeWorld maze_grid;
	private int rob_num;
	
	
	public MultiMazeProblem(MazeWorld m, int rn, Integer[] xStart, Integer[] yStart,
			Integer[] xGoal, Integer[] yGoal){
		maze_grid = m;
		rob_num = rn;
		sx = xStart;
		sy = yStart;
		gx = xGoal;
		gy = yGoal;
		startNode = new MultiMazeNode(sx, sy, 0, 0);
	}
	
	public ArrayList<ArrayList<String>> multiMazeAnimation(List<SearchNode> path, boolean showGoal){
		
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		MultiMazeNode goal = (MultiMazeNode)(path.get(path.size() - 1));
		int x, y;
		for(SearchNode p : path){
			ArrayList<ArrayList<Character>> grid = maze_grid.getGrid();
			if(showGoal){
				for(int r = 0; r < rob_num; r++){
					x = goal.getX(r);
					y = goal.getY(r);
					grid.get(x).set(y, (char)('a' + r));
				}
			}
			for(int r = 0; r < rob_num; r++){
				
				x = ((MultiMazeNode) p).getX(r);
				y = ((MultiMazeNode) p).getY(r);
				grid.get(x).set(y, (char)('A' + r));
			}
			res.add(ac2as(grid));
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
	
	public void pathToString(List<SearchNode> path){
		for(SearchNode p : path){
			String tmp = "[";
			for(int i = 0; i < rob_num; i++){
				int x = ((MultiMazeNode) p).getX(i);
				int y = ((MultiMazeNode) p).getY(i);
				tmp += "[" + Integer.toString(x) + ", " + Integer.toString(y) + "]" + ", ";
			}
			tmp += "]";
			System.out.println(tmp);
		}
	}
	
	public class MultiMazeNode implements SearchNode{
		
		protected int[][] robots;
		protected int turn;
		
		private double cost;
		
		public MultiMazeNode(Integer[] x, Integer[] y, double c, int t){
			robots = new int[rob_num][2];
			for(int i = 0; i < rob_num; i++){
				robots[i][0] = x[i];
				robots[i][1] = y[i];
			}
			turn = t;
			cost = c;
		}
		public int getX(int r){return robots[r][0];}
		public int getY(int r){return robots[r][1];}
		
		public ArrayList<SearchNode> getSuccessors(){
			ArrayList<SearchNode> suc = new ArrayList<SearchNode>();
			Integer[] xNew = new Integer[rob_num], yNew = new Integer[rob_num];
			for(int[] move : moves){
				for (int r = 0; r < rob_num; r++) {
					xNew[r] = robots[r][0];
					yNew[r] = robots[r][1];
				}
				xNew[turn] = robots[turn][0] + move[0];
				yNew[turn] = robots[turn][1] + move[1];
				
				if(maze_grid.isSafe(xNew[turn], yNew[turn]) && noCollision(xNew, yNew)){
					SearchNode cur = new MultiMazeNode(xNew, yNew, getCost() + 1.0, (turn + 1) % rob_num);
					suc.add(cur);
				}
			}
			
			return suc;
		}
		
		private boolean noCollision(Integer[] xNew, Integer[] yNew){
			HashSet<Coor> corSet = new HashSet<Coor>();
			for(int i = 0; i < rob_num; i++){
				Coor cur = new Coor(xNew[i], yNew[i]);
				if(corSet.contains(cur)) return false;
				corSet.add(cur);
			}
			return true;
		}
		
		//Check if the two nodes are the same node
		@Override
		public boolean equals(Object other) {
			boolean isEqual = true;
			for(int i = 0; i < rob_num; i++)
				isEqual &= Arrays.equals(robots[i], ((MultiMazeNode) other).robots[i]);
			isEqual &= ((MultiMazeNode) other).turn == turn;
			return isEqual;
		}
				
		//For the consistency as we override "equals" method
		//Declare a hash policy
		@Override
		public int hashCode() {
			int hashcode = 0;
			for(int i = 0; i < rob_num; i++)
				hashcode += Math.pow(37, i) * (37 * robots[i][0] + robots[i][1]);
			return hashcode;
		}
		
		@Override
		public int compareTo(SearchNode o) {
			// TODO Auto-generated method stub
			return (int) Math.signum(getPriority() - o.getPriority());
		}
		@Override
		public boolean goalTest() {
			// TODO Auto-generated method stub
			for(int i = 0; i < rob_num; i++)
				if(robots[i][0] != gx[i] || robots[i][1] != gy[i])
					return false;
			return true;
		}
		@Override
		public double getCost() {
			// TODO Auto-generated method stub
			return cost;
		}

		@Override
		public double getHeuristic() {
			// TODO Auto-generated method stub
			double res = 0;
			for(int i = 0; i < rob_num; i++)
				res += Math.abs(gx[i] - robots[i][0]) + Math.abs(gy[i] - robots[i][1]);
			return res;
		}
		@Override
		public double getPriority() {
			// TODO Auto-generated method stub
			return getHeuristic() + getCost();
		}
	}
	
}
