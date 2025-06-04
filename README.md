# Library Manager 图书管理系统

[![Java Version](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/)
[![MySQL Version](https://img.shields.io/badge/MySQL-8.0-orange)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/luminous-ChenXi/ASimpleBookManager)](https://github.com/luminous-ChenXi/ASimpleBookManager/releases)


[![Star History Chart](https://api.star-history.com/svg?repos=luminous-ChenXi/ASimpleBookManager&type=Date)](https://star-history.com/#luminous-ChenXi/ASimpleBookManager&Date)

## 项目概述
大学课上开小差时自己写的一些东西，也就那些学生的基本工作(为了交作业、为了玩)😊；
项目开源免费，随便拉取~

## 主要功能
- 添加新图书信息
- 删除图书记录
- 修改图书详情
- 查询图书列表
- 数据库持久化存储

## 技术栈
- **核心语言**: Java 17
- **数据库**: MySQL 8.0
- **数据库驱动**: MySQL Connector/J 8.0.11

## 快速开始
### 环境要求
1. JDK 17+
2. MySQL 8.0+

### 安装步骤
```bash
git clone https://github.com/luminous-ChenXi/ASimpleBookManager.git
cd ASimpleBookManager
mvn clean install
```

### 数据库配置
1. 创建数据库：
```sql
CREATE DATABASE library;
```
2. 导入表结构（参见src/main/resources/schema.sql）

## 使用方法
运行主类：
```java
src/main/java/Main.java
```

## 许可证
[MIT License](LICENSE)
