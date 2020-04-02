package com.imagination.cbs.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;

import org.springframework.stereotype.Component;

import com.imagination.cbs.exception.CBSValidationException;


@Component(value = "resultTrasnsformerHelper")
public class ResultTrasnsformerHelper {


	@SuppressWarnings("unchecked")
	public <T> List<T> transormListOfTupleToListOfInputObject(List<Tuple> listOfTuples, Class<T> classz)  {
		
		List<T> objectList = new ArrayList<>();

		for (Tuple tuple : listOfTuples) {
			Object object;
			try {
				object = classz.newInstance();
			} catch (ReflectiveOperationException roe) {
				throw new CBSValidationException("Not able to create object for '" + classz.getName() + "' class");
			}
			List<TupleElement<?>> elements = tuple.getElements();
			Field field = null;
			Method declaredMethod = null;
			for (TupleElement<?> element : elements) {
				try {
					field = classz.getDeclaredField(element.getAlias());

				} catch (NoSuchFieldException e) {
					throw new CBSValidationException("No field present like '" + element.getAlias() + "' in " + classz.getName());
				}
				try {
					declaredMethod = classz.getDeclaredMethod(
							"set" + element.getAlias().substring(0, 1).toUpperCase() + element.getAlias().substring(1), field.getType());
					declaredMethod.invoke(object, tuple.get(element.getAlias()));
				} catch (Exception e) {
				throw new CBSValidationException("No setter method present for '" + element.getAlias() + "' field in " + classz.getName() +"OR Not able to call method");
				}

			}
			objectList.add((T) object);
		}

		return objectList;
	}
}
