package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.*;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.enums.NourishmentTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class NourishmentMocks {

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

    public static NourishmentTypeUnitResponse getNourishmentTypeUnitResponse(Integer unit) {
        return NourishmentTypeUnitResponse.builder().nourishmentType(NourishmentTypeEnum.UNIT).unit(unit).build();
    }

    public static NourishmentTypePercentageResponse getNourishmentTypePercentageResponse(Integer percentage) {
        return NourishmentTypePercentageResponse.builder().nourishmentType(NourishmentTypeEnum.PERCENTAGE).percentage(percentage).build();
    }

    public static List<NourishmentResponse> getNourishmentResponseList() {
        return List.of(
                getNourishmentResponseMock(1L, "name1", "path1", "description1", true, getNourishmentTypeUnitResponse(10)),
                getNourishmentResponseMock(2L, "name2", "path2", "description2", true, getNourishmentTypePercentageResponse(65)),
                getNourishmentResponseMock(3L, "name3", "path3", "description3", true, getNourishmentTypeUnitResponse(10))
        );
    }

    public static NourishmentResponse getNourishmentResponse() {
        return getNourishmentResponseList().get(0);
    }

    public static NourishmentRequest getNourishmentRequest() {
        NourishmentTypePercentageRequest percentage = NourishmentTypePercentageRequest.builder().percentage(100).nourishmentType(NourishmentTypeEnum.PERCENTAGE).build();
        return NourishmentRequest.builder()
                .name("name")
                .imageUrl("imageUrl")
                .description("description")
                .type(percentage)
                .build();
    }
    public static NourishmentRequest getNourishmentRequestUnit() {
        NourishmentTypeUnitRequest unit = NourishmentTypeUnitRequest.builder().unit(15).nourishmentType(NourishmentTypeEnum.UNIT).build();
        return NourishmentRequest.builder()
                .name("name")
                .imageUrl("imageUrl")
                .description("description")
                .type(unit)
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
        Nourishment nourishmentMock = getNourishmentMock(1L, "name1", "path1", "description1");
        nourishmentMock.setNourishmentTypeId(1L);
        nourishmentMock.setPercentage(100);
        return nourishmentMock;
    }
}
