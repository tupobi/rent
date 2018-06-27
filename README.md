# 租房系统

## 数据库设计

- 角色表: user(两个角色：租客和房东)

  | 字段名       | 字段类型 | 字段长度 | 是否主键 | 不允许空 | 字段描述               |
  | ------------ | -------- | -------- | -------- | -------- | ---------------------- |
  | userId       | int      | 4        | true     | true     | 唯一ID，自增           |
  | userName     | varchar  | 32       | false    | true     | 账号                   |
  | userPassword | varchar  | 32       | false    | true     | 密码                   |
  | userType     | int      | 4        | false    | true     | 用户类别(0租客，1房东) |
  | avatarUrl    | varchar  | 1024     | false    | true     | 投降URL                |

- 房源表：house(房东的userName作为该表主键)

  | 字段名           | 字段类型 | 字段长度 | 是否主键 | 不允许空 | 字段描述                                |
  | ---------------- | -------- | -------- | -------- | -------- | --------------------------------------- |
  | userName         | varchar  | 32       | true     | true     | 房东userName                            |
  | houseName        | varchar  | 32       | false    | true     | 房源名称                                |
  | houseCity        | varchar  | 16       | false    | true     | 房源所在城市                            |
  | houseArea        | varchar  | 16       | false    | true     | 房源所在城市的区域                      |
  | houseTyle        | varchar  | 16       | false    | true     | 房源户型（一室，两室等）固定选择Spinner |
  | houseTitle       | varchar  | 64       | false    | true     | 房源标题，用于推广自己的房源            |
  | monthPrice       | int      | 4        | false    | true     | 月租多少钱                              |
  | houseDescription | text     | 1024     | false    | true     | 房源详情描述                            |
  | houseLocation    | varchar  | 128      | false    | true     | 房源详细地址                            |
  | ownerPhone       | varchar  | 16       | false    | fasle    | 房东联系方式                            |
  | pic1Url          | varchar  | 1024     | false    | true     | 第1张房源图片URL                        |
  | pic2Url          | varchar  | 1024     | false    | false    | 第2张房源图片URL                        |
  | pic3Url          | varchar  | 1024     | false    | false    | 第3张房源图片URL                        |
  | pic4Url          | varchar  | 1024     | false    | false    | 第4张房源图片URL                        |
  | pic5Url          | varchar  | 1024     | false    | false    | 第5张房源图片URL                        |
  | pic6Url          | varchar  | 1024     | false    | false    | 第6张房源图片URL                        |

## 记一次天坑

- debug时如果配置混淆了，千万记得在proguard中将bean对象keep了！！！如果入坑，表现的特点就是与服务器交互完全没毛病，但是得到的数据在反射机制的作用下找不大相应的setget方法，所以是拿不到数据的。
- 鲁班压缩如果异步的话，还没压缩完，没得到数据，而结果在主线程内，那么主线程可能先执行了，数据为空。