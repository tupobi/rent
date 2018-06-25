# 租房系统

## 数据库设计

- 角色表: user(两个角色：租客和房东)

  | 字段名       | 字段类型 | 字段长度 | 是否主键 | 不允许空 | 字段描述               |
  | ------------ | -------- | -------- | -------- | -------- | ---------------------- |
  | userId       | int      | 4        | true     | true     | 唯一ID，自增           |
  | userName     | varchar  | 36       | false    | true     | 账号                   |
  | userPassword | varchar  | 36       | false    | true     | 密码                   |
  | userType     | int      | 4        | false    | true     | 用户类别(0租客，1房东) |
  | avatarUrl    | varchar  | 1024     | false    | true     | 投降URL                |

- 啊

## 记一次天坑

debug时如果配置混淆了，千万记得在proguard中将bean对象keep了！！！