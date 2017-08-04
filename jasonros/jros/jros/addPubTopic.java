package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.ObjectTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class addPubTopic extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		String msgType = ((StringTerm)terms[1]).getString();
		Object data;
		if(terms[2].isAtom()){
			return JMethods.addPubTopic(ts.getUserAgArch().getAgName(), topicName, msgType, null);
		}
		if(terms.length > 3) data = terms;
		else if(terms[2].isNumeric())
			data = ((NumberTerm)terms[2]).solve();
		else if(terms[2].isString())
			data = ((StringTerm)terms[2]).getString();
		else data = ((ObjectTerm)terms[2]).getObject();
		return JMethods.addPubTopic(ts.getUserAgArch().getAgName(), topicName, msgType, data);
	}
}
