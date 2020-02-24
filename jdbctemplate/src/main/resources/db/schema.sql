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
