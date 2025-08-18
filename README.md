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

### Database Tables

The project uses the following tables to manage users, menu items, carts, orders, payments, and feedback:

| Table Name        | Description                                                  |
|------------------|--------------------------------------------------------------|
| `users`           | Stores user details including username, password, and role. |
| `menu_items`      | Contains all available menu items with name, description, price, and category. |
| `carts`           | Stores each user’s cart.                                     |
| `cart_items`      | Contains items added to a cart along with quantity and price. |
| `orders`          | Stores all orders placed by users.                           |
| `order_menu_items`| Links orders with the menu items included in each order.     |
| `payments`        | Stores payment details for orders.                           |
| `feedback`        | Stores feedback submitted by users about orders or service.  |


---

## **4. Project Flow**

Here’s the **simplest way to understand the flow**:

```
User → Browse Menu → Add to Cart → Checkout → Place Order → Payment → Delivery → Feedback
```

**Visual Diagram:**


<img width="1584" height="431" alt="flowchart" src="https://github.com/user-attachments/assets/e14de550-0bfd-4e4a-bd3d-1089f1bd5258" />


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
---

User Register Endpoint

>Method: POST

>Endpoint: /users/register

<img width="1742" height="932" alt="Screenshot 2025-08-18 004706" src="https://github.com/user-attachments/assets/5833615c-eeeb-4d47-b44e-f8f76a11027b" />

---
Login Endpoint 

>Method: POST

>Endpoint: /users/login

<img width="1889" height="962" alt="Screenshot 2025-08-18 005604" src="https://github.com/user-attachments/assets/0c438415-03a6-4905-9c0c-ddaa0df8914c" />

---
Get all users

>Method: GET

>Endpoint: /users/all

<img width="1771" height="925" alt="Screenshot 2025-08-18 005728" src="https://github.com/user-attachments/assets/267611a6-cb77-4a95-8fed-d255db9a677a" />

---
Update 

>Method: PUT

>Endpoint: /users/update/{id}

<img width="1760" height="894" alt="Screenshot 2025-08-18 010419" src="https://github.com/user-attachments/assets/a77d2f10-0750-422c-9039-80e0055ebfa1" />

---
Delete 

>Method: DELETE

>Endpoint: /users/delete/{id}

<img width="1816" height="863" alt="Screenshot 2025-08-18 010526" src="https://github.com/user-attachments/assets/ac1645db-c6d0-45cf-99aa-3eac300d82e6" />

---
### Menu Endpoints

| Feature             | Method | URL                 | Description            |
| ------------------- | ------ | ------------------- | ---------------------- |
| Add Menu Item       | POST   | `/menu/add`         | Admin: add menu item   |
| Get All Menu Items  | GET    | `/menu/all`         | Fetch all menu items   |
| Get Menu Item by ID | GET    | `/menu/{id}`        | Fetch single menu item |
| Update Menu Item    | PUT    | `/menu/update/{id}` | Update menu item       |
| Delete Menu Item    | DELETE | `/menu/delete/{id}` | Delete menu item       |

Add Menu Item  

>Method: POST

>Endpoint: http://localhost:8080/menu/add

<img width="1767" height="880" alt="Screenshot 2025-08-18 013245" src="https://github.com/user-attachments/assets/45afd049-0b72-48bb-b5a8-d695a586525f" />

---
Get all users

>Method: GET

>Endpoint: http://localhost:8080/menu/all

<img width="1799" height="966" alt="Screenshot 2025-08-18 012847" src="https://github.com/user-attachments/assets/2acbd4d9-bba5-4541-9311-81f14361961f" />

---

Get menu item by id

>Method: GET

>Endpoint: http://localhost:8080/menu/all

<img width="1829" height="552" alt="Screenshot 2025-08-18 013551" src="https://github.com/user-attachments/assets/ea203e42-1cc7-4334-9455-d0631b1a664a" />

---
Update 

>Method: PUT

>Endpoint: http://localhost:8080/menu/update/29

<img width="1768" height="695" alt="Screenshot 2025-08-18 013844" src="https://github.com/user-attachments/assets/d0e7e153-a7f9-4c5c-8c79-5556fd2eb3e7" />

---
Delete 

>Method: DELETE

>Endpoint: /users/delete/{id}

<img width="1754" height="271" alt="Screenshot 2025-08-18 014039" src="https://github.com/user-attachments/assets/b4224039-5a6f-4586-80ef-7183cc587e43" />

---
### Cart Endpoints

| Feature          | Method | URL                         | Description               |
| ---------------- | ------ | --------------------------- | ------------------------- |
| Add Item to Cart | POST   | `/cart/add`                 | Add item with quantity    |
| Update Cart Item | PUT    | `/cart/update`              | Update quantity or remove |
| Remove Cart Item | DELETE | `/cart/remove/{cartItemId}` | Remove item from cart     |
| Clear Cart       | DELETE | `/cart/clear/{userId}`      | Remove all items          |
| Checkout Cart    | POST   | `/cart/checkout/{userId}`   | Checkout and create order |

Add Cart Item  

>Method: POST

>Endpoint: http://localhost:8080/cart/add

<img width="1813" height="925" alt="Screenshot 2025-08-18 015955" src="https://github.com/user-attachments/assets/f928da94-a795-479d-aed7-6ac067777edf" />

---
Get all Cart Items of a user 

>Method: POST

>Endpoint: http://localhost:8080/cart/4

<img width="1783" height="958" alt="Screenshot 2025-08-18 113128" src="https://github.com/user-attachments/assets/0b38787b-3c48-49df-a1a4-d287cea877b4" />

---

Update menu item by id

>Method: GET

>Endpoint: http://localhost:8080/cart/add

<img width="1609" height="952" alt="Screenshot 2025-08-18 113057" src="https://github.com/user-attachments/assets/faeb642c-6cce-4f96-a20b-29aac6f524da" />

---
Delete 

>Method: DELETE

>Endpoint: http://localhost:8080/cart/item/3

<img width="1214" height="750" alt="Screenshot 2025-08-18 113953" src="https://github.com/user-attachments/assets/1c791d37-5f5f-4987-b008-dc136e5dc68c" />



---
### Order Endpoints

| Feature             | Method | URL                                                | Description            |
| ------------------- | ------ | -------------------------------------------------- | ---------------------- |
| Place Order         | POST   | `/orders`                                          | Place order directly   |
| Update Order Status | PUT    | `/orders/update-status/{orderId}?status=CONFIRMED` | Update order status    |
| Get All Orders      | GET    | `/orders/all`                                      | Admin: view all orders |

---
Add Order Item  

>Method: POST

>Endpoint: http://localhost:8080/orders

<img width="1743" height="866" alt="image" src="https://github.com/user-attachments/assets/b7fa1661-7b75-4628-99dd-e34ed7b8896e" />


---
Get all orders

>Method: GET

>Endpoint: http://localhost:8080/orders

<img width="1776" height="965" alt="Screenshot 2025-08-18 191949" src="https://github.com/user-attachments/assets/de9f6eea-ffcf-4bad-8129-66d16a4c50e7" />

---

Get order item by id

>Method: GET

>Endpoint: http://localhost:8080/orders/15

<img width="1820" height="896" alt="Screenshot 2025-08-18 192304" src="https://github.com/user-attachments/assets/a11cb30b-c373-4784-87d4-3d95909e890d" />


---
Update 

>Method: PUT

>Endpoint: http://localhost:8080/orders/16/status?status=CONFIRMED

<img width="1813" height="898" alt="Screenshot 2025-08-18 191551" src="https://github.com/user-attachments/assets/de2e45f9-5cc6-490a-b8a1-c8f5bd1fddb3" />

---
Delete 

>Method: DELETE

>Endpoint: http://localhost:8080/cart/item/1

<img width="1775" height="901" alt="Screenshot 2025-08-18 201550" src="https://github.com/user-attachments/assets/cec5e410-011e-4049-ae2e-77f3f9da1c97" />

---

### Payment Endpoints

| Feature             | Method | URL                                             | Description                   |
| ------------------- | ------ | ----------------------------------------------- | ----------------------------- |
| Pay & Deliver Order | POST   | `/payments/pay/{orderId}?amount=100&method=UPI` | Simulate payment and delivery |
---

Pay & Deliver Order

>Method: POST

>Endpoint: http://localhost:8080/payments/17?amount=500&method=UPI

<img width="1813" height="972" alt="Screenshot 2025-08-18 195151" src="https://github.com/user-attachments/assets/aaad68e3-ffc8-4b21-a17c-abcc99cdc0ac" />


### Feedback Endpoints

| Feature              | Method | URL                       | Description               |
| -------------------- | ------ | ------------------------- | ------------------------- |
| Add Feedback         | POST   | `/feedback/add`           | Submit feedback           |
| Get All Feedback     | GET    | `/feedback/all`           | Fetch all feedback        |
| Get Feedback by User | GET    | `/feedback/user/{userId}` | Fetch feedback for a user |

---
Add Feedback   

>Method: POST

>Endpoint: http://localhost:8080/feedback/add

<img width="1827" height="775" alt="Screenshot 2025-08-18 200408" src="https://github.com/user-attachments/assets/0dbe5f6c-f25e-4d4b-8177-7cf40d514dd2" />

---
Get all users feedback

>Method: GET

>Endpoint: http://localhost:8080/feedback/all

<img width="1758" height="895" alt="Screenshot 2025-08-18 200951" src="https://github.com/user-attachments/assets/37cbb770-15ba-4092-8695-46ad15748e63" />

---

Get feedback by id

>Method: GET

>Endpoint: http://localhost:8080/feedback/user/4

<img width="1789" height="737" alt="Screenshot 2025-08-18 200559" src="https://github.com/user-attachments/assets/2fbb10ab-4a4b-4ec1-8ec4-3e335e428846" />



---
Update 

>Method: PUT

>Endpoint: [/users/update/{id}](http://localhost:8080/menu/update/29)



---
Delete 

>Method: DELETE

>Endpoint: /users/delete/{id}



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

## **7. How to Run Locally**

1. Clone the repository:
   ```bash
   git clone https://github.com/Shamitha2004/Online-Food-Ordering-System.git
2. Start MySQL and create the database ONLINE_FOOD:
    ```bash
    CREATE DATABASE ONLINE_FOOD;
3. Update `application.properties` with  MySQL credentials.
    ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/ONLINE_FOOD
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
4.  Open the project in IntelliJ/Eclipse.
5.  Run the Spring Boot application:
    ```bash
    mvn spring-boot:run
6.  Use Postman or any API client to test endpoints at:
   ```bash
    http://localhost:8080

```

## **8. Testing**

* **Unit Tests**: Test individual service layers such as OrderServiceTest and UserServiceTest.
* **Integration Tests**: Test complete flows using MockMvc:
    * Menu CRUD → `MenuIntegrationTest`
    * Place Order → `OrdersIntegrationTest`
    * Payment & Delivery → `PaymentIntegrationTest`
    * Feedback Submission → `FeedbackIntegrationTest`

---

## **10. Application Flow**

The Online Food Ordering System follows these main steps from user interaction to order completion and feedback:

1. **User Registration and Login**
   - Users register with basic details (username, password, role).
   - Registered users log in to access the system.

2. **Browsing Menu**
   - Users can view all available menu items.
   - Menu items are categorized (e.g., Starters, Main Course, Desserts).

3. **Adding Items to Cart**
   - Users select desired menu items.
   - Items are added to a virtual cart for checkout.

4. **Placing an Order**
   - Users proceed to checkout.
   - An order is created in the system with:
     - User details
     - Selected menu items
     - Order time
     - Status initially set to `PENDING`

5. **Payment Process**
   - Users choose a payment method (UPI, Credit Card, Cash).
   - Payment is processed and recorded in the `payments` table.
   - Payment status is tracked as `PENDING`, `SUCCESS`, or `FAILED`.

6. **Order Status Updates**
   - After successful payment:
     - Order status changes from `PENDING` → `CONFIRMED`.
     - System simulates delivery with status updates:
       - `CONFIRMED` → `OUT_FOR_DELIVERY` → `DELIVERED`.
   - Users can cancel orders in certain statuses (e.g., `PENDING` or `CONFIRMED`).

7. **Feedback Submission**
   - Users can submit feedback for an order after delivery.
   - Feedback includes:
     - User ID
     - Message or comment
     - Rating (1–5)
   - Feedback is stored and can be retrieved individually or collectively.

8. **Admin/Service Management (Optional)**
   - Admins can manage menu items (CRUD operations).
   - Admins can view all orders, payments, and feedback.

9. **Error Handling**
   - Invalid transitions (e.g., skipping order statuses) are blocked.
   - Duplicate payments or missing data trigger informative error messages.

10. **Testing and Validation**
    - Unit tests validate individual services (OrderService, UserService, etc.).
    - Integration tests validate full flows:
      - Menu management
      - Order placement
      - Payment & delivery
      - Feedback submission
---

