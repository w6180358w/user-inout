-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `ID_`     INTEGER PRIMARY KEY NOT NULL,
    `NAME_`   CHAR(100) NOT NULL,
    `INOUT_` CHAR(100)      NOT NULL DEFAULT '0',
    `AREA_`  CHAR(100)      NOT NULL    DEFAULT NULL,
    `SEX_`  CHAR(100)       NOT NULL  DEFAULT NULL,
    `BIRTH_`  CHAR(100)       NOT NULL    DEFAULT NULL,
    `NUM_TYPOE_`  CHAR(100)          NOT NULL  DEFAULT NULL,
    `NUM_`  CHAR(100)          NOT NULL  DEFAULT NULL,
    `TIME_`  CHAR(100)      NOT NULL   DEFAULT NULL,
    `INOUTAREA_`  CHAR(100)   NOT NULL    DEFAULT NULL,
    `TRAFFIC_`  CHAR(100)       DEFAULT NULL,
    `TARGET_`  CHAR(100)   NOT NULL   DEFAULT NULL,
    `IMPORTTIME_`  CHAR(100)    NOT NULL    DEFAULT NULL
);