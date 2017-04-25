package jros;

import java.util.ArrayList;
import java.util.List;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class listenPerceptions extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		if(terms.length > 0){
			if(terms[0].isString()){
				String rcvFrom = ((StringTerm)terms[0]).getString();
				return JMethods.listenPerceptions(ts.getAg(),rcvFrom);
			}
		}
		return JMethods.listenPerceptions(ts.getAg(),null);
	}
}