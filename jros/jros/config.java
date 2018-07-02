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
		if(terms.length == 3){
			String remoteAgName = ((StringTerm)terms[2]).getString();
			System.out.println(ts.getUserAgArch().getAgName()+"<-----");
			return JMethods.rosConfig(ts.getUserAgArch().getAgName(), ts.getAg(), rosIP, rosPort,remoteAgName);
		}else if(terms.length == 4){
			String remoteAgName = ((StringTerm)terms[2]).getString();
			int index = ts.getAg().getASLSrc().lastIndexOf('/');
			String dir = ts.getAg().getASLSrc().substring(0, index)+"/";
			System.out.println("dir:"+dir);
			String configFile = dir + ((StringTerm)terms[3]).getString();
			System.out.println(ts.getUserAgArch().getAgName()+"<-----");
			return JMethods.rosConfig(ts.getUserAgArch().getAgName(), ts.getAg(), rosIP, rosPort,remoteAgName, configFile);
		}else{
			return JMethods.rosConfig(ts.getUserAgArch().getAgName(), ts.getAg(), rosIP, rosPort,null);
		}
	}
}