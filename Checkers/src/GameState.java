import java.util.List;

public interface GameState {
	String player();
	List<String> actions();
	GameState result(String action);
	boolean isTerminal();
	double utility(String player);  // should only need to work for terminal game states
}