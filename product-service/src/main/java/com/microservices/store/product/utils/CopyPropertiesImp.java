package com.microservices.store.product.utils;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CopyPropertiesImp implements CopyProperties {

    private ModelMapper mapper;
    public CopyPropertiesImp(){
        mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    @Override
    public void copyPropertiesWithoutNull(Object source, Object target) {
        mapper.map(source, target);
    }
}
