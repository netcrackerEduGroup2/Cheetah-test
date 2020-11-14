package com.ncedu.cheetahtest.developer.entity;

import lombok.Data;

@Data
public class DeveloperDto {
    private int id;
    private String login;
    private String name;
    private String role;
    private String status;

    public DeveloperDto(int id, String login, String name, String role, String status) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public static DeveloperDtoBuilder newBuilder() {
        return new DeveloperDtoBuilder();
    }

    public static final class DeveloperDtoBuilder {

        private int id;
        private String login;
        private String name;
        private String role;
        private String status;

        public DeveloperDtoBuilder setId(int id) {
            return this;
        }

        public DeveloperDtoBuilder setLogin(String login) {
            return this;
        }

        public DeveloperDtoBuilder setName(String name) {
            return this;
        }

        public DeveloperDtoBuilder setRole(String role) {
            return this;
        }

        public DeveloperDtoBuilder setStatus(String status) {
            return this;
        }

        public DeveloperDto build() {
            return new DeveloperDto(id, login, name, role, status);
        }
    }
}
