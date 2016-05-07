package za.ac.cut.buddy;

/**
 * Created by 215110048 on 2/23/2016.
 */
public class Buddy {
    private String name, surname, gender, picture, location, phone, email, group, status;
    final String DELIM = ",";

    public Buddy() {
    }

    public Buddy(String name, String surname, String gender, String picture, String location,
                 String phone, String email, String group, String status) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.picture = picture;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.group = group;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.name + DELIM + this.surname + DELIM + gender + DELIM + picture + DELIM +
                location + DELIM + phone + DELIM + email + DELIM + group + DELIM + status;
    }
}
