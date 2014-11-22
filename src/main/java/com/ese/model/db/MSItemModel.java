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
@Table(name = "item_master")
@Proxy(lazy=false)
public class MSItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ItemId")
    private String itemId;

    @Column(name = "ItemName")
    private String itemName;

    @Column(name = "ItemGroupId")
    private String itemGroupId;

    @Column(name = "ItemType")
    private int itemType;

    @Column(name = "PackagingGroupId")
    private String packagingGroupId;

    @Column(name = "NetWeight")
    private float netWeight;

    @Column(name = "DSG_MaxStock")
    private float dSG_MaxStock;

    @Column(name = "DSG_MinStock")
    private float dSG_MinStock;

    @Column(name = "DSG_SafetyStock")
    private float dSG_SafetyStock;

    @Column(name = "DSG_InternalItemId")
    private String dSG_InternalItemId;

    @Column(name = "DSG_Size")
    private String dSGSize;

    @Column(name = "DSG_RimWidth")
    private float dSG_RimWidth;

    @Column(name = "DSG_MaxLoad")
    private String dSG_MaxLoad;

    @Column(name = "DSG_MaxInflation")
    private float dSG_MaxInflation;

    @Column(name = "DSG_MaxSpeed")
    private float dSG_MaxSpeed;

    @Column(name = "DSG_E_Mark")
    private String dSG_E_Mark;

    @Column(name = "DSG_ETRTO")
    private String dSG_ETRTO;

    @Column(name = "DSG_LoadIndex")
    private String dSG_LoadIndex;

    @Column(name = "DSG_SpeedIndex")
    private String dSG_SpeedIndex;

    @Column(name = "DSG_RecommendedRim")
    private float dSG_RecommendedRim;

    @Column(name = "DSG_TreadDept")
    private float dSG_TreadDept;

    @Column(name = "DSG_AlternativeRim")
    private String dSG_AlternativeRim;

    @Column(name = "DSG_OfMold")
    private float dSG_OfMold;

    @Column(name = "DSG_Valve")
    private String dSG_Valve;

    @Column(name = "DSG_ID")
    private String dSG_ID;

    @Column(name = "DSG_QtyPerPallet")
    private float dSG_QtyPerPallet;

    @Column(name = "DSG_CurPerShif", nullable=false, columnDefinition="int default 0")
    private Integer dSG_CurPerShif;

    @Column(name = "DSG_ColorId1")
    private String dSG_ColorId1;

    @Column(name = "DSG_ColorSeparate1")
    private String dSG_ColorSeparate1;

    @Column(name = "DSG_ColorId2")
    private String dSG_ColorId2;

    @Column(name = "DSG_ColorSeparate2")
    private String dSG_ColorSeparate2;

    @Column(name = "DSG_ColorId3")
    private String dSG_ColorId3;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DSG_CreateDate")
    private Date dSG_CreateDate;

    @Column(name = "DSG_Originator")
    private String dSG_Originator;

    @Column(name = "DSG_ImageDir")
    private String dSG_ImageDir;

    @Column(name = "DSGProductGroupID")
    private String dSGProductGroupID;

    @Column(name = "DSGSubGroupID")
    private String dSGSubGroupID;

    @Column(name = "DSGPatternID")
    private String dSGPatternID;

    @Column(name = "DSGBandID")
    private String dSGBandID;

    @Column(name = "DSG_Model")
    private String dSG_Model;

    @Column(name = "DSGTyre_Types", nullable=false, columnDefinition="int default 0")
    private Integer dSGTyre_Types;

    @Column(name = "Tyre_Type")
    private String tyre_Type;

    @Column(name = "DSGRimSize")
    private String dSGRimSize;

    @Column(name = "DSGPLYRating")
    private String dSGPLYRating;

    @Column(name = "DSGThaiItemDescription")
    private String dSGThaiItemDescription;

    @Column(name = "DSG_EU", nullable=false, columnDefinition="int default 0")
    private Integer dSG_EU;

    @Column(name = "DSG_ItemLabelId")
    private String dSG_ItemLabelId;

    @Column(name = "DSG_DescriptionTextId")
    private String dSG_DescriptionTextId;

    @Column(name = "DSG_GradeProductId")
    private String dSG_GradeProductId;

    @Column(name = "DSG_FeatureProductId")
    private String dSG_FeatureProductId;

    @Column(name = "DSG_DescriptionPrefixId")
    private String dSG_DescriptionPrefixId;

    @Column(name = "DSG_GroupDescription")
    private String dSG_GroupDescription;

    @Column(name = "DSG_CopyFrom")
    private String dSG_CopyFrom;

    @Column(name = "DSG_MKProductGroupId")
    private String dSG_MKProductGroupId;

    @Column(name = "DSG_MKSubproductGroupId")
    private String dSG_MKSubproductGroupId;

    @Column(name = "DSG_MKProductTypeId")
    private String dSG_MKProductTypeId;

    @Column(name = "version", nullable=false, columnDefinition="int default 0")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("itemName", itemName)
                .append("itemGrpupId", itemGroupId)
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
