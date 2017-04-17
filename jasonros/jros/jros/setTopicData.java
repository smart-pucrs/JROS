package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class setTopicData extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String nodeName = ((StringTerm)terms[0]).getString();
		String topicName = ((StringTerm)terms[1]).getString();
		Object data;
		if(terms[2].isNumeric())
			data = ((NumberTerm)terms[2]).solve();
		else if(terms[1].isString())
			data = ((StringTerm)terms[2]).getString();
		else data = ((ObjectTerm)terms[2]).getObject();
		return JMethods.setTopicData(ts.getAg(), nodeName, topicName, data);
	}
}