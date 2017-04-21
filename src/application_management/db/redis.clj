(ns application-management.db.redis
(:require   [clojure.string :as str]
            [taoensso.carmine :as car :refer (wcar)]))


(def server-conn {:pool {} :spec {:host "localhost"
                               :port     6379
                               :timeout-ms  4000}})

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))


(defn save
  [email-id json]
  (let [ x (wcar* (car/hmset "geekskool-data" email-id (str json)))]))


(defn getAll
  [emailId]
  (let [ x (wcar* (car/hget "geekskool-data" emailId))]
    (prn (str "output" x))
    x))
