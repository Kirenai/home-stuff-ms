package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.*;
import me.kirenai.re.consumption.enums.NourishmentTypeEnum;

import java.util.List;

public class NourishmentMocks {

    public static NourishmentResponse getNourishmentResponse() {
        return getNourishmentResponseList().get(0);
    }

    public static List<NourishmentResponse> getNourishmentResponseList() {
        return List.of(
                getNourishmentResponseMock(1L, "name1", "path1", "description1", true, getNourishmentTypeUnitResponse(10)),
                getNourishmentResponseMock(2L, "name2", "path2", "description2", true, getNourishmentTypePercentageResponse(65)),
                getNourishmentResponseMock(3L, "name3", "path3", "description3", true, getNourishmentTypeUnitResponse(10))
        );
    }

    public static NourishmentTypeUnitResponse getNourishmentTypeUnitResponse(Integer unit) {
        return NourishmentTypeUnitResponse.builder().nourishmentType(NourishmentTypeEnum.UNIT).unit(unit).build();
    }

    public static NourishmentTypePercentageResponse getNourishmentTypePercentageResponse(Integer percentage) {
        return NourishmentTypePercentageResponse.builder().nourishmentType(NourishmentTypeEnum.PERCENTAGE).percentage(percentage).build();
    }

    public static NourishmentResponse getNourishmentResponseMock(Long id,
                                                                 String name,
                                                                 String path,
                                                                 String description,
                                                                 Boolean isAvailable,
                                                                 NourishmentTypeResponse type) {
        return NourishmentResponse.builder()
                .nourishmentId(id)
                .name(name)
                .imageUrl(path)
                .description(description)
                .isAvailable(isAvailable)
                .type(type)
                .build();
    }

    public static NourishmentRequest getNourishmentRequest() {
        return NourishmentRequest.builder()
                .name("name1")
                .imageUrl("path1")
                .description("description1")
                .type(getNourishmentTypeUnitRequest(10))
                .build();
    }

    public static NourishmentTypeUnitRequest getNourishmentTypeUnitRequest(Integer unit) {
        return NourishmentTypeUnitRequest.builder().unit(unit).build();
    }

}
