package me.kirenai.re.nourishment.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentTypePercentageRequest;
import me.kirenai.re.nourishment.dto.NourishmentTypeUnitRequest;
import me.kirenai.re.nourishment.entity.Nourishment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {

    public static Nourishment loadDataToNourishment(Nourishment nourishment, NourishmentRequest nourishmentRequest) {
        nourishment.setName(nourishmentRequest.getName());
        nourishment.setDescription(nourishmentRequest.getDescription());
        nourishment.setImageUrl(nourishmentRequest.getImageUrl());
        if (nourishmentRequest.getType() instanceof NourishmentTypeUnitRequest unit) {
            nourishment.setUnit(unit.getUnit());
            if (unit.getUnit() == 0) nourishment.setIsAvailable(Boolean.FALSE);
        }
        if (nourishmentRequest.getType() instanceof NourishmentTypePercentageRequest percentage) {
            nourishment.setPercentage(percentage.getPercentage());
            if (percentage.getPercentage() == 0) nourishment.setIsAvailable(Boolean.FALSE);
        }
        return nourishment;
    }

}
