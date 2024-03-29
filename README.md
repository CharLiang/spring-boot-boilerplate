
# 需求分析

## 需求的来源
 * 直接来源于客户，或者是市场上的需求
 * 借产品经理的手和眼，发现市场上需要，并且把他产品化
 
  
## 把文字化的语言化的的客户需求变成PRD 原型图

这时候可以用Azure 或者各种网上的免费的模型工具搭建一个丑丑的仅仅表达意思的抽象的模型图

开个需求评审会，确定需求的真伪，估时，并且组织开发


### 从原型图的PRD转化成为 表结构 和URL 的约定


心法： 
1. 按着界面构思需要哪些表 + 需要返回哪些URL （符合RESTFUL 的规范）
2. 全部列出来之后，合并同类项，精简一下
3. 看哪些URL 会大规模的重叠以至于可以合并的？
4. 关于数据的聚类，前端和后端都是可以做的

以下就是就是多，但是不复杂的系统设计问题！！！！！





# 系统后端API 设计如下

---
## **1 表格设计**

### **1.1 学校**(school)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|name|varchar|512|||√|学校名称|唯一约束|
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.2 教师**(teacher)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int|11|||√|学校ID||
|name|varchar|512|||√|教师姓名||
|no|varchar|512||''|√|教师编号||
|mobile|varchar|20|||√|手机号码|手机+学校ID唯一约束 同一时间同一个手机号只能有一个教师存活|
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.3 学生**(student)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int|11|||√|学校ID||
|name|varchar|512|||√|学生姓名||
|no|varchar|512|||√|学生编号|学号+学校ID唯一约束|
|mobile|varchar|20||''|√|手机号码||
|sn|bigint||||√|学生全局编号|唯一约束|
|password|varchar|512|||√|学生密码||
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.4 服务**(service)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int|11|||√|学校ID||
|service_type|tinyint||||√|服务代号|服务代号+学校ID唯一约束|
|content|text||||√|服务参数||
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.5 服务_教师**(service_teacher)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|teacher_id|int|11|||√|教师ID||
|service_id|int|11|||√|服务ID|两ID唯一约束|
|update_time|timestamp||||√|更新时间||

### **1.6 服务_学生**(service_student)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|student_id|int|11|||√|学生ID||
|service_id|int|11|||√|服务ID|两ID唯一约束|
|update_time|timestamp||||√|更新时间||

### **1.7 班级**(organization)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int|11|||√|学校ID||
|term_id|int|11|||√|班级学期||
|type|tinyint||||√|班级类型||
|grade|tinyint||||√|班级年级||
|subject|tinyint||0||√|班级学科||
|name|varchar|512|||√|班级名称|全条件唯一约束|
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.8 班级_教师**(organization_teacher)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|organization_id|int|11|||√|班级ID||
|teacher_id|int|11|||√|教师ID||
|subject|tinyint||0||√|学科|全条件唯一约束|
|update_time|timestamp||||√|更新时间||

### **1.9 班级_学生**(organization_student)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|organization_id|int|11|||√|班级ID||
|student_id|int|11|||√|学生ID|全条件唯一约束|
|update_time|timestamp||||√|更新时间||

### **1.10 学期**(term)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int|11|||√|学校ID||
|name|varchar|512|||√|学期名称|学校ID+名称唯一约束|
|start_time|datetime||||√|开始时间||
|end_time|datetime||||√|结束时间||
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.11 管理员表**(admin_user)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|user_id|varchar|512|||√|用户账号|唯一约束|
|remark|varchar|512||''|√|备注||
|update_time|timestamp||||√|更新时间||
|status|tinyint||0||√|状态||

### **1.12 学校学号计数器**(school_counter)
|字段名|字段类型|字段长度|默认值|主键|非空|注释|备注|
|------|--------|--------|------|----|----|----|----|
|id|int|11||√|√|ID||
|school_id|int||||√|学校ID|唯一约束|
|student_counter|int||||√|学生序列号||
|update_time|timestamp||||√|更新时间||


---
## **2 接口设计**
### **2.0 接口约定**
- 标准接口返回信息如下 各接口会依情况进行补充
|参数名|说明|类型|备注|
|------|----|----|----|
|status|请求结果|String|必填 -2:登录超时（需重新登录） -1:错误 0:失败 1:成功|
|message|结果描述|String|必填|
|data|返回值|结构化|必填|


---
### **2.1 管理员相关接口**
#### **2.1.1 新增学校接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/school/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|name|学校名称|String|必填|
|province|省份ID|String|选填|
|city|城市ID|String|选填|
|district|地区ID|String|选填|
|address|详细地址|String|选填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学校ID|int|选填 成功则有|

#### **2.1.2 修改学校接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/school/edit|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学校ID|int|必填|
|name|学校名称|String|必填 唯一约束|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.1.3 变更学校状态接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/school/status|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学校ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.1.4 学校列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/school/list|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学校ID|int|必填|
|name|学校名称|String|必填|
|status|状态|int|必填 1启用 -1停用|

#### **2.1.5 新增学校服务接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/service/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|schoolId|学校ID|int|必填|
|serviceType|服务代码|int|必填|
|content|服务参数|String|必填 JSON|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.1.6 修改学校服务接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/service/edit|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|服务ID|int|必填|
|content|服务参数|String|必填 JSON|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.1.7 修改学校服务状态接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/service/status|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|服务ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.1.8 学校服务列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/service/list|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|schoolId|学校ID|int|必填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|服务ID|int|必填|
|schoolId|学校ID|int|必填|
|serviceType|服务代码|int|必填|
|content|服务参数|String|必填 JSON|
|status|状态|int|必填 1启用 -1停用|

#### **2.1.9 新增学校教务接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/admin/service/manager|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|schoolId|学校ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|选填|
|mobile|手机号码|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|


---
### **2.2 学校教务相关接口**
#### **2.2.1 新增教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|name|教师姓名|String|必填|
|no|教师编号|String|选填|
|mobile|手机号码|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|选填 成功时返回|

#### **2.2.2 批量新增教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/addByExcel|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|file|excel文件|文件|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.3 获取教师批量表格样例**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/excelExample|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（文件）
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.4 修改教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/edit|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|选填|
|**mobile**|手机号码|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.5 修改教师状态接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/status|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.6 教师列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/list|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|
|subjectStrList|学科信息|`List<String>`|必填|
|classStrList|班级信息|`List<String>`|必填|

#### **2.2.7 查询教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/teacher/query|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|organizationId|班级ID|int|选填|
|key|关键字|String|选填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|
|subjectStrList|学科信息|`List<String>`|必填|
|classStrList|班级信息|`List<String>`|必填|

#### **2.2.8 修改学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/student/edit|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|name|学生姓名|String|选填|
|~~no~~|~~学生编号~~|~~String~~|~~选填~~|
|mobile|手机号码|String|选填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.9 修改学生状态接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/student/status|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.10 学生列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/student/list|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|sn|学生全局编号|String|必填|
|password|学生默认密码|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|
|classStrList|班级信息|`List<String>`|必填|

#### **2.2.11 查询学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/student/query|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|organizationId|班级ID|int|选填|
|key|关键字|String|选填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|sn|学生全局编号|String|必填|
|password|学生默认密码|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|
|classStrList|班级信息|`List<String>`|必填|

#### **2.2.12 新增班级接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|选填 成功时返回|

#### **2.2.13 修改班级接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/edit|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.14 修改班级状态接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/status|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.15 班级列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/list|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|typeStr|班级类型|String|必填|
|subjectStr|班级学科|String|必填|
|gradeStr|班级年级|String|必填|
|name|班级名称|String|必填|
|status|状态|int|必填 1启用 -1停用|
|teacherNum|教师人数|int|必填|
|studentNum|学生人数|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|termId|学期ID|int|必填|

#### **2.2.16 查询班级接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/query|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|type|班级类型|int|选填|
|grade|班级年级|int|选填|
|subject|班级学科|int|选填|
|name|班级名称|String|必填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|typeStr|班级类型|String|必填|
|subjectStr|班级学科|String|必填|
|gradeStr|班级年级|String|必填|
|name|班级名称|String|必填|
|status|状态|int|必填 1启用 -1停用|
|teacherNum|教师人数|int|必填|
|studentNum|学生人数|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|termId|学期ID|int|必填|

#### **2.2.17 查询班级ID和名称接口**（配合教师查询）
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/queryForTeacher|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|type|班级类型|int|选填|
|grade|班级年级|int|选填|
|subject|班级学科|int|选填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|name|班级名称|String|必填|

#### **2.2.18 班级详情接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/detail|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|typeStr|班级类型|int|必填|
|name|班级名称|String|必填|
|status|状态|int|必填 1启用 -1停用|
|teacherNum|教师人数|int|必填|
|studentNum|学生人数|int|必填|
|teacherList|教师信息|结构化|必填|
|studentList|学生信息|结构化|必填|
##### **teacherList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|
|subject|学科ID|int|必填|
|subjectStr|学科名称|String|必填|
##### **studentList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|sn|学生全局编号|String|必填|
|mobile|手机号码|String|必填|
|status|状态|int|必填 1启用 -1停用|

#### **2.2.19 班级配置教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/teacher/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|teacherId|教师ID|int|必填|
|subject|学科|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.20 班级删除教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/teacher/del|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|teacherId|教师ID|int|必填|
|organizationId|班级ID|int|必填|
|subject|学科ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.21 班级新增学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/student/add|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|name|学生姓名|String|必填|
|no|学生学号|String|行政班选填自动生成 教学班必填|
|mobile|学生手机号|String|选填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|选填 成功时返回|

#### **2.2.22 班级批量新增学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/student/addByExcel|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|file|excel文件|文件|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.23 获取学生批量表格样例**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/student/excelExample|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**（文件）
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.24 班级删除学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/student/del|POST||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|studentId|学生ID|int|必填|
|organizationId|班级ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|

#### **2.2.25 口语兑换码余量接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/oralNum|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|num|剩余|int|必填|
|max|总数|int|必填|

#### **2.2.26 班级名是否可用**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/manager/organization/checkNameAvailable|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|name|班级名称|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|available|是否可用|boolean|必填|


---
### **2.3 对外服务接口**
接口采用内网访问限制和白名单访问限制相结合的方式进行访问控制

#### **2.3.1 查询学生接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/query/student|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|schoolId|学校ID|int|必填|
|no|学号|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|sn|学生全局编号|String|必填|
|mobile|手机号码|String|必填|
|schoolId|学校ID|int|必填|
|schoolProvince|学校省份|String|必填|
|schoolCity|学校城市|String|必填|
|schoolDistrict|学校地区|String|必填|
|schoolAddress|学校地址|String|必填|
|schoolName|学校名称|String|必填|
|service|学生绑定服务|`List<int>`|必填|
|classList|班级信息|结构化|必填|
##### **class内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|


#### **2.3.2 查询教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/query/teacher|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|mobile|手机号|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|schoolId|学校ID|int|必填|
|schoolProvince|学校省份|String|必填|
|schoolCity|学校城市|String|必填|
|schoolDistrict|学校地区|String|必填|
|schoolAddress|学校地址|String|必填|
|schoolName|学校名称|String|必填|
|service|教师绑定服务|`List<int>`|必填|
|classList|班级信息|结构化|必填|
##### **class内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|


#### **2.3.3 查询班级接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/query/organization|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
|teacherNum|教师人数|int|必填|
|studentNum|学生人数|int|必填|
|teacherList|教师信息|结构化|必填|
|studentList|学生信息|结构化|必填|
|schoolProvince|学校省份|String|必填|
|schoolCity|学校城市|String|必填|
|schoolDistrict|学校地区|String|必填|
|schoolAddress|学校地址|String|必填|
|schoolName|学校名称|String|必填|
##### **teacherList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|关系ID|int|必填|
|teacherId|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|subject|学科|String|必填|
##### **studentList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|关系ID|int|必填|
|student_id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|mobile|手机号码|String|必填|


#### **2.3.4 查询口语教师接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/query/teacherOral|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|mobile|手机号|String|必填|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|schoolId|学校ID|int|必填|
|schoolProvince|学校省份|String|必填|
|schoolCity|学校城市|String|必填|
|schoolDistrict|学校地区|String|必填|
|schoolAddress|学校地址|String|必填|
|schoolName|学校名称|String|必填|
|service|教师绑定服务|`List<int>`|必填|
|classList|班级信息|结构化|必填|
##### **class内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|


#### **2.3.4 批量查询班级接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/query/organization/batch|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|ids|班级ID|`List<int>`|必填|
##### **返回值内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|班级ID|int|必填|
|type|班级类型|int|必填|
|grade|班级年级|int|必填|
|subject|班级学科|int|必填|
|name|班级名称|String|必填|
|termId|学期ID|int|必填|
|status|状态|int|必填 1启用 -1停用|
|teacherNum|教师人数|int|必填|
|studentNum|学生人数|int|必填|
|teacherList|教师信息|结构化|必填|
|studentList|学生信息|结构化|必填|
|schoolProvince|学校省份|String|必填|
|schoolCity|学校城市|String|必填|
|schoolDistrict|学校地区|String|必填|
|schoolAddress|学校地址|String|必填|
|schoolName|学校名称|String|必填|
##### **teacherList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|关系ID|int|必填|
|teacherId|教师ID|int|必填|
|name|教师姓名|String|必填|
|no|教师编号|String|必填|
|mobile|手机号码|String|必填|
|subject|学科|String|必填|
##### **studentList内容**（List）
|参数名|说明|类型|备注|
|------|----|----|----|
|id|关系ID|int|必填|
|student_id|学生ID|int|必填|
|name|学生姓名|String|必填|
|no|学生编号|String|必填|
|mobile|手机号码|String|必填|


---
### **2.4 通用数据接口**
#### **2.4.1 学校列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/common/school|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学校ID|int|必填|
|name|学校名称|String|必填|


#### **2.4.2 学科列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/common/subject|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学科ID|int|必填|
|name|学科名称|String|必填|


#### **2.4.3 年级列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/common/grade|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|年级ID|int|必填|
|name|年级名称|String|必填|


#### **2.4.4 学期列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/common/term|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|学期ID|int|必填|
|name|学期名称|String|必填|


#### **2.4.5 地区列表接口**
##### **接口地址**
|地址|类型|参数类型|
|----|----|--------|
|/api/common/region|GET||
##### **接口参数**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|地区ID|int|选填 默认0 列出所有省份|
##### **返回值内容**
|参数名|说明|类型|备注|
|------|----|----|----|
|id|地区id|int|必填|
|name|地区名称|String|必填|