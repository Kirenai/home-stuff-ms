package me.kirenai.re.nourishment.service;

import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import me.kirenai.re.nourishment.util.CategoryMocks;
import me.kirenai.re.nourishment.util.NourishmentClient;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import me.kirenai.re.nourishment.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NourishmentServiceTest {

    @InjectMocks
    private NourishmentService nourishmentService;
    @Mock
    private NourishmentRepository nourishmentRepository;
    @Mock
    private NourishmentMapper mapper;
    @Mock
    private NourishmentClient client;

    @Test
    @DisplayName("Should find a list of nourishment")
    void findAllTest() {
        int expectedSize = 3;
        Pageable pageableMock = mock(Pageable.class);
        Page<Nourishment> nourishmentPageMock = NourishmentMocks
                .getNourishmentPage();
        List<NourishmentResponse> nourishmentDtoListMock = NourishmentMocks
                .getNourishmentResponseList();

        when(this.nourishmentRepository.findAll(any(Pageable.class)))
                .thenReturn(nourishmentPageMock);
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any(Nourishment.class)))
                .thenReturn(nourishmentDtoListMock.get(0), nourishmentDtoListMock.get(1), nourishmentDtoListMock.get(2));

        List<NourishmentResponse> response = this.nourishmentService.findAll(pageableMock);

        assertEquals(expectedSize, response.size());
        assertEquals(nourishmentDtoListMock, response);
        verify(this.nourishmentRepository, times(1)).findAll(pageableMock);
        verify(this.mapper, times(3)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should find a list of nourishment by user id")
    void findAllByUserIdTest() {
        List<NourishmentResponse> nourishmentResponse = NourishmentMocks
                .getNourishmentResponseList();

        when(this.nourishmentRepository.findByUserId(anyLong()))
                .thenReturn(NourishmentMocks.getNourishmentPage().toList());
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse.get(0), nourishmentResponse.get(1), nourishmentResponse.get(2));

        List<NourishmentResponse> response = this.nourishmentService.findAllByUserId(1L);

        assertNotNull(response);
        assertEquals(3, response.size());
        assertEquals(nourishmentResponse, response);
        verify(this.nourishmentRepository, times(1)).findByUserId(anyLong());
        verify(this.mapper, times(3)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should find a nourishment when it exists by nourishmentId")
    void findOneTest() {
        NourishmentResponse response = NourishmentMocks.getNourishmentResponse();

        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(response);

        NourishmentResponse result = nourishmentService.findOne(1L);

        assertEquals(response, result);
        verify(this.nourishmentRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }


    @Test
    @DisplayName("Should find all nourishments by status when find all by is available when isAvailable is true")
    void findAllByIsAvailableTrueTest() {
        when(this.nourishmentRepository.findByIsAvailable(anyBoolean()))
                .thenReturn(List.of(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(NourishmentMocks.getNourishmentResponse());

        List<NourishmentResponse> allNourishmentByStatus = this.nourishmentService.findAllByIsAvailable(true);

        assertTrue(allNourishmentByStatus.get(0).getIsAvailable());
        verify(this.nourishmentRepository, times(1)).findByIsAvailable(anyBoolean());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should find all nourishments by status when find all by is available when isAvailable is false")
    void findAllByIsAvailableFalseTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();
        nourishmentResponse.setIsAvailable(false);

        when(this.nourishmentRepository.findByIsAvailable(anyBoolean()))
                .thenReturn(List.of(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        List<NourishmentResponse> allNourishmentByStatus = this.nourishmentService.findAllByIsAvailable(false);

        assertFalse(allNourishmentByStatus.get(0).getIsAvailable());
        verify(this.nourishmentRepository, times(1)).findByIsAvailable(anyBoolean());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should create a nourishment")
    void createTest() {
        when(this.mapper.mapOutNourishmentRequestToNourishment(any()))
                .thenReturn(NourishmentMocks.getNourishment());
        when(this.client.getResponse(anyLong(), anyString(), eq(UserResponse.class)))
                .thenReturn(UserMocks.getUserResponse());
        when(this.client.getResponse(anyLong(), anyString(), eq(CategoryResponse.class)))
                .thenReturn(CategoryMocks.getCategoryResponse());
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(NourishmentMocks.getNourishmentResponse());


        NourishmentResponse response =
                this.nourishmentService.create(1L, 1L, NourishmentMocks.getNourishmentRequest());

        assertNotNull(response);
        verify(this.mapper, times(1)).mapOutNourishmentRequestToNourishment(any());
        verify(this.client, times(2)).getResponse(anyLong(), anyString(), any());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should update a nourishment")
    void updateTest() {

        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(NourishmentMocks.getNourishmentResponse());

        NourishmentResponse response = this.nourishmentService.update(1L, NourishmentMocks.getNourishmentRequest());

        assertNotNull(response);
        verify(this.nourishmentRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should delete a nourishment")
    void deleteTest() {
        doNothing().when(this.nourishmentRepository).deleteById(anyLong());

        this.nourishmentService.delete(1L);

        verify(this.nourishmentRepository, times(1)).deleteById(anyLong());
    }

}