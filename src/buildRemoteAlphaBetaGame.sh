javac -cp "Sepia.jar" AlphaBetaAgent.java Node.java Unit.java State.java StateAction.java AlphaBetaLeaf.java AlphaBetaNode.java FootmanAlphaBetaNode.java ArcherAlphaBetaNode.java
java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:8000,server=y,suspend=n -cp "Sepia.jar;." edu.cwru.sepia.Main2 GameConfig.xml 
