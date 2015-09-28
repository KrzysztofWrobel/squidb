/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.velocicaptor.squidb.gson;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.aptutils.utils.AptUtils;
import com.yahoo.aptutils.writer.JavaFileWriter;
import com.yahoo.aptutils.writer.expressions.Expressions;
import com.yahoo.squidb.processor.data.ModelSpec;
import com.yahoo.squidb.processor.plugins.defaults.properties.generators.BasicStringPropertyGenerator;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.VariableElement;

public class GsonPropertyGenerator extends BasicStringPropertyGenerator {

    protected final DeclaredTypeName fieldType;

    public GsonPropertyGenerator(ModelSpec<?> modelSpec, VariableElement field, DeclaredTypeName fieldType,
                                 AptUtils utils) {
        super(modelSpec, field, utils);
        this.fieldType = fieldType;
    }

    @Override
    protected void registerAdditionalImports(Set<DeclaredTypeName> imports) {
        super.registerAdditionalImports(imports);
        imports.add(GsonTypeConstants.SQUIDB_GSON_SUPPORT);
    }

    @Override
    protected DeclaredTypeName getTypeForGetAndSet() {
        return fieldType;
    }

    @Override
    protected void writeGetterBody(JavaFileWriter writer) throws IOException {
        String typeName = fieldType.getPackageName() + "." + fieldType.getSimpleName();
        System.out.println("Type name: " + typeName);

        writer.writeStatement(Expressions.staticMethod(GsonTypeConstants.SQUIDB_GSON_SUPPORT, "fromJson",
                "this", propertyName, Expressions.classObject(new DeclaredTypeName(typeName))).returnExpr());
    }


    @Override
    protected void writeSetterBody(JavaFileWriter writer, String argName) throws IOException {
        writer.writeStatement(Expressions.staticMethod(GsonTypeConstants.SQUIDB_GSON_SUPPORT, "toJson",
                "this", propertyName, argName));
        writer.writeStringStatement("return this");
    }

}
