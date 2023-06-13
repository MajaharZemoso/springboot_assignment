-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springboot_assignment` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema spring_assignment
-- -----------------------------------------------------
USE `springboot_assignment` ;
-- -----------------------------------------------------
-- Create the Event table
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot_assignment`.`events` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(45) NOT NULL,
  `event_venue` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE = InnoDB;

-- ----------------------------------------------------------
-- Create the User table
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot_assignment`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `gender` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------------
-- Create the Organizer table
-- -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot_assignment`.`organizers` (
  `organizer_id` INT NOT NULL AUTO_INCREMENT,
  `organizer_name` VARCHAR(45) NOT NULL,
  `organizer_location` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`organizer_id`)
) ENGINE = InnoDB;

-- ---------------------------------------------------------
-- Create the many-to-many relationship table between Event and User
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot_assignment`.`event_user` (
  `event_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_event_user_event_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_event_user_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_user_event`
    FOREIGN KEY (`event_id`)
    REFERENCES `springboot_assignment`.`events` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `springboot_assignment`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- ----------------------------------------------------------------------
-- Create the many-to-many relationship table between Event and Organizer
-- -----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot_assignment`.`event_organizer` (
  `event_id` INT NOT NULL,
  `organizer_id` INT NOT NULL,
  INDEX `fk_event_organizer_event_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_event_organizer_organizer_idx` (`organizer_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_organizer_event`
    FOREIGN KEY (`event_id`)
    REFERENCES `springboot_assignment`.`events` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_organizer_organizer`
    FOREIGN KEY (`organizer_id`)
    REFERENCES `springboot_assignment`.`organizers` (`organizer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)engine=InnoDB;