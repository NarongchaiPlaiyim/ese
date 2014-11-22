package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "stock_inout_note")
@Proxy(lazy=false)
public class MSStockInOutNoteModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "inout_code")
    private String inoutCode;

    @Column(name = "inout_note")
    private String inoutNote;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("type", type)
                .append("inoutCode", inoutCode)
                .append("inoutNote", inoutNote)
                .append("remark", remark)
                .append("isValid", isValid)
                .append("version", version)
                .toString();
    }
}
