package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class getTopicData extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		Object data = JMethods.getTopicData(ts.getAg(), topicName);
		if (data == null)
			return false;
		ObjectTerm t = new ObjectTermImpl(data);
		return un.unifies(t,terms[1]);
	}
}