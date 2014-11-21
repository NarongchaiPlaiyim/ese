package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "location_items")
@Proxy(lazy=false)
public class LocationItemsModel extends AbstractModel{
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel locationId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletId;

    @Column(name = "create_by")
    private int createId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private int updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "isvalid", length = 1, nullable = false, columnDefinition = "int default 0")
    private int isvalid;

    @Column(name = "version")
    private int version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("locationId", locationId)
                .append("itemId", itemId)
                .append("palletId", palletId)
                .append("createId", createId)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .append("isvalid", isvalid)
                .append("version", version)
                .toString();
    }
}
