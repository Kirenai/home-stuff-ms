package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class NourishmentMocks {

    public static NourishmentResponse getNourishmentResponseMock(Long id,
                                                                 String name,
                                                                 String path,
                                                                 String description,
                                                                 Boolean isAvailable) {
        return NourishmentResponse.builder()
                .nourishmentId(id)
                .name(name)
                .imageUrl(path)
                .description(description)
                .isAvailable(isAvailable)
                .build();
    }

    public static List<NourishmentResponse> getNourishmentResponseList() {
        return List.of(
                getNourishmentResponseMock(1L, "name1", "path1", "description1", true),
                getNourishmentResponseMock(2L, "name2", "path2", "description2", true),
                getNourishmentResponseMock(3L, "name3", "path3", "description3", true)
        );
    }

    public static NourishmentResponse getNourishmentResponse() {
        return getNourishmentResponseList().get(0);
    }

    public static NourishmentRequest getNourishmentRequest() {
        return NourishmentRequest.builder()
                .name("name")
                .imageUrl("imageUrl")
                .description("description")
                .build();
    }

    public static Nourishment getNourishmentMock(Long id,
                                                 String name,
                                                 String path,
                                                 String description) {
        return Nourishment.builder()
                .nourishmentId(id)
                .name(name)
                .imageUrl(path)
                .description(description)
                .isAvailable(true)
                .build();
    }

    public static Page<Nourishment> getNourishmentPage() {
        return new PageImpl<>(List.of(
                getNourishmentMock(1L, "name1", "path1", "description1"),
                getNourishmentMock(2L, "name2", "path2", "description2"),
                getNourishmentMock(3L, "name3", "path3", "description3")
        ));
    }

    public static Nourishment getNourishment() {
        return getNourishmentMock(1L, "name1", "path1", "description1");
    }
}