CREATE DATABASE IF NOT EXISTS product_db DEFAULT CHARACTER SET utf8 ;
USE product_db;

 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT ;
 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS ;
 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION ;
 SET NAMES utf8 ;
 SET @OLD_TIME_ZONE=@@TIME_ZONE ;
 SET TIME_ZONE='+00:00' ;
 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 ;
 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 ;
 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' ;
 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 ;

-- -----------------------------------------------------
-- TABLE STRUCTURE FOR TABLE categories
-- -----------------------------------------------------

DROP TABLE IF EXISTS categories;
 SET @saved_cs_client     = @@character_set_client ;
 SET character_set_client = utf8 ;
CREATE TABLE categories (
  category_id int(7) NOT NULL AUTO_INCREMENT,
  category_name varchar(100) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (category_id),
  UNIQUE KEY category_name (category_name)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 SET character_set_client = @saved_cs_client ;
 
--
-- DATA FOR TABLE categories
--
LOCK TABLES categories WRITE;
ALTER TABLE categories DISABLE KEYS ;

INSERT INTO categories VALUES (1,'Category 1');
INSERT INTO categories VALUES (2,'Category 2');

ALTER TABLE categories ENABLE KEYS ;
UNLOCK TABLES;

-- -----------------------------------------------------
-- TABLE STRUCTURE FOR TABLE products
-- -----------------------------------------------------

DROP TABLE IF EXISTS products;
 SET @saved_cs_client     = @@character_set_client ;
 SET character_set_client = utf8 ;
CREATE TABLE products (
  product_id int(7) NOT NULL AUTO_INCREMENT,
  product_name varchar(100) CHARACTER SET utf8 NOT NULL,
  product_description varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  product_price double DEFAULT '0',
  category_id int(7) DEFAULT NULL,
  PRIMARY KEY (product_id),
  UNIQUE KEY product_name (product_name),
  KEY FK_products_categories (category_id),
  CONSTRAINT FK_products_categories FOREIGN KEY (category_id) REFERENCES categories (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 SET character_set_client = @saved_cs_client ;
 
--
-- DATA FOR TABLE products
--
LOCK TABLES products WRITE;
ALTER TABLE products DISABLE KEYS ;

INSERT INTO products VALUES (1,'Product 1','Description product 1',200000,1);
INSERT INTO products VALUES (2,'Product 2','Description product 2',250000,1);
INSERT INTO products VALUES (3,'Product 3','Description product 3',280000,2);
INSERT INTO products VALUES (4,'Product 4','Description product 4',180000,2);

ALTER TABLE products ENABLE KEYS ;
UNLOCK TABLES;

-- -----------------------------------------------------
-- TABLE STRUCTURE FOR TABLE attributes
-- -----------------------------------------------------

DROP TABLE IF EXISTS attributes;
 SET @saved_cs_client     = @@character_set_client ;
 SET character_set_client = utf8 ;
CREATE TABLE attributes (
  attribute_id int(7) NOT NULL AUTO_INCREMENT,
  attribute_name varchar(100) CHARACTER SET utf8 NOT NULL,
  category_id int(7) DEFAULT NULL,
  PRIMARY KEY (attribute_id),
  KEY FK_attributes_categories (category_id),
  CONSTRAINT FK_attributes_categories FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 SET character_set_client = @saved_cs_client ;
 
--
-- DATA FOR TABLE attributes
--
LOCK TABLES attributes WRITE;
ALTER TABLE attributes DISABLE KEYS ;

INSERT INTO attributes VALUES (1,'Width',1);
INSERT INTO attributes VALUES (2,'Height',1);
INSERT INTO attributes VALUES (3,'Size',1);

INSERT INTO attributes VALUES (4,'Width',2);
INSERT INTO attributes VALUES (5,'Height',2);
INSERT INTO attributes VALUES (6,'Weight',2);

ALTER TABLE attributes ENABLE KEYS ;
UNLOCK TABLES;

-- -----------------------------------------------------
-- TABLE STRUCTURE FOR TABLE product_attributes
-- -----------------------------------------------------

DROP TABLE IF EXISTS product_attributes;
 SET @saved_cs_client     = @@character_set_client ;
 SET character_set_client = utf8 ;
CREATE TABLE product_attributes (
  product_attribute_id int(7) NOT NULL AUTO_INCREMENT,
  attribute_id int(7) DEFAULT NULL,
  product_id int(7) DEFAULT NULL,
  attribute_value varchar(200) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (product_attribute_id),
  KEY FK_product_attributes_attributes (attribute_id),
  KEY FK_product_attributes_products (product_id),
  CONSTRAINT FK_product_attributes_attributes FOREIGN KEY (attribute_id) REFERENCES attributes (attribute_id),
  CONSTRAINT FK_product_attributes_products FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 SET character_set_client = @saved_cs_client ;
 
--
-- DATA FOR TABLE attributes
--
LOCK TABLES product_attributes WRITE;
ALTER TABLE product_attributes DISABLE KEYS ;

INSERT INTO product_attributes VALUES (1,1,1,'30');
INSERT INTO product_attributes VALUES (2,2,1,'40');
INSERT INTO product_attributes VALUES (3,3,1,'30');

INSERT INTO product_attributes VALUES (4,1,2,'25');
INSERT INTO product_attributes VALUES (5,2,2,'35');
INSERT INTO product_attributes VALUES (6,3,2,'35');

ALTER TABLE product_attributes ENABLE KEYS ;
UNLOCK TABLES;
 