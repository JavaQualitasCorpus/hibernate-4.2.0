/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.ejb.criteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.Test;

import org.hibernate.ejb.test.BaseEntityManagerFunctionalTestCase;

/**
 * @author Steve Ebersole
 */
public class ParameterTest extends BaseEntityManagerFunctionalTestCase {

	@Test
	public void testPrimitiveArrayParameterBinding() {
		EntityManager em = getOrCreateEntityManager();
		em.getTransaction().begin();
		CriteriaQuery<MultiTypedBasicAttributesEntity> criteria = em.getCriteriaBuilder()
				.createQuery( MultiTypedBasicAttributesEntity.class );
		
		Root<MultiTypedBasicAttributesEntity> rootEntity = criteria.from( MultiTypedBasicAttributesEntity.class );
		//Path<byte[]> someBytesPath = rootEntity.get( MultiTypedBasicAttributesEntity_.someBytes );
		/*
	     * QualitaCorpus.class: we replaced the line above with the line below
	     * because there is a syntax error and visibility and modifiers violation.
	     */    
		Path<byte[]> someBytesPath = null;
		
		ParameterExpression<byte[]> param = em.getCriteriaBuilder().parameter( byte[].class, "theBytes" );
		criteria.where( em.getCriteriaBuilder().equal( someBytesPath, param ) );
		TypedQuery<MultiTypedBasicAttributesEntity> query = em.createQuery( criteria );
		query.setParameter( param, new byte[] { 1,1,1 } );
		query.getResultList();
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testNonPrimitiveArrayParameterBinding() {
		EntityManager em = getOrCreateEntityManager();
		em.getTransaction().begin();
		CriteriaQuery<MultiTypedBasicAttributesEntity> criteria = em.getCriteriaBuilder()
				.createQuery( MultiTypedBasicAttributesEntity.class );
		Root<MultiTypedBasicAttributesEntity> rootEntity = criteria.from( MultiTypedBasicAttributesEntity.class );
		
		//Path<Byte[]> thePath = rootEntity.get( MultiTypedBasicAttributesEntity_.someWrappedBytes );
		/*
	     * QualitaCorpus.class: we replaced the line above with the line below
	     * because there is a syntax error and visibility and modifiers violation.
	     */    
		Path<byte[]> thePath = null;
		
		ParameterExpression<Byte[]> param = em.getCriteriaBuilder().parameter( Byte[].class, "theBytes" );
		criteria.where( em.getCriteriaBuilder().equal( thePath, param ) );
		TypedQuery<MultiTypedBasicAttributesEntity> query = em.createQuery( criteria );
		query.setParameter( param, new Byte[] { Byte.valueOf((byte)1), Byte.valueOf((byte)1), Byte.valueOf((byte)1) } );
		query.getResultList();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Class[] getAnnotatedClasses() {
		return new Class[] { MultiTypedBasicAttributesEntity.class };
	}
}
