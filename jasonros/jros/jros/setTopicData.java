package jros;

import java.util.ArrayList;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class setTopicData extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String nodeName = ((StringTerm)terms[0]).getString();
		ArrayList<Object> params = new ArrayList<Object>();
		for(int i = 1; i < terms.length;i++){
			if(terms[i].isNumeric()){
				params.add(((NumberTerm)terms[i]).solve());
			}else if(terms[i].isString()){
				params.add(((StringTerm)terms[i]).getString());
				break;
			}else{
				params.add(((ObjectTerm)terms[i]).getObject());
			}
		}
		return JMethods.setTopicData(ts.getUserAgArch().getAgName(), nodeName, params);
	}
}