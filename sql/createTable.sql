/*

数据库建表 + 插入最简单的测试数据

*/



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for campaign
-- ----------------------------
DROP TABLE IF EXISTS `campaign`;
CREATE TABLE `campaign`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动名称',
  `createTime` bigint(20) NOT NULL COMMENT '活动创建时间',
  `startTime` bigint(20) NOT NULL COMMENT '活动开始时间',
  `endTime` bigint(20) NOT NULL COMMENT '活动结束时间',
  `status` int(11) NOT NULL COMMENT '活动状态 0 进行中 1 已结束 2 未开始',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of campaign
-- ----------------------------
INSERT INTO `campaign` VALUES (1, '首次充值', 1554194798000, 1554652800000, 1557676800000, 0);
INSERT INTO `campaign` VALUES (2, '非首次充值', 1554194798000, 1554652800000, 1557676800000, 0);

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for welfare
-- ----------------------------
DROP TABLE IF EXISTS `welfare`;
CREATE TABLE `welfare`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '福利名称',
  `status` int(11) NOT NULL COMMENT '福利状态 0 可使用 1 不可使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of welfare
-- ----------------------------
INSERT INTO `welfare` VALUES (1, '智云体验金', 0);
INSERT INTO `welfare` VALUES (2, '网易严选购物券', 0);

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for welfare_get
-- ----------------------------
DROP TABLE IF EXISTS `welfare_get`;
CREATE TABLE `welfare_get`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `orderId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `getStatus` int(11) NOT NULL COMMENT '领取状态 0 已领取 1 未领取',
  `campaignId` int(11) NOT NULL COMMENT '活动id',
  `welfareId` int(11) NOT NULL COMMENT '所选福利id',
  `userId` int(11) NOT NULL COMMENT '领取福利用户id',
  `money` int(11) NOT NULL COMMENT '涉及金额',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_campaign_user`(`orderId`, `campaignId`, `userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;



