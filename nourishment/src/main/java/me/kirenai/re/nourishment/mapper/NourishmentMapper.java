package me.kirenai.re.nourishment.mapper;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class NourishmentMapper {

    public abstract Nourishment mapInNourishmentRequestToNourishment(NourishmentRequest nourishmentRequest);

    public abstract NourishmentResponse mapOutNourishmentToNourishmentResponse(Nourishment nourishment);

    @BeforeMapping
    public void mapInLog(NourishmentRequest nourishmentRequest) {
       log.info("Invoking NourishmentMapper.mapInNourishmentRequestToNourishment method");
    }

    @BeforeMapping
    public void mapOutLog(Nourishment nourishment) {
        log.info("Invoking NourishmentMapper.mapOutNourishmentToNourishmentResponse method");
    }

}