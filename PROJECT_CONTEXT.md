# Project Context

## Project

- Name: `CycleMarket`
- Type: Spring Boot diploma project
- Theme: online store for bicycle goods
- Status: initial scaffold, development starts almost from scratch

## Current Tech State

- Build tool: Maven Wrapper (`mvnw`, `mvnw.cmd`)
- Spring Boot parent version in `pom.xml`: `4.0.5`
- Main build file: `pom.xml`
- Main application class: `src/main/java/com/example/cyclemarket/CycleMarketApplication.java`
- Test scaffold: `src/test/java/com/example/cyclemarket/CycleMarketApplicationTests.java`
- Config file: `src/main/resources/application.properties`

## Working Agreement

- This file stores persistent context for future Codex sessions.
- At the start of a new session, it should be read first to restore project state.
- Important architecture decisions, implemented features, database structure, and next steps should be appended here.

## Decisions

- Persistent project context is stored in this file: `PROJECT_CONTEXT.md`

## Database

- Preliminary tables proposed by the user:
- `users(email, password)`
- `roles(...)`
- `users_roles(id_user -> users, id_role -> roles)`
- `customers(id_user -> users, personal data for customer cabinet)`
- `employees(id_user -> users, employee data)`
- `shops(address)`
- `products(name, price)`
- `stock(id_product -> products, id_shop -> shops, quantity)`
- `orders(id_shop -> shops, id_user -> users)`
- `order_item(order_id -> orders, product_id -> products, quantity)`
- Shopping cart is planned but not finalized yet: expected `cart` and `cart_item`
- Product categories are planned but not finalized yet

## Current Modeling Notes

- The user wants one account table `users` with role separation through `roles` and `users_roles`.
- `customers` and `employees` are planned as `one-to-one` profile extensions of `users`.
- Stock is modeled per product and per shop through a junction table.
- Order flow should support adding products to cart and then placing an order.
- Product categorization direction chosen by the user:
- `categories(id, name, parent_id)`
- `products_categories(product_id, category_id)`

## Agreed Design Directions

- Use category tree with parent-child categories
- Use product-category many-to-many relation through `products_categories`
- Add `product_images(id, product_id, image_url, is_main)`
- Add `manufacturers` or at minimum a product brand field; dedicated `manufacturers` table is currently preferred
- Add `orders.status`
- Add `employees.shop_id` if an employee belongs to a specific shop
- Add `customers.address` for delivery / profile use
- Keep `customers` and `employees` as one-to-one extensions of `users`

## Next Steps

- Finalize cart model: `cart` and `cart_item`
- Finalize required attributes for `users`, `customers`, `employees`, `shops`, `products`, `orders`, and category tables
- Convert the agreed domain model into JPA entities and project structure
