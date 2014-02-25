/**
 *  Strategy Engine for Programming Intelligent Agents (SEPIA)
    Copyright (C) 2012 Case Western Reserve University

    This file is part of SEPIA.

    SEPIA is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SEPIA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SEPIA.  If not, see <http://www.gnu.org/licenses/>.
 */
//package edu.cwru.sepia.agent;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.history.History;
import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.environment.model.state.Unit.UnitView;

/**
 * This agent will use minimax with alpha beta pruning to hunt down and kill the
 * archers.
 * 
 * @author wkr3, jxb532
 * 
 */
public class AlphaBetaAgent extends Agent {
    private static final long serialVersionUID = -4047208702628325380L;

    private int step;

    private int treeDepth = 5;

    public AlphaBetaAgent(int playernum, String[] arguments) {
        super(playernum);

        if (arguments.length >= 1) {
            String depth = arguments[0];
            treeDepth = Integer.parseInt(depth);
            treeDepth -= treeDepth % 2;
        }
    }

    @Override
    public Map<Integer, edu.cwru.sepia.action.Action> initialStep(StateView newstate, History.HistoryView statehistory) {
        step = 0;
        
        State.setupBoard(0, newstate.getXExtent(), 0, newstate.getYExtent());
        
        List<Integer> unitIds = newstate.getAllUnitIds();
        List<UnitView> footmans = new ArrayList<>();
        List<UnitView> archers = new ArrayList<>();
        for (int i = 0; i < unitIds.size(); i++) {
            int id = unitIds.get(i);
            UnitView unit = newstate.getUnit(id);
            String unitTypeName = unit.getTemplateView().getName();
            Node.Log("Unit Type Name: " + unitTypeName);
            if (unitTypeName.equalsIgnoreCase("Footman")) {
                footmans.add(unit);
            } else if (unitTypeName.equalsIgnoreCase("Archer")) {
                archers.add(unit);
            }
        }
        
        Unit.setupUnit(archers.get(0).getTemplateView().getRange(),
        		archers.get(0).getTemplateView().getBasicAttack(), footmans.get(0).getTemplateView().getBasicAttack());
        
        return middleStep(newstate, statehistory);
    }

    @Override
    public Map<Integer, edu.cwru.sepia.action.Action> middleStep(StateView newState, History.HistoryView statehistory) {
        step++;
        
        StateView currentStateView = newState;
        Map<Integer,edu.cwru.sepia.action.Action> builder = new HashMap<Integer,edu.cwru.sepia.action.Action>();
        
        // Generate the search space and find the optimal path.
        AlphaBetaNode.setupSearch(treeDepth);
        
        // Create a state based on the current game layout
        State currentState = generateState(currentStateView);
        
        // Create the root node of the search tree using the current state
        AlphaBetaNode searchSpace = new FootmanAlphaBetaNode(currentState);
        Node.Log("CREATED INITIAL NODE: " + searchSpace, Node.Level.Low);
        
        // Find the next move by creating an alpha-beta pruned tree
        State nextState = searchSpace.getBestNode().getState();
        Node.Log("Found next state!", Node.Level.High);

        // Print the best alpha-beta tree traversal
        List<Node> path = searchSpace.getBestNode().getBestPath();
        Node.Log("\nCurrent best path:");
        for (Node node : path) {
            Node.Log("State actions:");
            Node.Log(node.getState().getAction1().toString());
            if (node.getState().getAction2().getType() != StateAction.Type.UNDEFINED) {
                Node.Log(node.getState().getAction2().toString());
            }
        }
        Node.Log("");
        
        List<StateAction> actions = new ArrayList<>();
        actions.add(nextState.getAction1());
        actions.add(nextState.getAction2());
        
        // Apply the actions associated with the best move
        for (StateAction action : actions) {
        	Action move = null;
        	switch (action.getType()) {
        	case ATTACK:
                Node.Log("Attacking!");
        		move = Action.createPrimitiveAttack(action.getEntity().getId(), action.getTarget().getId());
        		builder.put(action.getEntity().getId(), move);
        		break;
        	case MOVE:
                Node.Log("Moving!");
        		move = Action.createPrimitiveMove(action.getEntity().getId(), action.getDirection());
        		builder.put(action.getEntity().getId(), move);
        		break;
        	default:
        		Node.Log("Waiting!");
        		break;
        	}
        }
        
        return builder;
    }

    /**
     * Creates a state object based on the current gameboard
     * @param currentStateView
     * @return the state
     */
    private State generateState(StateView currentStateView) {
        List<Integer> unitIds = currentStateView.getAllUnitIds();
        List<Integer> footmanIds = new ArrayList<Integer>();
        List<Integer> archerIds = new ArrayList<Integer>();
        for (int i = 0; i < unitIds.size(); i++) {
            int id = unitIds.get(i);
            UnitView unit = currentStateView.getUnit(id);
            String unitTypeName = unit.getTemplateView().getName();
            if (unitTypeName.equalsIgnoreCase("Footman")) {
                footmanIds.add(id);
            } else if (unitTypeName.equalsIgnoreCase("Archer")) {
                archerIds.add(id);
            }
        }
        
        List<Unit> footmen = new ArrayList<Unit>();
        for (Integer id : footmanIds) {
            footmen.add(new Unit(currentStateView.getUnit(id)));
        }
        
        List<Unit> archers = new ArrayList<Unit>();
        for (Integer id : archerIds) {
            archers.add(new Unit(currentStateView.getUnit(id)));
        }
        
        State initialState = new State(footmen, archers);
        return initialState;
    }

    @Override
    public void terminalStep(StateView newstate, History.HistoryView statehistory) {
        step++;
    }

    public static String getUsage() {
        return "Minimaxes a thing.";
    }

    @Override
    public void savePlayerData(OutputStream os) {
        // this agent lacks learning and so has nothing to persist.
    }

    @Override
    public void loadPlayerData(InputStream is) {
        // this agent lacks learning and so has nothing to persist.
    }

    //
    // Alpha Beta Algorithms
    //
}
