package com.example.prm392_group_project.models;

public class AccountResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String avatar;
    private Boolean isBanned;
    private AccountRoleEnum role;
    private AccountStatusEnum status;
    private AccountGenderEnum gender;

    // Getter và Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Boolean isBanned() { return isBanned; }
    public void setBanned(Boolean banned) { isBanned = banned; }

    public AccountRoleEnum getRole() { return role; }
    public void setRole(AccountRoleEnum role) { this.role = role; }

    public AccountStatusEnum getStatus() { return status; }
    public void setStatus(AccountStatusEnum status) { this.status = status; }

    public AccountGenderEnum getGender() { return gender; }
    public void setGender(AccountGenderEnum gender) { this.gender = gender; }
}

