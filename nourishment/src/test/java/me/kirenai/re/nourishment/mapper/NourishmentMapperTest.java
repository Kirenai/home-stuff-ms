package me.kirenai.re.nourishment.mapper;

import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NourishmentMapperTest {

    @InjectMocks
    private NourishmentMapper mapper;
    @Spy
    private ModelMapper modelMapper;

    @Test
    @DisplayName("mapInTest")
    public void mapInNourishmentRequestToNourishmentTest() {
        NourishmentRequest input = NourishmentMocks.getNourishmentRequest();
        Nourishment request = this.mapper.mapInNourishmentRequestToNourishment(input);

        assertNotNull(request);
        assertNotNull(request.getName());
        assertNotNull(request.getImageUrl());
        assertNotNull(request.getDescription());
        assertNotNull(request.getPercentage());

        assertEquals(input.getName(), request.getName());
        assertEquals(input.getImageUrl(), request.getImageUrl());
        assertEquals(input.getDescription(), request.getDescription());
        assertEquals(input.getPercentage(), request.getPercentage());

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

    @Test
    @DisplayName("mapOutTest")
    public void mapOutNourishmentToNourishmentResponseTest() {
        Nourishment input = NourishmentMocks.getNourishment();
        NourishmentResponse response = this.mapper.mapOutNourishmentToNourishmentResponse(input);

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

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }


}