package org.kt.parttime.parttime.dto;

import lombok.Getter;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.utils.PriceUtils;

@Getter
public class PartTimeBriefDto {
    private Long id;
    private String name;
    private Integer hourPrice;

    public PartTimeBriefDto(PartTime partTime){
        this.id = partTime.getId();
        this.name = partTime.getName();
        this.hourPrice = partTime.getHourPrice();
    }

    public String getFormattedHourPrice(){
        return PriceUtils.format(this.hourPrice);
    }

}
