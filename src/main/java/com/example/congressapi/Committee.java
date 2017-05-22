package com.example.congressapi;

import org.apache.commons.lang3.text.WordUtils;

public class Committee {

    private String committee_id;
    private String name;
    private String chamber;
    private String parent_committee_id;
    private String phone;
    private String office;
    private String favorite; // this is not from the JSON but needed for favoriting mechanism

    public Committee(String committee_id, String name, String chamber, String parent_committee_id, String phone, String office) {
        this.committee_id = committee_id;
        this.name = name;
        this.chamber = WordUtils.capitalize(chamber);
        this.parent_committee_id = parent_committee_id;
        this.phone = phone;
        this.office = office;
        this.favorite = "false"; // instantiate each new Bill with a favorite set to false, although favorite should be a boolean, we use String here for easier use when filtering in the adapter
    }

    public String getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(String committee_id) {
        this.committee_id = committee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getParent_committee_id() {
        return parent_committee_id;
    }

    public void setParent_committee_id(String parent_committee_id) {
        this.parent_committee_id = parent_committee_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String isFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Committee{" +
                "committee_id='" + committee_id + '\'' +
                ", name='" + name + '\'' +
                ", chamber='" + chamber + '\'' +
                ", parent_committee_id='" + parent_committee_id + '\'' +
                ", phone='" + phone + '\'' +
                ", office='" + office + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}
