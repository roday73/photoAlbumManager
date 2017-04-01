package com.rpo.domain;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;


public class AssignedSequenceStyleGenerator 
extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SessionImplementor session, 
			Object obj) {
		if(obj instanceof Identifiable) {
			Identifiable identifiable = (Identifiable) obj;
			Serializable id = identifiable.getId();
			if(id != null) {
				return id;
			}
		}
		return super.generate(session, obj);
	}
}