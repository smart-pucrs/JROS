package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class createSubNode extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String nodeName = ((StringTerm)terms[0]).getString();
		return JMethods.createSubNode(ts.getAg(), nodeName);
	}
}