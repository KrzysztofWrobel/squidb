package com.velocicaptor.squidb.gson;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.squidb.processor.data.ModelSpec;
import com.yahoo.squidb.processor.plugins.PluginEnvironment;
import com.yahoo.squidb.processor.plugins.defaults.properties.TableModelSpecFieldPlugin;
import com.yahoo.squidb.processor.plugins.defaults.properties.generators.PropertyGenerator;

import javax.lang.model.element.VariableElement;

/**
 * Created by krzysztofwrobel on 25/09/15.
 */
public class GsonFieldPlugin extends TableModelSpecFieldPlugin {
    public GsonFieldPlugin(ModelSpec<?> modelSpec, PluginEnvironment pluginEnv) {
        super(modelSpec, pluginEnv);
    }

    @Override
    public boolean processVariableElement(VariableElement field, DeclaredTypeName fieldType) {
        if (field.getAnnotation(GsonProperty.class) == null) {
            return false;
        }
        return super.processVariableElement(field, fieldType);
    }

    @Override
    protected boolean hasPropertyGeneratorForField(VariableElement field, DeclaredTypeName fieldType) {
        return true;
    }

    @Override
    public PropertyGenerator getPropertyGenerator(VariableElement field, DeclaredTypeName fieldType) {

        return new GsonPropertyGenerator(modelSpec, field, fieldType, utils);
    }
}
