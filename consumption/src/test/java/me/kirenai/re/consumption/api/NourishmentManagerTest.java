package me.kirenai.re.consumption.api;

import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.util.NourishmentMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NourishmentManagerTest {

    @InjectMocks
    private NourishmentManager nourishmentManager;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void findNourishmentTest() {
        when(this.restTemplate.exchange(anyString(), any(), any(), eq(NourishmentResponse.class), anyLong()))
                .thenReturn(NourishmentMocks.getNourishmentResponseEntity());
        this.nourishmentManager.findNourishment(1L);
        verify(this.restTemplate, times(1)).exchange(anyString(), any(), any(), eq(NourishmentResponse.class), anyLong());
        verify(this.jwtTokenProvider, times(1)).getCurrentTokenAsHeader();
    }

    @Test
    public void updateNourishmentTest() {
        this.nourishmentManager.updateNourishment(new NourishmentRequest(), NourishmentMocks.getNourishmentResponse());
        verify(this.restTemplate, times(1)).exchange(anyString(), any(), any(), eq(Void.class), anyLong());
        verify(this.jwtTokenProvider, times(1)).getCurrentTokenAsHeader();
    }

}