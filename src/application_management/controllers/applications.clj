(ns application-management.controllers.applications
(:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [taoensso.carmine :as car :refer (wcar)]
            ;; [application-management.db.redis :as redis]
            ))


(def server-conn {:pool {} :spec {:host "localhost"
                               :port     6379
                               :timeout-ms  4000}})

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))


(defn create
  [application]
  (prn (str "output"  application)
  "<h2>hi we got result</h2>"))

(defn getApplications
  []
  (let [ x (wcar* (car/set "192:168:1:115" "application 1")
                (car/set "192:168:1:122" "application 2")
                (car/set "192:168:1:123" "application 3")
                (car/set "192:168:1:124" "application 4"))]
    (prn x))
  "<h2>hi we got result</h2>")
