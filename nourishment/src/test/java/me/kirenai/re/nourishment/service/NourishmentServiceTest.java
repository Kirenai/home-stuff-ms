package me.kirenai.re.nourishment.service;

import me.kirenai.re.exception.nourishment.NourishmentNotFoundException;
import me.kirenai.re.nourishment.api.CategoryManager;
import me.kirenai.re.nourishment.api.UserManager;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import me.kirenai.re.nourishment.util.CategoryMocks;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import me.kirenai.re.nourishment.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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
    private UserManager userManager;
    @Mock
    private CategoryManager categoryManager;

//    @Test
//    @DisplayName("Should find a list of nourishment")
//    void findAllTest() {
//        int expectedSize = 3;
//        Pageable pageableMock = mock(Pageable.class);
//        Page<Nourishment> nourishmentPageMock = NourishmentMocks
//                .getNourishmentPage();
//        List<NourishmentResponse> nourishmentDtoListMock = NourishmentMocks
//                .getNourishmentResponseList();
//
//        when(this.nourishmentRepository.findAll(any(Pageable.class)))
//                .thenReturn(nourishmentPageMock);
//        when(this.mapper.mapOutNourishmentToNourishmentResponse(any(Nourishment.class)))
//                .thenReturn(nourishmentDtoListMock.get(0), nourishmentDtoListMock.get(1), nourishmentDtoListMock.get(2));
//
//        List<NourishmentResponse> response = this.nourishmentService.findAll(pageableMock);
//
//        assertEquals(expectedSize, response.size());
//        assertEquals(nourishmentDtoListMock, response);
//        verify(this.nourishmentRepository, times(1)).findAll(pageableMock);
//        verify(this.mapper, times(3)).mapOutNourishmentToNourishmentResponse(any());
//    }

    @Test
    @DisplayName("Should find a list of nourishment by user id")
    void findAllByUserIdTest() {
        List<NourishmentResponse> nourishmentResponse = NourishmentMocks
                .getNourishmentResponseList();

        when(this.nourishmentRepository.findByUserId(anyLong()))
                .thenReturn(Flux.fromIterable(NourishmentMocks.getNourishmentPage().toList()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse.get(0), nourishmentResponse.get(1), nourishmentResponse.get(2));

        Flux<NourishmentResponse> response = this.nourishmentService.findAllByUserId(1L);

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse.get(0))
                .expectNext(nourishmentResponse.get(1))
                .expectNext(nourishmentResponse.get(2))
                .verifyComplete();

        verify(this.nourishmentRepository, times(1)).findByUserId(anyLong());
        verify(this.mapper, times(3)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should find a nourishment when it exists by nourishmentId")
    void findOneTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();

        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Mono.just(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        Mono<NourishmentResponse> response = nourishmentService.findOne(1L);

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse)
                .verifyComplete();

        verify(this.nourishmentRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should throw an nourishment not found exception when GET")
    void findOneThrowExceptionGETTest() {
        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        Mono<NourishmentResponse> response = nourishmentService.findOne(1L);

        StepVerifier
                .create(response)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.nourishmentRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("Should find all nourishments by status when find all by is available when isAvailable is true")
    void findAllByIsAvailableTrueTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();

        when(this.nourishmentRepository.findByIsAvailable(anyBoolean()))
                .thenReturn(Flux.just(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        Flux<NourishmentResponse> response = this.nourishmentService.findAllByIsAvailable(true);

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse)
                .verifyComplete();

        verify(this.nourishmentRepository, times(1)).findByIsAvailable(anyBoolean());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should find all nourishments by status when find all by is available when isAvailable is false")
    void findAllByIsAvailableFalseTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();
        nourishmentResponse.setIsAvailable(false);

        when(this.nourishmentRepository.findByIsAvailable(anyBoolean()))
                .thenReturn(Flux.just(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        Flux<NourishmentResponse> response = this.nourishmentService.findAllByIsAvailable(false);

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse)
                .verifyComplete();

        verify(this.nourishmentRepository, times(1)).findByIsAvailable(anyBoolean());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should create a nourishment")
    void createTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();

        when(this.mapper.mapInNourishmentRequestToNourishment(any()))
                .thenReturn(NourishmentMocks.getNourishment());
        when(this.userManager.findUser(anyLong(), anyString()))
                .thenReturn(Mono.just(UserMocks.getUserResponse()));
        when(this.categoryManager.findCategory(anyLong(), anyString()))
                .thenReturn(Mono.just(CategoryMocks.getCategoryResponse()));
        when(this.nourishmentRepository.save(any()))
                .thenReturn(Mono.just(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        Mono<NourishmentResponse> response =
                this.nourishmentService.create(1L, 1L, NourishmentMocks.getNourishmentRequest(), "Bearer ");

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse)
                .verifyComplete();

        verify(this.mapper, times(1)).mapInNourishmentRequestToNourishment(any());
        verify(this.userManager, times(1)).findUser(anyLong(), anyString());
        verify(this.categoryManager, times(1)).findCategory(anyLong(), anyString());
        verify(this.nourishmentRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should update a nourishment")
    void updateTest() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();

        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Mono.just(NourishmentMocks.getNourishment()));
        when(this.nourishmentRepository.save(any()))
                .thenReturn(Mono.just(NourishmentMocks.getNourishment()));
        when(this.mapper.mapOutNourishmentToNourishmentResponse(any()))
                .thenReturn(nourishmentResponse);

        Mono<NourishmentResponse> response = this.nourishmentService.update(1L, NourishmentMocks.getNourishmentRequest());

        StepVerifier
                .create(response)
                .expectNext(nourishmentResponse)
                .verifyComplete();

        verify(this.nourishmentRepository, times(1)).findById(anyLong());
        verify(this.nourishmentRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapOutNourishmentToNourishmentResponse(any());
    }

    @Test
    @DisplayName("Should throw a nourishment not found exception when PUT")
    void updateThrowExceptionPUTTest() {
        when(this.nourishmentRepository.findById(anyLong()))
                .thenReturn(Mono.empty());

        Mono<NourishmentResponse> response = this.nourishmentService.update(1L, NourishmentMocks.getNourishmentRequest());

        StepVerifier
                .create(response)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.nourishmentRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should delete a nourishment")
    void deleteTest() {
        when(this.nourishmentRepository.findById(anyLong())).thenReturn(Mono.just(NourishmentMocks.getNourishment()));
        when(this.nourishmentRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Void> response = this.nourishmentService.delete(1L);

        StepVerifier
                .create(response.log())
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return an nourishment not found exception when DELETE")
    void shouldDeleteTest() {
        when(this.nourishmentRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> response = this.nourishmentService.delete(1L);

        StepVerifier
                .create(response)
                .expectError(NourishmentNotFoundException.class)
                .verify();
    }
}