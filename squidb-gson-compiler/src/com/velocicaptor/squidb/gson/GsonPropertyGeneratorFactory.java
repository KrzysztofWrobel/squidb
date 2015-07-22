/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.velocicaptor.squidb.gson;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.aptutils.utils.AptUtils;
import com.yahoo.squidb.processor.properties.factory.PluggablePropertyGeneratorFactory;
import com.yahoo.squidb.processor.properties.generators.PropertyGenerator;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class GsonPropertyGeneratorFactory extends PluggablePropertyGeneratorFactory {

    public GsonPropertyGeneratorFactory(AptUtils utils) {
        super(utils);
    }

    @Override
    public boolean canHandleElement(VariableElement element, DeclaredTypeName elementType, TypeElement parentElement) {
        if (element.getAnnotation(GsonProperty.class) == null) {
            return false;
        } else {
            System.out.println("Handling element: " + elementType.toString());
            return true;
        }

    }

    @Override
    public PropertyGenerator getPropertyGenerator(VariableElement element, DeclaredTypeName elementType,
                                                  DeclaredTypeName modelClass) {

        return new GsonPropertyGenerator(element, elementType, modelClass, utils);
    }

}
