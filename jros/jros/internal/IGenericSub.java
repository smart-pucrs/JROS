package jros.internal;

import org.ros.node.ConnectedNode;

import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public interface IGenericSub {
	void subProc(ConnectedNode connectedNode, String topicName, String msgType, Unifier un, Term[] terms);
}
