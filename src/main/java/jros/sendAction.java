package jros;

import java.util.ArrayList;
import java.util.List;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class sendAction extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String action = ((StringTerm)terms[0]).getString();
		ArrayList<Object> params = new ArrayList<Object>();
		for(int i = 1;i < terms.length;i++){
			if(terms[i].isNumeric()){
				double num = ((NumberTerm)terms[i]).solve();
				params.add(num);
			}else
				params.add(((StringTerm)terms[i]).getString());
		}
		return JMethods.sendAction(ts.getUserAgArch().getAgName(), action, params);
	}
}
