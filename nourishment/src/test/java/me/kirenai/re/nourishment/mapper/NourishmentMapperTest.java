package me.kirenai.re.nourishment.mapper;

import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.dto.NourishmentTypePercentageRequest;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.enums.NourishmentTypeEnum;
import me.kirenai.re.nourishment.repository.NourishmentTypeRepository;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import me.kirenai.re.nourishment.util.NourishmentTypeMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NourishmentMapperTest {

    @InjectMocks
    private final NourishmentMapper mapper = Mappers.getMapper(NourishmentMapper.class);
    @Mock
    private NourishmentTypeRepository nourishmentTypeRepository;

    @Test
    @DisplayName("mapInTest")
    public void mapInNourishmentRequestToNourishmentTest() throws IOException {
        when(this.nourishmentTypeRepository.findByName(anyString()))
                .thenReturn(Mono.just(NourishmentTypeMocks.getInstance().getNourishmentType()));

        NourishmentRequest input = NourishmentMocks.getNourishmentRequest();
        Nourishment request = this.mapper.mapInNourishmentToMono(input).block();


        assertNotNull(request);
        assertNotNull(request.getName());
        assertNotNull(request.getImageUrl());
        assertNotNull(request.getDescription());
        assertNotNull(request.getPercentage());
        assertNotNull(request.getNourishmentTypeId());

        assertEquals(input.getName(), request.getName());
        assertEquals(input.getImageUrl(), request.getImageUrl());
        assertEquals(input.getDescription(), request.getDescription());
        assertEquals(((NourishmentTypePercentageRequest) input.getType()).getPercentage(), request.getPercentage());
        assertEquals(((NourishmentTypePercentageRequest) input.getType()).getNourishmentType(), NourishmentTypeEnum.PERCENTAGE);
    }

    @Test
    @DisplayName("mapOutTest")
    public void mapOutNourishmentToNourishmentResponseTest() throws IOException {
        when(this.nourishmentTypeRepository.findById(anyLong()))
                .thenReturn(Mono.just(NourishmentTypeMocks.getInstance().getNourishmentType()));

        Nourishment input = NourishmentMocks.getNourishment();
        NourishmentResponse response = this.mapper.mapOutNourishmentResponseToMono(input).block();

        assertNotNull(response);
        assertNotNull(response.getNourishmentId());
        assertNotNull(response.getName());
        assertNotNull(response.getImageUrl());
        assertNotNull(response.getDescription());
        assertNotNull(response.getIsAvailable());

        assertEquals(input.getNourishmentId(), response.getNourishmentId());
        assertEquals(input.getName(), response.getName());
        assertEquals(input.getImageUrl(), response.getImageUrl());
        assertEquals(input.getDescription(), response.getDescription());
        assertEquals(input.getIsAvailable(), response.getIsAvailable());
    }

}