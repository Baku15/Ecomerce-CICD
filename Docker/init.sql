-- Script para crear las tablas en la base de datos
-- Tabla para las autorizaciones

CREATE TABLE Us_authorization (authorization_id SERIAL PRIMARY KEY,
                                                               permission VARCHAR(255) NOT NULL);

-- Tabla para las personas

CREATE TABLE Us_people (id_people SERIAL PRIMARY KEY,
                                                 people_name VARCHAR(250) NOT NULL,
                                                                          people_lastname VARCHAR(500) NOT NULL,
                                                                                                       carnet VARCHAR(250) NOT NULL UNIQUE,
                                                                                                                                    phone VARCHAR(250) NOT NULL);

-- Tabla para los roles

CREATE TABLE Us_Roles (role_id SERIAL PRIMARY KEY,
                                              role_name VARCHAR(255) NOT NULL);

-- Tabla para la relación entre roles y autorizaciones

CREATE TABLE roles_permissions (role_id INT, authorization_id INT, PRIMARY KEY (role_id,
                                                                                authorization_id),
                                FOREIGN KEY (role_id) REFERENCES Us_Roles(role_id),
                                FOREIGN KEY (authorization_id) REFERENCES Us_authorization(authorization_id));

-- Tabla para los usuarios

CREATE TABLE Us_user (id_user SERIAL PRIMARY KEY,
                                             email VARCHAR(250) NOT NULL,
                                                                password VARCHAR(100) NOT NULL,
                                                                                      username VARCHAR(50) NOT NULL,
                                                                                                           person_id INT, account_non_expired BOOLEAN, account_non_locked BOOLEAN, credentials_non_expired BOOLEAN, roles BOOLEAN,
                      FOREIGN KEY (person_id) REFERENCES Us_people(id_people));

-- Tabla para la relación entre usuarios y roles

CREATE TABLE User_Roles (user_id INT, role_id INT, PRIMARY KEY (user_id,
                                                                role_id),
                         FOREIGN KEY (user_id) REFERENCES Us_user(id_user),
                         FOREIGN KEY (role_id) REFERENCES Us_Roles(role_id));

-- Tabla para la Descuentos

CREATE TABLE Discounts (discount_id SERIAL PRIMARY KEY,
                                                   percentage DECIMAL(5,2) NOT NULL);

-- Tabla para las marcas de productos

CREATE TABLE Pr_Brand (brand_id SERIAL PRIMARY KEY,
                                               brand_name VARCHAR(100) NOT NULL,
                                                                       description VARCHAR(250) NOT NULL,
                                                                                                url_img VARCHAR(500) NOT NULL);

-- Tabla para los productos

CREATE TABLE Pr_Product (product_id SERIAL PRIMARY KEY,
                                                   name VARCHAR(100) NOT NULL,
                                                                     description VARCHAR(500) NOT NULL,
                                                                                              url_img VARCHAR(500) NOT NULL,
                                                                                                                   stock INT NOT NULL,
                                                                                                                             price DECIMAL(10,2) NOT NULL,
                                                                                                                                                 brand_id INT,
                         FOREIGN KEY (brand_id) REFERENCES Pr_Brand(brand_id));

-- Tabla para la relación entre Descuento y Productos

CREATE TABLE Product_Discount (product_id INT NOT NULL,
                                              discount_id INT NOT NULL,
                                                              PRIMARY KEY (product_id,
                                                                           discount_id),
                               FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id),
                               FOREIGN KEY (discount_id) REFERENCES Discounts(discount_id));

-- Tabla para las ventas

CREATE TABLE V_sale (id_sale SERIAL PRIMARY KEY,
                                            quantity INT NOT NULL,
                                                         sale_date DATE DEFAULT CURRENT_DATE,
                                                                                price DECIMAL(10,2) NOT NULL,
                                                                                                    product_id INT NOT NULL,
                                                                                                                   user_id INT NOT NULL,
                     FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id),
                     FOREIGN KEY (user_id) REFERENCES Us_user(id_user));

-- Tabla para las categorías

CREATE TABLE Pr_Category (id_category SERIAL PRIMARY KEY,
                                                     name_category VARCHAR(100) NOT NULL,
                                                                                description_category VARCHAR(100) NOT NULL);

-- Tabla de relación muchos a muchos entre productos y categorías

CREATE TABLE Product_Category (product_id INT, category_id INT, PRIMARY KEY (product_id,
                                                                             category_id),
                               FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id),
                               FOREIGN KEY (category_id) REFERENCES Pr_Category(id_category));

-- Tabla para las direcciones de usuarios

CREATE TABLE Us_address (address_id SERIAL PRIMARY KEY,
                                                   address VARCHAR(255) NOT NULL,
                                                                        city VARCHAR(255) NOT NULL,
                                                                                          postal_code VARCHAR(20) NOT NULL,
                                                                                                                  people_id INT NOT NULL,
                         FOREIGN KEY (people_id) REFERENCES Us_people(id_people));

-- Tabla para los comentarios de personas a los productos

CREATE TABLE Us_Comment (comment_id SERIAL PRIMARY KEY,
                                                   comment VARCHAR(500) NOT NULL,
                                                                        comment_date DATE NOT NULL,
                                                                                          rating INT NOT NULL,
                                                                                                     user_id INT NOT NULL,
                                                                                                                 product_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES Us_user(id_user),
                         FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id));

-- Tabla para los usuarios y productos

CREATE TABLE User_Product (user_id INT, product_id INT, PRIMARY KEY (user_id,
                                                                     product_id),
                           FOREIGN KEY (user_id) REFERENCES Us_user(id_user),
                           FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id));

-- Tabla para el registro de carrito de compras

CREATE TABLE Purchase_Record (id SERIAL PRIMARY KEY,
                                                purchase_date DATE DEFAULT CURRENT_DATE,
                                                                           user_id INT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES Us_user(id_user));

-- Tabla para el carrito de compras

CREATE TABLE Shopping_Cart (id SERIAL PRIMARY KEY,
                                              quantity INT NOT NULL,
                                                           product_id INT NOT NULL,
                                                                          purchase_record_id INT NOT NULL,
                            FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id),
                            FOREIGN KEY (purchase_record_id) REFERENCES Purchase_Record(id));

-- Tabla para los detalles del pedido

CREATE TABLE Order_Detail (idOrder SERIAL PRIMARY KEY,
                                                  start_date DATE NOT NULL,
                                                                  delivery_date DATE, user_id INT NOT NULL,
                                                                                                  shipping_status VARCHAR(50),
                           FOREIGN KEY (user_id) REFERENCES Us_user(id_user));

-- Tabla para los elementos del pedido

CREATE TABLE Order_Item (ordei_id SERIAL PRIMARY KEY,
                                                 order_id INT NOT NULL,
                                                              product_id INT NOT NULL,
                                                                             quantity INT NOT NULL,
                                                                                          price NUMERIC(10, 2) NOT NULL,
                                                                                                               product_name VARCHAR(255) NOT NULL,
                         FOREIGN KEY (order_id) REFERENCES Order_Detail(idOrder),
                         FOREIGN KEY (product_id) REFERENCES Pr_Product(product_id));

-- Tabla para los elementos de errores

CREATE TABLE logs (id SERIAL PRIMARY KEY,
                                     timestamp TIMESTAMP NOT NULL,
                                                         level VARCHAR(10) NOT NULL,
                                                                           logger VARCHAR(255) NOT NULL,
                                                                                               message TEXT NOT NULL,
                   exception TEXT);