package com.googlecode.transloader.test.function;

import com.googlecode.transloader.TransloaderFactory;
import com.googlecode.transloader.test.BaseTestCase;
import com.googlecode.transloader.test.Triangulator;
import com.googlecode.transloader.test.fixture.HiearchyWithFieldsBottom;
import com.googlecode.transloader.test.fixture.IndependentClassLoader;
import com.googlecode.transloader.test.fixture.NonCommonJavaObject;
import com.googlecode.transloader.test.fixture.NonCommonJavaType;
import com.googlecode.transloader.test.fixture.SelfAndChildReferencingParent;
import com.googlecode.transloader.test.fixture.SelfAndParentReferencingChild;
import com.googlecode.transloader.test.fixture.SerializableWithAnonymousClassFields;
import com.googlecode.transloader.test.fixture.WithArrayFields;
import com.googlecode.transloader.test.fixture.WithListFields;
import com.googlecode.transloader.test.fixture.WithNonCommonJavaFields;
import com.googlecode.transloader.test.fixture.WithPrimitiveFields;
import com.googlecode.transloader.test.fixture.WithStringField;

public abstract class CloningTestCase extends BaseTestCase {

	protected Object assertDeeplyClonedToOtherClassLoader(NonCommonJavaType original) throws Exception {
		String originalString = original.toString();
		Object clone = getTransloaderFactory().wrap(original).cloneWith(IndependentClassLoader.getInstance());
		assertNotSame(original, clone);
		assertEqualExceptForClassLoader(originalString, clone);
		return clone;
	}

	protected abstract TransloaderFactory getTransloaderFactory();

	public void testClonesObjectsWithPrimitiveFields() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new WithPrimitiveFields());
	}

	public void testClonesObjectsNotOfCommonJavaTypes() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new NonCommonJavaObject());
	}

	public void testClonesObjectsWithFieldsOfCommonJavaTypes() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new WithStringField(Triangulator.anyString()));
	}

	public void testClonesObjectsWithFieldsNotOfCommonJavaTypes() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new WithNonCommonJavaFields(new WithStringField(Triangulator.anyString())));
	}

	public void testClonesObjectsWithArrayFields() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new WithArrayFields());
	}

	public void testClonesFieldsThroughoutHierarchies() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new HiearchyWithFieldsBottom(new NonCommonJavaObject(),
				Triangulator.anyInt(), Triangulator.anyString(), Triangulator.eitherBoolean()));
	}

	public void testClonesObjectsOfSerializableAnonymousClasses() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new SerializableWithAnonymousClassFields(Triangulator.anyInteger()));
	}

	public void testClonesObjectsWithListFields() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new WithListFields());
	}

	public void testClonesAllFieldsWithCircularReferences() throws Exception {
		cloneWithCircularReferences();
	}

	public void testClonesAllFieldsWithCircularReferencesConcurrently() throws Exception {
		cloneWithCircularReferences();
	}

	public void testClonesAllFieldsWithCircularReferencesYetMoreConcurrently() throws Exception {
		cloneWithCircularReferences();
	}

	private void cloneWithCircularReferences() throws Exception {
		assertDeeplyClonedToOtherClassLoader(new SelfAndParentReferencingChild(Triangulator.anyString(),
				new SelfAndChildReferencingParent(Triangulator.anyString())));
	}
}