package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;

import java.util.List;

public class NourishmentMocks {

    public static NourishmentResponse getNourishmentResponse() {
        return getNourishmentResponseList().get(0);
    }

    public static List<NourishmentResponse> getNourishmentResponseList() {
        return List.of(
                getNourishmentResponseMock(1L, "name1", "path1", "description1", true, 10, null),
                getNourishmentResponseMock(2L, "name2", "path2", "description2", true, null, 65),
                getNourishmentResponseMock(3L, "name3", "path3", "description3", true, 20, null)
        );
    }

    public static NourishmentResponse getNourishmentResponseMock(Long id,
                                                                 String name,
                                                                 String path,
                                                                 String description,
                                                                 Boolean isAvailable,
                                                                 Integer unit,
                                                                 Integer percentage) {
        return NourishmentResponse.builder()
                .nourishmentId(id)
                .name(name)
                .imageUrl(path)
                .description(description)
                .isAvailable(isAvailable)
                .unit(unit)
                .percentage(percentage)
                .build();
    }

    public static NourishmentRequest getNourishmentRequest() {
        return NourishmentRequest.builder()
                .name("name1")
                .imageUrl("path1")
                .description("description1")
                .unit(10)
                .build();
    }
}
