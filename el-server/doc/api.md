# API

## Overview

本文有以下几种API

* 提供给easyload_ui的API
    * [Easy Load API](#easyload_ui-api)
        * [Activity](#activity)  
        * [Attender](#attender)
        * [Server Folder](#server-folder)
  
## easyload_ui API

### Activity

####  获取最新Round的AttenderId

##### Request

URL `GET /api/activity/{elActivityId}/round/latest/attender`


##### Response

Example: `GET /api/activity/2c9c473167cac2450167cb73acc30000/round/latest/attender`

Status: 200 OK

Body:

```json
[
  "f2cc8a446e5c416baafcb8e42f2ac701"
]
```

### Attender
 
#### 获取所有Attender信息

##### Request

URL:  `GET /api/attender`

##### Response

Example: `GET /api/attender`

Status:200 OK

Body:

```json
[
    {
        "id": "f2cc8a446e5c416baafcb8e42f2ac701",
        "domain": "DOC",
        "owner": null,
        "createTime": "2018-12-20T05:00:59.000+0000",
        "updateTime": "2018-12-20T05:01:13.000+0000"
    },
    {
        "id": "f2cc8a446e5c416baafcb8e42f2ac702",
        "domain": "HRC",
        "owner": null,
        "createTime": "2018-12-20T05:00:59.000+0000",
        "updateTime": "2018-12-20T05:01:13.000+0000"
    },
    {
        "id": "f2cc8a446e5c416baafcb8e42f2ac703",
        "domain": "SHP",
        "owner": null,
        "createTime": "2018-12-20T05:00:59.000+0000",
        "updateTime": "2018-12-20T05:01:13.000+0000"
    }
]
```

### Server Folder

#### 根据attenderId获取Server Folder

##### Request

URL: `GET /api/server-folder/search`

parameter:

| Name       | Type   | Description         |
| ---------- | ------ | ------------------- |
| attenderId | string | 参与者的attender Id |

##### Response

Example: `GET /api/server-folder/search?attenderId=f2cc8a446e5c416baafcb8e42f2ac701
`

Stataus: 200 OK

Body:

```json
{
  "folderId": "e02e241bd498403d9768b4a35c8a7e4d",
  "path": "\\\\wuja12-w10\\DOC",
  "elScripts": [
    {
      "scriptId": "571542f10e3d4646a80b34fed3d3b6c5",
      "name": "xxx.jar",
      "lastExecute": null,
      "type": null
    }
  ]
}
```

#### 根据serverFolderId重新扫描Script

##### request


