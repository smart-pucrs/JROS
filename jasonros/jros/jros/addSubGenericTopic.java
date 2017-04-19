package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class addSubGenericTopic extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		String msgType = ((StringTerm)terms[1]).getString();
		String className = ((StringTerm)terms[2]).getString();
		return JMethods.addSubGenericTopic(ts.getAg(), topicName, msgType, className);
	}
}
