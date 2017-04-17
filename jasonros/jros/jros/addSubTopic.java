package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class addSubTopic extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		String msgType = ((StringTerm)terms[1]).getString();
		return JMethods.addSubTopic(ts.getAg(), topicName, msgType);
	}
}
