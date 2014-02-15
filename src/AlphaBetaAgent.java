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
import edu.cwru.sepia.util.Direction;

/**
 * This agent will first collect gold to produce a peasant, then the two
 * peasants will collect gold and wood separately until reach goal.
 *
 * @author Feng
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
        }
    }

    @Override
    public Map<Integer, Action> initialStep(StateView newstate, History.HistoryView statehistory) {
        step = 0;

        return middleStep(newstate, statehistory);
    }

    @Override
    public Map<Integer, Action> middleStep(StateView newState, History.HistoryView statehistory) {
        step++;

        return null;
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
}
