package jros;

import java.util.ArrayList;
import java.util.List;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.ObjectTerm;
import jason.asSyntax.ObjectTermImpl;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jros.internal.JMethods;

public class recvData extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String actionName = ((StringTerm)terms[0]).getString();
		Object data = JMethods.recvData(ts.getUserAgArch().getAgName(), actionName, un);
		if (data == null){
			System.out.println("recvData");
			return false;
		}
		if(data instanceof List){
			ListTerm t = new ListTermImpl();
			List<Double> l = (List<Double>)data;
			for(Double n : l)
				t.add(new NumberTermImpl(n));
			if(un.unifies(t,terms[1]))
				return true;
			else return false;
					
		}else{
			ObjectTerm t = new ObjectTermImpl(data);
			if(un.unifies(t,terms[1]))
				return true;
			else return false;
		}
	}
}
