# 🍔 Online Food Ordering System

**Online Food Ordering System** is a **user-friendly web application** where users can browse food items, add them to their cart, place orders, make payments, and provide feedback. Admins can manage menu items, view users, and track orders.

Built with: **Spring Boot | MySQL | Java | REST APIs | JUnit**

---

## **1. Project Overview**

### Features for Users

* Register and login
* View all menu items
* Add, update, remove items in cart
* Checkout and place orders
* Make payments
* Track order status
* Give feedback

### Features for Admins

* Add, update, delete menu items
* View all users and orders
* Update order status

---

## **2. Database Setup**

1. Start MySQL.
2. Create a database:

```sql
CREATE DATABASE ONLINE_FOOD;
```

4. Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ONLINE_FOOD
spring.datasource.username=root
spring.datasource.password=sharadhi
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

---

## **3. Entities**

| Entity        | Description                         |
| ------------- | ----------------------------------- |
| **User**      | Stores username, password, and role |
| **MenuItems** | Name, price, description, category  |
| **Cart**      | User's current items                |
| **CartItem**  | Each item in the cart with quantity |
| **Orders**    | Tracks orders and order status      |
| **Payment**   | Tracks payment details and status   |
| **Feedback**  | Stores user feedback and rating     |

---

## **4. Project Flow**

Here’s the **simplest way to understand the flow**:

```
User → Browse Menu → Add to Cart → Checkout → Place Order → Payment → Delivery → Feedback
```

**Visual Diagram:**

```text
 +--------+       +--------+       +-------+       +--------+       +---------+
 |  User  | ----> |  Cart  | ----> | Order | ----> | Payment| ----> |Delivery |
 +--------+       +--------+       +-------+       +--------+       +---------+
       |                                                          |
       |---------------------------------------------------------->|
                                  Feedback


![System Flow](images/flowchart.png)
```

---

## **5. API Endpoints**

> Base URL: `http://localhost:8080`

### User Endpoints

| Feature       | Method | URL                  | Description           |
| ------------- | ------ | -------------------- | --------------------- |
| Register      | POST   | `/users/register`    | Add a new user        |
| Login         | POST   | `/users/login`       | Login user            |
| Get All Users | GET    | `/users/all`         | Admin: list all users |
| Update User   | PUT    | `/users/update/{id}` | Update user info      |
| Delete User   | DELETE | `/users/delete/{id}` | Delete user           |

### Menu Endpoints

| Feature             | Method | URL                 | Description            |
| ------------------- | ------ | ------------------- | ---------------------- |
| Add Menu Item       | POST   | `/menu/add`         | Admin: add menu item   |
| Get All Menu Items  | GET    | `/menu/all`         | Fetch all menu items   |
| Get Menu Item by ID | GET    | `/menu/{id}`        | Fetch single menu item |
| Update Menu Item    | PUT    | `/menu/update/{id}` | Update menu item       |
| Delete Menu Item    | DELETE | `/menu/delete/{id}` | Delete menu item       |

### Cart Endpoints

| Feature          | Method | URL                         | Description               |
| ---------------- | ------ | --------------------------- | ------------------------- |
| Add Item to Cart | POST   | `/cart/add`                 | Add item with quantity    |
| Update Cart Item | PUT    | `/cart/update`              | Update quantity or remove |
| Remove Cart Item | DELETE | `/cart/remove/{cartItemId}` | Remove item from cart     |
| Clear Cart       | DELETE | `/cart/clear/{userId}`      | Remove all items          |
| Checkout Cart    | POST   | `/cart/checkout/{userId}`   | Checkout and create order |

### Order Endpoints

| Feature             | Method | URL                                                | Description            |
| ------------------- | ------ | -------------------------------------------------- | ---------------------- |
| Place Order         | POST   | `/orders`                                          | Place order directly   |
| Update Order Status | PUT    | `/orders/update-status/{orderId}?status=CONFIRMED` | Update order status    |
| Get All Orders      | GET    | `/orders/all`                                      | Admin: view all orders |

### Payment Endpoints

| Feature             | Method | URL                                             | Description                   |
| ------------------- | ------ | ----------------------------------------------- | ----------------------------- |
| Pay & Deliver Order | POST   | `/payments/pay/{orderId}?amount=100&method=UPI` | Simulate payment and delivery |

### Feedback Endpoints

| Feature              | Method | URL                       | Description               |
| -------------------- | ------ | ------------------------- | ------------------------- |
| Add Feedback         | POST   | `/feedback/add`           | Submit feedback           |
| Get All Feedback     | GET    | `/feedback/all`           | Fetch all feedback        |
| Get Feedback by User | GET    | `/feedback/user/{userId}` | Fetch feedback for a user |

---

## **6. Sample JSON Requests**

### Register User

```json
{
  "username": "john_doe",
  "password": "12345",
  "role": "USER"
}
```

### Add Menu Item (Admin)

```json
{
  "name": "Pizza",
  "price": 500,
  "description": "Delicious cheese pizza",
  "category": "Fast Food"
}
```

### Add Item to Cart

```json
{
  "userId": 1,
  "menuItemId": 1,
  "quantity": 2
}
```

### Place Order

```json
{
  "userId": 1,
  "menuItemIds": [1, 2]
}
```

### Add Feedback

```json
{
  "userId": 1,
  "message": "The pizza was amazing!",
  "rating": 5
}
```

---

## **7. How to Run Locally**

1. Clone the repository.
2. Start MySQL and create `ONLINE_FOOD` database.
3. Update `application.properties` with your MySQL credentials.
4. Open the project in IntelliJ/Eclipse.
5. Run the Spring Boot application.
6. Use Postman to test endpoints with `http://localhost:8080`.

---

## **8. Testing**

* **Unit Tests**: Test services individually (`OrderServiceTest`, `UserServiceTest` etc.)
* **Integration Tests**: Test full flows using MockMvc:

  * Menu CRUD → `MenuIntegrationTest`
  * Place Order → `OrdersIntegrationTest`
  * Payment & Delivery → `PaymentIntegrationTest`

---

## **9.Flow**

1. User registers → logs in.
2. Browses menu → adds items to cart.
3. Checkout → order is created.
4. Payment → order status changes → delivery simulated.
5. User gives feedback.

