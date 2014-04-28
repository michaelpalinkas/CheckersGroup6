
public class AlphaBetaSearch {

	private String bestAction;
	
	public AlphaBetaSearch () {

		String bestAction = "";
	}
	
	public String getBestAction () {
		return bestAction;
	}
	
	public double decision(GameState state, int depth, double alpha, double beta, boolean minOrMax, boolean initial) {
		
		if (depth == 0 || state.isTerminal()) {
			return (state.utility(state.player()));
		}
		
		if (minOrMax) {		//maximizing, Black to play
			double best = -100;
			
			for (String s : state.actions()) {
				alpha = Math.max(alpha, decision(state.result(s), depth-1, alpha, beta, false, false));
				
				if (alpha > best) {
					best = alpha;
					if (initial) {
						bestAction = s;
						System.out.print(bestAction);
						System.out.println("value: "+ best);
					}
				}
				
				if (beta <= alpha) {
					break; //beta cutoff
				}
			}
			
			return alpha;
		}
		else {		//minimizing, white to play
			double best = 100;
			
			for (String s : state.actions()) {
				beta = Math.min(beta, decision(state.result(s), depth-1, alpha, beta, true, false));
				
				if (beta < best) {
					best = beta;
					if (initial) {
						bestAction = s;
						System.out.print(bestAction);
						System.out.println("value: "+ best);
					}
				}
				
				if (beta <= alpha) {
					break; //alpha cutoff
				}
			}
			
			return beta;
		}
	}
}