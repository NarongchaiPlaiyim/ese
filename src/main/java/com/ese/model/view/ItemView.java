package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class ItemView {
    private int id;
    private String itemId;
    private String itemName;
    private String itemGrpupId;
    private int itemType;
    private String packagingGroupId;
    private float netWeight;
    private float dSG_MaxStock;
    private float dSG_MinStock;
    private float dSG_SafetyStock;
    private String dSG_InternalItemId;
    private String dSGSize;
    private float dSG_RimWidth;
    private String dSG_MaxLoad;
    private float dSG_MaxInflation;
    private float dSG_MaxSpeed;
    private String dSG_E_Mark;
    private String dSG_ETRTO;
    private String dSG_LoadIndex;
    private String dSG_SpeedIndex;
    private float dSG_RecommendedRim;
    private float dSG_TreadDept;
    private String dSG_AlternativeRim;
    private float dSG_OfMold;
    private String dSG_Valve;
    private String dSG_ID;
    private float dSG_QtyPerPallet;
    private Integer dSG_CurPerShif;
    private String dSG_ColorId1;
    private String dSG_ColorSeparate1;
    private String dSG_ColorId2;
    private String dSG_ColorSeparate2;
    private String dSG_ColorId3;
    private Date dSG_CreateDate;
    private String dSG_Originator;
    private String dSG_ImageDir;
    private String dSGProductGroupID;
    private String dSGSubGroupID;
    private String dSGPatternID;
    private String dSGBandID;
    private String dSG_Model;
    private Integer dSGTyre_Types;
    private String tyre_Type;
    private String dSGRimSize;
    private String dSGPLYRating;
    private String dSGThaiItemDescription;
    private Integer dSG_EU;
    private String dSG_ItemLabelId;
    private String dSG_DescriptionTextId;
    private String dSG_GradeProductId;
    private String dSG_FeatureProductId;
    private String dSG_DescriptionPrefixId;
    private String dSG_GroupDescription;
    private String dSG_CopyFrom;
    private String dSG_MKProductGroupId;
    private String dSG_MKSubproductGroupId;
    private String dSG_MKProductTypeId;
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("itemName", itemName)
                .append("itemGrpupId", itemGrpupId)
                .append("itemType", itemType)
                .append("packagingGroupId", packagingGroupId)
                .append("netWeight", netWeight)
                .append("dSG_MaxStock", dSG_MaxStock)
                .append("dSG_MinStock", dSG_MinStock)
                .append("dSG_SafetyStock", dSG_SafetyStock)
                .append("dSG_InternalItemId", dSG_InternalItemId)
                .append("dSGSize", dSGSize)
                .append("dSG_RimWidth", dSG_RimWidth)
                .append("dSG_MaxLoad", dSG_MaxLoad)
                .append("dSG_MaxInflation", dSG_MaxInflation)
                .append("dSG_MaxSpeed", dSG_MaxSpeed)
                .append("dSG_E_Mark", dSG_E_Mark)
                .append("dSG_ETRTO", dSG_ETRTO)
                .append("dSG_LoadIndex", dSG_LoadIndex)
                .append("dSG_SpeedIndex", dSG_SpeedIndex)
                .append("dSG_RecommendedRim", dSG_RecommendedRim)
                .append("dSG_TreadDept", dSG_TreadDept)
                .append("dSG_AlternativeRim", dSG_AlternativeRim)
                .append("dSG_OfMold", dSG_OfMold)
                .append("dSG_Valve", dSG_Valve)
                .append("dSG_ID", dSG_ID)
                .append("dSG_QtyPerPallet", dSG_QtyPerPallet)
                .append("dSG_CurPerShif", dSG_CurPerShif)
                .append("dSG_ColorId1", dSG_ColorId1)
                .append("dSG_ColorSeparate1", dSG_ColorSeparate1)
                .append("dSG_ColorId2", dSG_ColorId2)
                .append("dSG_ColorSeparate2", dSG_ColorSeparate2)
                .append("dSG_ColorId3", dSG_ColorId3)
                .append("dSG_CreateDate", dSG_CreateDate)
                .append("dSG_Originator", dSG_Originator)
                .append("dSG_ImageDir", dSG_ImageDir)
                .append("dSGProductGroupID", dSGProductGroupID)
                .append("dSGSubGroupID", dSGSubGroupID)
                .append("dSGPatternID", dSGPatternID)
                .append("dSGBandID", dSGBandID)
                .append("dSG_Model", dSG_Model)
                .append("dSGTyre_Types", dSGTyre_Types)
                .append("tyre_Type", tyre_Type)
                .append("dSGRimSize", dSGRimSize)
                .append("dSGPLYRating", dSGPLYRating)
                .append("dSGThaiItemDescription", dSGThaiItemDescription)
                .append("dSG_EU", dSG_EU)
                .append("dSG_ItemLabelId", dSG_ItemLabelId)
                .append("dSG_DescriptionTextId", dSG_DescriptionTextId)
                .append("dSG_GradeProductId", dSG_GradeProductId)
                .append("dSG_FeatureProductId", dSG_FeatureProductId)
                .append("dSG_DescriptionPrefixId", dSG_DescriptionPrefixId)
                .append("dSG_GroupDescription", dSG_GroupDescription)
                .append("dSG_CopyFrom", dSG_CopyFrom)
                .append("dSG_MKProductGroupId", dSG_MKProductGroupId)
                .append("dSG_MKSubproductGroupId", dSG_MKSubproductGroupId)
                .append("dSG_MKProductTypeId", dSG_MKProductTypeId)
                .append("version", version)
                .toString();
    }
}
