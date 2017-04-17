package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class searchState extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		Object data;
		if(terms[1].isNumeric())
			data = ((NumberTerm)terms[1]).solve();
		else if(terms[1].isString())
			data = ((StringTerm)terms[1]).getString();
		else data = ((ObjectTerm)terms[1]).getObject();
		boolean rv = JMethods.searchExact(ts.getAg(), topicName, data);
		//ObjectTermImpl l = new ObjectTermImpl(rv);
		return rv;
	}
}