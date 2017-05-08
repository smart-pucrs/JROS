package jros;

import java.util.Iterator;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class listBeliefs extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		Iterator<Literal> it = ts.getAg().getBB().iterator();
		System.out.println("Belief Base:");
		while(it.hasNext())
			System.out.println(it.next());
		return true;
	}
}