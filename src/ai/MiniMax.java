package ai;

import java.util.*;

/**
 * An interface for MiniMax algorithms, where the AI tries to maximize a certain value, while another player tries to minimize it.
 *
 * @author Timo Friedl
 *
 * @param <State>  the type of the state objects, e.g. the game field
 * @param <Action> the type of action objects, e.g. positions to place symbols
 * @param <Value>  the type of measurable state and action value, e.g. some kind of score number
 */
public interface MiniMax<State, Action, Value extends Comparable<Value>> {
    /**
     * An instance of {@link Random} to be able to choose random action in case of equal values.
     */
    Random RANDOM = new Random();

    /**
     * Computes all possible outcome states after the AI applies a given action.
     *
     * @param state  the current game state before taking the action
     * @param action the action to take
     * @return the set of all possible outcome states
     */
    Set<State> possibleOutcome(State state, Action action);

    /**
     * Computes all possible actions for the AI to choose from, given a certain state.
     *
     * @param state the current game state
     * @return the set of all possible actions
     */
    Set<Action> possibleActions(State state);

    /**
     * Computes the value of a given state, assuming this state is somehow "basic",
     * meaning the state value is trivially known (e.g. game is over).
     *
     * @param state the base state to compute its value for
     * @return a value that measures, how great this state would be for the AI
     */
    Value baseStateValue(State state);

    /**
     * Computes the value of a given state.
     * The value is defined as the maximum action value of all possible actions to choose.
     *
     * @param state the current state to compute the value for
     * @return the value of this state
     */
    default Value stateValue(State state) {
        return possibleActions(state).stream()
                .map(a -> actionValue(state, a))
                .max(Value::compareTo)
                .orElseGet(() -> baseStateValue(state));
    }

    /**
     * Computes the value of a certain action, given the current state.
     * The value is defined as the minimum state value of all possible outcome states after applying the action.
     *
     * @param state  the current system state before applying the action
     * @param action the action to compute the value for
     * @return the value of the given action, based on the current state
     */
    default Value actionValue(State state, Action action) {
        return possibleOutcome(state, action).stream()
                .map(this::stateValue)
                .min(Value::compareTo)
                .orElseThrow();
    }

    /**
     * Computes the best possible action of all actions to choose from the current state.
     * If there are multiple possible actions with equal value, a random best action is chosen.
     *
     * @param state the current state to choose an action from
     * @return (one of) the best action the AI could choose
     */
    default Action bestAction(State state) {
        return randomMax(possibleActions(state), (a1, a2) -> actionValue(state, a1).compareTo(actionValue(state, a2)));
    }

    /**
     * Returns the maximum elements of a given {@link Collection} in terms of a certain order.
     * If there are multiple maximum elements, a random one is chosen.
     *
     * @param elements   the collection to choose the maximum from
     * @param comparator a {@link Comparator} that defines the order of the elements
     * @param <T>        the type of elements in the collection
     * @return a random maximum value
     */
    private static <T> T randomMax(Collection<T> elements, Comparator<T> comparator) {
        List<T> maxElements = List.of();
        for (T element : elements) {
            int comp;
            if (maxElements.isEmpty() || (comp = comparator.compare(element, maxElements.get(0))) > 0) {
                maxElements = new ArrayList<>(1);
                maxElements.add(element);
            } else if (comp == 0)
                maxElements.add(element);
        }
        return maxElements.get(RANDOM.nextInt(maxElements.size()));
    }
}
