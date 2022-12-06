package com.insomnia_studio.w4156pj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class Token {
  private UUID clientId;

  public Token(UUID clientId) {
    this.clientId = clientId;
  }
}
