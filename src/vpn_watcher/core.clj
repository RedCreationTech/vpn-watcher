(ns vpn-watcher.core
  (:gen-class)
  (:require
   [etaoin.api :as web]
   [taoensso.timbre :as timbre :refer [debug log spy warn]]
   [taoensso.timbre.appenders.core :as appenders]
   [taoensso.timbre.appenders.3rd-party.rolling :as rolling]
   [etaoin.keys :as k])
  (:import
   (java.net InetSocketAddress Socket SocketTimeoutException)
   (java.util TimeZone)))

(defn log-config
  "设置timbre的基础日志规则:
  系统中有sl4j的日志和timbre的日志, 如果在`project.clj`中引入依赖:
  [com.fzakaria/slf4j-timbre \"0.3.21\"], 则sl4j的日志会自动输出到timbre.
  如果不引入此依赖, 日志会有两套系统, sl4j的日志会输出到
  - `sql-device-sensor`
  - `info-device-sensor`
  - `error-device-sensor`

  1. 函数参数`level`统一设置了log级别 :trace :debug :info :warn :error
     指定系统的最低级别的日志输出, appender级别可以比它更高
  2. 统一设置了时间输出格式, 使用本地时区 + 时间(时间精确到毫秒)
  3. 文件输出统一到`log/trace-rolling.log`, 每天滚动
  3. appender分为
     - `console` : 控制台输出, 级别设置为`:info`
     - `spit`    : 文件输出, 每天输出一个, 级别为`:trace`
       `spit` 中使用去除了异常的颜色文本输出, 更加可读"
  [level]
  (timbre/merge-config!
   {:level level
    :ns-blacklist   []
    :timestamp-opts {:pattern "yy-MM-dd HH:mm:ss:SSS",
                     :locale :jvm-default,
                     :timezone (TimeZone/getDefault)}
    :appenders {:println (merge (appenders/println-appender )
                                {:min-level :info})
                :spit    (merge (rolling/rolling-appender
                                 {:path    "log/trace-rolling.log"
                                  :pattern :daily})
                                {:min-level :trace
                                 :output-fn (partial timbre/default-output-fn {:stacktrace-fonts {}})})}}))

(defn service-available?
  "以建立tcp连接的方式, 测试tcp服务是否可用.
   如果在指定的超时时间内成功建立连接, 则认为`host`和`port`上的服务可用
   默认超时时间是200ms, 也可以指定以毫秒为单位的超时时间"
  ([host port timeout]
   (let [socket-address (InetSocketAddress. ^String host ^Integer port)
         socket         (Socket.)]
     (boolean
      (try
        (.connect socket socket-address timeout)
        true
        (catch SocketTimeoutException _ false)
        (catch Exception _ false)
        (finally (.close socket))))))
  ([host port]
   (service-available? host port 200)))




(defn find-switch [driver]
  (let [item-col (web/query
                  driver
                  {:tag :tr
                   :class "device-item"
                   :fn/has-string "iPad-pro-2"}) ]
    (web/child driver item-col {:tag :a})))



(defn restart-router []
  (doto @(def b (web/chrome {:headless true}))
    (web/go "http://router.asus.comg/Main_Login.asp")
    (web/wait-visible {:id "login_username"})
    (web/fill {:id "login_username"} "redcreation")
    (web/fill {:name "login_passwd"}  "hczt1234")
    (web/fill {:name "login_passwd"} k/enter)
    (web/wait-visible {:tag :span :fn/has-text "重新启动"})
    (web/click {:tag :span :fn/has-text "重新启动"})
    (web/wait-has-alert)
    (web/accept-alert)))


(defn connected-to-vpn-router? []
  (service-available? "router.asus.com" 80))

(defn google-reachable? []
  (service-available? "google.com" 80))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (and (connected-to-vpn-router?)
           (not (google-reachable?)))
    (restart-router)
    (warn "no need to restart router now....")))
