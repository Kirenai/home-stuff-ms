package me.kirenai.re.nourishment.mapper;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.*;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.enums.NourishmentTypeEnum;
import me.kirenai.re.nourishment.repository.NourishmentTypeRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class NourishmentMapper {

    private NourishmentTypeRepository nourishmentTypeRepository;

    public Mono<Nourishment> mapInNourishmentToMono(NourishmentRequest nourishmentRequest) {
        if (Objects.isNull(nourishmentRequest)) return Mono.empty();

        Nourishment nourishment = this.mapInNourishmentRequestToNourishment(nourishmentRequest);

        if (nourishmentRequest.getType() instanceof NourishmentTypeUnitRequest unit) {
            nourishment.setUnit(unit.getUnit());
            return this.nourishmentTypeRepository.findByName(unit.getNourishmentType().name())
                    .map(type -> {
                        nourishment.setNourishmentTypeId(type.getNourishmentTypeId());
                        return nourishment;
                    });
        }
        if (nourishmentRequest.getType() instanceof NourishmentTypePercentageRequest percentage) {
            nourishment.setPercentage(percentage.getPercentage());
            return this.nourishmentTypeRepository.findByName(percentage.getNourishmentType().name())
                    .map(type -> {
                        nourishment.setNourishmentTypeId(type.getNourishmentTypeId());
                        return nourishment;
                    });
        }

        return Mono.empty();
    }

    public abstract Nourishment mapInNourishmentRequestToNourishment(NourishmentRequest nourishmentRequest);

    public Mono<NourishmentResponse> mapOutNourishmentResponseToMono(Nourishment nourishment) {
        if (Objects.isNull(nourishment)) return Mono.empty();

        NourishmentResponse nourishmentResponse = this.mapOutNourishmentToNourishmentResponse(nourishment);

        return this.nourishmentTypeRepository.findById(nourishment.getNourishmentTypeId())
                .map(nourishmentType -> {
                    if (nourishmentType.getName().equals(NourishmentTypeEnum.UNIT.getName())) {
                        nourishmentResponse.setType(this.mapOutNourishmentTypeUnit(nourishment, nourishmentType.getName()));
                    }
                    if (nourishmentType.getName().equals(NourishmentTypeEnum.PERCENTAGE.getName())) {
                        nourishmentResponse.setType(this.mapOutNourishmentTypePercentage(nourishment, nourishmentType.getName()));
                    }
                    return nourishmentResponse;
                });
    }

    public abstract NourishmentResponse mapOutNourishmentToNourishmentResponse(Nourishment nourishment);

    @Mapping(target = "nourishmentType", source = "type")
    @Mapping(target = "unit", source = "nourishment.unit")
    protected abstract NourishmentTypeUnitResponse mapOutNourishmentTypeUnit(Nourishment nourishment, String type);

    @Mapping(target = "nourishmentType", source = "type")
    @Mapping(target = "percentage", source = "nourishment.percentage")
    protected abstract NourishmentTypePercentageResponse mapOutNourishmentTypePercentage(Nourishment nourishment, String type);

    @BeforeMapping
    public void mapInLog(NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentMapper.mapInNourishmentRequestToNourishment method");
    }

    @BeforeMapping
    public void mapOutLog(Nourishment nourishment, @MappingTarget NourishmentResponse.NourishmentResponseBuilder nourishmentResponse) {
        log.info("Invoking NourishmentMapper.mapOutNourishmentToNourishmentResponse method");
    }

    @Autowired
    public void setNourishmentTypeRepository(NourishmentTypeRepository nourishmentTypeRepository) {
        this.nourishmentTypeRepository = nourishmentTypeRepository;
    }

}