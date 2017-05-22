package com.example.congressapi;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Bill {

    // For embedded fields in the JSON response, how to find them
    //      in the JSON is added a comment by the field
    private String bill_id;
    private String title; // try short_title in JSON, if null, use official_title in JSON
    private String introduced_on;
    private String introduced_on_formatted; // this is not from the JSON but introduced_on in MMM DD, YYYY format
    private String bill_type;
    private String sponsor_title; // title in sponsor of JSON
    private String sponsor_last_name; // last_name in sponsor of JSON
    private String sponsor_first_name; // first_name in sponsor of JSON
    private String sponsor_full_name; // this is not from the JSON but the following concatenation: "sponsor_title. sponsor_last_name, sponsor_first_name"
    private String chamber;
    private String active; // active in history of JSON (this comes from the JSON as a boolean, but we store it as a string)
    private String url; // congress in urls of JSON
    private String version_name; // version_name in last_version of JSON
    private String pdf; // pdf in urls in last_version of JSON
    private String favorite; // this is not from the JSON but needed for favoriting mechanism

    public Bill(String bill_id, String title, String introduced_on, String bill_type, String sponsor_title, String sponsor_last_name, String sponsor_first_name, String chamber, String active, String url, String version_name, String pdf) {
        this.bill_id = StringUtils.upperCase(bill_id);
        this.title = title;
        this.introduced_on = introduced_on;
        this.introduced_on_formatted = formatDate(this.introduced_on);
        this.bill_type = StringUtils.upperCase(bill_type);
        this.sponsor_title = sponsor_title;
        this.sponsor_last_name = sponsor_last_name;
        this.sponsor_first_name = sponsor_first_name;
        this.sponsor_full_name = (this.sponsor_title + ". " + this.sponsor_last_name + ", " + this.sponsor_first_name);
        this.chamber = WordUtils.capitalize(chamber);
        this.active = active;
        this.url = url;
        this.version_name = version_name;
        this.pdf = pdf;
        this.favorite = "false"; // instantiate each new Bill with a favorite set to false, although favorite should be a boolean, we use String here for easier use when filtering in the adapter
    }

    // introduced_on is in the form YYYY-MM-DD
    // This method will return it in the date format we want: MMM DD, YYYY
    public String formatDate(String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8);

        if(month.equals("01")) {
            month = "Jan";
        }
        else if(month.equals("02")) {
            month = "Feb";
        }
        else if(month.equals("03")) {
            month = "Mar";
        }
        else if(month.equals("04")) {
            month = "Apr";
        }
        else if(month.equals("05")) {
            month = "May";
        }
        else if(month.equals("06")) {
            month = "Jun";
        }
        else if(month.equals("07")) {
            month = "Jul";
        }
        else if(month.equals("08")) {
            month = "Aug";
        }
        else if(month.equals("09")) {
            month = "Sep";
        }
        else if(month.equals("10")) {
            month = "Oct";
        }
        else if(month.equals("11")) {
            month = "Nov";
        }
        else {
            month = "Dec";
        }
        return (month + " " + day + "," + " " + year);
    }

    // The introduced_on date is a String in the JSON, we parse the date as an int when we
    //      want to sort the bills by introduced_on
    public int parseDate() {
        String date = this.getIntroduced_on();

        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8);

        date = year+month+day;

        return Integer.parseInt(date);
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduced_on() {
        return introduced_on;
    }

    public void setIntroduced_on(String introduced_on) {
        this.introduced_on = introduced_on;
    }

    public String getIntroduced_on_formatted() {
        return introduced_on_formatted;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getSponsor_title() {
        return sponsor_title;
    }

    public void setSponsor_title(String sponsor_title) {
        this.sponsor_title = sponsor_title;
    }

    public String getSponsor_last_name() {
        return sponsor_last_name;
    }

    public void setSponsor_last_name(String sponsor_last_name) {
        this.sponsor_last_name = sponsor_last_name;
    }

    public String getSponsor_first_name() {
        return sponsor_first_name;
    }

    public void setSponsor_first_name(String sponsor_first_name) {
        this.sponsor_first_name = sponsor_first_name;
    }

    public String getSponsor_full_name() {
        return sponsor_full_name;
    }

    public void setSponsor_full_name(String sponsor_full_name) {
        this.sponsor_full_name = sponsor_full_name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String isFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "bill_id='" + bill_id + '\'' +
                ", title='" + title + '\'' +
                ", introduced_on='" + introduced_on + '\'' +
                ", introduced_on_formatted='" + introduced_on_formatted + '\'' +
                ", bill_type='" + bill_type + '\'' +
                ", sponsor_title='" + sponsor_title + '\'' +
                ", sponsor_last_name='" + sponsor_last_name + '\'' +
                ", sponsor_first_name='" + sponsor_first_name + '\'' +
                ", sponsor_full_name='" + sponsor_full_name + '\'' +
                ", chamber='" + chamber + '\'' +
                ", active='" + active + '\'' +
                ", url='" + url + '\'' +
                ", version_name='" + version_name + '\'' +
                ", pdf='" + pdf + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
