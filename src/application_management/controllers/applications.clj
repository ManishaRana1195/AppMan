(ns application-management.controllers.applications
(:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [taoensso.carmine :as car :refer (wcar)]
            [application-management.db.redis :as redis]
            ))


(def server-conn {:pool {} :spec {:host "localhost"
                               :port     6379
                               :timeout-ms  4000}})

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(def response "<h1>Record Saved</h1>" )

(defn create
  [application]
  (redis/save (get application "emailId") application) 
  response)

(defn getApplications
  [body]
  (prn "reached in controller")
  (redis/getAll (get body "emailId")))
