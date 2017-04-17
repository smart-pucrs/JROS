package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class searchBTState extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		if(terms[1].isNumeric()){
			boolean rv = JMethods.searchBT(ts.getAg(), topicName, ((NumberTerm)terms[1]).solve());
			//ObjectTermImpl l = new ObjectTermImpl(rv);
			return rv;
		}else return false;
	}
}