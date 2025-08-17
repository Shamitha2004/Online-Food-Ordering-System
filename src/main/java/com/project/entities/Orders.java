package com.project.entities;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MenuItems> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItems> menuItems) {
		this.menuItems = menuItems;
	}


	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}


	@Override
	public String toString() {
		return "Orders [id=" + id + ", user=" + user + ", menuItems=" + menuItems + ", status=" + status
				+ ", orderTime=" + orderTime + "]";
	}
	public OrderStatus getStatus() {
	    return status;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "order_menu_items",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private List<MenuItems> menuItems;

    public void setStatus(OrderStatus status) {
		this.status = status;
	}
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    // Getter and Setter
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


	@Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;// e.g., PENDING, CONFIRMED, DELIVERED
    private LocalDateTime orderTime;
    
    

    public Orders() {
    	
    }

 
}
