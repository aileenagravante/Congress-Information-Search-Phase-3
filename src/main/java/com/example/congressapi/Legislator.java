package com.example.congressapi;

import org.apache.commons.lang3.text.WordUtils;
import java.util.Calendar;

import java.util.Date;

public class Legislator {

    private static final String IMG_URL = "https://theunitedstates.io/images/congress/225x275/";
    private static final String FACEBOOK_URL = "https://www.facebook.com/";
    private static final String TWITTER_URL = "https://www.twitter.com/";

    private String bioguide_id;
    private String title;
    private String last_name;
    private String first_name;
    private String party;
    private String state; // abbreviation of state
    private String state_name; // full name of state
    private String district;
    private String oc_email;
    private String chamber;
    private String phone;
    private String term_start;
    private String term_end;
    private String office;
    private String fax;
    private String birthday;
    private String facebook_id;
    private String twitter_id;
    private String website;
    private String favorite; // this is not from the JSON but needed for favoriting mechanism

    public Legislator(String bioguide_id, String title, String last_name, String first_name, String party, String state, String state_name, String district, String oc_email, String chamber, String phone, String term_start, String term_end, String office, String fax, String birthday, String facebook_id, String twitter_id, String website) {
        this.bioguide_id = bioguide_id;
        this.title = title;
        this.last_name = last_name;
        this.first_name = first_name;
        this.party = party;
        this.state = state;
        this.state_name = state_name;
        this.district = district;
        this.oc_email = oc_email;
        this.chamber = WordUtils.capitalize(chamber);
        this.phone = phone;
        this.term_start = term_start;
        this.term_end = term_end;
        this.office = office;
        this.fax = fax;
        this.birthday = birthday;
        this.facebook_id = facebook_id;
        this.twitter_id = twitter_id;
        this.website = website;
        this.favorite = "false"; // instantiate each new Bill with a favorite set to false, although favorite should be a boolean, we use String here for easier use when filtering in the adapter
    }

    public String getBioguide_id() {
        return bioguide_id;
    }

    public void setBioguide_id(String bioguide_id) {
        this.bioguide_id = bioguide_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getName() {
        return this.last_name + ", " + this.first_name;
    }

    public String getNameFull() {
        return this.title + ". " + this.last_name + ", " + this.first_name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPartyFull() {
        String party = "";
        if(this.party.equalsIgnoreCase("R")) {
            party = "Republican";
        }
        else if(this.party.equalsIgnoreCase("D")) {
            party = "Democrat";
        }
        else {
            party = "Independent";
        }
        return party;
    }

    public String getPartyFormatted() {
        String party = "";
        if(this.party.equalsIgnoreCase("R")) {
            party = "(R)";
        }
        else if(this.party.equalsIgnoreCase("D")) {
            party = "(D)";
        }
        else {
            party = "(I)";
        }
        return party;
    }

    public String getDetailsForListView() {
        return (getPartyFormatted() + " " + this.state_name  + " - District " + this.district);
    }

    public String getImageURL() {
        return IMG_URL + this.bioguide_id + ".jpg";
    }

    public String getFacebookUrl() {
        return FACEBOOK_URL + this.facebook_id;
    }

    public String getTwitterUrl() {
        return TWITTER_URL + this.twitter_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOc_email() {
        return oc_email;
    }

    public void setOc_email(String oc_email) {
        this.oc_email = oc_email;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTerm_start() {
        return term_start;
    }

    public void setTerm_start(String term_start) {
        this.term_start = term_start;
    }

    public String getTerm_end() {
        return term_end;
    }

    public void setTerm_end(String term_end) {
        this.term_end = term_end;
    }

    // term_end, term_start, birthday are in the form YYYY-MM-DD
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

    // Ideally we should convert the date Strings into the Date type to do this math, but here
    //      we will use brute force by parsing the date Strings into doubles and calculating
    //      the progress manually.
    public int calculateTermProgress() {
        String termStart = this.term_start;
        String termEnd = this.term_end;

        // Calendar returns an int so we multiply by 1.0
        double yearNow = Calendar.getInstance().get(Calendar.YEAR) * 1.0;

        // Calendar.MONTH is 0 indexed so we add 1.0 to get the actual calendar month
        double monthNow = (Calendar.getInstance().get(Calendar.MONTH)+1.0) * 1.0;

        double yearStart = Double.parseDouble(termStart.substring(0,4));
        double monthStart = Double.parseDouble(termStart.substring(5,7));

        double yearEnd = Double.parseDouble(termEnd.substring(0,4));
        double monthEnd = Double.parseDouble(termEnd.substring(5,7));

        // First, calculate how many months from the start of the term to now
        double startToNow = ((yearNow-yearStart) * 12.0) + (monthNow-monthStart);

        // Then, calculate how many months from start to the end of the term
        double startToEnd = ((yearEnd-yearStart) * 12.0) + (monthEnd-monthStart);

        // That should result in a fraction, so multiple by 100 to get a percent
        //      and take the ceiling to round.
        return (int) Math.ceil((startToNow/startToEnd) * 100.0);
    }

    public String getTermProgress() {
        return "" + calculateTermProgress() + "%";
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String isFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Legislator{" +
                "bioguide_id='" + bioguide_id + '\'' +
                ", title='" + title + '\'' +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", party='" + party + '\'' +
                ", state='" + state + '\'' +
                ", state_name='" + state_name + '\'' +
                ", district='" + district + '\'' +
                ", oc_email='" + oc_email + '\'' +
                ", chamber='" + chamber + '\'' +
                ", phone='" + phone + '\'' +
                ", term_start='" + term_start + '\'' +
                ", term_end='" + term_end + '\'' +
                ", office='" + office + '\'' +
                ", fax='" + fax + '\'' +
                ", birthday='" + birthday + '\'' +
                ", facebook_id='" + facebook_id + '\'' +
                ", twitter_id='" + twitter_id + '\'' +
                ", website='" + website + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}
