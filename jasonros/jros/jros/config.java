package jros;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class config extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String rosIP = ((StringTerm)terms[0]).getString();
		String rosPort = ((StringTerm)terms[1]).getString();
		if(terms.length > 2){
			String remoteAgName = ((StringTerm)terms[2]).getString();
			System.out.println(ts.getUserAgArch().getAgName()+"<-----");
			return JMethods.rosConfig(ts.getUserAgArch().getAgName(), ts.getAg(), rosIP, rosPort,remoteAgName);
		}else{
			return JMethods.rosConfig(ts.getUserAgArch().getAgName(), ts.getAg(), rosIP, rosPort,null);
		}
	}
}