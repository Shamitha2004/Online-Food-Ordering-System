package com.project.dtos;

import java.util.List;

public class OrderRequestDTO {
    private Long userId;
    private List<Long> menuItemIds;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<Long> getMenuItemIds() { return menuItemIds; }
    public void setMenuItemIds(List<Long> menuItemIds) { this.menuItemIds = menuItemIds; }
}
