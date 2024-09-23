package com.hei.patrimoinerest.model;

import java.time.LocalDateTime;

public record Patrimoine(String possesseur, LocalDateTime dernierModification) {
}
