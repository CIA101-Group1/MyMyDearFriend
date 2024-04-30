# 賣~買Dear Friends

## 啟動步驟

環境需求: java17

將`mmdf-backend`、`mmdf-frontend`
專案內src/main/resource中的application-prd.yml複製一份取名叫做application-dev.yml。

調整application-dev.yml中的參數為自己系統環境。

執行maven clean install，可增加`-Dmaven.test.skip=true`參數略過進行單元測試，即可取得產出可執行jar檔於個服務目錄下target/內。

`mmdf-backend`啟動可以至[MmdfBackendApplication.java](mmdf-backend/src/main/java/com/tibame/group1/backend/MmdfBackendApplication.java)執行main方法，並且將profile指定為dev啟動即可。

`mmdf-frontend`啟動可以至[MmdfFrontendApplication.java](mmdf-frontend/src/main/java/com/tibame/group1/frontend/MmdfFrontendApplication.java)執行main方法，並且將profile指定為dev啟動即可。

如果要變更運行環境，於對應服務專案下要執行指令後加入-P指定運行yml檔。

## 專案架構
![mmdf-structure](mmdf-structure.jpg)

* docker -> 專案DockerFile包image腳本
* mmdf-common -> 共用服務
* mmdf-db -> 資料庫服務
* mmdf-backend -> 後台服務，服務預設port為8001
* mmdf-frontend -> 前台服務，服務預設port為8002

## 目錄結構

* annotation -> 客製化標籤
* config -> 環境起始設定資源
* controller -> api服務進入口
* dto -> api進出資料模型
* entity -> 資料庫表格資料模型
* enums -> 客製化類別(enum)
* exception -> 客製化錯誤型別
* filter -> 客製化過濾器
* handler -> 客製化全域攔截器
* interceptor -> 客製化服務入口攔截器
* jobs -> 排程服務實體
* listener -> 客製化監聽器
* repository -> 資料庫傳輸介面
* service -> 邏輯服務介面
    * impl -> 邏輯服務實作
* utils -> 常用共用方法

