    package com.example.prm392_group_project.models;

    import com.google.gson.annotations.SerializedName;

    public class EmailRequest {
        @SerializedName("email")
        private String email;

        public EmailRequest(String email) {
            this.email = email;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }