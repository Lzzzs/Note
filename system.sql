/*
 Navicat Premium Data Transfer

 Source Server         : study_mysql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : pc

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 03/06/2023 17:49:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect_num
-- ----------------------------
DROP TABLE IF EXISTS `collect_num`;
CREATE TABLE `collect_num` (
  `user_id` varchar(255) NOT NULL,
  `note_id` int NOT NULL,
  KEY `userCollect` (`user_id`),
  CONSTRAINT `userCollect` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for comment_like_num
-- ----------------------------
DROP TABLE IF EXISTS `comment_like_num`;
CREATE TABLE `comment_like_num` (
  `comment_id` int NOT NULL,
  `like_num` int NOT NULL DEFAULT '0',
  KEY `comment_like_num` (`comment_id`),
  CONSTRAINT `comment_like_num` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for comment_like_relation
-- ----------------------------
DROP TABLE IF EXISTS `comment_like_relation`;
CREATE TABLE `comment_like_relation` (
  `user_id` varchar(255) NOT NULL,
  `comment_id` int NOT NULL,
  KEY `userId-comment` (`user_id`),
  KEY `coment-id` (`comment_id`),
  CONSTRAINT `coment-id` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `userId-comment` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论表的id',
  `user_id` varchar(255) NOT NULL COMMENT '发布评论的用户id',
  `comment_content` varchar(255) NOT NULL COMMENT '评论内容',
  `created_time` datetime NOT NULL COMMENT '发布评论的时间',
  `parent_id` int NOT NULL COMMENT '父评论的id，没有父评论为0',
  `reply_id` int NOT NULL COMMENT '回复某评论的id，没有回复为0',
  `note_id` int NOT NULL COMMENT '评论关联的笔记id',
  PRIMARY KEY (`id`),
  KEY `comment-userId` (`user_id`),
  KEY `comment-noteId` (`note_id`),
  CONSTRAINT `comment-noteId` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`),
  CONSTRAINT `comment-userId` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `user_id` varchar(255) NOT NULL,
  `grade` int unsigned NOT NULL DEFAULT '1' COMMENT '等级',
  `experience` int NOT NULL DEFAULT '0' COMMENT '经验条',
  KEY `grade` (`user_id`),
  CONSTRAINT `grade` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for like_num
-- ----------------------------
DROP TABLE IF EXISTS `like_num`;
CREATE TABLE `like_num` (
  `user_id` varchar(255) NOT NULL,
  `note_id` int NOT NULL,
  KEY `user` (`user_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for notes
-- ----------------------------
DROP TABLE IF EXISTS `notes`;
CREATE TABLE `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` longtext NOT NULL,
  `tag` varchar(50) NOT NULL,
  `note_img` varchar(255) NOT NULL,
  `created_time` datetime NOT NULL,
  `like_num` int unsigned DEFAULT '0',
  `collect_num` int unsigned DEFAULT '0',
  `is_group` int DEFAULT '0',
  `isDelete` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for organize
-- ----------------------------
DROP TABLE IF EXISTS `organize`;
CREATE TABLE `organize` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UserIDorganize` (`user_id`),
  CONSTRAINT `UserIDorganize` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for organize_note
-- ----------------------------
DROP TABLE IF EXISTS `organize_note`;
CREATE TABLE `organize_note` (
  `organize_id` int NOT NULL,
  `note_id` int NOT NULL,
  KEY `groupNoteId` (`note_id`),
  KEY `organizeIdNote` (`organize_id`),
  CONSTRAINT `groupNoteId` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`),
  CONSTRAINT `organizeIdNote` FOREIGN KEY (`organize_id`) REFERENCES `organize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for organize_user
-- ----------------------------
DROP TABLE IF EXISTS `organize_user`;
CREATE TABLE `organize_user` (
  `organize_id` int NOT NULL,
  `user_id` varchar(255) NOT NULL,
  KEY `group_user_id` (`user_id`),
  KEY `organizeUserId` (`organize_id`),
  CONSTRAINT `group_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `organizeUserId` FOREIGN KEY (`organize_id`) REFERENCES `organize` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for point
-- ----------------------------
DROP TABLE IF EXISTS `point`;
CREATE TABLE `point` (
  `user_id` varchar(255) NOT NULL,
  `point_num` int NOT NULL DEFAULT '0',
  KEY `point` (`user_id`),
  CONSTRAINT `point` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_note
-- ----------------------------
DROP TABLE IF EXISTS `user_note`;
CREATE TABLE `user_note` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `note_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `noteId` (`note_id`),
  CONSTRAINT `noteId` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(50) NOT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_time` datetime NOT NULL,
  `avatar_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
