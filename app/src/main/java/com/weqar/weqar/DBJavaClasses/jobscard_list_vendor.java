package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 22/3/18.
 */

public class jobscard_list_vendor implements Parcelable{
    String Id;
    String Name;
    String Logo;
    String JobField;
    String Internship;
    String CompanyContact;
    String Description;
    String ClosingDate;
    String JobType;
    String CompanyInfo;
    String JobFieldId;
    String  JobTypeId;

    protected jobscard_list_vendor(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Logo = in.readString();
        JobField = in.readString();
        Internship = in.readString();
        CompanyContact = in.readString();
        Description = in.readString();
        ClosingDate = in.readString();
        JobType = in.readString();
        CompanyInfo = in.readString();
        JobFieldId = in.readString();
        JobTypeId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(Logo);
        dest.writeString(JobField);
        dest.writeString(Internship);
        dest.writeString(CompanyContact);
        dest.writeString(Description);
        dest.writeString(ClosingDate);
        dest.writeString(JobType);
        dest.writeString(CompanyInfo);
        dest.writeString(JobFieldId);
        dest.writeString(JobTypeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<jobscard_list_vendor> CREATOR = new Creator<jobscard_list_vendor>() {
        @Override
        public jobscard_list_vendor createFromParcel(Parcel in) {
            return new jobscard_list_vendor(in);
        }

        @Override
        public jobscard_list_vendor[] newArray(int size) {
            return new jobscard_list_vendor[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getJobField() {
        return JobField;
    }

    public void setJobField(String jobField) {
        JobField = jobField;
    }

    public String getInternship() {
        return Internship;
    }

    public void setInternship(String internship) {
        Internship = internship;
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

    public String getClosingDate() {
        return ClosingDate;
    }

    public void setClosingDate(String closingDate) {
        ClosingDate = closingDate;
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
}
