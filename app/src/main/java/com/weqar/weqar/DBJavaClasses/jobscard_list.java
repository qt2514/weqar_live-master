package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 20/3/18.
 */

public class jobscard_list implements Parcelable{
    String Id;
    String OpeningDate;
    String ClosingDate;
    String Name;
    String Logo;
    String JobFieldId;
    String JobTypeId;
    String JobField;
    String JobType;
    String CompanyInfo;
    String CompanyContact;
    String Description;
    Boolean Applied;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOpeningDate() {
        return OpeningDate;
    }

    public void setOpeningDate(String openingDate) {
        OpeningDate = openingDate;
    }

    public String getClosingDate() {
        return ClosingDate;
    }

    public void setClosingDate(String closingDate) {
        ClosingDate = closingDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getJobFieldId() {
        return JobFieldId;
    }

    public void setJobFieldId(String jobFieldId) {
        JobFieldId = jobFieldId;
    }

    public String getJobTypeId() {
        return JobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        JobTypeId = jobTypeId;
    }

    public String getJobField() {
        return JobField;
    }

    public void setJobField(String jobField) {
        JobField = jobField;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getCompanyInfo() {
        return CompanyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        CompanyInfo = companyInfo;
    }

    public String getCompanyContact() {
        return CompanyContact;
    }

    public void setCompanyContact(String companyContact) {
        CompanyContact = companyContact;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getApplied() {
        return Applied;
    }

    public void setApplied(Boolean applied) {
        Applied = applied;
    }

    public static Creator<jobscard_list> getCREATOR() {
        return CREATOR;
    }

    protected jobscard_list(Parcel in) {
        Id = in.readString();
        OpeningDate = in.readString();
        ClosingDate = in.readString();
        Name = in.readString();
        Logo = in.readString();
        JobFieldId = in.readString();
        JobTypeId = in.readString();
        JobField = in.readString();
        JobType = in.readString();
        CompanyInfo = in.readString();
        CompanyContact = in.readString();
        Description = in.readString();
        byte tmpApplied = in.readByte();
        Applied = tmpApplied == 0 ? null : tmpApplied == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(OpeningDate);
        dest.writeString(ClosingDate);
        dest.writeString(Name);
        dest.writeString(Logo);
        dest.writeString(JobFieldId);
        dest.writeString(JobTypeId);
        dest.writeString(JobField);
        dest.writeString(JobType);
        dest.writeString(CompanyInfo);
        dest.writeString(CompanyContact);
        dest.writeString(Description);
        dest.writeByte((byte) (Applied == null ? 0 : Applied ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<jobscard_list> CREATOR = new Creator<jobscard_list>() {
        @Override
        public jobscard_list createFromParcel(Parcel in) {
            return new jobscard_list(in);
        }

        @Override
        public jobscard_list[] newArray(int size) {
            return new jobscard_list[size];
        }
    };
}
