package com.hei.patrimoinerest.model;

import java.time.LocalDateTime;

public record Patrimoine(String id, String possesseur, LocalDateTime dernierModification) {
}
