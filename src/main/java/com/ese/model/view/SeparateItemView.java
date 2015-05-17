package com.ese.model.view;

import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SeparateItemView {
    private String headerSubName;
    private int itemId;
    private List<ContainerItemView> containerItemViewList = Utils.getEmptyList();

    public SeparateItemView() {
    }

    public SeparateItemView(String header, int id) {
        headerSubName = header;
        itemId = id;
    }

    public int getSumQty(){
        int sum = 0;
        for (ContainerItemView view : containerItemViewList){
            sum += view.getContainerQty();
        }

        return sum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("headerSubName", headerSubName)
                .append("itemId", itemId)
                .append("containerItemViewList", containerItemViewList)
                .toString();
    }
}
