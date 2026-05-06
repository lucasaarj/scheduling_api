package br.com.lucasaraujo.schedulingapi.dto;

import br.com.lucasaraujo.schedulingapi.model.UserRole;

public record RegisterRequest(String name, String email, String password, UserRole role) {
}
