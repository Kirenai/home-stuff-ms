package me.kirenai.re.nourishment.mapper;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NourishmentMapper {

    private final ModelMapper modelMapper;

    public Nourishment mapInNourishmentRequestToNourishment(NourishmentRequest nourishmentRequest) {
        return this.modelMapper.map(nourishmentRequest, Nourishment.class);
    }

    public NourishmentResponse mapOutNourishmentToNourishmentResponse(Nourishment nourishment) {
        return this.modelMapper.map(nourishment, NourishmentResponse.class);
    }

}