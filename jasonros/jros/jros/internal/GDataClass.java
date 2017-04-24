package jros.internal;

import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class GDataClass extends DataClass{
	private String className;
	private Unifier un;
	private Term[] terms;
	
	public GDataClass(String topicName, String msgType, String className, Unifier un, Term[] terms) {
		super(topicName, msgType, null);
		this.className = className;
		this.un = un;
		this.terms = terms;
	}
	
	public String getClassName(){
		return className;
	}
	
	public Unifier getUnifier(){
		return un;
	}
	
	public Term[] getTerms(){
		return terms;
	}

}
