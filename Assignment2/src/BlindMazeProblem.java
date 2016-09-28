package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlindMazeProblem extends SearchProblem{
	private static int moves[][] = { MazeWorld.NORTH, MazeWorld.EAST, MazeWorld.SOUTH, MazeWorld.WEST };
	//Record current belief states
	private Coor goal;
	private MazeWorld maze_grid;
	
	public BlindMazeProblem(MazeWorld m, int gx, int gy){
		maze_grid = m;
		goal = new Coor(gx, gy);
		HashSet<Coor> startCoor = new HashSet<Coor>();
		for(int i = 0; i < maze_grid.height; i++){
			for(int j = 0; j < maze_grid.width; j++){
				if(maze_grid.isSafe(i, j))
					startCoor.add(new Coor(i, j));
			}
		}
		startNode = new BlindMazeNode(startCoor, 0);
	}
	
public ArrayList<ArrayList<String>> blindMazeAnimation(List<SearchNode> path){	
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		int x, y;
		for(SearchNode p : path){
			ArrayList<ArrayList<Character>> grid = maze_grid.getGrid();
			HashSet<Coor> states = ((BlindMazeNode)(p)).states;
			for(Coor state : states){
				x = state.x;
				y = state.y;
				grid.get(x).set(y, 'O');
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
	
	public class BlindMazeNode implements SearchNode{
		//protected List<Double> sumStates;
		protected HashSet<Coor> states;
		private double cost;
		
		public BlindMazeNode(HashSet<Coor> s, double c){
			states = s;
			cost = c;
		}

		@Override
		public ArrayList<SearchNode> getSuccessors() {
			// TODO Auto-generated method stub
			ArrayList<SearchNode> suc = new ArrayList<SearchNode>();
			for(int[] move : moves){
				HashSet<Coor> nextStates = new HashSet<Coor>();
				for(Coor state : states){
					Coor next = new Coor(state.x + move[0], state.y + move[1]);
					if(maze_grid.isSafe(next.x, next.y))
						nextStates.add(next);
					else
						nextStates.add(state);
				}
				suc.add(new BlindMazeNode(nextStates, getCost() + 1.0 * nextStates.size()));
			}
			return suc;
		}

		@Override
		public int compareTo(SearchNode arg0) {
			// TODO Auto-generated method stub
			return (int) Math.signum(getPriority() - arg0.getPriority());
		}

		@Override
		public boolean goalTest() {
			// TODO Auto-generated method stub
			return states.size() == 1 && states.contains(goal);
		}

		@Override
		public double getCost() {
			// TODO Auto-generated method stub
			return cost;
		}

		@Override
		public double getHeuristic() {
			// TODO Auto-generated method stub
			double centerX = 0.0, centerY = 0.0;
			//Center Point
			for(Coor state : states){
				centerX += state.x;
				centerY += state.y;
			}
			centerX /= states.size();
			centerY /= states.size();
			
			//Manhattan Distance
			double dist = Math.abs(centerX - goal.x) + Math.abs(centerY - goal.y);
			
			//Standard Deviation
			double sDiviation = 0.0;
			for(Coor state : states){
				sDiviation += Math.pow(state.x - centerX, 2) + Math.pow(state.y - centerY, 2);
			}
			sDiviation = Math.pow(sDiviation / states.size(), 0.5);
			
			
			return Math.max(dist, sDiviation);
		}

		@Override
		public double getPriority() {
			// TODO Auto-generated method stub
			return getHeuristic() + getCost();
		}
	}
}
