package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers;

public interface Mapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

}
