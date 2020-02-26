DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `uid`         INT(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `name`        VARCHAR(32) NOT NULL COMMENT '用户名',
    `birthday`    DATETIME COMMENT '出生日期',
    `create_date` TIMESTAMP   NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_date` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 用户表';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `id`          INT(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `name`        VARCHAR(32) NOT NULL COMMENT '部门名称',
    `superior`    INT(11) COMMENT '上级id',
    `level`       INT(11)     NOT NULL COMMENT '层级',
    `create_date` TIMESTAMP   NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_date` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 部门表';

DROP TABLE IF EXISTS `user_dept`;
CREATE TABLE `user_dept`
(
    `id`          INT(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `uid`         INT(11)   NOT NULL COMMENT '用户id',
    `dept_id`     INT(11)   NOT NULL COMMENT '部门id',
    `create_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 关联表';

