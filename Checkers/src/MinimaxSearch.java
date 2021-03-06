public class MinimaxSearch {
	
	private final int MAXDEPTH = 5;
	
	public String minimaxDecision(GameState state, boolean minOrMax){

		if (minOrMax) { //maximizing, Black to play
			double vBest = -100;
			double v;
			String bestAction = "";
			
			for (String s : state.actions()) {
				
				v = minValue(state.result(s), 0);
				System.out.print(s);
				System.out.println("v: " + v);
				if (v > vBest) {
					vBest = v;
					bestAction = s;
				}
			}
			
			return bestAction;
		}
		else {	//minimizing, White to play
			double vBest = 100;
			double v;
			String bestAction = "";
			
			for (String s : state.actions()) {
				
				v = maxValue(state.result(s), 0);
				System.out.print(s);
				System.out.println("v: " + v);
				if (v < vBest) {
					vBest = v;
					bestAction = s;
				}
			}
			
			return bestAction;
		}		
	}
	
	public double minValue(GameState state, int depth){

		if (state.isTerminal() || depth > MAXDEPTH)
			return state.utility(state.player());
		
		double v = 100;
		
		depth++;
		for (String s : state.actions()) {
			//System.out.println(s);
			v = Math.min(v, maxValue(state.result(s), depth));
		}
		
		return v;
	}
	
	public double maxValue(GameState state, int depth){

		if (state.isTerminal() || depth > MAXDEPTH)
			return state.utility(state.player());
		
		double v = -100;
		
		depth++;
		for (String s : state.actions()) {
			//System.out.println(s);
			v = Math.max(v, minValue(state.result(s), depth));
		}
			
		return v;
	}
}