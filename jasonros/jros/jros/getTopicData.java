package jros;

import java.util.List;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.*;
import jros.internal.JMethods;


public class getTopicData extends DefaultInternalAction{
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
		String topicName = ((StringTerm)terms[0]).getString();
		Object data = JMethods.getTopicData(ts.getAg(), topicName);
		if (data == null){
			System.out.println("getTopicData");
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